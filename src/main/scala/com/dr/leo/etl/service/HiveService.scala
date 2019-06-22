package com.dr.leo.etl.service

import com.dr.leo.etl.exception.EtlHiveException
import com.dr.leo.etl.model.PartitionsModel
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HiveService {
  @Autowired
  private val sparkSession: SparkSession = null

  def tableIsExist(dbName: String, tableName: String): Boolean = {
    val tables = sparkSession.sqlContext.tableNames(dbName)
    tables.contains(tableName)
  }

  def dropTable(dbName: String, tableName: String): Boolean = {
    if (tableIsExist(dbName, tableName)) {
      sparkSession.sql(s"drop table if exists $dbName.$tableName").show()
      true
    } else {
      false
    }
  }

  private def createTable(dbName: String, tableName: String, fields: Array[(String, String)],
                          partitions: Array[(String, String)] = null, fileFormat: String = "parquet"): Boolean = {
    if (null == fields || fields.isEmpty) {
      throw new EtlHiveException(s"表$dbName.$tableName 创建时没有指定需要创建的字段！")
    }
    var fields_ = ""
    for (field <- fields) {
      fields_ += s"${field._1} ${field._2},"
    }
    fields_ = fields_.dropRight(1)
    var sql = ""
    if (null == partitions || partitions.isEmpty) {
      sql =
        s"""
           |create table $dbName.$tableName(
           |$fields_
           |)
           |stored as $fileFormat
        """.stripMargin
    } else {
      var partitions_ = ""
      for (partition <- partitions) {
        partitions_ += s"${partition._1} ${partition._2},"
      }
      partitions_ = partitions_.dropRight(1)
      sql =
        s"""
           |create table $dbName.$tableName(
           |$fields_
           |)
           |partitioned by($partitions_)
           |stored as $fileFormat
        """.stripMargin
    }
    sparkSession.sql(sql).show()
    tableIsExist(dbName, tableName)
  }

  def createDimTable(dbName: String, tableName: String, fields: Array[(String, String)]): Boolean = {
    if (tableIsExist(dbName, tableName)) {
      dropTable(dbName, tableName)
      createTable(dbName, tableName, fields)
    } else {
      createTable(dbName, tableName, fields)
    }
  }

  def dropPartitions(dbName: String, tableName: String, partitions: PartitionsModel*): Boolean = {
    if (null == partitions || partitions.isEmpty) {
      throw new EtlHiveException(s"没有指定要删除 $dbName.$tableName 的分区条件！")
    } else if (!tableIsExist(dbName, tableName)) {
      true
    } else {
      var p = ""
      for (partition <- partitions) {
        p += s"${partition.partitionKey}='${partition.partitionValue}',"
      }
      p = p.dropRight(0)
      try {
        sparkSession.sql(s"ALTER TABLE $dbName.$tableName " +
          s"DROP IF EXISTS PARTITION($p)").show()
      } catch {
        case e: Exception => e.printStackTrace()
          throw new EtlHiveException(s"$dbName.$tableName 分区数据删除时出现异常")
      }
      true
    }
  }

}
