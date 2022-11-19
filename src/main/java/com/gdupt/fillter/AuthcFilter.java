package com.gdupt.fillter;

import cn.hutool.core.util.StrUtil;
import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.shiro.UserToken;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import com.gdupt.util.JwtUtil;
import com.gdupt.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/11/16
 * //需要认证的url经过该过滤器
 */
@Slf4j
@SuppressWarnings("Duplicates")
public class AuthcFilter extends AccessControlFilter {
    private  static List<String> ignoredPaths;
    static {
        ignoredPaths = Arrays.asList("/login", "/logout");
    }

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
        HttpServletRequest request =((HttpServletRequest) servletRequest);
        HttpServletResponse response =((HttpServletResponse) servletResponse);
        String path = request.getServletPath();
        if (ignoredPaths.contains(path)){return true;}
        log.info("[xhp] method:{} path: {}", new Object[]{request.getMethod(), path});
        String JWT = ((HttpServletRequest) servletRequest).getHeader("token");
        Subject subject = null;
        if (StrUtil.isNotBlank(JWT)){
            try {
                JwtUtil.parseJWT(JWT);
                UserToken userToken = new UserToken(JWT, null);
                subject = getSubject(servletRequest,servletResponse);
                subject.login(userToken);
            }catch (Exception e){
                e.printStackTrace();
                ApiResults apiResults = ApiResultUtils.getFail(ErrorCodeEnum.NO_AUTHORIZATION);
                WebUtils.renderString(response,apiResults);
                return false;
            }
            return true;
        }
        return false;
    }



    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //直接设置401 未认证
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
