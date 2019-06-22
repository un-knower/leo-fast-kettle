package com.dr.leo.etl.util;

import com.dr.leo.etl.common.LogResult;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 18:48
 * @since 1.0
 */
public class FileUtil {
    public static void write(String fileName, String content) {
        if (fileName == null || fileName.trim().length() == 0) {
            return;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        if (content == null) {
            content = "";
        }
        content += "\r\n";
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static LogResult read(String fileName, int fromLineNum) {
        File file = new File(fileName);
        StringBuilder logBuilder = new StringBuilder();
        int toLineNum = 0;
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file),
                    StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                toLineNum = reader.getLineNumber();
                if (toLineNum >= fromLineNum) {
                    logBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new LogResult(fromLineNum, toLineNum, logBuilder.toString(), false);
    }
}
