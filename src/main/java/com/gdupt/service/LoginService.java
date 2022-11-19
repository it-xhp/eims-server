package com.gdupt.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.gdupt.constant.RedisConstant;
import com.gdupt.entity.User;
import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.shiro.UserToken;
import com.gdupt.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XHP
 * @date 2022/10/14
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RightService rightService;


    /**
     * 登录
     * @param data
     * @return
     */
    public ApiResults login(JSONObject data) {
        User user = data.toBean(User.class);
        Integer isRemember = data.getInt("isRemember");
        String authenticationToken = "";
        if (StrUtil.isBlank(user.getUserName())){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"用户名不能为空");
        }else if (StrUtil.isBlank(user.getPassword())){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"密码不能为空");
        }
        User loginUser;
        try {
            Subject subject = SecurityUtils.getSubject();
            UserToken token = new UserToken(null,user);
            try {
                subject.login(token);
            }catch (Exception e){
                return ApiResultUtils.getFail(ErrorCodeEnum.LOGIN_FAIL,e.getLocalizedMessage());
            }
            loginUser = (User) subject.getPrincipal();
            String userId = loginUser.getUserId().toString();
            //if (!MemoryData.getSessionIdMap().containsKey(userId)){
            //    MemoryData.getSessionIdMap().put(userId,sessionId);
            //}else if (MemoryData.getSessionIdMap().containsKey(userId) && !sessionId.equals(MemoryData.getSessionIdMap().get(userId))) {
            //    MemoryData.getSessionIdMap().remove(userId);
            //    MemoryData.getSessionIdMap().put(userId, sessionId);
            //}
            if (isRemember.equals(1)){
                authenticationToken = JwtUtil.createJWT(userId);
                redisUtil.setObject(RedisConstant.USER_PREFIX + userId,loginUser);
            }
        }catch (AuthenticationException e){
            return ApiResultUtils.getFail(ErrorCodeEnum.LOGIN_FAIL,e.toString());
        }
        data.set("username",loginUser.getUserName());
        data.set("token",authenticationToken);
        return ApiResultUtils.getSuccess("登录成功",data);
    }


    /**
     * 注销
     *
     * @return {@link ApiResults}
     */
    public ApiResults logout(HttpServletRequest request) {
        String authorizedToken = request.getHeader("Authorization-Token");
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        if (subject.isAuthenticated()){
            subject.logout();
            MemoryData.getSessionIdMap().remove(currentUser.getUserId().toString());
            if (StrUtil.isNotBlank(authorizedToken)){
                redisUtil.del(RedisConstant.USER_PREFIX + currentUser.getUserId().toString());
            }
            return ApiResultUtils.getSuccess("OK");
        }
        return ApiResultUtils.getFail(ErrorCodeEnum.NO_AUTHORIZATION,ErrorCodeEnum.NO_AUTHORIZATION.getDescription());
    }


    /**
     * 获取基本信息
     * @return
     */
    public ApiResults getInfo() {
        return null;
    }
}
