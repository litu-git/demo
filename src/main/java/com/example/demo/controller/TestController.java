package com.example.demo.controller;

import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/16 16:10
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String testHelloWorld(String req) {
        log.info("req:" + req);
        return testService.printString(req);
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();

    }

}
