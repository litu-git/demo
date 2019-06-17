/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: WangYanChao[wang_yc@suixingpay.com]
 * @date: 2017年4月14日 下午6:08:26
 * @Copyright ©2017 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.example.demo.utils;

/**
 * 时间日期格式枚举类
 *
 * @Author: litu[li_tu@suixingpay.com]
 * @Date: 18:04 2019/5/20
 **/
public enum DateEnum {
    /**
     * yyyyMMddHHmmss
     */
    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    /**
     * yyyyMMdd HHmmss
     */
    yyyyMMdd_KONG_HHmmss("yyyyMMdd HHmmss"),
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    yyyy_HENG_MM_HENG_dd_KONG_HH_MAO_mm_MAO_ss("yyyy-MM-dd HH:mm:ss"),
    /**
     * yyyyMMdd HH:mm:ss
     */
    yyyyMMdd_KONG_HH_MAO_mm_MAO_ss("yyyyMMdd HH:mm:ss"),
    /**
     * yyyy年MM月dd日HH:mm:ss
     */
    yyyy_NIAN_MM_YUE_dd_RI_HH_MAO_mm_MAO_ss("yyyy年MM月dd日HH:mm:ss"),
    /**
     * yyyy年MM月dd日 HH:mm:ss
     */
    yyyy_NIAN_MM_YUE_dd_RI_KONG_HH_MAO_mm_MAO_ss("yyyy年MM月dd日 HH:mm:ss"),
    /**
     * HH:mm:ss
     */
    HH_MAO_mm_MAO_ss("HH:mm:ss"),
    /**
     * yyyyMMdd
     */
    yyyyMMdd("yyyyMMdd"),
    /**
     * yyyy-MM-dd
     */
    yyyy_HENG_MM_HENG_dd("yyyy-MM-dd"),
    /**
     * yyyy年MM月dd日
     */
    yyyy_NIAN_MM_YUE_dd_RI("yyyy年MM月dd日"),
    /**
     * HHmmss
     */
    HHmmss("HHmmss");

    private String dateFormat;

    DateEnum(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
