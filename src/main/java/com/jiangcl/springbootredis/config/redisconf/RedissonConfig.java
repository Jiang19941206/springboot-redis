package com.jiangcl.springbootredis.config.redisconf;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangcl
 * @date 2020/11/25
 * @desc redisson连接redis集群
 */
@Configuration
public class RedissonConfig {
    @Autowired
    private RedisProperties properties;

    @Bean
    public RedissonClient redisson(){
        Config config = new Config();
        List<String> clusterNodes = new ArrayList<>();
        //获取redis所有节点信息
        String[] redisNodes = properties.getNodes().split(",");
        //将地址组合成redisson连接需要的格式
        for (String node : redisNodes) {
            clusterNodes.add("redis://" + node);
        }
        //设置连接配置信息
        config.useClusterServers()
                .addNodeAddress(clusterNodes.toArray(new String[redisNodes.length]))
                .setPassword(properties.getPassword());
        return Redisson.create(config);
    }
}
