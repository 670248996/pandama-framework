package com.pandama.top.app.controller;

import com.pandama.top.app.pojo.dto.DictionaryListDTO;
import com.pandama.top.app.pojo.vo.DictionaryListVO;
import com.pandama.top.app.pojo.vo.DictionaryTreeVO;
import com.pandama.top.app.service.DictionaryService;
import com.pandama.top.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 字典控制器
 * @author: 王强
 * @dateTime: 2023-04-22 12:23:15
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Response<List<DictionaryTreeVO>> tree() {
        return Response.success(dictionaryService.tree());
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response<List<DictionaryListVO>> list(@RequestBody DictionaryListDTO dto) {
        return Response.success(dictionaryService.list(dto));
    }

}
