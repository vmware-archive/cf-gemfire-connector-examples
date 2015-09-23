package pivotal;

import com.gemstone.gemfire.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pivotal on 9/22/15.
 */
@RestController
public class Client {

    @Autowired
    @Qualifier("my-client-cache")
    ClientCache cache;

    @RequestMapping("/poolIdleTimeout")
    public Long getClientPoolIdleTimeout() {
        return cache.getDefaultPool().getIdleTimeout();
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
