package com.gdupt.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gdupt.constant.RedisConstant;
import com.gdupt.entity.User;
import com.gdupt.mapper.UserMapper;
import com.gdupt.service.UserService;
import com.gdupt.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author xuhuaping
 * @date 2022/11/21
 */
public class UserCredentialsMatcher extends SimpleCredentialsMatcher {

    private final Integer PASSWORD_LOCK = 5;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UserToken userToken=(UserToken) token;
        User user = (User)token.getPrincipal();

        String inPassword = (String)userToken.getCredentials();
        String dbPassword = (String) info.getCredentials();
        boolean flag = Objects.equals(inPassword, dbPassword);
        Integer loginCount = (Integer)redisUtil.get(RedisConstant.LOGIN_COUNT + user.getUserId());
        if (!flag){
            redisUtil.incr(RedisConstant.LOGIN_COUNT + user.getUserId(),1);
        }else {
            redisUtil.del(RedisConstant.LOGIN_COUNT + user.getUserId());
        }
        if (PASSWORD_LOCK.equals(loginCount)){
            User updateUser = new User();
            updateUser.setIsLocked(1);
            updateUser.setUserId(user.getUserId());
            userMapper.updateById(updateUser);
            throw new LockedAccountException("账户被锁定,请联系管理员");
        }
        return flag;
    }
}
