package io.pivotal.pccpizza;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.cache.GemfireCacheManager;

@Profile("Test")
@Configuration
public class TestConfig {

    @Bean
    public ClientCache gemfireCache() {
        ClientCacheFactory factory = new ClientCacheFactory();
        return factory.create();
    }

    @Bean
    public GemfireCacheManager cacheManager(ClientCache gemfireCache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
        cacheManager.setCache(gemfireCache);
        return cacheManager;
    }
}
