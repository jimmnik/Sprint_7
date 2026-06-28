package tests;

import io.restassured.response.Response;
import models.request.CreateCourierRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.List;
import static steps.CourierSteps.*;

public class CreateCourierTests extends BaseTest{

    @Test
    void createCourierSuccessful() {
        CreateCourierRequest courier = randomCourier();

        Response courierResponse = createCourierAccount(courier);

        assertSuccessfulCourierCreated(courierResponse);

        createdCouriers.add(courier);
    }

    @Test
    void createCourierSuccessfulWithOutFirstName() {
        CreateCourierRequest courier = randomCourier();
        courier.setFirstName(null);

        Response courierResponse = createCourierAccount(courier);

        assertSuccessfulCourierCreated(courierResponse);

        createdCouriers.add(courier);
    }

    @Test
    void createCourierFailsWithoutLogin() {
        CreateCourierRequest courier = randomCourier();
        courier.setLogin(null);

        Response courierResponse = createCourierAccount(courier);

        courierResponse.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    void createCourierFailsWithoutPassword() {
        CreateCourierRequest courier = randomCourier();
        courier.setPassword(null);

        Response courierResponse = createCourierAccount(courier);

        courierResponse.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    void duplicateCourier() {
        CreateCourierRequest courierOriginal = randomCourier();
        CreateCourierRequest courierDuplicate = randomCourier();
        courierDuplicate.setLogin(courierOriginal.getLogin());
        courierDuplicate.setFirstName("Naruto");

        //Выполнение запроса на создание курьера
        Response courierOriginalResponse = createCourierAccount(courierOriginal);
        assertSuccessfulCourierCreated(courierOriginalResponse);

        //Выполнение запроса на создание дубликата курьера
        Response courierDuplicateResponse = createCourierAccount(courierDuplicate);
        courierDuplicateResponse.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        createdCouriers.add(courierOriginal);

    }

}

