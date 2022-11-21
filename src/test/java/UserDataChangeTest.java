
import client.UserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.CreateTheUserRequest.getNewUserDataForChange;
import static model.CreateTheUserRequest.getUserAllRequiredField;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@DisplayName("Изменение данных пользователя")
public class UserDataChangeTest {

    UserClient userClient;
    String accessToken;
    private static final String EXPECTED_MESSAGE_NOT_AUTH = "You should be authorised";
    @Before
    public void setUp() {
        userClient = new UserClient();

    }

    @Test
    @DisplayName("Авторизованный пользователь может изменить данные")
    public void checkThatAuthUserCanChangeData() {
        CreateTheUserRequest createTheUserRequest = getUserAllRequiredField();
        accessToken = userClient.getAccessToken(createTheUserRequest);
        CreateTheUserRequest changedUser = getNewUserDataForChange();
        userClient.changeUserDataWithAuth(changedUser, accessToken)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.email", is(changedUser.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Неавторизованный пользователь не может изменить данные")
    public void checkThatCantChangeDataWithoutAuth() {
        CreateTheUserRequest createTheUserRequest = getUserAllRequiredField();
        userClient.changeUserDataWithoutAuth(createTheUserRequest)
                .then()
                .assertThat()
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

