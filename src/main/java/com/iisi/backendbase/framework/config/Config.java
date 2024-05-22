package com.iisi.backendbase.framework.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class Config {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public CacheManager cacheManager(Caffeine authCache) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("authCache");
        cacheManager.setCaffeine(authCache);
        return cacheManager;
    }

    @Bean(name = "authCache")
    public Caffeine caffeineSpec() {
        return Caffeine.newBuilder().initialCapacity(10).maximumSize(100).expireAfterAccess(10, TimeUnit.MINUTES);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getName() + "_" + method.getName() + "_" + Arrays.toString(params);
            }
        };
    }

    /**
     * Jackson 設定日期時間格式
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
            builder.modules(new JavaTimeModule());
        };
    }


}