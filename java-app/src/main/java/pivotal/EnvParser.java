package pivotal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sbawaskar on 8/21/15.
 */
public class EnvParser {
  private static EnvParser instance;
  private final Pattern p = Pattern.compile("(.*)\\[(\\d*)\\]");

  private EnvParser() {
  }

  public static EnvParser getInstance() {
    if (instance != null) {
      return instance;
    }
    synchronized (EnvParser.class) {
      if (instance == null) {
        instance = new EnvParser();
      }
    }
    return instance;
  }

  public List<URI> getLocators() throws IOException, URISyntaxException {
    List<URI> locatorList = Arrays.asList(new URI("locator://localhost:51590"));
return locatorList;
//    Map credentials = getCredentials();
//    List<String> locators = (List<String>) credentials.get("locators");
//    for (String locator : locators) {
//      Matcher m = p.matcher(locator);
//      if (!m.matches()) {
//        throw new IllegalStateException("Unexpected locator format. expected host[port], got"+locator);
//      }
//      locatorList.add(new URI("locator://" + m.group(1) + ":" + m.group(2)));
//    }
//    return locatorList;
  }

  public String getUsername() throws IOException {
    String username = null;
    Map credentials = getCredentials();
    username = (String) credentials.get("username");
    return username;
  }

  public String getPasssword() throws IOException {
    String password = null;
    Map credentials = getCredentials();
    password = (String) credentials.get("password");
    return password;
  }

  private Map getCredentials() throws IOException {
    Map credentials = null;
    String envContent = System.getenv().get("VCAP_SERVICES");
    List<URI> locatorList = new ArrayList<URI>();
    ObjectMapper objectMapper = new ObjectMapper();
    Map services = objectMapper.readValue(envContent, Map.class);
    List gemfireService = getGemFireService(services);
    if (gemfireService != null) {
      Map serviceInstance = (Map) gemfireService.get(0);
      credentials = (Map) serviceInstance.get("credentials");
    }
    return credentials;

  }

  private List getGemFireService(Map services) {
    List l = (List) services.get("p-gemfire");
    if (l == null) {
      throw new IllegalStateException("GemFire service is not bound to this application");
    }
    return l;
  }
}
