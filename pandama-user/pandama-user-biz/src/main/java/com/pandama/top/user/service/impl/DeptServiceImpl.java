package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.user.pojo.dto.DeptCreateDTO;
import com.pandama.top.user.pojo.dto.DeptSearchDTO;
import com.pandama.top.user.pojo.dto.DeptUpdateDTO;
import com.pandama.top.user.pojo.vo.DeptDetailResultVO;
import com.pandama.top.user.pojo.vo.DeptSearchResultVO;
import com.pandama.top.user.pojo.vo.DeptTreeVO;
import com.pandama.top.user.constant.Constants;
import com.pandama.top.user.entity.SysDept;
import com.pandama.top.user.entity.SysDeptUser;
import com.pandama.top.user.mapper.DeptMapper;
import com.pandama.top.user.service.DeptService;
import com.pandama.top.user.service.DeptUserService;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.core.utils.TreeUtils;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 部门信息实现类
 * @author: 白剑民
 * @dateTime: 2022/10/21 16:16
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeptServiceImpl extends ServiceImpl<DeptMapper, SysDept> implements DeptService {

    private final DeptMapper departmentMapper;

    private final DeptUserService departmentUserService;

    private final RedisUtils redisUtils;


    @Override
    public String getNo() {
        // 部门编码生成前缀
        String prefix = Constants.Prefix.DEPT;
        // 部门可用编码对应的redis中的key
        String codeKey = String.format(Constants.Redis.BASE_KEY, prefix);
        // 部门编码锁对应的redis中的key
        String lockKey = String.format(Constants.Redis.LOCK_KEY, prefix);
        String val = redisUtils.get(codeKey).orElse("").toString();
        // 如果没有，就生成缓存
        if (val.length() == 0) {
            // 加锁
            boolean lock = redisUtils.tryReentrantLock(lockKey, "", 200, 5000);
            try {
                if (lock) {
                    // 双重判断
                    val = redisUtils.get(codeKey).orElse("").toString();
                    if (val.length() == 0) {
                        // 部门编码，倒序取最大的编码
                        LambdaQueryWrapper<SysDept> group = new LambdaQueryWrapper<SysDept>()
                                .eq(SysDept::getIsDelete, false)
                                .orderByDesc(SysDept::getDeptCode);
                        List<SysDept> childList = departmentMapper.selectList(group);
                        // 如果存在部门编码
                        if (childList.size() > 0) {
                            // 取最大编码
                            SysDept firstChild = departmentMapper.selectList(group).get(0);
                            String newCode = firstChild.getDeptCode();
                            // 截取部门编码中表示数值的字符
                            String code = newCode.substring(prefix.length());
                            if (StringUtils.isNumeric(code)) {
                                // 部门编码数值递增1，并按3位字符补0
                                val = prefix + String.format("%03d", Integer.parseInt(code) + 1);
                            }
                        } else {
                            // 不存在就直接拼接001作为第一位部门编码
                            val = prefix + "001";
                        }
                        // 生成的部门编码存入redis
                        redisUtils.set(codeKey, val);
                    }
                }
            } catch (Exception e) {
                log.error("部门编码缓存生成出错,错误信息: {}", e.getMessage());
                throw new CommonException("编码缓存异常，请稍后再试。");
            } finally {
                // 如果加锁成功则进行解锁
                if (lock) {
                    redisUtils.tryReentrantUnlock(lockKey, "");
                }
            }
        }
        return val;
    }

    @Override
    public void create(DeptCreateDTO dto) {
        SysDept parent = dto.getParentId() == 0 ? new SysDept() : departmentMapper.selectById(dto.getParentId());
        if (parent == null) {
            throw new CommonException("父节点不存在");
        }
        // 部门编码生成前缀
        String prefix = Constants.Prefix.DEPT;
        // 部门可用编码对应的redis中的key
        String codeKey = String.format(Constants.Redis.BASE_KEY, prefix);
        // 部门编码锁对应的redis中的key
        String lockKey = String.format(Constants.Redis.LOCK_KEY, prefix);
        boolean lock = redisUtils.tryReentrantLock(lockKey, "", 400, 5000);
        try {
            if (lock) {
                AtomicInteger index = new AtomicInteger(Integer.parseInt(getNo().substring(prefix.length())));
                // 将传参字段转换赋值成部门实体属性
                SysDept sysDept = BeanConvertUtils.convert(dto, SysDept::new, (s, t) -> {
                    t.setId(IdWorker.getId());
                    t.setDeptCode(prefix + String.format("%03d", index.getAndIncrement()));
                    t.setParentId(s.getParentId());
                    t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                    t.setLevel(t.getIds().split(",").length);
                }).orElseThrow(() -> new CommonException("部门创建出错"));
                departmentMapper.insert(sysDept);
                // 入库成功后，将缓存值递增并刷新缓存
                redisUtils.set(codeKey, prefix + String.format("%03d", index.get()));
            } else {
                throw new RedisException("部门锁获取失败");
            }
        } catch (Exception e) {
            log.error("部门创建失败，错误信息: {}", e.getMessage());
            throw new CommonException("部门创建出错");
        } finally {
            // 如果加锁成功则进行解锁
            if (lock) {
                redisUtils.tryReentrantUnlock(lockKey, "");
            }
        }
    }

    @Override
    public DeptDetailResultVO detail(Long deptId) {
        SysDept dept = departmentMapper.selectById(deptId);
        return BeanConvertUtils.convert(dept, DeptDetailResultVO::new).orElse(new DeptDetailResultVO());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeptUpdateDTO dto) {
        // 根据部门id获取部门实体信息
        SysDept dept = departmentMapper.selectById(dto.getDeptId());
        // 判断是否为迁移部门操作（变更父级部门id）
        if (StringUtils.isEmpty(dept.getIds()) || !Objects.equals(dept.getParentId(), dto.getParentId())) {
            SysDept parent = dto.getParentId() == 0 ? new SysDept() : departmentMapper.selectById(dto.getParentId());
            if (parent == null) {
                throw new CommonException("父节点不存在");
            }
            // 更新部门信息
            dept = BeanConvertUtils.convert(dto, SysDept::new, (s, t) -> {
                t.setId(s.getDeptId());
                t.setParentId(dto.getParentId());
                t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                t.setLevel(t.getIds().split(",").length);
            }).orElseThrow(() -> new CommonException("部门更新出错"));
            // 获取部门下的子部门列表
            List<SysDept> childDeptList =
                    departmentMapper.getDeptListByParentIds(Collections.singletonList(dto.getDeptId()), false);
            // 更新子部门ids
            for (SysDept child : childDeptList) {
                List<String> split = Arrays.asList(child.getIds() == null ? new String[]{""} : child.getIds().split(","));
                List<String> strings = split.subList(split.indexOf(String.valueOf(dept.getId())), split.size());
                strings.set(0, dept.getIds());
                child.setIds(strings.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")));
                child.setLevel(child.getIds().split(",").length);
            }
            childDeptList.add(dept);
            // 批量更新部门信息
            updateBatchById(childDeptList);
        } else {
            // 将传参字段转换赋值成部门实体属性
            dept = BeanConvertUtils.convert(dto, SysDept::new, (s, t) -> {
                t.setId(s.getDeptId());
            }).orElseThrow(() -> new CommonException("部门更新出错"));
            // 更新单个部门信息
            departmentMapper.updateById(dept);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> deptIds) {
        // 获取目标逻辑删除的部门
        List<SysDept> deptList = departmentMapper.getDeptListByParentIds(deptIds, true);
        List<Long> deptIdList = deptList.stream().map(SysDept::getId).collect(Collectors.toList());
        // 判断部门是否存在关联用户
        if (CollectionUtils.isNotEmpty(departmentUserService.getUserIdsByDeptIds(deptIdList))) {
            throw new CommonException("部门下存在关联用户信息，不允许删除");
        }
        departmentMapper.deleteBatchIds(deptIdList);
    }

    @Override
    public List<DeptSearchResultVO> list(DeptSearchDTO dto) {
        List<SysDept> list = departmentMapper.list(dto);
        return (List<DeptSearchResultVO>) BeanConvertUtils.convertCollection(list, DeptSearchResultVO::new).orElse(new ArrayList<>());
    }

    @Override
    public List<DeptSearchResultVO> listByUserIds(List<Long> userIds) {
        List<Long> deptIds = departmentUserService.getDeptIdsByUserIds(userIds);
        if (CollectionUtils.isNotEmpty(deptIds)) {
            List<SysDept> list = departmentMapper.selectBatchIds(deptIds);
            return (List<DeptSearchResultVO>) BeanConvertUtils.convertCollection(list, DeptSearchResultVO::new).orElse(new ArrayList<>());
        }
        return new ArrayList<>();
    }

    @Override
    public Map<Long, DeptSearchResultVO> mapByUserIds(List<Long> userIds) {
        List<SysDeptUser> deptUserList = departmentUserService.getListByUserIds(userIds);
        Map<Long, Long> deptUserMap = deptUserList.stream().collect(Collectors.toMap(SysDeptUser::getDeptId, SysDeptUser::getUserId, (k1, k2) -> k1));
        List<DeptSearchResultVO> deptList = listByUserIds(userIds);
        return deptList.stream().collect(Collectors.toMap(p -> deptUserMap.get(p.getId()),
                Function.identity(), (k1, k2) -> k1));
    }

    @Override
    public List<DeptTreeVO> tree(DeptSearchDTO dto) {
        List<SysDept> list = departmentMapper.list(dto);
        // 返回树状列表
        return TreeUtils.listToTree(list, DeptTreeVO::new);
    }

    @Override
    public List<DeptTreeVO> getTreeByDeptId(Long deptId) {
        // 获取指定父级部门的节点下的数据(含父级部门)
        List<SysDept> deptList =
                departmentMapper.getDeptListByParentIds(Collections.singletonList(deptId), true);
        // 返回树状列表
        return TreeUtils.listToTree(deptList, DeptTreeVO::new);
    }

    /**
     * @description: 初始化数据
     * @author: 王强
     * @date: 2023-05-04 13:00:56
     * @return: void
     * @version: 1.0
     */
    @PostConstruct
    public void initData() {
        log.info("=====================初始化部门数据=====================");
        List<SysDept> allDeptList = departmentMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getId, SysDept::getIds, SysDept::getParentId));
        Map<Long, String> deptIdsMap = new HashMap<>(allDeptList.size());
        for (SysDept dept : allDeptList) {
            String parentIds = deptIdsMap.get(dept.getParentId());
            dept.setIds(parentIds == null ? String.valueOf(dept.getId()) : parentIds + "," + dept.getId());
            dept.setLevel(dept.getIds().split(",").length);
            deptIdsMap.put(dept.getId(), dept.getIds());
        }
        updateBatchById(allDeptList);
    }
}
