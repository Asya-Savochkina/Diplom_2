package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateTheUserRequest;
import static config.BurgerConfig.*;
import static io.restassured.RestAssured.given;
import static model.CreateTheUserRequest.getUserForAuth;

public class UserClient {

    @Step("Запрос на регистрацию нового пользователя")
    public Response getUniqUser(CreateTheUserRequest createTheUserRequest) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(createTheUserRequest)
                .when()
                .post(USER_REG);
        response.then();
        return  response;
    }

    @Step("Получение access token успешно зарегистрированного пользователя")
    public String getAccessToken(CreateTheUserRequest createTheUserRequest) {
        return getUniqUser(createTheUserRequest)
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Запрос на авторизацию пользователя")
    public Response authorizationForUser(CreateTheUserRequest createTheUserRequest) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                //.body(getUserForAuth(createTheUserRequest))
                .body(createTheUserRequest)
                .when()
                .post(USER_AUTH);
        response.then();
        return  response;
    }

    @Step("Запрос на изменение данных авторизованного пользователя")
    public Response changeUserDataWithAuth(CreateTheUserRequest createTheUserRequest, String accessToken) {
        Response response = given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(createTheUserRequest)
                .when()
                .patch(USER);
        response.then();
        return response;
    }

    @Step("Запрос на изменение данных неавторизованного пользователя")
    public Response changeUserDataWithoutAuth(CreateTheUserRequest createTheUserRequest) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(createTheUserRequest)
                .when()
                .patch(USER);
        response.then();
        return response;
    }

    @Step("Запрос на удаление пользователя")
    public Response removeForUser(String accessToken) {
        Response response = given()
                .header("Authorization", accessToken)
                .baseUri(getBaseUri())
                .when()
                .delete(USER);
        return response;
    }
}
