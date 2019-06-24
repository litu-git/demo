package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.service.gof23.impl.PrototypeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 原型模式测试类
 *
 * @Author li_tu@suixingpay.com
 * @Date $date$ $time$
 * @Version 1.0
 */
public class PrototypeTest extends DemoApplicationTests {
    @Autowired
    private PrototypeServiceImpl prototypeServiceImpl;

    @Test
    public void test1() throws CloneNotSupportedException {
        PrototypeServiceImpl obj = new PrototypeServiceImpl();
        PrototypeServiceImpl obj1 = (PrototypeServiceImpl) prototypeServiceImpl.clone();
        PrototypeServiceImpl obj2 = (PrototypeServiceImpl) prototypeServiceImpl.clone();
        System.out.println("obj==obj1?" + (obj == obj1));
        System.out.println("obj==obj2?" + (obj == obj2));
        System.out.println("obj1==obj2?" + (obj1 == obj2));
    }
}
