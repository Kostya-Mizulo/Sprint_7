import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderCreationModel;
import org.apache.http.HttpStatus;
import org.junit.After;
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
    private int track;


    public OrderCreationParameterizedTest(List<String> color) {
        this.color = color;
    }


    @Parameterized.Parameters(name = "Order with colors: {0}")
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


    @After
    public void cleanUp(){
        orderApi.deleteOrder(track);
    }


    @Test
    @DisplayName("Check order created normally with different color inputs")
    public void checkOrderCreatedWithDifferentColorInputsTest(){
        orderCreationModel.setColor(color);
        Response response = orderApi.createCourier(orderCreationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("track", notNullValue());

        if (response.jsonPath().getInt("track") != 0)
            track = response.jsonPath().getInt("track");
    }
}