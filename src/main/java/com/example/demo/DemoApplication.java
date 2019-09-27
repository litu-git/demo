package com.example.demo;

import com.example.demo.utils.StartApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "com")
public class DemoApplication {

    /**
     * gradledemo
     *
     * @Author: litu[li_tu@suixingpay.com]
     * @Date: 14:53 2019/5/20
     **/
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        log.info(StartApplicationUtil.printBuddha());
    }

}
