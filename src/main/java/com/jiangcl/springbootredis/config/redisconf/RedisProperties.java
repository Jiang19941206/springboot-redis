package com.jiangcl.springbootredis.config.redisconf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiangcl
 * @date 2020/11/25
 * @desc redis连接配置信息
 */
@Component
@ConfigurationProperties(prefix = "redis.cluster")
@Getter
@Setter
@ToString
public class RedisProperties {
    /**
     * 服务器地址
     */
    private String nodes;

    /**
     * 最大连接数
     */
    private Integer maxTotal;

    /**
     * 最大空闲数
     */
    private Integer maxIdle;

    /**
     * 最小空闲数
     */
    private Integer minIdle;

    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     */
    private Integer maxWaitMillis;

    /**
     * 连接超时时间
     */
    private Integer connectionTimeout;

    /**
     * 数据读取超时时间
     */
    private Integer soTimeout;

    /**
     * 重试次数
     */
    private Integer maxAttempts;

    /**
     * redis密码
     */
    private String password;

    private String redissonNodes;
}
