package pivotal;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientRegionFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.GemfireConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pivotal on 7/16/15.
 */
@RestController
public class Client {

    @Resource(name = "example")
    Region myRegion;

    @Autowired
    @Bean(name = "example")
    public ClientRegionFactoryBean<Integer, String> exampleLocalRegion(ClientCache cache) {
        ClientRegionFactoryBean<Integer, String> exampleRegion = new ClientRegionFactoryBean<>();
        exampleRegion.setName("example");
        exampleRegion.setCache(cache);
        exampleRegion.setShortcut(ClientRegionShortcut.PROXY);
        return exampleRegion;
    }

    @RequestMapping("/dotest")
    public String doTest() {
        myRegion.put(1, "one");
        Object val = myRegion.get(1);
        if (!"one".equals(val)) {
            throw new IllegalStateException("Expected value not returned, expected: one, found:" + val);
        }
        return "ok";
    }

    @ExceptionHandler()
    public void handleException(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), getStackString(ex));
    }

    private String getStackString(Exception e) {
        StringBuilder s = new StringBuilder(e.getClass().getCanonicalName()).append(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            s.append("  at ").append(ste.toString()).append("\n");
        }
        return s.toString();
    }
}
