package com.gdupt.shiro;

import com.gdupt.entity.User;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author xuhuaping
 * @date 2022/11/16
 */
public class UserToken  implements AuthenticationToken {
    private String jwt;
    private User user;



    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public Object getCredentials() {
        return this.user;
    }

    public UserToken(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public UserToken(String jwt) {
        this.jwt = jwt;
    }

    public UserToken(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
