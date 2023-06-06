package com.pandama.top.user.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandama.top.user.api.pojo.dto.DictCreateDTO;
import com.pandama.top.user.api.pojo.dto.DictSearchDTO;
import com.pandama.top.user.api.pojo.dto.DictUpdateDTO;
import com.pandama.top.user.api.pojo.vo.DictCreateResultVO;
import com.pandama.top.user.api.pojo.vo.DictSearchResultVO;
import com.pandama.top.user.api.pojo.vo.DictTreeVO;
import com.pandama.top.user.biz.entity.SysDict;
import com.pandama.top.user.biz.mapper.DictMapper;
import com.pandama.top.user.biz.service.DictService;
import com.pandama.top.cache.utils.RedisUtils;
import com.pandama.top.pojo.vo.PageResultVO;
import com.pandama.top.user.biz.constant.Constants;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.TreeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 数据字典实现类
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
    public DictCreateResultVO create(DictCreateDTO dto) {
        SysDict dictionary = BeanConvertUtils.convert(dto, SysDict::new, (s, t) -> {
            if (s.getDictParentId() != null && s.getDictParentId() > 0) {
                SysDict parentDict = dictionaryMapper.selectById(s.getDictParentId());
                t.setLevel(parentDict.getLevel() + 1);
                t.setParentId(s.getDictParentId());
            }
        }).orElse(new SysDict());
        dictionaryMapper.insert(dictionary);
        DictCreateResultVO resultVO = new DictCreateResultVO();
        resultVO.setId(dictionary.getId());
        return resultVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> dictIds) {
        dictIds.forEach(dictId -> {
            List<SysDict> dictList = dictionaryMapper.getDictEntityListByParentId(dictId, true);
            dictionaryMapper.deleteBatchIds(dictList.stream().map(SysDict::getId).collect(Collectors.toList()));
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictUpdateDTO dto) {
        SysDict dictionary = dictionaryMapper.selectById(dto.getDictId());
        BeanConvertUtils.convert(dto, () -> dictionary);
        dictionaryMapper.updateById(dictionary);
    }

    @Override
    public SysDict getDictById(Long dictId) {
        return dictionaryMapper.selectById(dictId);
    }

    @Override
    public PageResultVO<DictTreeVO> page(DictSearchDTO dto) {
        // 先按条件查出数据，不分页
        List<DictTreeVO> dictPages = dictionaryMapper.getDictList(dto);
        List<DictTreeVO> allDict;
        // 是否存在字典缓存
        boolean hasCache = false;
        // 从缓存取出所有字典数据，如果没有命中缓存则从数据库直接读取
        if (redisUtils.hasKey(Constants.RedisConfig.ALL_DICT_CACHE).orElse(false)) {
            allDict = JSON.parseArray(redisUtils.get(Constants.RedisConfig.ALL_DICT_CACHE).orElse("").toString(), DictTreeVO.class);
            hasCache = true;
        } else {
            allDict = dictionaryMapper.getDictList(new DictSearchDTO());
        }
        // 待匹配字典代码列表
        List<String> codes = new ArrayList<>();
        // 一级字典代码列表
        List<String> topDict = new ArrayList<>();
        // 按规则切分数据库搜索结果中字典代码的等级，每三个数字作一级
        dictPages.forEach(d -> {
            // 按三个数字切分，得到从一级字典开始的字典代码
            String[] dictCodes = d.getDictCode().split("(?<=\\G\\d{3})");
            topDict.add(dictCodes[0]);
            StringBuilder parentDictCode = new StringBuilder();
            for (String dictCode : dictCodes) {
                parentDictCode.append(dictCode);
                // 从一级开始每拼接一次就是一个父级字典，直到自身，然后将其放入字典代码列表
                codes.add(parentDictCode.toString());
            }
        });
        IPage<DictTreeVO> topDictList;
        if (topDict.size() > 0) {
            // 从所有字典中过滤出指定的代码列表数据
            List<DictTreeVO> matchResult = allDict.stream().filter(d -> codes.contains(d.getDictCode()))
                    .collect(Collectors.toList());
            // 转换树结构
            List<DictTreeVO> dicTree = TreeUtils.listToTree(matchResult);
            // 按一级字典分页
            topDictList = dictionaryMapper.getTopDictList(new Page<>(dto.getCurrent(), dto.getSize()), topDict);
            // 由于转换成树结构之后，顶级节点就是一级字典，所以直接替换一级字典分页后的实际数据
            topDictList.setRecords(dicTree);
        } else {
            topDictList = new Page<>(dto.getCurrent(), dto.getSize());
        }
        return BeanConvertUtils.convert(topDictList, PageResultVO<DictTreeVO>::new).orElse(new PageResultVO<>());
    }

    @Override
    public List<DictSearchResultVO> list(DictSearchDTO dto) {
        List<SysDict> dictList = dictionaryMapper.selectList(Wrappers.emptyWrapper());
        return (List<DictSearchResultVO>) BeanConvertUtils.convertCollection(dictList, DictSearchResultVO::new).orElse(new ArrayList<>());
    }

    @Override
    public List<DictSearchResultVO> listType() {
        return dictionaryMapper.listType();
    }

    @Override
    public List<DictTreeVO> getDictByType(String dictType) {
        List<DictTreeVO> dictList = dictionaryMapper.getDictByType(dictType);
        return TreeUtils.listToTree(dictList, DictTreeVO::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(Long dictId) {
        List<SysDict> dictList = dictionaryMapper.getDictEntityListByParentId(dictId, true);
        dictList.forEach(d -> d.setStatus(!d.getStatus()));
        this.updateBatchById(dictList);
    }
}
