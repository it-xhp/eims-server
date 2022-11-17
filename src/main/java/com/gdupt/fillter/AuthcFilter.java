package com.gdupt.fillter;

import cn.hutool.core.util.StrUtil;
import com.gdupt.shiro.UserToken;
import com.gdupt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuhuaping
 * @date 2022/11/16
 * //需要认证的url经过该过滤器
 */
@Component
@SuppressWarnings("Duplicates")
public class AuthcFilter extends AccessControlFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Content-Type","application/json;charset=UTF-8");
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            return true;
        }
        return super.preHandle(request, response);
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        String JWT = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (StrUtil.isNotBlank(JWT)){
            Claims claims = JwtUtil.parseJWT(JWT);
            String userId = claims.getSubject();
            UserToken userToken = new UserToken(userId, null);
            SecurityUtils.getSubject().login(userToken);
        }else {

        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}