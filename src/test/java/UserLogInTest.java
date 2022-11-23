import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class UserLogInTest {
    UserClient userClient;
    CreateTheUserRequest createTheUserRequest;
    CreateTheUserRequest createTheUserWithIncorrectEmail;
    protected static String accessToken;
    private static final String EXPECTED_MESSAGE_INCORRECT_EMAIL_OR_PASSWORD = "email or password are incorrect";


    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @After
    public void deleteTestUser() {
        if (accessToken != null) {
            userClient.removeForUser(accessToken)
                    .then()
                    .statusCode(SC_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Корректная авторизация пользователя")
    @Description("Проверяем позитивный сценарий авторизации пользователя. Ожидаем, что возвращается \"success\": true, код 200")
    public void checkSuccessAuthWithCorrectUserData() {
        createTheUserRequest = CreateTheUserRequest.getUserAllRequiredField();
        userClient.getUniqUser(createTheUserRequest);
        userClient.authorizationForUser(createTheUserRequest)
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success", equalTo(true));
        accessToken = userClient.getAccessToken(createTheUserRequest);
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным логином")
    @Description("Ожидаем ошибку авторизации при введении неверного логина  \"message\": \"email or password are incorrect")
    public void checkUnsuccessfulAuthWithIncorrectEmail() {
        createTheUserWithIncorrectEmail = CreateTheUserRequest.getUserAllRequiredField();
        userClient.getUniqUser(createTheUserWithIncorrectEmail);
        createTheUserWithIncorrectEmail.setEmail(CreateTheUserRequest.createRandomEmail());
        userClient.authorizationForUser(createTheUserWithIncorrectEmail)
                .then().statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_INCORRECT_EMAIL_OR_PASSWORD));
        accessToken = userClient.getAccessToken(createTheUserWithIncorrectEmail);
    }
}
