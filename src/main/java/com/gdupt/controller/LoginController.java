package com.gdupt.controller;

import com.gdupt.service.LoginService;
import com.gdupt.util.ApiResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author XHP
 * @date 2022/10/14
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public ApiResults login(@RequestParam("username")String username,
                            @RequestParam("password")String password,
                            @RequestParam("isRemember")Integer isRemember, HttpSession session){
        return loginService.login(username,password,isRemember,session);
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
