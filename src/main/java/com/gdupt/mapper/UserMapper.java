package com.gdupt.mapper;

import com.gdupt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
@Repository
public interface UserMapper {

     /**
      * 更新登录失败次数
      * @param userId
      * @return
      */
     boolean updateLoginFailNum(Integer userId);

     /**
      * 通过用户名查询用户
      * @param username
      * @return
      */
     User getUsersByUserName(String username);


     /**
      * 通过ID查询单条数据
      *
      * @param userId 主键
      * @return 实例对象
      */
     User queryById(Integer userId);

     /**
      * 查询指定行数据
      *
      * @param User 查询条件
      * @param pageable         分页对象
      * @return 对象列表
      */
     List<User> queryAllByLimit(User User, @Param("pageable") Pageable pageable);

     /**
      * 统计总行数
      *
      * @param User 查询条件
      * @return 总行数
      */
     long count(User User);

     /**
      * 新增数据
      *
      * @param User 实例对象
      * @return 影响行数
      */
     int insert(User User);

     /**
      * 批量新增数据（MyBatis原生foreach方法）
      *
      * @param entities List<User> 实例对象列表
      * @return 影响行数
      */
     int insertBatch(@Param("entities") List<User> entities);

     /**
      * 批量新增或按主键更新数据（MyBatis原生foreach方法）
      *
      * @param entities List<User> 实例对象列表
      * @return 影响行数
      * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
      */
     int insertOrUpdateBatch(@Param("entities") List<User> entities);

     /**
      * 修改数据
      *
      * @param User 实例对象
      * @return 影响行数
      */
     int update(User User);

     /**
      * 通过主键删除数据
      *
      * @param userId 主键
      * @return 影响行数
      */
     int deleteById(Integer userId);


}
