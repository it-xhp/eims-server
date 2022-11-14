package com.gdupt.controller;

import com.alibaba.fastjson.JSONObject;
import com.gdupt.service.FilesService;
import com.gdupt.util.ApiResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@RestController
@RequestMapping("files")
public class FilesController {

    @Autowired
    private FilesService filesService;


    @RequestMapping("uploads")
    public ApiResults uploadImage(HttpServletRequest request, HttpServletResponse response)  {
        return filesService.uploads(request, response);
    }
}
