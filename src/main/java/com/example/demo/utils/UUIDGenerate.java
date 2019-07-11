package com.example.demo.utils;

import java.util.UUID;

/**
 * 生成UUID
 *
 * @Author li_tu@suixingpay.com
 * @Date 2019-07-06 10:42
 * @Version 1.0
 */
public class UUIDGenerate {
    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月2日 下午3:05:20
     * @version: V1.0
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
