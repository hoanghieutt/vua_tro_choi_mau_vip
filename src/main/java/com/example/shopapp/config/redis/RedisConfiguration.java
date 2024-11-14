//package com.example.shopapp.config.redis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//public class RedisConfiguration {
//  @Value("6379")
//  private String redisPost;
//  @Value("localhost")
//  private String redisHost;
//  @Value("${spring.redis.password}")
//  private String redisPassword;
//
//  @Bean
//  public LettuceConnectionFactory redisConnectionFactory() {
//    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//    redisConfig.setHostName(redisHost);
//    redisConfig.setPort(redisPost);
//    redisConfig.setPassword(redisPassword);
//
//    return new LettuceConnectionFactory(redisConfig);
//  }
//
//  @Bean
//  @Primary
//  public RedisTemplate<Object, Object> redisTemplate(
//      RedisConnectionFactory redisConnectionFactory
//  ) {
//    RedisTemplate<Object, Object> template = new RedisTemplate<>();
//    template.setConnectionFactory(redisConnectionFactory);
//
//    return template;
//  }
//}
