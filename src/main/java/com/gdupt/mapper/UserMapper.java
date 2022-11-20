package com.gdupt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdupt.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户/用户组(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-17 15:47:53
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}

