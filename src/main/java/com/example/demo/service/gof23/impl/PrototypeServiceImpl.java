package com.example.demo.service.gof23.impl;

import com.example.demo.service.gof23.PrototypeService;
import org.springframework.data.annotation.Version;
import org.springframework.stereotype.Service;

/**
 * 原型（Prototype）模式:用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象。
 * 在这里，原型实例指定了要创建的对象的种类。用这种方式创建对象非常高效，根本无须知道对象创建的细节。
 *
 * @Author li_tu@suixingpay.com
 * @Date 2019-06-20 17:15
 * @Version 1.0
 */
@Service
public class PrototypeServiceImpl implements PrototypeService, Cloneable {

    public PrototypeServiceImpl() {
        System.out.println("具体原型创建成功！");
    }

    public Object clone() throws CloneNotSupportedException
    {
        System.out.println("具体原型复制成功！");
        return (PrototypeServiceImpl)super.clone();
    }
}
