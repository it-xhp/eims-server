package com.gdupt.controller;


import cn.hutool.json.JSONObject;
import com.gdupt.entity.User;
import com.gdupt.service.UserService;
import com.gdupt.util.ApiResults;
import com.gdupt.util.PageParam;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;



    @PostMapping("add")
    public ApiResults add(@RequestBody JSONObject jsonObject) {
        return userService.add(jsonObject);
    }

    @PostMapping("update")
    public ApiResults update(@RequestBody JSONObject data) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        return userService.update(data,currentUser);
    }

    @RequestMapping("delete")
    public ApiResults deleteVue(@RequestParam("userId") Integer userId ) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        return userService.delete(userId,currentUser);
    }


    @RequestMapping("loadTable")
    public ApiResults loadTable(@RequestBody JSONObject data) {
        PageParam pageParams = getPageParams(data);
        return userService.loadTable(pageParams);
    }

    @PostMapping("revisePassword")
    public ApiResults revisePassword(@RequestBody JSONObject data) {
        User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
        return userService.revisePassword(data, currentUser);
    }
    @PostMapping("resetPassword")
    public ApiResults resetPassword(@RequestBody JSONObject data){
        User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
        return userService.resetPassword(data, currentUser);
    }

}
