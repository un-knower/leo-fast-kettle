package com.dr.leo.etl.util

import java.util

object StrUtil {
  def main(args: Array[String]): Unit = {
    val contents: util.List[String] = HdfsUtil.read("/test/error1.log")
    for (i <- 0 until contents.size()) {
      println(contents.get(i))
    }
  }
}
