import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheOrderRequest;
import model.CreateTheUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.CreateTheOrderRequest.*;
import static model.CreateTheUserRequest.getUserAllRequiredField;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateTheOrderTest {

    OrderClient orderClient;
    UserClient userClient;
    CreateTheOrderRequest fullOrderRequest;
    CreateTheOrderRequest emptyOrderRequest;
    CreateTheOrderRequest wrongOrderRequest;
    CreateTheUserRequest createTheUserRequest;
    String accessToken;

    private static final String EXPECTED_MESSAGE_NO_INGREDIENTS = "Ingredient ids must be provided";

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        createTheUserRequest = getUserAllRequiredField();

        accessToken = userClient.getUniqUser(createTheUserRequest)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Создание корректного заказа с ингредиентами и авторизацией")
    public void checkTheCreationOfCorrectOrderWithAuth() {
            fullOrderRequest = createTheOrderRequestWithIngredients();
            orderClient.createNewOrderWithAuth(fullOrderRequest, accessToken)
                    .then().statusCode(200)
                    .and()
                    .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами без авторизации")
    public void checkTheCreationOfCorrectOrderWithoutAuth() {
        orderClient.createNewOrderWithoutAuth(createTheOrderRequestWithIngredients())
                .then().statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void checkTheCreationOfOrderWithoutIngredients() {
        emptyOrderRequest = createTheOrderRequestWithoutIngredients();
        orderClient.createNewOrderWithAuth(emptyOrderRequest, accessToken)
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_NO_INGREDIENTS));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиента")
    public void checkTheCreationOfOrderWithWrongIngredientHash() {
        wrongOrderRequest = createTheOrderRequestWrongHashOfIngredients();
        orderClient.createNewOrderWithAuth(wrongOrderRequest, accessToken)
                .then().statusCode(500);
    }

    @After
    public void deleteTestUser() {
        if (accessToken != null) {
            userClient.removeForUser(accessToken)
                    .then()
                    .statusCode(202);
        }
    }
}
