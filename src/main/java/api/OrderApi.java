package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CourierRegistrationModel;
import model.OrderCreationModel;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi{
    public static final String ORDER_CREATE_URL = "/api/v1/orders";

    @Step("Send POST request to create new order")
    public Response createCourier(OrderCreationModel order){
        Response response = given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(ORDER_CREATE_URL);

        return response;
    }
}