package com.dr.leo.etl.controller;

import com.dr.leo.etl.common.ResponseRestResult;
import com.dr.leo.etl.dto.EtlDimTableDto;
import com.dr.leo.etl.entity.EtlDimTable;
import com.dr.leo.etl.service.DimTableService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维表相关操作的API
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-21 22:07
 * @since 1.0
 */
@RestController
@RequestMapping("/leo/etl/api/dimTable")
public class DimTableRestController extends BaseRestController {
    private final DimTableService dimTableService;

    public DimTableRestController(DimTableService dimTableService) {
        this.dimTableService = dimTableService;
    }

    @PostMapping("/add")
    public ResponseRestResult addDimTable(@RequestBody EtlDimTableDto etlDimTableDto) {
        EtlDimTable etlDimTable = dimTableService.add(etlDimTableDto.convertTo());
        Map<String, EtlDimTable> data = new HashMap<>(1);
        data.put("etlDimTable", etlDimTable);
        return success(data);
    }

    @PostMapping("/update")
    public ResponseRestResult updateDimTable(@RequestBody EtlDimTableDto etlDimTableDto) {
        dimTableService.update(etlDimTableDto.convertTo());
        return success();
    }

    @GetMapping("/list")
    public ResponseRestResult allDimTables() {
        List<EtlDimTable> etlDimTableList = dimTableService.list();
        Map<String, List<EtlDimTable>> data = new HashMap<>(1);
        data.put("etlDimTableList", etlDimTableList);
        return success(data);
    }

    @GetMapping("/disable/{id}")
    public ResponseRestResult disableDimTable(@PathVariable int id) {
        dimTableService.disable(id);
        return success();
    }

    @GetMapping("/enable/{id}")
    public ResponseRestResult enableDimTable(@PathVariable int id) {
        dimTableService.enable(id);
        return success();
    }

    @GetMapping("/delete/{id}")
    public ResponseRestResult deleteDimTable(@PathVariable int id) {
        dimTableService.delete(id);
        return success();
    }

    @GetMapping("/flush")
    public ResponseRestResult flushDimTable(@RequestParam String tableIds) {
        int[] tableIdArr = Arrays.stream(tableIds.split(",")).mapToInt(Integer::valueOf)
                .toArray();
        dimTableService.work(tableIdArr);
        return success();
    }
}
