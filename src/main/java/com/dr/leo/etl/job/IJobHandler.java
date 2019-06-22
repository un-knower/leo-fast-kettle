package com.dr.leo.etl.job;

import com.dr.leo.etl.common.ResponseRestResult;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 22:46
 * @since 1.0
 */
public abstract class IJobHandler {
    /**
     * success
     */
    public static final ResponseRestResult<String> SUCCESS = ResponseRestResult.ok();
    /**
     * fail
     */
    public static final ResponseRestResult<String> FAIL = ResponseRestResult.failed();
    /**
     * fail timeout
     */
    public static final ResponseRestResult<String> FAIL_TIMEOUT = ResponseRestResult.failed();


    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @param param
     * @return
     * @throws Exception
     */
    public abstract ResponseRestResult<String> execute(String param) throws Exception;


    /**
     * init handler, invoked when JobThread init
     */
    public void init() {
        // TODO
    }


    /**
     * destroy handler, invoked when JobThread destroy
     */
    public void destroy() {
        // TODO
    }
}
