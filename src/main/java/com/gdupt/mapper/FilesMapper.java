package com.gdupt.mapper;

import com.gdupt.entity.Files;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Repository
public interface FilesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    Files queryById(Integer fileId);

    /**
     * 通过批量ID查询多条数据
     * @param fileIds
     * @return
     */
    List<Files> queryByIds(@Param("fileIds")List<Integer> fileIds);


    /**
     * 通过ID查询多条数据
     * @param fileId
     * @return
     */
    List<Files> queryBatchById(@Param("fileId")Integer fileId);

    /**
     * 1
     * @param relationTypeId
     * @param relationId
     * @return
     */
    List<Files> findOneByRelationTypeIdOrRelationTypeId(@Param("relationTypeId")Integer relationTypeId,@Param("relationId")Integer relationId);

    /**
     * 查询指定行数据
     *
     * @param Files 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Files> queryAllByLimit(Files Files, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param Files 查询条件
     * @return 总行数
     */
    long count(Files Files);

    /**
     * 新增数据
     *
     * @param Files 实例对象
     * @return 影响行数
     */
    int insert(Files Files);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Files> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Files> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Files> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Files> entities);

    /**
     * 修改数据
     *
     * @param Files 实例对象
     * @return 影响行数
     */
    int update(Files Files);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 影响行数
     */
    int deleteById(Integer fileId);
}
