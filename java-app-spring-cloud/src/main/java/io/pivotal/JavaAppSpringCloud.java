package io.pivotal;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
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
    GemfireServiceInfo myService = (GemfireServiceInfo) cloud.getServiceInfo("service0");
    Properties props = new Properties();
    props.setProperty("security-client-auth-init", "io.pivotal.ClientAuthInitialize.create");
    ClientCacheFactory ccf = new ClientCacheFactory(props);
    URI[] locators = myService.getLocators();
    for (URI locator : locators) {
      ccf.addPoolLocator(locator.getHost(), locator.getPort());
    }
    ClientCache client = ccf.create();
    Region r = client.createClientRegionFactory(ClientRegionShortcut.PROXY).create("test");
    r.put("1", "one");
    if (!r.get("1").equals("one")) {
      throw new RuntimeException("Expected value to be \"one\", but was:"+r.get("1"));
    }
    try {
      Thread.sleep(Long.MAX_VALUE);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
