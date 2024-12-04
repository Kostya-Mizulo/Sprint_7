import api.CourierApi;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.CourierModel;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private CourierApi courierApi;
    private CourierModel courier;
    @Before
    public void invokeRequestForLoginCourier(){
        courierApi = new CourierApi();
    }

    @After
    public void cleanUp(){
        courierApi.deleteCourier(courier);
    }


    @Description("Check courier created successfully")
    @Test
    public void checkCourierCouldBeAuthorizedTest() {

        courier = CourierModel.createCourier("login", "pass", "Ivan");
        courierApi.createCourier(courier);

        Response response = courierApi.authCourier(courier);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", notNullValue());
    }
}
