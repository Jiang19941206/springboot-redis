package com.jiangcl.springbootredis.config.redisconf;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangcl
 * @date 2020/11/25
 * @desc 使用JedisCluster，不需要手动关闭连接，jediscluster在调用过程中就已经关闭了连接
 */
@Configuration
public class JedisClusterConfig {
    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisCluster jedisCluster() {
        /**
         * 连接池配置
         */
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(properties.getMaxTotal());
        poolConfig.setMaxIdle(properties.getMaxIdle());
        poolConfig.setMinIdle(properties.getMinIdle());
        poolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());

        /**
         * 服务器节点配置
         */
        Set<HostAndPort> nodes = new HashSet<>();

        String[] ipPorts = properties.getNodes().split(",");
        for (String ipPort : ipPorts) {
            String[] ipAndPort = ipPort.split(":");
            HostAndPort hostAndPort = new HostAndPort(ipAndPort[0].trim(), Integer.parseInt(ipAndPort[1].trim()));
            nodes.add(hostAndPort);
        }

        /**
         * nodes 节点集合
         * connectionTimeout 连接超时时间 单位：ms
         * soTimeout 数据读取时间  单位：ms
         * maxAttempts 最大尝试次数
         * password 密码
         * poolConfig 连接池配置信息
         */
        return new JedisCluster(nodes, properties.getConnectionTimeout(), properties.getSoTimeout(), properties.getMaxAttempts(), properties.getPassword(), poolConfig);
    }
}
