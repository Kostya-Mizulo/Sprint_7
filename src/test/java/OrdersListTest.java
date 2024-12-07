import api.OrderApi;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

public class OrdersListTest {
    @Test
    public void checkMethodReturnsAllOrdersInSystemTest(){
        OrderApi orderApi = new OrderApi();

        Response response = orderApi.getAllOrders();
        response
                .then()
                .assertThat()
                .body("orders", is(not(empty())))
                .log().all();
    }
}