package com.gdupt.service;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdupt.constant.RedisConstant;
import com.gdupt.entity.User;
import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.mapper.UserMapper;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import com.gdupt.util.PageParam;
import com.gdupt.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;
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
        User usersByUserName = getUserByUsername(updateUser.getUsername());
        if (!updateUser.getUserId().equals(usersByUserName.getUserId())){
            return ApiResultUtils.getFail(ErrorCodeEnum.DUPLICATE_DATA,"用户名重复");
        }
        userMapper.updateById(updateUser);
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
        int delRow = userMapper.deleteById(user.getUserId());
        if (delRow>0){
            return ApiResultUtils.getSuccess("删除成功");
        }
        return ApiResultUtils.getFail(ErrorCodeEnum.DATA_NOT_FOUND,"删除失败");
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
        IPage<User> page = new Page<>();
        page.setSize(count);
        page.setCurrent(posStart);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        IPage<User> userIPage = userMapper.selectPage(page, userLambdaQueryWrapper);
        List<User> users = userIPage.getRecords();
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
        User user = userMapper.selectById(userId);
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
        userMapper.updateById(updateUser);
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
        User user = userMapper.selectById(userId);
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
        userMapper.updateById(updateUser);
        return ApiResultUtils.getSuccess("重置密码成功");
    }

    /**
     * 新增用户
     * @param data
     * @return
     */
    public ApiResults add(JSONObject data) {
        User user = data.toBean(User.class);
        String userName = user.getUsername();
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
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        return user;
    }

    public ApiResults updateLocked(Integer lockedUserId) {
        if (lockedUserId == null){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"锁定用户id不得为空");
        }
        User user = User.builder()
                .userId(lockedUserId)
                .isLocked(0)
                .build();
        userMapper.updateById(user);
        redisUtil.del(RedisConstant.LOGIN_COUNT+lockedUserId);
        return ApiResultUtils.getSuccess("解除成功");
    }
}
