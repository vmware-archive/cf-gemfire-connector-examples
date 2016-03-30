package io.pivotal;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.distributed.DistributedMember;
import com.gemstone.gemfire.security.AuthInitialize;
import com.gemstone.gemfire.security.AuthenticationFailedException;
import io.pivotal.spring.cloud.service.common.GemfireServiceInfo;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;

import java.util.Properties;

/**
 * Created by sbawaskar on 8/7/15.
 */
public class ClientAuthInitialize implements AuthInitialize {

  public static final String USER_NAME = "security-username";
  public static final String PASSWORD = "security-password";

  public GemfireServiceInfo serviceInfo;

  private ClientAuthInitialize() {
    CloudFactory cloudFactory = new CloudFactory();
    Cloud cloud = cloudFactory.getCloud();
    serviceInfo = (GemfireServiceInfo) cloud.getServiceInfo("service0");
  }

  public static AuthInitialize create() {
    return new ClientAuthInitialize();
  }

  @Override
  public void close() {
  }

  @Override
  public Properties getCredentials(Properties arg0, DistributedMember arg1,
                                   boolean arg2) throws AuthenticationFailedException {
    Properties props = new Properties();

    String username = serviceInfo.getUsername();
    String password = serviceInfo.getPassword();
    props.put(USER_NAME, username);
    props.put(PASSWORD, password);

    return props;
  }

  @Override
  public void init(LogWriter arg0, LogWriter arg1)
      throws AuthenticationFailedException {
  }
}
