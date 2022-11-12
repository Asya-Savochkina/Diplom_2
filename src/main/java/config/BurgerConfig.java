package config;

public class BurgerConfig {
    public static final String MAIN_URL = "https://stellarburgers.nomoreparties.site/api/";

    public static String getBaseUri(){
        return MAIN_URL;
    }

    public static final String ORDERS = "orders";

    public static final String USER_REG = "auth/register";

    public static final String USER_AUTH = "auth/login";

    public static final String USER_LOGOUT= "auth/logout";

    public static final String REFRESH_TOKEN= "auth/token";

    public static final String USER_DATA = "auth/user";

    public static final String INGREDIENTS = "ingredients";
}
