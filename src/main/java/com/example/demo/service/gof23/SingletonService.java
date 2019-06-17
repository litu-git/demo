package com.example.demo.service.gof23;

import com.example.demo.service.gof23.impl.SingletonServiceImpl;

/**
 * 单例（Singleton）模式：某个类只能生成一个实例，该类提供了一个全局访问点供外部获取该实例，其拓展是有限多例模式
 *
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/23 14:48
 * @version: V1.0
 */
public interface SingletonService {

    /**
     * 根据参数返回单例对象
     *
     * @Author: litu[li_tu@suixingpay.com]
     * @Date: 18:21 2019/5/28
     **/
    SingletonServiceImpl getInstance(String type);
}
