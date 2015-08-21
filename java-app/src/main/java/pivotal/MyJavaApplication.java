package pivotal;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

/**
 * Created by sbawaskar on 8/21/15.
 */
public class MyJavaApplication {
  public static void main(String[] args) {
    Properties props = new Properties();
    props.setProperty("security-client-auth-init", "pivotal.ClientAuthInitialize.create");
    ClientCacheFactory ccf = new ClientCacheFactory(props);
    try {
      List<URI> locatorList = EnvParser.getInstance().getLocators();
      for (URI locator : locatorList) {
        ccf.addPoolLocator(locator.getHost(), locator.getPort());
      }
      ClientCache client = ccf.create();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException("Could not deploy Application", e);
    }
  }
}
