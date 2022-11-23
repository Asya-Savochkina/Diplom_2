package client;

import io.qameta.allure.Step;
import model.CreateTheOrderRequest;
import model.CreateTheUserRequest;
import io.restassured.response.Response;

import static config.BurgerConfig.*;
import static io.restassured.RestAssured.given;

public class OrderClient {

    String accessToken;

    @Step
    public Response createNewOrderWithAuth(CreateTheOrderRequest createTheOrderRequest, String accessToken) {
        Response response = given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(createTheOrderRequest)
                .when()
                .post(ORDERS);
        response.then();
        return  response;
    }

    @Step
    public Response createNewOrderWithoutAuth(CreateTheOrderRequest createTheOrderRequest) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(createTheOrderRequest)
                .when()
                .post(ORDERS);
        response.then();
        return  response;
    }

    @Step
    public Response getOrderListForAuthUser(String accessToken) {
        Response response = given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .get("orders");
        response.then();
        return response;
    }

    @Step
    public Response getOrderListWithoutAuth() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .get("orders");
        response.then();
        return response;
    }
}
