package com.example.demo.controller;

import com.example.demo.service.gof23.SingletonService;
import com.example.demo.service.gof23.impl.SingletonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单例模式接口
 *
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/28 18:12
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/singleton")
public class SingletonController {

    @Autowired
    SingletonService singletonService;

    /**
     * 懒汉单例
     * @Author: litu[li_tu@suixingpay.com]
     * @Date: 18:14 2019/5/28
     **/
    @RequestMapping(value = "/lazySingleton", method = RequestMethod.GET)
    public Object lazySingleton(String req) {
        SingletonServiceImpl instanceLazySingleton = singletonService.getInstance(req);
        log.info("懒汉单例对象:{}" , instanceLazySingleton);
        return instanceLazySingleton;
    }
}
