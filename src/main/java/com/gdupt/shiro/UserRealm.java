package com.gdupt.shiro;

import com.gdupt.entity.User;
import com.gdupt.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 获取身份认证信息，当前为用户名密码认证
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        User user = userService.getUserByUsername(username);
        if (user == null){
            throw new UnknownAccountException("该账号不存在");
        }else if (user.getIsLocked().equals(1)){
            throw new LockedAccountException("该账号被锁定，请联系管理员");
        }else if (!user.getPassword().equals(password)){
            throw new  AuthenticationException("用户名或者密码错误");
        }
        return new SimpleAuthenticationInfo(user,password,getName());
    }
}
