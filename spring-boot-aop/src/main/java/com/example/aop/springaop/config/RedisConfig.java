package com.example.aop.springaop.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;

//@Slf4j
//@Configuration
//@EnableRedisRepositories
//public class RedisConfig {
//
//@Bean
//public JedisConnectionFactory connectionFactory() {
//    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//    configuration.setHostName("localhost");
//    configuration.setPort(6379);
//    return new JedisConnectionFactory(configuration);
//}
//
//    @Bean
//    CacheManagerCustomizer<ConcurrentMapCacheManager> customizer(){
//        return new ConcurrentCustomizer();
//    }
//
//    class ConcurrentCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager>{
//
//        @Override
//        public void customize(ConcurrentMapCacheManager cacheManager) {
//            cacheManager.setAllowNullValues(true);
//            //cacheManager.setStoreByValue(true);
//            log.info("customizer invoked!!");
//        }
//    }
//}

@Configuration
@Slf4j
@EnableRedisRepositories
public class RedisConfig {

	@Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.cache.redis.time-to-live}")
    private Long expireTime;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        log.info("Inside Jedis Connection Factory Method");

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        configuration.setUsername(redisUsername);
        configuration.setPassword(redisPassword);

        log.info("RedisHost  -> " + configuration.getHostName());
        log.info("RedisPort  -> " + configuration.getPort());
        log.info("ExpireTime -> " + expireTime);

        return new JedisConnectionFactory(configuration);
    }


	@Bean
	public RedisTemplate<String, Object> template() {
		log.info("Inside Redis Template Method");
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new JdkSerializationRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.setEnableTransactionSupport(true);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {

		log.info("Inside Redis Cache Configuration Method");

		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(expireTime)).disableCachingNullValues()
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		cacheConfig.usePrefix();

		log.info("Duration -> " + cacheConfig.getTtl().getSeconds());

		return cacheConfig;
	}

	@Bean
	public RedisCacheManager cacheManager() {

		log.info("Info -> Redis Cache Manager");

		return RedisCacheManager.builder(this.connectionFactory()).cacheDefaults(this.cacheConfiguration()).build();

	}
}
