package com.example.demo.service.gof23.impl;

import com.example.demo.service.gof23.SingletonService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 单例（Singleton）模式：某个类只能生成一个实例，该类提供了一个全局访问点供外部获取该实例，其拓展是有限多例模式
 *
 * @author: litu[li_tu@suixingpay.com]
 * @date: 2019/5/23 15:22
 * @version: V1.0
 */
@Service
public class SingletonServiceImpl implements SingletonService {

    private static volatile SingletonServiceImpl instance = null;    //保证 instance 在所有线程中同步

    private SingletonServiceImpl() {
    }    //private 避免类在外部被实例化

    @Override
    public SingletonServiceImpl getInstance(String type) {
        if (StringUtils.isEmpty(type) || "1".equals(type)) {
            return getInstancelazySingleton();
        }
        if ("2".equals(type)) {

        }
        return getInstancelazySingleton();
    }

    /**
     * 懒汉式单例:如果编写的是多线程程序，则不要删除上例代码中的关键字 volatile 和 synchronized，否则将存在线程非安全的问题。
     * 如果不删除这两个关键字就能保证线程安全，但是每次访问时都要同步，会影响性能，且消耗更多的资源，这是懒汉式单例的缺点。
     *
     * @Author: litu[li_tu@suixingpay.com]
     * @Date: 18:26 2019/5/28
     **/
    private static synchronized SingletonServiceImpl getInstancelazySingleton() {
        //getInstance 方法前加同步
        if (instance == null) {
            instance = new SingletonServiceImpl();
        }
        return instance;
    }


    // *****饿汉式单例start*****
    /**
     * 该模式的特点是类一旦加载就创建一个单例，保证在调用 getInstance 方法之前单例已经存在了。
     * 饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以是线程安全的，可以直接用于多线程而不会出现问题。
     * @Author: litu[li_tu@suixingpay.com]
     * @Date: 9:33 2019/5/29
     **/
    private static final SingletonServiceImpl hungrySingleton=new SingletonServiceImpl();
    private static SingletonServiceImpl getInstanceHungrySingleton(){
        return hungrySingleton;
    }
    // *****饿汉式单例end*****
}
