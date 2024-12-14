package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderCreationModel;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi{
    public static final String ORDER_CREATE_URL = "/api/v1/orders";
    public static final String DECLINE_ORDER_URL = "/api/v1/orders/cancel/";
    public static final String GET_ALL_ORDERS_URL = "/api/v1/orders";


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


    @Step("Get all orders in system")
    public Response getAllOrders(){
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get(GET_ALL_ORDERS_URL);

        return response;
    }


    @Step("Delete order")
    public void deleteOrder(int track){
        String fullUrl = DECLINE_ORDER_URL + track;

        given()
                .spec(requestSpecification())
                .when()
                .put(fullUrl)
                .then().log().all();
    }
}