import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateTheUserRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateTheUserTest {
    UserClient userClient;
    CreateTheUserRequest createTheUserRequest;

    @Before
    public void setup() {
        createTheUserRequest = CreateTheUserRequest.getUserAllRequiredField();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Корректное создание пользователя")
    @Description("Проверяем позитивный сценарий создания уникального пользователя. Ожидаем, что возвращается \"success\": true,")
    public void createNewUniqUser() {
        userClient.getUniqUser(createTheUserRequest.getUserAllRequiredField())
                .then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

}
