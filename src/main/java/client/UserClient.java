package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateTheUserRequest;

import static config.BurgerConfig.USER_REG;
import static config.BurgerConfig.getBaseUri;
import static io.restassured.RestAssured.given;

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
}
