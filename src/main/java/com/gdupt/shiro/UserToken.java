package com.gdupt.shiro;

import com.gdupt.entity.User;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author xuhuaping
 * @date 2022/11/16
 */
public class UserToken  implements AuthenticationToken {
    private String JWT;
    private User user;



    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public Object getCredentials() {
        return this.user;
    }

    public UserToken(String JWT, User user) {
        this.JWT = JWT;
        this.user = user;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
