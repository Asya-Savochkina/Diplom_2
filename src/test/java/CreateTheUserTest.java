import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateTheUserTest {

    UserClient userClient;
    CreateTheUserRequest createTheUserRequest;
    String accessToken;


    private static final String EXPECTED_MESSAGE_DOUBLE = "User already exists";
    private static final String EXPECTED_MESSAGE_EMPTY_REQUIRED_FIELD = "Email, password and name are required fields";


    @Before
    public void setup() {
        createTheUserRequest = CreateTheUserRequest.getUserAllRequiredField();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Корректное создание пользователя")
    @Description("Проверяем позитивный сценарий создания уникального пользователя. Ожидаем, что возвращается \"success\": true,")
    public void checkTheCreationOfNewUniqUser() {
        userClient.getUniqUser(createTheUserRequest.getUserAllRequiredField())
                .then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        accessToken = userClient.getAccessToken(createTheUserRequest);
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Проверяем сценарий создания дубля пользователя. Ожидаем, что возвращается код 403 и \"message\": \"User already exists ")
    public void checkTheCreationOfExistingUser() {
        userClient.getUniqUser(createTheUserRequest.getUserAllRequiredField());
        userClient.getUniqUser(createTheUserRequest.getUserAllRequiredField())
                .then().statusCode(403)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_DOUBLE));
    }

    @Test
    @DisplayName("Создание пользователя с незаполненным обязательным полем email")
    @Description("Проверяем сценарий создания дубля пользователя. Ожидаем, что возвращается код 403 и \"message\": \"Email, password and name are required fields ")
    public void verifyNotCreateUserWithoutEmail() {
        userClient.getUniqUser(createTheUserRequest.getUserWithoutMail())
                .then().statusCode(403)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_EMPTY_REQUIRED_FIELD));
    }

    @Test
    @DisplayName("Создание пользователя с незаполненным обязательным полем password")
    @Description("Проверяем сценарий создания дубля пользователя. Ожидаем, что возвращается код 403 и \"message\": \"Email, password and name are required fields ")
    public void verifyNotCreateUserWithoutPassword() {
        userClient.getUniqUser(createTheUserRequest.getUserWithoutPassword())
                .then().statusCode(403)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_EMPTY_REQUIRED_FIELD));
    }

    @Test
    @DisplayName("Создание пользователя с незаполненным обязательным полем name")
    @Description("Проверяем сценарий создания дубля пользователя. Ожидаем, что возвращается код 403 и \"message\": \"Email, password and name are required fields ")
    public void verifyNotCreateUserWithoutName() {
        userClient.getUniqUser(createTheUserRequest.getUserWithoutName())
                .then().statusCode(403)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_EMPTY_REQUIRED_FIELD));
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
