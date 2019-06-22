package com.dr.leo.etl.job;

import com.dr.leo.etl.common.ResponseRestResult;
import com.dr.leo.etl.executor.EtlJobExecutor;
import com.dr.leo.etl.trigger.TriggerParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 22:52
 * @since 1.0
 */
public class JobThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(JobThread.class);
    private int jobId;
    private IJobHandler handler;
    private LinkedBlockingQueue<TriggerParam> triggerQueue;
    private Set<Integer> triggerLogIdSet;

    private volatile boolean toStop = false;
    private String stopReason;

    private boolean running = false;
    private int idleTimes = 0;

    public JobThread(int jobId, IJobHandler handler) {
        this.jobId = jobId;
        this.handler = handler;
        this.triggerQueue = new LinkedBlockingQueue<>();
        this.triggerLogIdSet = Collections.synchronizedSet(new HashSet<>());
    }

    public IJobHandler getHandler() {
        return handler;
    }

    public ResponseRestResult<String> pushTriggerQueue(TriggerParam triggerParam) {
        if (triggerLogIdSet.contains(triggerParam.getLogId())) {
            logger.info(">>>>>>>>>>> repeat trigger job, logId:{}", triggerParam.getLogId());
            return ResponseRestResult.failed("repeat trigger job, logId:" + triggerParam.getLogId());
        }
        triggerLogIdSet.add(triggerParam.getLogId());
        triggerQueue.add(triggerParam);
        return ResponseRestResult.ok();
    }

    public void toStop(String stopReason) {
        /**
         * Thread.interrupt只支持终止线程的阻塞状态(wait、join、sleep)，
         * 在阻塞出抛出InterruptedException异常,但是并不会终止运行的线程本身；
         * 所以需要注意，此处彻底销毁本线程，需要通过共享变量方式；
         */
        this.toStop = true;
        this.stopReason = stopReason;
    }

    public boolean isRunningOrHasQueue() {
        return running || triggerQueue.size() > 0;
    }

    @Override
    public void run() {
        try {
            handler.init();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        //execute
        while (!toStop) {
            running = false;
            idleTimes++;
            TriggerParam triggerParam = null;
            ResponseRestResult<String> executeResult = null;
            try {
                triggerParam = triggerQueue.poll(3L, TimeUnit.SECONDS);
                if (triggerParam != null) {
                    running = true;
                    idleTimes = 0;
                    triggerLogIdSet.remove(triggerParam.getLogId());
                    //log fileName
                    String logFileName = EtlJobLogAppender
                            .makeLogFileName(new Date(triggerParam.getLogDateTim()), triggerParam.getLogId());
                    EtlJobLogAppender.CONTEXT_HOLDER.set(logFileName);
                    // execute
                    EtlJobLogger.log("<br>----------- xxl-job job execute start -----------<br>----------- ");
                    if (triggerParam.getExecutorTimeout() > 0) {
                        //limit timeout
                        Thread futureThread = null;
                        try {
                            final TriggerParam triggerParamTmp = triggerParam;
                            FutureTask<ResponseRestResult<String>> futureTask =
                                    new FutureTask<>(() -> handler.execute("暂不支持参数"));
                            futureThread = new Thread(futureTask);
                            futureThread.start();
                            executeResult = futureTask.get(triggerParam.getExecutorTimeout(), TimeUnit.SECONDS);
                        } catch (TimeoutException e) {
                            EtlJobLogger.log("<br>----------- xxl-job job execute timeout");
                            EtlJobLogger.log(e);
                        } finally {
                            if (futureThread != null) {
                                futureThread.interrupt();
                            }
                        }
                    } else {
                        //just execute
                        executeResult = handler.execute("暂不支持参数");
                    }
                    if (executeResult == null) {
                        executeResult = IJobHandler.FAIL;
                    } else {
                        executeResult.setMsg(executeResult.getMsg() != null && executeResult.getMsg().length() > 50000
                                ? executeResult.getMsg().substring(0, 50000).concat("...")
                                : executeResult.getMsg());
                        executeResult.setPayload(null);
                    }
                    EtlJobLogger.log("<br>----------- xxl-job job execute end(finish) -----------<br>----------- ReturnT:" + executeResult);
                } else {
                    if (idleTimes > 30) {
                        EtlJobExecutor.removeJobThread(jobId, "executor idle times over limit.");
                    }
                }
            } catch (Throwable e) {
                if (toStop) {
                    EtlJobLogger.log("<br>----------- JobThread toStop, stopReason:" + stopReason);
                }
                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                String errorMsg = stringWriter.toString();
                executeResult = ResponseRestResult.failed(errorMsg);
                EtlJobLogger.log("<br>----------- JobThread Exception:" + errorMsg + "<br>----------- xxl-job job execute end(error) -----------");
            } finally {
                if (triggerParam != null) {
                    //执行回调
                    if(!toStop){
                        //正常获取结果

                    }else{
                        // is killed

                    }
                }
            }
        }

    }
}
