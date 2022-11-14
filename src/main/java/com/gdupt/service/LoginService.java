package com.gdupt.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.gdupt.constant.RedisConstant;
import com.gdupt.entity.User;
import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import com.gdupt.util.MemoryData;
import com.gdupt.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * @param username
     * @param password
     * @return
     */
    public ApiResults login(String username, String password, Integer isRemember,HttpSession session) {
        JSONObject data = new JSONObject();
        String authenticationToken = "";
        if (StrUtil.isBlank(username)){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"用户名不能为空");
        }else if (StrUtil.isBlank(password)){
            return ApiResultUtils.getFail(ErrorCodeEnum.INVALID_PARAM,"密码不能为空");
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            User loginUser = (User) subject.getPrincipal();
            String userId = loginUser.getUserId().toString();
            String sessionId = session.getId();
            if (!MemoryData.getSessionIdMap().containsKey(userId)){
                MemoryData.getSessionIdMap().put(userId,sessionId);
            }else if (MemoryData.getSessionIdMap().containsKey(userId) && !sessionId.equals(MemoryData.getSessionIdMap().get(userId))) {
                MemoryData.getSessionIdMap().remove(userId);
                MemoryData.getSessionIdMap().put(userId, sessionId);
            }
            if (isRemember.equals(1)){
                authenticationToken = IdUtil.simpleUUID();
                redisUtil.set(RedisConstant.USER_PREFIX + userId,authenticationToken,30*24*60*60);
            }
        }catch (AuthenticationException e){
            return ApiResultUtils.getFail(ErrorCodeEnum.LOGIN_FAIL,e.toString());
        }
        data.set("username",username);
        data.set("Authorized-Token",authenticationToken);
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
