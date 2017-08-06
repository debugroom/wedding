package org.debugroom.wedding.config.infra;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Bean
    CacheManager cacheManager() { 
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("menus"),
                new ConcurrentMapCache("existUser"),
                new ConcurrentMapCache("photo")));
        return cacheManager;
    }

}
