package com.gdupt.controller;

import cn.hutool.json.JSONObject;
import com.gdupt.entity.User;
import com.gdupt.service.ActivitiService;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XHP
 * @date 2022/10/28
 */
@RestController
@RequestMapping("activiti")
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;

    @RequestMapping("submitApplication")
    public ApiResults submitApplication(JSONObject data){
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        return activitiService.submitApplication(data,currentUser);
    }

    @RequestMapping("queryApplications")
    public ApiResults queryApplications(JSONObject data){
        return activitiService.queryApplications(data);
    }

    @RequestMapping("cancelApplication")
    public ApiResults cancelApplication(@RequestParam("key") String key){
        return activitiService.cancelApplication(key);
    }

    @RequestMapping("checkApplicationProgress")
    public ApiResults checkApplicationProgress(@RequestParam("key") String key){
        return activitiService.checkApplicationProgress(key);
    }
}
