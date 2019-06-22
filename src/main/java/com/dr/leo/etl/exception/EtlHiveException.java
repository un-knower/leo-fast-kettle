package com.dr.leo.etl.exception;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-21 22:25
 * @since 1.0
 */
public class EtlHiveException extends EtlServiceException {
    private static final long serialVersionUID = 55655119095773222L;

    public EtlHiveException() {
        super();
    }

    public EtlHiveException(String message) {
        super((message));
    }

    public EtlHiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
