import api.CourierApi;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.CourierModel;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class CourierCreationTest {
    private CourierApi courierApi;
    private CourierModel courier;
    @Before
    public void invokeRequestForCreateCourier(){
        courierApi = new CourierApi();
    }

    @After
    public void cleanUp(){
        courierApi.deleteCourier(courier);
    }


    @Description("Check courier created successfully")
    @Test
    public void checkCourierCouldBeCreatedTest() {

        courier = CourierModel.createCourier("login", "pass", "Ivan");

        Response response = courierApi.createCourier(courier);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", is(true));
    }

    @Description("Check impossible to create more then one same courier")
    @Test
    public void checkCreationOfSecondSameCourierReturnsErrorTest(){
        courier = CourierModel.createCourier("login", "pass", "Ivan");

        courierApi.createCourier(courier);
        Response response = courierApi.createCourier(courier);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Description("Check impossible to create courier without login")
    @Test
    public void checkCreationOfCourierWithoutLoginIsImpossible(){
        courier = CourierModel.createCourier("login", "pass", "Ivan");
        courier.deleteLoginFromCourier();

        Response response = courierApi.createCourier(courier);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Description("Check impossible to create courier without password")
    @Test
    public void checkCreationOfCourierWithoutPassIsImpossible(){
        courier = CourierModel.createCourier("login", "pass", "Ivan");
        courier.deletePassFromCourier();

        Response response = courierApi.createCourier(courier);

        response.then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
