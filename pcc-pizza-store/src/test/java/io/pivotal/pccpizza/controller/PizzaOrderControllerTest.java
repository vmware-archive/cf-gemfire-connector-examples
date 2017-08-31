package io.pivotal.pccpizza.controller;

import io.pivotal.pccpizza.model.Customer;
import io.pivotal.pccpizza.model.PizzaOrder;
import io.pivotal.pccpizza.service.PizzaOrderService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PizzaOrderController.class)
@Ignore
public class PizzaOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PizzaOrderService pizzaOrderService;

    @Before
    public void setup(){
        PizzaOrder pizzaOrder = new PizzaOrder();
        Customer customer = new Customer();
        customer.setFirstName("name");
        customer.setEmail("email");
        pizzaOrder.setCustomer(customer);

        when(pizzaOrderService.getAllOrders()).thenReturn(Arrays.asList(pizzaOrder));
    }

    @Test
    public void shouldGetListOfPizzaOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].customer.firstName").value("name"))
                .andExpect(jsonPath("$[0].customer.email").value("email"));
    }
}