package com.jiangcl.springbootredis.config.redisconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangcl
 * @date 2020/11/25
 * @desc Redistemplate连接redis集群
 * 第一步：创建JedisConnectionFactory
 * 第二部：创建RedisTemplate
 */
@Configuration
public class RedisTemplateConfig {
    @Autowired
    private RedisProperties properties;

    /**
     * 创建factory
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        /**
         * 连接池配置
         */
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(properties.getMaxTotal());
        poolConfig.setMaxIdle(properties.getMaxIdle());
        poolConfig.setMinIdle(properties.getMinIdle());
        poolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());

        /**
         * 集群配置
         */
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String[] serverArray = properties.getNodes().split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for(String ipPort:serverArray){
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setPassword(properties.getPassword());

        /**
         * 创建factory
         */
        JedisConnectionFactory  factory = new JedisConnectionFactory(redisClusterConfiguration,poolConfig);
        return factory;
    }

    /**
     * 创建RedisTemplate
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}
