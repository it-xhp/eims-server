package com.gdupt.shiro;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdupt.constant.RedisConstant;
import com.gdupt.entity.User;
import com.gdupt.service.UserService;
import com.gdupt.util.JwtUtil;
import com.gdupt.util.RedisUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 获取身份认证信息，当前为用户名密码认证
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UserToken token = (UserToken) authenticationToken;
        User user = token.getUser();
        String jwt = token.getJWT();
        User authenticationUser;
        if (StrUtil.isNotBlank(jwt)){
            Claims claims;
            try {
                claims = JwtUtil.parseJWT(jwt);
            } catch (Exception e) {
                throw new ExpiredCredentialsException ("登录已失效");
            }
            String userId = claims.getSubject();
            String userString = redisUtil.get(RedisConstant.USER_PREFIX + userId);
            authenticationUser = JSONUtil.toBean(userString, User.class);
            if (Objects.isNull(authenticationUser)){
                throw new ExpiredCredentialsException ("登录已失效");
            }
        }else {
            authenticationUser = userService.getUserByUsername(user.getUserName());
            if (Objects.isNull(authenticationUser)){
                throw new UnknownAccountException("该账号不存在");
            }else if (user.getIsLocked().equals(1)){
                throw new LockedAccountException("该账号被锁定，请联系管理员");
            }else if (!user.getPassword().equals(authenticationUser.getPassword())){
                throw new  AuthenticationException("用户名或者密码错误");
            }
        }
        return new SimpleAuthenticationInfo(authenticationUser,user.getPassword(),getName());
    }
}
