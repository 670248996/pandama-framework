package com.pandama.top.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.user.pojo.dto.DictCreateDTO;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import com.pandama.top.user.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.entity.SysDict;
import com.pandama.top.user.enums.CustomErrorCodeEnum;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 字典实现类
 * @author: 白剑民
 * @dateTime: 2023/5/2 20:38
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
            throw new CommonException(CustomErrorCodeEnum.PARENT_NOT_EXIT);
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
                    t.setDictCode(dto.getDictCode());
                    t.setParentId(s.getParentId());
                    t.setIds(parent.getIds() == null ? String.valueOf(t.getId()) : parent.getIds() + "," + t.getId());
                    t.setLevel(t.getIds().split(",").length);
                }).orElseThrow(() -> new CommonException(CustomErrorCodeEnum.DEPARTMENT_CREATE_ERROR));
                dictionaryMapper.insert(sysDict);
            } else {
                throw new RedisException("字典锁获取失败");
            }
        } catch (Exception e) {
            log.error("字典创建失败，错误信息: {}", e.getMessage());
            throw new CommonException(CustomErrorCodeEnum.DEPARTMENT_CREATE_ERROR);
        } finally {
            // 如果加锁成功则进行解锁
            if (lock) {
                redisUtils.tryReentrantUnlock(lockKey, "");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> dictIds) {
        // 获取目标逻辑删除的字典
        List<SysDict> dictList = dictionaryMapper.getDictListByParentIds(dictIds, true);
        List<Long> deptIdList = dictList.stream().map(SysDict::getId).collect(Collectors.toList());
        dictionaryMapper.deleteBatchIds(deptIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictUpdateDTO dto) {
        SysDict dictionary = dictionaryMapper.selectById(dto.getDictId());
        BeanConvertUtils.convert(dto, () -> dictionary);
        dictionaryMapper.updateById(dictionary);
    }

    @Override
    public DictDetailResultVO detail(Long dictId) {
        SysDict dict = dictionaryMapper.selectById(dictId);
        return BeanConvertUtils.convert(dict, DictDetailResultVO::new).orElse(new DictDetailResultVO());
    }

    @Override
    public List<DictSearchResultVO> list(DictSearchDTO dto) {
        List<SysDict> list = dictionaryMapper.list(dto);
        return (List<DictSearchResultVO>) BeanConvertUtils.convertCollection(list, DictSearchResultVO::new).orElse(new ArrayList<>());
    }

    @Override
    public void changeStatus(Long dictId, Boolean status) {
        SysDict sysRole = new SysDict();
        sysRole.setId(dictId);
        sysRole.setStatus(status);
        dictionaryMapper.updateById(sysRole);
    }
}
