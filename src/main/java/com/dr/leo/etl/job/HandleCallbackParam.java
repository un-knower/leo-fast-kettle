package com.dr.leo.etl.job;

import com.dr.leo.etl.common.ResponseRestResult;

import java.io.Serializable;

/**
 * 执行回调
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 23:27
 * @since 1.0
 */
public class HandleCallbackParam implements Serializable {
    private int logId;
    private long logDateTime;
    private ResponseRestResult<String> executeResult;

    public HandleCallbackParam() {
    }

    public HandleCallbackParam(int logId, long logDateTime, ResponseRestResult<String> executeResult) {
        this.logId = logId;
        this.logDateTime = logDateTime;
        this.executeResult = executeResult;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public long getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(long logDateTime) {
        this.logDateTime = logDateTime;
    }

    public ResponseRestResult<String> getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(ResponseRestResult<String> executeResult) {
        this.executeResult = executeResult;
    }

    @Override
    public String toString() {
        return "HandleCallbackParam{" +
                "logId=" + logId +
                ", logDateTime=" + logDateTime +
                ", executeResult=" + executeResult +
                '}';
    }
}
