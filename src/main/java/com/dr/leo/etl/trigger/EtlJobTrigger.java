package com.dr.leo.etl.trigger;

import com.dr.leo.etl.common.ResponseRestResult;
import com.dr.leo.etl.job.EtlJobInfo;
import com.dr.leo.etl.job.EtlJobLog;

import java.util.Date;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 21:24
 * @since 1.0
 */
public class EtlJobTrigger {
    public static void trigger(int jobId, TriggerTypeEnum triggerType, int failRetryCount) {
        EtlJobInfo jobInfo = new EtlJobInfo();
        //TODO jobInfo
        int finalFailRetryCount = failRetryCount >= 0 ? failRetryCount : jobInfo.getExecutorFailRetryCount();
        processTrigger(jobInfo, finalFailRetryCount, triggerType);
    }

    private static void processTrigger(EtlJobInfo jobInfo, int finalFailRetryCount, TriggerTypeEnum triggerType) {
        //1. save log
        EtlJobLog jobLog = new EtlJobLog();
        jobLog.setJobId(jobInfo.getId());
        jobLog.setTriggerTime(new Date());
        //TODO save log info
        //2. init trigger-param
        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(jobInfo.getId());
        triggerParam.setExecutorTimeout(jobInfo.getExecutorTimeout());
        triggerParam.setLogId(jobLog.getId());
        triggerParam.setLogDateTim(jobLog.getTriggerTime().getTime());
        //执行
    }

    public static ResponseRestResult<String> runExecutor(TriggerParam triggerParam) {
        ResponseRestResult<String> runResult = null;
        return runResult;
    }

}
