package com.dr.leo.etl.trigger;

import com.dr.leo.etl.job.HandleCallbackParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 请求回调线程
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 23:29
 * @since 1.0
 */
public class TriggerCallbackThread {
    private static Logger logger = LoggerFactory.getLogger(TriggerCallbackThread.class);
    private static TriggerCallbackThread instance = new TriggerCallbackThread();

    public static TriggerCallbackThread getInstance() {
        return instance;
    }

    /**
     * job results callback queue
     */
    private LinkedBlockingQueue<HandleCallbackParam> callBackQueue =
            new LinkedBlockingQueue<>();

    public static void pushCallBack(HandleCallbackParam callback) {
        getInstance().callBackQueue.add(callback);
        logger.debug(">>>>>>>>>>> xxl-job, push callback request, logId:{}", callback.getLogId());
    }

    /**
     * 回调线程
     */
    private Thread triggerCallbackThread;
    private Thread triggerRetryCallbackThread;

    private volatile boolean toStop = false;

    public void start() {
        triggerCallbackThread = new Thread(() -> {
            //正常回调
            while (!toStop) {
                try {
                    HandleCallbackParam callback = getInstance().callBackQueue.take();
                    //回调参数列表
                    List<HandleCallbackParam> callbackParamList = new ArrayList<>();
                    int drainToNum = getInstance().callBackQueue.drainTo(callbackParamList);
                    callbackParamList.add(callback);
                    if(callbackParamList!=null&&callbackParamList.size()>0){

                    }
                } catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
    }

    private void doCallback(List<HandleCallbackParam> callbackParamList){

    }

}
