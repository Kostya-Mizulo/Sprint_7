import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLoginModel;
import model.CourierRegistrationModel;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class CourierLoginTest {
    private CourierApi courierApi;
    private CourierRegistrationModel courierRegistrationModel;
    private CourierLoginModel courierLoginModel;
    public static final String NOT_ENOUGH_DATA_FOR_LOGIN_ERROR = "Недостаточно данных для входа";
    public static final String ACCOUNT_NOT_FOUND_ERROR = "Учетная запись не найдена";


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


    @DisplayName("Check existing courier could authorize")
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

    @DisplayName("Check impossible to authorize without login")
    @Test
    public void checkAuthorizeWithoutLoginReturnsErrorTest(){
        courierLoginModel = CourierLoginModel.createCourierLoginModelObject(courierRegistrationModel);
        courierLoginModel.setLogin(null);
        Response response = courierApi.authCourier(courierLoginModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(NOT_ENOUGH_DATA_FOR_LOGIN_ERROR));
    }


    @DisplayName("Check impossible to authorize without password")
    @Test
    public void checkAuthorizeWithoutPasswordReturnsErrorTest(){
        courierLoginModel = CourierLoginModel.createCourierLoginModelObject(courierRegistrationModel);
        courierLoginModel.setPassword(null);
        Response response = courierApi.authCourier(courierLoginModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(NOT_ENOUGH_DATA_FOR_LOGIN_ERROR));
    }


    @DisplayName("Check authorization with not existing key login-pass returns not found error")
    @Test
    public void checkAuthorizeWithNotExistingKeyLoginPassReturnsNotFoundErrorTest(){
        courierLoginModel = CourierLoginModel.createCourierLoginModelObject(courierRegistrationModel);
        courierLoginModel.setPassword(courierLoginModel.getPassword() + "Wrong");
        Response response = courierApi.authCourier(courierLoginModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", equalTo(ACCOUNT_NOT_FOUND_ERROR));
    }


    @DisplayName("Check authorization with not existing courier returns not found error")
    @Test
    public void checkAuthorizeWithNotExistingCourierReturnsNotFoundErrorTest(){
        courierLoginModel = CourierLoginModel.createCourierLoginModelObject(courierRegistrationModel);
        courierLoginModel.setLogin(courierLoginModel.getLogin() + "new");
        courierLoginModel.setPassword(courierLoginModel.getPassword() + "new");
        Response response = courierApi.authCourier(courierLoginModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", equalTo(ACCOUNT_NOT_FOUND_ERROR));
    }
}