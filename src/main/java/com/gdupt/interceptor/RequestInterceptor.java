package com.gdupt.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.gdupt.entity.User;
import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import com.gdupt.util.MemoryData;
import com.gdupt.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import com.gdupt.constant.RedisConstant;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    private final List<String> ignorePathList = Arrays.asList("/", "/login", "/login/check", "/logout", "/register", "/error");



    @PostConstruct
    private void init() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        if (path.startsWith("/resources/")) {
            return true;
        }
        log.info("[xhp] method:{} path: {}", new Object[]{request.getMethod(), path});

        if (ignorePathList.contains(path)) {
            return true;
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String authorizedToken = request.getHeader("Authorized-Token");
        boolean isToLogin = true;
        if (user == null) {
            isToLogin = false;
        } else {
            String sessionId = request.getRequestedSessionId();
            String userId = user.getUserId().toString();
            String token = (String) redisUtil.get(RedisConstant.USER_PREFIX + userId);
            if (StrUtil.isBlank(authorizedToken)||!token.equals(authorizedToken)){
                redisUtil.del(RedisConstant.USER_PREFIX + userId);
                isToLogin = false;
                SecurityUtils.getSubject().logout();
            }else {
                if (MemoryData.getSessionIdMap().containsKey(userId) && !sessionId.equals(MemoryData.getSessionIdMap().get(userId))) {
                    redisUtil.del(RedisConstant.USER_PREFIX + userId);
                    isToLogin = false;
                }else{
                    if (StrUtil.isBlank(token)){
                        MemoryData.getSessionIdMap().remove(user.getUserId().toString());
                        SecurityUtils.getSubject().logout();
                        if (StrUtil.isNotBlank(token)){
                            redisUtil.del(RedisConstant.USER_PREFIX + userId);
                        }
                        isToLogin = false;
                    }
                }
            }

        }
        if (!isToLogin){
            // 返回登录失效
            response.setContentType("application/json;charset=utf-8");
            ApiResults result = ApiResultUtils.getFail(ErrorCodeEnum.NO_AUTHORIZATION,ErrorCodeEnum.NO_AUTHORIZATION.getDescription());
            response.getWriter().write(JSONUtil.toJsonStr(result));
        }
        return isToLogin;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

}
