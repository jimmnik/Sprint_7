package steps;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.request.CourierLoginRequest;
import models.request.CreateCourierRequest;

import static client.BaseHttpClient.doDeleteRequest;
import static client.BaseHttpClient.doPostRequest;
import static client.Config.PATH_COURIER;
import static client.Config.PATH_LOGIN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class CourierSteps {

    @Step("Отпрвака запроса на создание курьера")
    public static Response createCourierAccount(CreateCourierRequest courier){

        return doPostRequest(PATH_COURIER, courier);

    }
    @Step("Генерация данных для создания курьера")
    public static CreateCourierRequest randomCourier() {
        return new CreateCourierRequest(
                "ninja" + System.currentTimeMillis(),
                "1234",
                "Saske"
        );
    }

    public static CreateCourierRequest createCourier(){
        CreateCourierRequest courier = randomCourier();
        createCourierAccount(courier);
        return courier;

    }

    @Step("Вход в систему для куратора")
    public static Response loginCourier(CreateCourierRequest courier){
        return doPostRequest(PATH_LOGIN, new CourierLoginRequest(courier));
    }

    @Step("Удаление куратора")
    public static Response deleteCourier(CreateCourierRequest courier){
        Response login = loginCourier(courier);
        int id = login.path("id");
        return doDeleteRequest(PATH_COURIER, id);
    }

    @Step("Проверка успешного входа в систему")
    public static void assertSuccessfulLogin(Response response) {
        response.then()
                .statusCode(200)
                .body("id", greaterThanOrEqualTo(0));

    }

    @Step
    public static void assertSuccessfulCourierCreated(Response response) {
        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }
}
