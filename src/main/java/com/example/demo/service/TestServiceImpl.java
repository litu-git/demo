package com.example.demo.service;

import com.example.demo.utils.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/20 16:55
 */
@Service
public class TestServiceImpl implements TestService {

    // 20190619测试springboot自动加载application.properties配置文件
    @Value("${litu.name}")
    private String myname;
    @Value("${litu.sex}")
    private String mysex;
    @Value("${litu.age}")
    private String myage;

    private String LITU_NAME = Config.getString("litu.name");

    @Override
    public String printString(String req) {
        if (StringUtils.isEmpty(req)) {
            req = "Hello World";
        }
        String like = "漂亮";
        if ("男生".equals(mysex)) {
            like = "帅气";
        }

        String result = "我的名字叫" + myname + "，今年" + myage + "岁了，是个" + like + "的" + mysex + ",我想对你说：" + req;
        System.out.println(LITU_NAME);
        return result;
    }
}
