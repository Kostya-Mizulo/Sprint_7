import api.OrderApi;
import io.restassured.response.Response;
import model.OrderCreationModel;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationParameterizedTest {
    private final List<String> color;
    private OrderApi orderApi;
    private OrderCreationModel orderCreationModel;

    public OrderCreationParameterizedTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] getScootersColor() {
        return new Object[] {
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "Gray"),
                List.of(),
                null
        };
    }

    @Before
    public void setup(){
        orderApi = new OrderApi();
        orderCreationModel = new OrderCreationModel();
        orderCreationModel.setFirstName("Ivan");
        orderCreationModel.setLastName("Ivanov");
        orderCreationModel.setAddress("ulica Pushkina");
        orderCreationModel.setMetroStation("Delovoy centr");
        orderCreationModel.setPhone("+7123121212");
        orderCreationModel.setRentTime(2);
        orderCreationModel.setDeliveryDate("01.01.2025");
        orderCreationModel.setComment("Comment");
    }


    @Test
    public void checkOrderCreatedWithDifferentColorInputsTest(){
        orderCreationModel.setColor(color);

        Response response = orderApi.createCourier(orderCreationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("track", notNullValue());

        int track = response.jsonPath().getInt("track");
    }
}