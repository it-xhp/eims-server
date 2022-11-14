package com.gdupt.service;


import com.gdupt.entity.User;
import com.gdupt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 获取用户通过用户名和密码
     *
     * @return {@link User}
     */
    public User getUserByUsername(String username){
        User user = userMapper.getUsersByUserName(username);
        return user;
    }




}
