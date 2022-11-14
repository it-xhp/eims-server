package com.gdupt.mapper;

import com.gdupt.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Repository
public interface LogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param logId 主键
     * @return 实例对象
     */
    Log queryById(Object logId);

    /**
     * 查询指定行数据
     *
     * @param Log 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Log> queryAllByLimit(Log Log, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param Log 查询条件
     * @return 总行数
     */
    long count(Log Log);

    /**
     * 新增数据
     *
     * @param Log 实例对象
     * @return 影响行数
     */
    int insert(Log Log);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Log> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Log> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Log> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Log> entities);

    /**
     * 修改数据
     *
     * @param Log 实例对象
     * @return 影响行数
     */
    int update(Log Log);

    /**
     * 通过主键删除数据
     *
     * @param logId 主键
     * @return 影响行数
     */
    int deleteById(Object logId);

}
