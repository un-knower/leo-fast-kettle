package com.dr.leo.etl.exception;

import com.dr.leo.etl.common.ResponseRestResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 16:27
 * @since 1.0
 */
public abstract class BaseExceptionHandler {
    protected ResponseRestResult<String> serviceExceptionHandler(EtlServiceException e) {
        e.printStackTrace();
        return ResponseRestResult.failed(e.getMessage());
    }

    protected ResponseRestResult<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseRestResult.failed("你把系统玩炸了，开发人员正在火速救场！");
    }

    protected ResponseRestResult<String> validException(FieldError fieldError) {
        String errorMsg = "参数校验异常！";
        if (null != fieldError) {
            errorMsg = fieldError.getDefaultMessage();
        }
        return ResponseRestResult.failed(errorMsg);
    }

    protected ResponseRestResult<String> bindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMsg = "必填校验异常！";
        if (null != fieldError) {
            errorMsg = fieldError.getDefaultMessage();
        }
        return ResponseRestResult.failed(errorMsg);
    }

}
