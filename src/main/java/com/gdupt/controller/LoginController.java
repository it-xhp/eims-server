package com.gdupt.controller;


import cn.hutool.json.JSONObject;
import com.gdupt.service.LoginService;
import com.gdupt.util.ApiResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XHP
 * @date 2022/10/14
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public ApiResults login(@RequestBody JSONObject data){
        return loginService.login(data);
    }

    @RequestMapping("logout")
    public ApiResults logout(HttpServletRequest request){
        return loginService.logout(request);
    }

    @RequestMapping("getInfo")
    public ApiResults getInfo(){
        return loginService.getInfo();
    }
}
