package com.example.demo.utils;

import java.util.ResourceBundle;


/**
 *
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2018年11月5日 下午2:32:55
 * @version: V1.0
 * @review: litu[li_tu@suixingpay.com]/2018年11月5日 下午2:32:55
 */
public class Config {
    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:33:00
     * @version: V1.0
     */
    public static String getString(String key) {
        // crs_core(resources包下的第一层)/dao(resources包下的第二层)/dao(resources包下的第三层)
        ResourceBundle rb = ResourceBundle.getBundle("crs_core/dao/dao", new java.util.Locale("zh", "CN"));
        return rb.getString(key);
    }

}
