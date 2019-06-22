package com.dr.leo.etl.service

import java.util

import com.dr.leo.etl.dao.EtlDimTableRepository
import com.dr.leo.etl.entity.EtlDimTable
import com.dr.leo.etl.exception.EtlServiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * @author leo.jie (weixiao.me@aliyun.com)
  * @organization DataReal
  * @version 1.0
  * @website https://www.jlpyyf.com
  * @date 2019-06-21 23:11
  * @since 1.0
  */
@Service
class DimTableService {
  @Autowired
  private val hiveService: HiveService = null
  @Autowired
  private val etlDimTableRepository: EtlDimTableRepository = null

  def list(): util.List[EtlDimTable] = {
    etlDimTableRepository.findAll(new Sort(Sort.Direction.ASC, "tableName"))
  }

  def findByDbNameAndTableName(dbName: String, tableName: String): EtlDimTable = {
    etlDimTableRepository.findByDbNameAndTableName(dbName, tableName)
  }

  def add(etlDimTable: EtlDimTable): EtlDimTable = {
    if (null != findByDbNameAndTableName(etlDimTable.getDbName, etlDimTable.getTableName)) {
      throw new EtlServiceException(s"维表[${etlDimTable.getDbName}.${etlDimTable.getTableName}]已经存在！")
    }
    etlDimTable.setCreateOn(System.currentTimeMillis())
    etlDimTable.setCreateBy("leo")
    etlDimTable.setUpdateOn(0L)
    etlDimTable.setDisable(0)
    etlDimTableRepository.save(etlDimTable)
  }

  def update(etlDimTable: EtlDimTable): Unit = {
    val etlDimTableExists = findByDbNameAndTableName(etlDimTable.getDbName, etlDimTable.getTableName)
    if (null != etlDimTableExists && (etlDimTableExists.getId != etlDimTable.getId)) {
      throw new EtlServiceException(s"维表[${etlDimTable.getDbName}.${etlDimTable.getTableName}]已经存在！")
    }
    etlDimTable.setUpdateOn(System.currentTimeMillis())
    etlDimTable.setUpdateBy("leo")
    etlDimTableRepository.updateDimTable(etlDimTable.getDbName, etlDimTable.getTableName, etlDimTable.getField,
      etlDimTable.getDescription, etlDimTable.getUpdateOn, etlDimTable.getUpdateBy, etlDimTable.getId)
  }

  def disable(id: Int): Unit = {
    etlDimTableRepository.disableDimTable(1, id)
  }

  def enable(id: Int): Unit = {
    etlDimTableRepository.disableDimTable(0, id)
  }

  def delete(id: Int): Unit = {
    etlDimTableRepository.delete(id)
  }

  def work(ids: Array[Int]): Unit = {
    val dimTables: mutable.Buffer[EtlDimTable] = etlDimTableRepository.findAllByIdIsIn(ids).asScala
    for (dimTable <- dimTables) {
      //只刷新不禁用的维表
      if (dimTable.getDisable == 0) {
        createDimTable(dimTable)
      }
    }
  }

  private def createDimTable(dimTable: EtlDimTable): Unit = {
    val fields: Array[(String, String)] = createFieldsOfDimTable(dimTable)
    hiveService.createDimTable(dimTable.getDbName, dimTable.getTableName, fields)
  }

  private def createFieldsOfDimTable(dimTable: EtlDimTable): Array[(String, String)] = {
    if (null == dimTable.getField) {
      null
    } else {
      val fields: Array[String] = dimTable.getField.split(",")
      fields.map(f => for (d <- f.trim.split("\\s+")) yield d.trim)
        .map(fArr => (fArr(0), fArr(1) + " " + fArr(2) + " " + fArr(3)))
    }
  }
}
