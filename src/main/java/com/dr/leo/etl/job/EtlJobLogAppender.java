package com.dr.leo.etl.job;

import com.dr.leo.etl.common.LogResult;
import com.dr.leo.etl.util.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * etl系统日志输出
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 19:28
 * @since 1.0
 */
public class EtlJobLogAppender {
    public static final InheritableThreadLocal<String> CONTEXT_HOLDER = new InheritableThreadLocal<>();
    private static String logBasePath = "/data/leo-fast-kettle/job/logs/";

    public static void initLogPath(String logPath) {
        if (logPath != null && logPath.trim().length() > 0) {
            logBasePath = logPath;
        }
        File logPathDir = new File(logBasePath);
        if (!logPathDir.exists()) {
            logPathDir.mkdirs();
        }
        logBasePath = logPathDir.getPath();
    }

    public static String getLogPath() {
        return logBasePath;
    }

    public static String makeLogFileName(Date submitDate, int jobId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File logFilePath = new File(getLogPath(), sdf.format(submitDate));
        if (!logFilePath.exists()) {
            logFilePath.mkdir();
        }
        return logFilePath.getPath()
                .concat(File.separator)
                .concat(String.valueOf(jobId))
                .concat(".log");
    }

    public static void appendLog(String logFileName, String appendLog) {
        FileUtil.write(logFileName, appendLog);
    }

    public static LogResult readLog(String logFileName, int fromLineNum) {
        if (logFileName == null || logFileName.trim().length() == 0) {
            return new LogResult(fromLineNum, 0, "readLog fail, logFile not found", true);
        }
        File logFile = new File(logFileName);

        if (!logFile.exists()) {
            return new LogResult(fromLineNum, 0, "readLog fail, logFile not exists", true);
        }

        return FileUtil.read(logFileName, fromLineNum);
    }
}
