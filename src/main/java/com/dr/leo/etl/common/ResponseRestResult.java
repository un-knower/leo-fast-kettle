package com.dr.leo.etl.common;
import java.io.Serializable;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 14:10
 * @since 1.0
 */
public class ResponseRestResult<T> implements Serializable {
    private static final long serialVersionUID = 343828705354668910L;

    private boolean success;
    private int code = 200;
    private String msg;
    private long timestamp = System.currentTimeMillis();
    private T payload;

    public ResponseRestResult() {

    }

    public ResponseRestResult(boolean success) {
        this.success = success;
    }

    public ResponseRestResult(boolean success, T payload) {
        this.success = success;
        this.payload = payload;
    }

    public ResponseRestResult<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public ResponseRestResult<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    public ResponseRestResult<T> code(int code) {
        this.code = code;
        return this;
    }

    public ResponseRestResult<T> message(String msg) {
        this.msg = msg;
        return this;
    }

    public static <T> ResponseRestResult<T> ok() {
        return (new ResponseRestResult<T>()).success(true);
    }

    public static <T> ResponseRestResult<T> ok(T payload) {
        return (new ResponseRestResult<T>()).success(true).payload(payload);
    }

    public static <T> ResponseRestResult<T> ok(T payload, int code) {
        return (new ResponseRestResult<T>()).success(true).payload(payload).code(code);
    }

    public static <T> ResponseRestResult<T> failed() {
        return (new ResponseRestResult<T>()).success(false);
    }

    public static <T> ResponseRestResult<T> failed(T payload) {
        return (new ResponseRestResult<T>()).success(false).payload(payload);
    }

    public static <T> ResponseRestResult<T> failed(String errorMsg) {
        return (new ResponseRestResult<T>()).success(false).message(errorMsg);
    }

    public static <T> ResponseRestResult<T> failed(T payload, int code) {
        return (new ResponseRestResult<T>()).success(false).payload(payload).code(code);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
