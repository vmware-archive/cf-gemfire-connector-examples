package io.pivotal.pccpizza.config;

import io.pivotal.pccpizza.model.PizzaOrder;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.net.URI;

import static org.springframework.data.gemfire.util.CollectionUtils.asSet;

@Profile("Default")
@Configuration
@EnableGemfireRepositories("io.pivotal.pccpizza.repository.gemfire")
public class GemfireConfiguration {

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

        factory.set(SECURITY_CLIENT, "io.pivotal.pccpizza.config.UserAuthInitialize.create");
        factory.set(SECURITY_USERNAME, serviceInfo.getUsername());
        factory.set(SECURITY_PASSWORD, serviceInfo.getPassword());
        factory.setPdxSerializer(new ReflectionBasedAutoSerializer("io.pivotal.model.PizzaOrder"));
        factory.setPoolSubscriptionEnabled(true); // to enable CQ
        return factory.create();
    }

//    @Bean
//    public ClientCache gemfireCache() {
//        ClientCacheFactory factory = new ClientCacheFactory();
//
//        factory.addPoolLocator("10.74.47.200", 55221);
//        factory.addPoolLocator("10.74.47.201", 55221);
//        factory.addPoolLocator("10.74.47.202", 55221);
//        factory.set(SECURITY_CLIENT, "io.pivotal.pccpizza.config.UserAuthInitialize.create");
//        factory.set(SECURITY_USERNAME, "cluster_operator");
//        factory.set(SECURITY_PASSWORD, "yNZWdLwK1Byizui8oucw");
//        factory.setPdxSerializer(new ReflectionBasedAutoSerializer("io.pivotal.model.PizzaOrder"));
//        factory.setPoolSubscriptionEnabled(true); // to enable CQ
//        return factory.create();
//    }

    @Bean
    public GemfireCacheManager cacheManager(ClientCache gemfireCache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
        cacheManager.setCache(gemfireCache);
        return cacheManager;
    }

    @Bean(name = "PizzaOrder")
    ClientRegionFactoryBean<Long, PizzaOrder> orderRegion(@Autowired ClientCache gemfireCache) {
        ClientRegionFactoryBean<Long, PizzaOrder> orderRegion = new ClientRegionFactoryBean<>();

        orderRegion.setCache(gemfireCache);
        orderRegion.setClose(false);
        orderRegion.setShortcut(ClientRegionShortcut.PROXY);
        orderRegion.setLookupEnabled(true);

        return orderRegion;
    }

//    @Bean
//    ContinuousQueryListenerContainer continuousQueryListenerContainer(ClientCache gemfireCache) {
//        Region<Long, PizzaOrder> pizzaOrders = gemfireCache.getRegion("/PizzaOrder");
//        ContinuousQueryListenerContainer container = new ContinuousQueryListenerContainer();
//
//        container.setCache(gemfireCache);
//        container.setQueryListeners(asSet(cheapOrdersQuery(pizzaOrders)));
//
//        return container;
//    }
//
//    private ContinuousQueryDefinition cheapOrdersQuery(Region<Long, PizzaOrder> pizzaOrders) {
//
//        String query = String.format("SELECT * FROM /PizzaOrder");
//
//        return new ContinuousQueryDefinition("Pizza Orders", query,
//                newQueryListener(pizzaOrders));
//    }
//
//    private ContinuousQueryListener newQueryListener(Region<Long, PizzaOrder> pizzaOrders) {
//
//        return event -> {
//            PizzaOrder pizzaOrder = (PizzaOrder) event.getNewValue();
//            System.out.println("ORDER " + pizzaOrder);
//        };
//
//    }
}
