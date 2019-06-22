package com.dr.leo.etl.controller;

import com.dr.leo.etl.common.ResponseRestResult;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 14:40
 * @since 1.0
 */
public abstract class BaseRestController {
    ResponseRestResult success() {
        return ResponseRestResult.ok();
    }

    ResponseRestResult success(Object t) {
        return ResponseRestResult.ok(t);
    }

    ResponseRestResult failed() {
        return ResponseRestResult.failed();
    }

    ResponseRestResult failed(String msg) {
        return ResponseRestResult.failed(msg);
    }
}
