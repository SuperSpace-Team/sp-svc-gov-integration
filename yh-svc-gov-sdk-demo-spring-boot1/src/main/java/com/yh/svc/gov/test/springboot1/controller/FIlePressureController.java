//package com.yh.svc.gov.test.springboot1.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yh.svc.gov.test.springboot1.service.FilePressureService;
//
//@RestController
//@RequestMapping("/file")
//public class FIlePressureController{
//
//    @Autowired
//    private FilePressureService filePressureService;
//
//    @GetMapping("/test")
//    public String test(){
//        return "ok";
//    }
//
//    @GetMapping("/start")
//    public String start(@RequestParam String tenantId,@RequestParam String folder){
//        filePressureService.start(tenantId, folder);
//        return "ok";
//    }
//
//    @GetMapping("/stop")
//    public String stop(){
//        filePressureService.stop();
//        return "ok";
//    }
//
//    @GetMapping("/update")
//    public String updateThreadCount(@RequestParam int corePoolSize,@RequestParam int maximumPoolSize){
//        filePressureService.updateThreadCount(corePoolSize,maximumPoolSize);
//        return "ok";
//    }
//
//
//}
