package com.dr.leo.etl.util

import java.util

/**
  * @author leo.jie (weixiao.me@aliyun.com)
  * @organization DataReal
  * @version 1.0
  * @website https://www.jlpyyf.com
  * @date 2019-06-22 17:25
  * @since 1.0
  */
object TestUtil {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 100) {
      HdfsUtil.write("/test/error4.log", s"${i + 1} - i Love YYf!我爱杨雨凡！\n")
    }
    val contents: util.List[String] = HdfsUtil.read("/test/error4.log")
    for (i <- 0 until contents.size()) {
      println(contents.get(i))
    }
  }
}
