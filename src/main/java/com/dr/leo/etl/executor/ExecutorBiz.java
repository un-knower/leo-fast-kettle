package com.dr.leo.etl.executor;

import com.dr.leo.etl.common.LogResult;
import com.dr.leo.etl.common.ResponseRestResult;
import com.dr.leo.etl.trigger.TriggerParam;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 21:50
 * @since 1.0
 */
public interface ExecutorBiz {
    /**
     * beat
     *
     * @return 执行结果
     */
    ResponseRestResult<String> beat();

    /**
     * idle beat
     *
     * @param jobId jobID
     * @return 执行结果
     */
    ResponseRestResult<String> idleBeat(int jobId);

    /**
     * kill
     *
     * @param jobId jobID
     * @return 执行结果
     */
    ResponseRestResult<String> kill(int jobId);

    /**
     * log
     *
     * @param logDateTim  日志时间
     * @param logId       日志id
     * @param fromLineNum 开始行号
     * @return 日志
     */
    ResponseRestResult<LogResult> log(long logDateTim, int logId, int fromLineNum);

    /**
     * run
     *
     * @param triggerParam 执行参数
     * @return 执行结果
     */
    ResponseRestResult<String> run(TriggerParam triggerParam);


}
