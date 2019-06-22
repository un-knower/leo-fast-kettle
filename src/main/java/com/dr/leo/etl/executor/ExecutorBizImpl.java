package com.dr.leo.etl.executor;

import com.dr.leo.etl.common.LogResult;
import com.dr.leo.etl.common.ResponseRestResult;
import com.dr.leo.etl.job.EtlJobLogAppender;
import com.dr.leo.etl.trigger.TriggerParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 21:54
 * @since 1.0
 */
public class ExecutorBizImpl implements ExecutorBiz {
    private static Logger logger = LoggerFactory.getLogger(ExecutorBizImpl.class);

    @Override
    public ResponseRestResult<String> beat() {
        return ResponseRestResult.ok();
    }

    @Override
    public ResponseRestResult<String> idleBeat(int jobId) {
        boolean isRunningOrHasQueue = false;

        return null;
    }

    @Override
    public ResponseRestResult<String> kill(int jobId) {
        return null;
    }

    @Override
    public ResponseRestResult<LogResult> log(long logDateTim, int logId, int fromLineNum) {
        String logFileName = EtlJobLogAppender.makeLogFileName(new Date(logDateTim), logId);
        LogResult logResult = EtlJobLogAppender.readLog(logFileName, fromLineNum);
        return ResponseRestResult.ok(logResult);
    }

    @Override
    public ResponseRestResult<String> run(TriggerParam triggerParam) {
        return null;
    }
}
