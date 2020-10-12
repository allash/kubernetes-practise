package com.architect.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Configuration
@ConfigurationProperties("redis")
@Getter
@Setter
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    private String host;
    private int port;
    private String password;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Init redis config...");

        if (host == null || password == null) {
            throw new IllegalStateException("Redis host and password cannot be null!");
        }

        LOGGER.info("Redis hostname: {}, port: {}, password: {}", host, port, password);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(RedisPassword.of(password));

        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<Serializable, Object> redisTemplate() {
        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}
