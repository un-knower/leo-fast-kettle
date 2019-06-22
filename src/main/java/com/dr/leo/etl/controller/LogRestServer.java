package com.dr.leo.etl.controller;

import com.dr.leo.etl.common.ResponseRestResult;
import com.dr.leo.etl.util.FileUtil;
import com.dr.leo.etl.util.HdfsUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 18:36
 * @since 1.0
 */
@RestController
@RequestMapping("/leo/etl/api/log")
public class LogRestServer extends BaseRestController {
    @GetMapping("/detail/{jobId}")
    public ResponseRestResult logDetail(@PathVariable int jobId) {
        List<String> logContent = HdfsUtil.read("/test/error4.log");
        return success(logContent);
    }

    @GetMapping("/detailLocal/{jobId}")
    public ResponseRestResult logDetailLocal(@PathVariable int jobId, @RequestParam String filePath) {
        String logContent = FileUtil.read(filePath);
        return success(logContent);
    }

    @GetMapping("/write")
    public ResponseRestResult write(@RequestParam String filePath) {
        FileUtil.write(filePath, "19/06/22 19:00:39 INFO DispatcherServlet: FrameworkServlet " +
                "'dispatcherServlet': initialization completed in 34 ms 结束！");
        return success();
    }
}
