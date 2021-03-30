package com.yh.svc.gov.test.springboot1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.log.GsLogService;

/**
 * @author hsh10697
 */
@RestController
@RequestMapping("")
public class GsLogController {

    @Autowired
    private GsLogService gsLogService;

    @PostMapping("/log/add")
    public BaseResponseEntity<String> add(@RequestBody Map<String, Object> params, @RequestParam String logType) {
        gsLogService.log(logType, params);
        return new BaseResponseEntity<>(200, "success");
    }
}
