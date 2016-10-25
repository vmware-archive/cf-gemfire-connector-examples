package io.pivotal;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;


@Configuration
@EnableGemfireRepositories
public class ApplicationConfig extends AbstractCloudConfig {

    public ServiceConnectorConfig createGemfireConnectorConfig() {
        // Create a custom service connector config object which sets specific properties
        // for the ClientCache as exposed by the GemfireServiceConnectorConfig.
        GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
        gemfireConfig.setPoolIdleTimeout(7777L);

        return gemfireConfig;
    }

    @Bean(name = "gemfireCache")
    public ClientCache getGemfireClientCache() {
        CloudFactory cloudFactory = new CloudFactory();

        // Obtain the Cloud object for the environment in which the application is running.
        // Note that you must have a CloudConnector suitable for your deployment environment on your classpath.
        // For example, if you are deploying the application to Cloud Foundry, you must add the Cloud Foundry
        // Connector to your classpath. If no suitable CloudConnector is found, the getCloud() method will throw
        // a CloudException. Use the Cloud instance to access application and service information and to create
        // service connectors.
        Cloud cloud = cloudFactory.getCloud();

        // Let Spring Cloud create a service connector for you.
        ClientCache cache = cloud.getServiceConnector("service0", ClientCache.class,
                createGemfireConnectorConfig());

        return cache;
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
