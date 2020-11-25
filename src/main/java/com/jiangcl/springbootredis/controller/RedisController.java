package com.jiangcl.springbootredis.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

/**
 * @author jiangcl
 * @date 2020/11/25
 * @desc redis测试
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("cluster")
    public String redisCluster(){
        String response = "success";
        try {
            jedisCluster.set("name","lisi");
        } catch (Exception e) {
            e.printStackTrace();
            response = "false";
        }
        return response;
    }

    @RequestMapping("redisson")
    public String redisson(){
        String response = "success";
        try {
            RLock lock = redissonClient.getLock("myLock");
            lock.lock();
            System.out.println("在获取锁的情况下打印了这句话。。。。。。");
            lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
            response = "false";
        }
        return response;
    }

    @RequestMapping("template")
    public String template(){
        String response = "success";
        try {
            redisTemplate.opsForValue().set("address","成都");
        } catch (Exception e) {
            e.printStackTrace();
            response = "false";
        }
        return response;
    }
}
