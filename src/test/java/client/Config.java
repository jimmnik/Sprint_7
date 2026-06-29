package client;

public final class Config {
    private Config(){}

    public static final String BASE_URL = "https://qa-scooter.education-services.ru/";

    public static final String PATH_COURIER  = "/api/v1/courier/";
    public static final String PATH_LOGIN = "/api/v1/courier/login";
    public static final String PATH_ORDER = "/api/v1/orders";
    public static final String PATH_ACCEPT_ORDER = "/api/v1/orders/accept/";
    public static final String PATH_ORDER_TRACK = "/api/v1/orders/track";
}
