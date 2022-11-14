package com.gdupt.mapper;

import com.gdupt.entity.RoleRight;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Repository
public interface RoleRightMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleRight queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param RoleRight 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RoleRight> queryAllByLimit(RoleRight RoleRight, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param RoleRight 查询条件
     * @return 总行数
     */
    long count(RoleRight RoleRight);

    /**
     * 新增数据
     *
     * @param RoleRight 实例对象
     * @return 影响行数
     */
    int insert(RoleRight RoleRight);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RoleRight> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RoleRight> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RoleRight> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RoleRight> entities);

    /**
     * 修改数据
     *
     * @param RoleRight 实例对象
     * @return 影响行数
     */
    int update(RoleRight RoleRight);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
