import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierRegistrationModel;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class CourierCreationTest {
    private CourierApi courierApi;
    private CourierRegistrationModel courierRegistrationModel;
    public static final String LOGIN_ALREADY_USED_ERROR = "Этот логин уже используется. Попробуйте другой.";
    public static final String NOT_ENOUGH_DATA_ERROR = "Недостаточно данных для создания учетной записи";
    @Before
    public void invokeRequestForCreateCourier(){
        courierApi = new CourierApi();
    }


    @After
    public void cleanUp(){
        courierApi.deleteCourier(courierRegistrationModel);
    }


    @DisplayName("Check courier created successfully")
    @Test
    public void checkCourierCouldBeCreatedTest() {
        courierRegistrationModel = CourierRegistrationModel.createCourier("login", "pass", "Ivan");
        Response response = courierApi.createCourier(courierRegistrationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", is(true));
    }


    @DisplayName("Check courier created successfully without name")
    @Test
    public void checkCourierCouldBeCreatedWithoutNameTest() {
        courierRegistrationModel = CourierRegistrationModel.createCourier("login", "pass");
        Response response = courierApi.createCourier(courierRegistrationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", is(true));
    }


    @DisplayName("Check impossible to create more then one same courier")
    @Test
    public void checkCreationOfSecondSameCourierReturnsErrorTest(){
        courierRegistrationModel = CourierRegistrationModel.createCourier("login", "pass", "Ivan");
        courierApi.createCourier(courierRegistrationModel);
        Response response = courierApi.createCourier(courierRegistrationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", equalTo(LOGIN_ALREADY_USED_ERROR));
    }


    @DisplayName("Check impossible to create courier without login")
    @Test
    public void checkCreationOfCourierWithoutLoginIsImpossibleTest(){
        courierRegistrationModel = CourierRegistrationModel.createCourier("login", "pass", "Ivan");
        courierRegistrationModel.deleteLoginFromCourier();
        Response response = courierApi.createCourier(courierRegistrationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(NOT_ENOUGH_DATA_ERROR));
    }


    @DisplayName("Check impossible to create courier without password")
    @Test
    public void checkCreationOfCourierWithoutPassIsImpossibleTest(){
        courierRegistrationModel = CourierRegistrationModel.createCourier("login", "pass", "Ivan");
        courierRegistrationModel.deletePassFromCourier();
        Response response = courierApi.createCourier(courierRegistrationModel);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(NOT_ENOUGH_DATA_ERROR));
    }
}