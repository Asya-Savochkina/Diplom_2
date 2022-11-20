import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserLogInTest {
    UserClient userClient;
    CreateTheUserRequest createTheUserRequest;
    protected static String accessToken;
    private static final String EXPECTED_MESSAGE_INCORRECT_EMAIL_OR_PASSWORD = "email or password are incorrect";


    @Before
    public void setup() {
        createTheUserRequest = CreateTheUserRequest.getUserAllRequiredField();
        userClient = new UserClient();
        userClient.getUniqUser(createTheUserRequest);
    }

    @Test
    @DisplayName("Корректная авторизация пользователя")
    @Description("Проверяем позитивный сценарий авторизации пользователя. Ожидаем, что возвращается \"success\": true, код 200")
    public void checkSuccessAuthWithCorrectUserData() {

        userClient.authorizationForUser(createTheUserRequest)
                .then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        accessToken = userClient.getAccessToken(createTheUserRequest);
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным логином")
    @Description("Ожидаем ошибку авторизации при введении неверного логина  \"message\": \"email or password are incorrect")
    public void checkUnsuccessfulAuthWithIncorrectEmail() {
        accessToken = userClient.getAccessToken(createTheUserRequest);
        createTheUserRequest.setEmail(createTheUserRequest.createRandomEmail());
        userClient.authorizationForUser(createTheUserRequest)
                .then().statusCode(401)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_INCORRECT_EMAIL_OR_PASSWORD));
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
