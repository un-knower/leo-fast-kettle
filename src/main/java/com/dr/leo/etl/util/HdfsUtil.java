package com.dr.leo.etl.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 16:57
 * @since 1.0
 */
public class HdfsUtil {
    private final static Configuration CONF = new Configuration();
    private static FileSystem FS;

    static {
        CONF.setBoolean("fs.hdfs.impl.disable.cache", true);
        try {
            FS = FileSystem.get(CONF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> read(String filePath) {
        FSDataInputStream inputStream;
        List<String> contentList = new ArrayList<>(8);
        try {
            FS = FileSystem.get(CONF);
            Path path = new Path(filePath);
            inputStream = FS.open(path);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                contentList.add(new String(bytes));
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return contentList;
    }

    public static void write(String filePath, String content) {
        FSDataOutputStream outputStream;
        try {
            Path path = new Path(filePath);
            if (!FS.exists(path)) {
                FS.createNewFile(path);
            }
            outputStream = FS.append(path);
            Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(content);
            bufferedWriter.close();
            writer.close();
            outputStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
