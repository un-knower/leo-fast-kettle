package com.dr.leo.etl.exception;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-21 22:25
 * @since 1.0
 */
public class EtlServiceException extends RuntimeException {
    private static final long serialVersionUID = 1503892065063797652L;

    public EtlServiceException() {
        super();
    }

    public EtlServiceException(String message) {
        super((message));
    }

    public EtlServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
