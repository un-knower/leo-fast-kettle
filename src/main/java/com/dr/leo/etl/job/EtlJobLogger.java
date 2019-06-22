package com.dr.leo.etl.job;

import com.dr.leo.etl.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 19:51
 * @since 1.0
 */
public class EtlJobLogger {
    private static Logger logger = LoggerFactory.getLogger("Leo Fast Kettle Logger");

    private static void logDetail(StackTraceElement callInfo, String appendLog) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(DateUtil.formatDateTime(new Date())).append(" ")
                .append("[" + callInfo.getClassName() + "#" + callInfo.getMethodName() + "]").append("-")
                .append("[" + callInfo.getLineNumber() + "]").append("-")
                .append("[" + Thread.currentThread().getName() + "]").append(" ")
                .append(appendLog != null ? appendLog : "");
        String formatAppendLog = stringBuffer.toString();
        String logFileName = EtlJobLogAppender.CONTEXT_HOLDER.get();
        if (logFileName != null && logFileName.trim().length() > 0) {
            EtlJobLogAppender.appendLog(logFileName, formatAppendLog);
        } else {
            logger.info(">>>>>>>>>>> {}", formatAppendLog);
        }
    }

    public static void log(String appendLogPattern, Object... appendLogArguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(appendLogPattern, appendLogArguments);
        String appendLog = ft.getMessage();
        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        logDetail(callInfo, appendLog);
    }

    public static void log(Throwable e) {

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String appendLog = stringWriter.toString();
        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        logDetail(callInfo, appendLog);
    }

}
