package api;

import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import model.CourierLoginModel;
import model.CourierRegistrationModel;
import io.qameta.allure.Step;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CourierApi extends RestApi{
    public static final String CREATE_COURIER_URL = "/api/v1/courier";
    public static final String AUTH_COURIER_URL = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URL = "/api/v1/courier/";


    @Step("Send POST request to create new courier")
    public Response createCourier(CourierRegistrationModel courier){
        Response response = given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URL);

        return response;
    }


    @Step("Send POST request to authorized courier")
    public Response authCourier(CourierLoginModel courier){
        Response response = given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(AUTH_COURIER_URL);

        return response;
    }

    @Step("Delete courier")
    public void deleteCourier(CourierRegistrationModel courier) {
        CourierLoginModel courierLogin = CourierLoginModel.createCourierLoginModelObject(courier);
        Response response = authCourier(courierLogin);

        if (response.jsonPath().get("id") != null) {
             int id = response.jsonPath().getInt("id");
            sendMethodToDeleteCourier(id);
        }
    }

    @Step("Send DELETE method to delete courier by id")
    public void sendMethodToDeleteCourier(int id){
        String urlToDeleteCourier = DELETE_COURIER_URL + id;

        given()
                .spec(requestSpecification())
                .when()
                .delete(urlToDeleteCourier)
                .then().log().all();

    }
}