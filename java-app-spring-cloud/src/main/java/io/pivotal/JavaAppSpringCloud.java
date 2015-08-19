package io.pivotal;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import io.pivotal.spring.cloud.service.common.GemfireServiceInfo;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;

import java.net.URI;
import java.util.Properties;

/**
 * Created by sbawaskar on 8/7/15.
 */
public class JavaAppSpringCloud {
  public static void main(String[] args) {
    CloudFactory cloudFactory = new CloudFactory();
    Cloud cloud = cloudFactory.getCloud();
    GemfireServiceInfo myService = (GemfireServiceInfo) cloud.getServiceInfo("MyService");
    Properties props = new Properties();
    props.setProperty("security-client-auth-init", "io.pivotal.ClientAuthInitialize.create");
    ClientCacheFactory ccf = new ClientCacheFactory(props);
    URI[] locators = myService.getLocators();
    for (URI locator : locators) {
      ccf.addPoolLocator(locator.getHost(), locator.getPort());
    }
    ClientCache cache = ccf.create();
  }
}
