package com.dr.leo.etl.exception;

import com.dr.leo.etl.common.ResponseRestResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 16:26
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {
    @Override
    @ExceptionHandler(EtlServiceException.class)
    public ResponseRestResult<String> serviceExceptionHandler(EtlServiceException e) {
        String error = e.getMessage();
        return ResponseRestResult.failed(error);
    }

    @Override
    @ExceptionHandler(Exception.class)
    public ResponseRestResult<String> exceptionHandler(Exception e) {
        return super.exceptionHandler(e);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseRestResult<String> validException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        return super.validException(fieldError);
    }

    @Override
    @ExceptionHandler({BindException.class})
    public ResponseRestResult<String> bindException(BindException e) {
        return super.bindException(e);
    }
}
