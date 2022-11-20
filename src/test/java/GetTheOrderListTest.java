import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheOrderRequest;
import model.CreateTheUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.CreateTheOrderRequest.createTheOrderRequestWithIngredients;
import static model.CreateTheUserRequest.getUserAllRequiredField;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetTheOrderListTest {

    UserClient userClient;
    OrderClient orderClient;
    String accessToken;
    private static final String EXPECTED_MESSAGE_NOT_AUTH = "You should be authorised";

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void shouldGetTheOrderListForAuthUser() {
        CreateTheUserRequest createTheUserRequest = getUserAllRequiredField();
        accessToken = userClient.getAccessToken(createTheUserRequest);
        CreateTheOrderRequest createTheOrderRequest = createTheOrderRequestWithIngredients();
        orderClient.createNewOrderWithAuth(createTheOrderRequest, accessToken);
        orderClient.getOrderListForAuthUser(accessToken)
                .then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    public void checkThatCantGetTheOrderListWithoutAuth() {
        CreateTheOrderRequest createTheOrderRequest = createTheOrderRequestWithIngredients();
        orderClient.createNewOrderWithoutAuth(createTheOrderRequest);
        orderClient.getOrderListWithoutAuth()
                .then()
                .statusCode(401)
                .and()
                .body("message", equalTo(EXPECTED_MESSAGE_NOT_AUTH));
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
