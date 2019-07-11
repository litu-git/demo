package com.example.demo.controller;

import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/16 16:10
 * @version: V1.0
 */
@Slf4j
@RestController
public class Test2Controller extends TestController{

    @Autowired
    TestService testService;

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public String testHelloWorld(String req) {
        log.info("req:" + req);
        return testService.printString(req);
    }

}
