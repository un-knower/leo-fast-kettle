package com.dr.leo.etl.thread;

import com.dr.leo.etl.trigger.EtlJobTrigger;
import com.dr.leo.etl.trigger.TriggerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务提交的线程管理器
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 20:38
 * @since 1.0
 */
public class JobTriggerPoolHelper {
    private static Logger logger = LoggerFactory.getLogger(JobTriggerPoolHelper.class);

    private ThreadPoolExecutor fastTriggerPool = new ThreadPoolExecutor(8, 200, 60L,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
            r -> new Thread(r, "Leo-Etl-Fast-Kettle-FastTriggerPool-" + r.hashCode()));

    private ThreadPoolExecutor slowTriggerPool = new ThreadPoolExecutor(0, 100, 60L,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000),
            r -> new Thread(r, "Leo-Etl-Fast-Kettle-SlowTriggerPool-" + r.hashCode()));

    private volatile long minTim = System.currentTimeMillis() / 60000;
    private volatile Map<Integer, AtomicInteger> jobTimeoutCountMap = new ConcurrentHashMap<>();

    public void addTrigger(final int jobId, final TriggerTypeEnum triggerType, final int failRetryCount) {
        ThreadPoolExecutor triggerpool = fastTriggerPool;
        AtomicInteger jobTimeoutCount = jobTimeoutCountMap.get(jobId);
        if (jobTimeoutCount != null && jobTimeoutCount.get() > 10) {
            triggerpool = slowTriggerPool;
        }

        triggerpool.execute(() -> {
            long start = System.currentTimeMillis();
            try {
                EtlJobTrigger.trigger(jobId, triggerType, failRetryCount);
            } finally {
                long minTimeNow = System.currentTimeMillis() / 60000;
                if (minTim != minTimeNow) {
                    minTim = minTimeNow;
                    jobTimeoutCountMap.clear();
                }
                long cost = System.currentTimeMillis() - start;
                if (cost > 500) {
                    AtomicInteger timeoutCount = jobTimeoutCountMap.put(jobId, new AtomicInteger(1));
                    if (timeoutCount != null) {
                        timeoutCount.incrementAndGet();
                    }
                }
            }
        });
    }

    public void stop() {
        fastTriggerPool.shutdownNow();
        slowTriggerPool.shutdownNow();
        logger.info(">>>>>>>>> etl job trigger thread pool shutdown success.");
    }

    private static JobTriggerPoolHelper helper = new JobTriggerPoolHelper();

    public static void trigger(int jobId, TriggerTypeEnum triggerType, int failRetryCount) {
        helper.addTrigger(jobId, triggerType, failRetryCount);
    }

    public static void toStop() {
        helper.stop();
    }
}
