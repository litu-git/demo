package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/20 16:55
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String printString(String req) {
        if(StringUtils.isEmpty(req)){
            req = "Hello World";
        }
        return req;
    }
}
