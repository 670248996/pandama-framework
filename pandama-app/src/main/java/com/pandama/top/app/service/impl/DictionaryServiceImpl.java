package com.pandama.top.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pandama.top.app.mapper.DictionaryMapper;
import com.pandama.top.app.pojo.dto.DictionaryListDTO;
import com.pandama.top.app.pojo.entity.Dictionary;
import com.pandama.top.app.pojo.vo.DictionaryListVO;
import com.pandama.top.app.pojo.vo.DictionaryTreeVO;
import com.pandama.top.app.service.DictionaryService;
import com.pandama.top.utils.BeanConvertUtils;
import com.pandama.top.utils.Tree;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 设备服务impl
 * @author: 王强
 * @dateTime: 2023-04-19 16:44:34
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    @Override
    public List<DictionaryTreeVO> tree() {
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(null);
        List<DictionaryTreeVO> directoryTreeList = (List<DictionaryTreeVO>) BeanConvertUtils.convertCollection(dictionaryList, DictionaryTreeVO::new).orElse(new ArrayList<>());
        Tree<DictionaryTreeVO, DictionaryTreeVO> treeUtil = new Tree<>();
        return treeUtil.listToTree(directoryTreeList);
    }

    @Override
    public List<DictionaryListVO> list(DictionaryListDTO dto) {
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(new LambdaQueryWrapper<Dictionary>()
                .eq(StringUtils.isNotBlank(dto.getDictType()), Dictionary::getDictType, dto.getDictType()));
        return (List<DictionaryListVO>) BeanConvertUtils.convertCollection(dictionaryList, DictionaryListVO::new).orElse(new ArrayList<>());
    }
}
