package com.gdupt.service;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.gdupt.entity.User;
import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.mapper.UserMapper;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import com.gdupt.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 更新用户数据
     * @param data
     * @return
     */
    public ApiResults update(JSONObject data,User user) {
        User updateUser = data.toBean(User.class);
        updateUser.setUpdateUser(user.getUserId());
        Date date = new Date();
        updateUser.setUpdateTime(date);
        User usersByUserName = getUserByUsername(updateUser.getUserName());
        if (!updateUser.getUserId().equals(usersByUserName.getUserId())){
            return ApiResultUtils.getFail(ErrorCodeEnum.DUPLICATE_DATA,"用户名重复");
        }
        userMapper.update(updateUser);
        return ApiResultUtils.getSuccess("更新成功");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    public ApiResults delete(Integer userId,User user) {
        if(userId == null){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM, "userId不能为空,删除失败!");
        }
        Date date = new Date();
        userMapper.deleteById(user.getUserId(),date,userId);
        return ApiResultUtils.getSuccess("删除成功");
    }

    /**
     * 加载员工表格
     * @param pageParams
     * @return
     */
    public ApiResults loadTable(PageParam pageParams) {
        User user = pageParams.getParams().toBean(User.class);
        int posStart = pageParams.getPosStart();
        int count = pageParams.getCount();
        PageRequest pageRequest = PageRequest.of(posStart, count);
        List<User> users = userMapper.queryAllByLimit(user, pageRequest);
        return ApiResultUtils.getSuccess(users);
    }

    /**
     * 修改密码
     * @param data
     * @param currentUser
     * @return
     */
    public ApiResults revisePassword(JSONObject data, User currentUser) {
        Integer userId = data.getInt("userId");
        String newPassword = data.getStr("newPassword");
        String oldPassword = data.getStr("oldPassword");
        if (StrUtil.isBlank(newPassword)){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"请输入新密码");
        }
        User user = userMapper.queryById(userId);
        if (user == null){
            return ApiResultUtils.getFail(ErrorCodeEnum.DATA_NOT_FOUND,"请检查该用户是否存在");
        }
        if (user.getPassword().equals(oldPassword)){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"原密码有误，请重新输入");
        }
        if (newPassword.equals(oldPassword)){
            return ApiResultUtils.getFail(ErrorCodeEnum.DUPLICATE_DATA,"新密码不得与久密码相同");
        }
        User updateUser = new User();
        Date date = new Date();
        updateUser.setUpdateTime(date);
        updateUser.setPassword(newPassword);
        updateUser.setUpdateUser(currentUser.getUserId());
        userMapper.update(updateUser);
        return ApiResultUtils.getSuccess("密码修改成功");
    }

    /**
     * 重置密码
     * @param data
     * @param currentUser
     * @return
     */
    public ApiResults resetPassword(JSONObject data, User currentUser) {
        Integer userId = data.getInt("userId");
        User user = userMapper.queryById(userId);
        if (user == null){
            return ApiResultUtils.getFail(ErrorCodeEnum.DATA_NOT_FOUND,"请检查该用户是否存在");
        }
        String resetPassword = RandomUtil.randomBytes(10).toString();
        User updateUser = User.builder()
                .userId(userId)
                .password(resetPassword)
                .updateTime(new Date())
                .updateUser(currentUser.getUserId())
                .build();
        userMapper.update(updateUser);
        return ApiResultUtils.getSuccess("重置密码成功");
    }

    /**
     * 新增用户
     * @param data
     * @return
     */
    public ApiResults add(JSONObject data) {
        User user = data.toBean(User.class);
        String userName = user.getUserName();
        User usersByUserName = getUserByUsername(userName);
        if (usersByUserName != null){
            return ApiResultUtils.getFail(ErrorCodeEnum.DUPLICATE_DATA,"用户名重复");
        }
        userMapper.insert(user);
        return ApiResultUtils.getSuccess("新增用户成功");
    }


    /**
     * 获取用户通过用户名和密码
     *
     * @return {@link User}
     */
    public User getUserByUsername(String username){
        User user = userMapper.getUsersByUserName(username);
        return user;
    }
}
