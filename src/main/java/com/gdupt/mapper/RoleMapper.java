package com.gdupt.mapper;

import com.gdupt.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Repository
public interface RoleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    Role queryById(Integer roleId);

    /**
     * 查询指定行数据
     *
     * @param Role 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Role> queryAllByLimit(Role Role, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param Role 查询条件
     * @return 总行数
     */
    long count(Role Role);

    /**
     * 新增数据
     *
     * @param Role 实例对象
     * @return 影响行数
     */
    int insert(Role Role);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Role> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Role> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Role> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Role> entities);

    /**
     * 修改数据
     *
     * @param Role 实例对象
     * @return 影响行数
     */
    int update(Role Role);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteById(Integer roleId);

}
