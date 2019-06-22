package com.dr.leo.etl.dto;

import com.dr.leo.etl.entity.EtlDimTable;
import com.google.common.base.Converter;
import org.springframework.beans.BeanUtils;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 13:56
 * @since 1.0
 */
public class EtlDimTableDto {
    private int id;
    private String dbName;
    private String tableName;
    private String field;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EtlDimTable convertTo() {
        EtlDimTableDtoConvert etlDimTableDtoConvert = new EtlDimTableDtoConvert();
        return etlDimTableDtoConvert.convert(this);
    }

    public EtlDimTableDto convertFor(EtlDimTable etlDimTable) {
        EtlDimTableDtoConvert etlDimTableDtoConvert = new EtlDimTableDtoConvert();
        return etlDimTableDtoConvert.reverse().convert(etlDimTable);
    }

    private static class EtlDimTableDtoConvert extends Converter<EtlDimTableDto, EtlDimTable> {
        @Override
        protected EtlDimTable doForward(EtlDimTableDto etlDimTableDto) {
            EtlDimTable etlDimTable = new EtlDimTable();
            BeanUtils.copyProperties(etlDimTableDto, etlDimTable);
            return etlDimTable;
        }

        @Override
        protected EtlDimTableDto doBackward(EtlDimTable etlDimTable) {
            EtlDimTableDto etlDimTableDto = new EtlDimTableDto();
            BeanUtils.copyProperties(etlDimTable, etlDimTableDto);
            return etlDimTableDto;
        }
    }
}
