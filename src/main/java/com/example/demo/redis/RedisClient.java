/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: zhoumaowang<zhou_mw                               @                               suixingpay.com>
 * @date: 2017/3/22 上午11:01
 * @Copyright: ©2017 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.example.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis公共service
 *
 * @Author: litu[li_tu@suixingpay.com]
 * @Date: 17:38 2019/5/20
 **/
@Slf4j
public class RedisClient {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取缓存值
     *
     * @param cacheKey
     * @return
     */
    public String getCacheValue(String cacheKey) {
        String cacheValue = null;
        try {
            cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.error("redis获取数据异常{}", e);
        }
        return cacheValue;
    }

    /**
     * 设置缓存值
     *
     * @param key
     * @param value
     */
    public void setCacheValue(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("redis设置数据异常{}", e);
        }
    }

    /**
     * 设置多组缓存值
     *
     * @param
     */
    public void setMultiCacheValue(Map<String, String> redisMap) {
        redisTemplate.opsForValue().multiSet(redisMap);
    }

    /**
     * 设置缓存值并设置有效期
     *
     * @param key
     * @param value
     */
    public void setCacheValueForTime(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 删除key值
     *
     * @param key
     */
    public void delCacheByKey(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 删除keys值
     *
     * @param keys
     */
    public void delCacheByKey(Set<String> keys) {
        redisTemplate.opsForValue().getOperations().delete(keys);
    }

    /**
     * 获取token的有效期
     *
     * @param key
     */
    public long getExpireTime(String key) {
        long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return time;
    }

    /**
     * 设置token的有效期
     *
     * @param key
     * @return
     */
    public boolean setExpireTime(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @return boolean
     * @Title exists
     * @Description 判断key是否存在
     */
    public boolean exists(String key) {
        return ((Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        })).booleanValue();
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 上午11:00:48
     * @version: V1.0
     */
    public ZSetOperations<String, String> zSet() {
        return redisTemplate.opsForZSet();
    }

    /**
     * @return the redisTemplate
     */
    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

}
