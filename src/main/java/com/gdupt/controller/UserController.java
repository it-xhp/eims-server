package com.gdupt.controller;


import com.gdupt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;



    //@RequestMapping("add")
    //public JSONObject addVue(@RequestBody JSONObject jsonObject) {
    //    User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
    //    return userService.addOrUpdateUsers(jsonObject,"add",currentUser);
    //}
    //
    //@RequestMapping("update")
    //public JSONObject updateVue(@RequestBody JSONObject jsonObject) {
    //    User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
    //    return userService.addOrUpdateUsers(jsonObject,"edit",currentUser);
    //}
    //
    //@RequestMapping("delete")
    //public JSONObject deleteVue(@RequestBody JSONObject jsonObject) {
    //    Integer userId = jsonObject.getInteger("userId");
    //    if(userId == null){
    //        return JsonUtil.getFailResponseJson(ErrorCodeEnum.INVALID_PARAM, "userId不能为空,删除失败!");
    //    }
    //    return userService.delete(jsonObject);
    //}
    //
    ///**
    // * 重置密码，获取信息
    // * @author pp
    // */
    //@RequestMapping("resetPassword")
    //public JSONObject resetPassword(@RequestBody JSONObject jsonObject) {
    //    User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
    //    return userService.resetPassword(jsonObject, currentUser);
    //}
    //
    //@RequestMapping("loadTable")
    //public JSONObject loadTable(@RequestBody JSONObject page) {
    //    PageParam pageParam = getPageParams(page);
    //    User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
    //    return userService.loadTable(pageParam,currentUser.getCompanyId());
    //}
    //
    //@RequestMapping("changePassword")
    //public JSONObject changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
    //    if (StringUtils.isEmpty(oldPassword)) {
    //        return JsonUtil.getFailResponseJson(ErrorCodeEnum.INVALID_PARAM, "请输入旧密码");
    //    }
    //    if (StringUtils.isEmpty(newPassword)) {
    //        return JsonUtil.getFailResponseJson(ErrorCodeEnum.INVALID_PARAM, "请输入新密码");
    //    }
    //    if (oldPassword.equals(newPassword)) {
    //        return JsonUtil.getFailResponseJson(ErrorCodeEnum.INVALID_PARAM, "新密码不能与旧密码相同");
    //    }
    //    User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
    //    return userService.changePassword(oldPassword, newPassword, currentUser);
    //}
    //
    //@RequestMapping("loadAllOptionList")
    //public JSONObject loadAllOptionList(){
    //    return userService.loadAllOptionList();
    //}
    //
    //@RequestMapping("loadOptionListByUserName")
    //public JSONObject loadOptionListByUserName(@RequestParam("userName") String userName){
    //    return userService.loadOptionListByUserName(userName);
    //}
}
