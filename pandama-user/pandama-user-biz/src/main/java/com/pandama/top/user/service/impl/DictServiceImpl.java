package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.user.pojo.dto.DictCreateDTO;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import com.pandama.top.user.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.entity.SysDict;
import com.pandama.top.user.mapper.DictMapper;
import com.pandama.top.user.pojo.vo.DictDetailResultVO;
import com.pandama.top.user.pojo.vo.DictSearchResultVO;
import com.pandama.top.user.service.DictService;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.user.constant.Constants;
import com.pandama.top.core.utils.BeanConvertUtils;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:53:46
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DictServiceImpl extends ServiceImpl<DictMapper, SysDict> implements DictService {

    private final DictMapper dictionaryMapper;

    private final RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DictCreateDTO dto) {
        SysDict parent = dto.getParentId() == 0 ? new SysDict() : dictionaryMapper.selectById(dto.getParentId());
        if (parent == null) {
            throw new CommonException("父节点不存在");
        }
        if (parent.getLevel() > 1) {
            throw new CommonException("无法创建多级字典");
        }
        // 字典编码锁对应的redis中的key
        String lockKey = String.format(Constants.Redis.LOCK_KEY, "DICT");
        boolean lock = redisUtils.tryReentrantLock(lockKey, "", 400, 5000);
        try {
            if (lock) {
                // 将传参字段转换赋值成字典实体属性
                SysDict sysDict = BeanConvertUtils.convert(dto, SysDict::new, (s, t) -> {
                    t.setId(IdWorker.getId());
                    t.setDictType(parent.getDictType());
                    t.setDictCode(dto.getDictCode());
                    t.setParentId(s.getParentId());
                    t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                    t.setLevel(t.getIds().split(",").length);
                }).orElseThrow(() -> new CommonException("部门创建出错"));
                dictionaryMapper.insert(sysDict);
            } else {
                throw new RedisException("字典锁获取失败");
            }
        } catch (Exception e) {
            log.error("字典创建失败，错误信息: {}", e.getMessage());
            throw new CommonException("部门创建出错");
        } finally {
            // 如果加锁成功则进行解锁
            if (lock) {
                redisUtils.tryReentrantUnlock(lockKey, "");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        // 获取目标逻辑删除的字典
        List<SysDict> dictList = dictionaryMapper.getDictListByParentIds(ids, true);
        List<Long> deptIdList = dictList.stream().map(SysDict::getId).collect(Collectors.toList());
        dictionaryMapper.deleteBatchIds(deptIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictUpdateDTO dto) {
        SysDict dictionary = dictionaryMapper.selectById(dto.getId());
        BeanConvertUtils.convert(dto, () -> dictionary);
        dictionaryMapper.updateById(dictionary);
    }

    @Override
    public DictDetailResultVO detail(Long id) {
        SysDict dict = dictionaryMapper.selectById(id);
        return BeanConvertUtils.convert(dict, DictDetailResultVO::new).orElse(new DictDetailResultVO());
    }

    @Override
    public List<DictSearchResultVO> list(DictSearchDTO dto) {
        List<SysDict> list = dictionaryMapper.list(dto);
        return (List<DictSearchResultVO>) BeanConvertUtils.convertCollection(list, DictSearchResultVO::new).orElse(new ArrayList<>());
    }

    @Override
    public void changeStatus(Long id, Boolean status) {
        SysDict sysRole = new SysDict();
        sysRole.setId(id);
        sysRole.setStatus(status);
        dictionaryMapper.updateById(sysRole);
    }

    /**
     * 初始化数据
     *
     * @author 王强
     */
    @PostConstruct
    public void initData() {
        log.info("=====================初始化字典数据=====================");
        List<SysDict> allDictList = dictionaryMapper.selectList(new LambdaQueryWrapper<SysDict>()
                .select(SysDict::getId, SysDict::getIds, SysDict::getParentId));
        Map<Long, String> dictIdsMap = new HashMap<>(allDictList.size());
        for (SysDict dict : allDictList) {
            String parentIds = dictIdsMap.get(dict.getParentId());
            dict.setIds(parentIds == null ? String.valueOf(dict.getId()) : parentIds + "," + dict.getId());
            dict.setLevel(dict.getIds().split(",").length);
            dictIdsMap.put(dict.getId(), dict.getIds());
        }
        updateBatchById(allDictList);
    }
}
