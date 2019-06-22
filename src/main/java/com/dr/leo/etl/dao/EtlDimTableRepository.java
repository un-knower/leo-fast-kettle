package com.dr.leo.etl.dao;

import com.dr.leo.etl.entity.EtlDimTable;
import com.dr.leo.etl.exception.EtlServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-21 23:41
 * @since 1.0
 */
@Repository
public interface EtlDimTableRepository extends JpaRepository<EtlDimTable, Integer>,
        JpaSpecificationExecutor<EtlDimTable> {

    /**
     * 根据数据库和表名查找某一个维表配置信息
     *
     * @param dbName    数据库名
     * @param tableName 维表名
     * @return 维表配置
     */
    EtlDimTable findByDbNameAndTableName(String dbName, String tableName);

    /**
     * 查询维表
     *
     * @param ids ID数组
     * @return 维表列表
     */
    List<EtlDimTable> findAllByIdIsIn(int[] ids);

    /**
     * 更新维表信息
     *
     * @param dbName      数据库名
     * @param tableName   维表名
     * @param field       字段
     * @param description 描述
     * @param updateOn    更新时间
     * @param updateBy    更新人
     * @param id          id
     */
    @Transactional(rollbackFor = {EtlServiceException.class})
    @Query(value = "update EtlDimTable dim set dim.dbName=:dbName," +
            "dim.tableName=:tableName,dim.field=:field,dim.description=:description," +
            "dim.updateOn=:updateOn,dim.updateBy=:updateBy where dim.id=:id")
    @Modifying
    void updateDimTable(@Param(value = "dbName") String dbName,
                        @Param(value = "tableName") String tableName,
                        @Param(value = "field") String field,
                        @Param(value = "description") String description,
                        @Param(value = "updateOn") long updateOn,
                        @Param(value = "updateBy") String updateBy,
                        @Param(value = "id") int id);

    /**
     * 是否禁用维表
     *
     * @param disable 是否禁用
     * @param id      ID
     */
    @Transactional(rollbackFor = {EtlServiceException.class})
    @Query(value = "update EtlDimTable dim set dim.disable = :disable where dim.id = :id")
    @Modifying
    void disableDimTable(@Param(value = "disable") int disable, @Param(value = "id") int id);
}
