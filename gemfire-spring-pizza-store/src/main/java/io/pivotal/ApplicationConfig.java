package io.pivotal;


import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.net.URI;


@Configuration
@EnableGemfireRepositories
public class ApplicationConfig extends AbstractCloudConfig {

    private static final String SECURITY_CLIENT = "security-client-auth-init";
    private static final String SECURITY_USERNAME = "security-username";
    private static final String SECURITY_PASSWORD = "security-password";

    @Bean
    public ClientCache gemfireCache() {
        Cloud cloud = new CloudFactory().getCloud();
        ServiceInfo serviceInfo = (ServiceInfo) cloud.getServiceInfos().get(0);

        ClientCacheFactory factory = new ClientCacheFactory();
        for (URI locator : serviceInfo.getLocators()) {
            factory.addPoolLocator(locator.getHost(), locator.getPort());
        }

        factory.set(SECURITY_CLIENT, "io.pivotal.UserAuthInitialize.create");
        factory.set(SECURITY_USERNAME, serviceInfo.getUsername());
        factory.set(SECURITY_PASSWORD, serviceInfo.getPassword());
        factory.setPdxSerializer(new ReflectionBasedAutoSerializer("io.pivotal.Pizza"));

        return factory.create();
    }

    @Bean(name = "Pizza")
    public ClientRegionFactoryBean<String, Pizza> pizzaRegion(@Autowired ClientCache gemfireCache) {
        ClientRegionFactoryBean<String, Pizza> pizzaRegion = new ClientRegionFactoryBean<>();

        pizzaRegion.setCache(gemfireCache);
        pizzaRegion.setClose(false);
        pizzaRegion.setShortcut(ClientRegionShortcut.PROXY);
        pizzaRegion.setLookupEnabled(true);
        return pizzaRegion;
    }
}
