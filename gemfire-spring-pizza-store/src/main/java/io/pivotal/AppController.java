package io.pivotal;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@DependsOn({"gemfireCache", "Pizza"})
public class AppController {

    @Autowired
    private ClientCache gemfireCache;

    @Autowired
    private GemfireRepository<Pizza, String> repository;

    @RequestMapping("/healthcheck")
    public ResponseEntity<Object> healthCheck() {
        LogWriter logger = gemfireCache.getLogger();
        Set<String> toppings = new HashSet<>();
        toppings.add("cheese");
        toppings.add("sauce");

        Pizza pizza = new Pizza("plain", toppings);
        repository.save(pizza);
        logger.info("Finished inserting the element");

        Pizza found = repository.findOne("plain");
        if(found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!found.toppings.contains("cheese") || !found.toppings.contains("sauce")) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
