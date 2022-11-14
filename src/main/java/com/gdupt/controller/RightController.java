package com.gdupt.controller;

import com.gdupt.entity.User;
import com.gdupt.service.RightService;
import com.gdupt.util.ApiResults;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@RestController
@RequestMapping("right")
public class RightController {

    @Autowired
    private RightService rightService;

    @RequestMapping("getAllRights")
    public ApiResults getAllRights(){
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        return rightService.getAllRights(currentUser);
    }
}
