package pivotal;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.HttpTransport;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by sbawaskar on 8/21/15.
 */
public class MyJavaApplication {
  static final MetricRegistry metrics = new MetricRegistry();
  private static final Meter requests = metrics.meter("requests");

  private static void WriteAttributes(final MBeanServer mBeanServer, final ObjectName http)
          throws InstanceNotFoundException, IntrospectionException, ReflectionException
  {
    MBeanInfo info = mBeanServer.getMBeanInfo(http);
    MBeanAttributeInfo[] attrInfo = info.getAttributes();

    System.out.println("Attributes for object: " + http +":\n");
    for (MBeanAttributeInfo attr : attrInfo)
    {
      System.out.println("  " + attr.getName() + "\n");
    }
  }

  public static void main(String[] args) {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();
    HttpTransport transport = new HttpTransport.Builder().withApiKey("6e02dcebb0c3efa7e14a0be383a0d378").build();
    DatadogReporter dataDog = DatadogReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .withTransport(transport)
            .build();
//    reporter.start(1, TimeUnit.SECONDS);
//    dataDog.start(1, TimeUnit.SECONDS);



    Properties props = new Properties();
//    props.setProperty("security-client-auth-init", "pivotal.ClientAuthInitialize.create");
    ClientCacheFactory ccf = new ClientCacheFactory(props);
    List<URI> locatorList = null;
    try {
      locatorList = EnvParser.getInstance().getLocators();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for (URI locator : locatorList) {
      ccf.addPoolLocator(locator.getHost(), locator.getPort());
    }
    ClientCache client = ccf.create();
    final Region r = client.createClientRegionFactory(ClientRegionShortcut.PROXY).setStatisticsEnabled(true).create("test");


    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
    for (ObjectName mbean : server.queryNames(null, null))
    {
      try {
        WriteAttributes(server, mbean);
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }

//    metrics.register(MetricRegistry.name(MyJavaApplication.class, "reads"), new Gauge<Long>() {
//      @Override
//      public Long getValue() {
//        return r.get;
//      }
//    });

//    while(true) {
//      r.put("1", "one");
//      requests.mark();
//      if (!r.get("1").equals("one")) {
//        throw new RuntimeException("Expected value to be \"one\", but was:"+r.get("1"));
//      }
//    }
  }
}
