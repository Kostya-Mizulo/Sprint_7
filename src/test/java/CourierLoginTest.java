import api.CourierApi;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.CourierLoginModel;
import model.CourierRegistrationModel;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private CourierApi courierApi;
    private CourierRegistrationModel courierRegistrationModel;
    private CourierLoginModel courierLoginModel;
    @Before
    public void setUp(){
        courierApi = new CourierApi();
        courierRegistrationModel = CourierRegistrationModel.createCourier("login", "password", "Ivan");
        courierApi.createCourier(courierRegistrationModel);
    }

    @After
    public void cleanUp(){
        courierApi.deleteCourier(courierRegistrationModel);
    }


    @Description("Check courier created successfully")
    @Test
    public void checkCourierCouldBeAuthorizedTest() {

        courierLoginModel = CourierLoginModel.createCourierLoginModelObject(courierRegistrationModel);


        Response response = courierApi.authCourier(courierLoginModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", notNullValue());
    }
}