package com.jiangcl.springbootredis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author jiangcl
 * @date 2020/11/26
 * @desc
 */
public class TestClass {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(300);
        poolConfig.setMaxIdle(100);
        poolConfig.setMinIdle(20);
        poolConfig.setMaxWaitMillis(-1);
        JedisPool jedisPool = new JedisPool(poolConfig,"localhost",6379,30000,"123456");
        TestClass.jedisPool = jedisPool;
    }

    public static void main(String[] args) {
        GetValueThread get1 = new GetValueThread("address", jedisPool.getResource());
        Thread thread1 = new Thread(get1);
        GetValueThread get2 = new GetValueThread("age", jedisPool.getResource());
        Thread thread2 = new Thread(get2);
        GetValueThread get3 = new GetValueThread("name", jedisPool.getResource());
        Thread thread3 = new Thread(get3);
        GetValueThread get4 = new GetValueThread("email", jedisPool.getResource());
        Thread thread4 = new Thread(get4);
        GetValueThread get5 = new GetValueThread("gender", jedisPool.getResource());
        Thread thread5 = new Thread(get5);
        GetValueThread get6 = new GetValueThread("idCar", jedisPool.getResource());
        Thread thread6 = new Thread(get6);
        GetValueThread get7 = new GetValueThread("phone", jedisPool.getResource());
        Thread thread7 = new Thread(get7);
        GetValueThread get8 = new GetValueThread("province", jedisPool.getResource());
        Thread thread8 = new Thread(get8);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
    }

}

class GetValueThread implements Runnable{
    private String key;

    private Jedis jedis;

    public GetValueThread(String key,Jedis jedis){
        this.key = key;
        this.jedis = jedis;
    }
    @Override
    public void run(){
        try {
            String s = jedis.get(key);
            System.out.println(Thread.currentThread() + ">>>>>>>>>>>" + s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
