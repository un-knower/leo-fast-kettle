package com.dr.leo.etl.executor;

import com.dr.leo.etl.job.IJobHandler;
import com.dr.leo.etl.job.JobThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 22:30
 * @since 1.0
 */
public class EtlJobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EtlJobExecutor.class);
    private static ConcurrentHashMap<String, IJobHandler> jobHandlerRepository = new ConcurrentHashMap<>();

    public static IJobHandler registerJobHandler(String name, IJobHandler jobHandler) {
        logger.info(">>>>>>>>>>> xxl-job register jobhandler success, name:{}, jobHandler:{}", name, jobHandler);
        return jobHandlerRepository.put(name, jobHandler);
    }

    public static IJobHandler loadJobHandler(String name) {
        return jobHandlerRepository.get(name);
    }

    private static ConcurrentHashMap<Integer, JobThread> jobThreadRepository = new ConcurrentHashMap<>();

    public static JobThread registerJobThread(int jobId, IJobHandler handler, String removeOldReason) {
        JobThread newJobThread = new JobThread(jobId, handler);
        newJobThread.start();
        logger.info(">>>>>>>>>>> xxl-job regist JobThread success, jobId:{}, handler:{}", new Object[]{jobId, handler});
        JobThread oldJobThread = jobThreadRepository.put(jobId, newJobThread);
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }
        return newJobThread;
    }

    public static void removeJobThread(int jobId, String removeOldReason) {
        JobThread oldJobThrad = jobThreadRepository.remove(jobId);
        if (oldJobThrad != null) {
            oldJobThrad.toStop(removeOldReason);
            oldJobThrad.interrupt();
        }
    }

    public static JobThread loadJobThread(int jobId) {
        return jobThreadRepository.get(jobId);
    }


}
