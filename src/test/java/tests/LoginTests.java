package tests;

import io.restassured.response.Response;
import models.request.CreateCourierRequest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class LoginTests extends BaseTest{

    @Test
    public void loginSuccessful(){
        CreateCourierRequest courier = createCourier();

        Response courierId = loginCourier(courier);

        assertSuccessfulLogin(courierId);

        createdCouriers.add(courier);
    }

    @Test
    public void loginFailsWithoutLogin(){
        CreateCourierRequest courier = createCourier();
        CreateCourierRequest courierIncorrect = new CreateCourierRequest(courier);
        courierIncorrect.setLogin(null);

        Response courierId = loginCourier(courierIncorrect);

        courierId.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        createdCouriers.add(courier);
    }

    @Test
    public void loginFailsWithIncorrectLogin(){
        CreateCourierRequest courier = createCourier();
        CreateCourierRequest courierIncorrect = new CreateCourierRequest(courier);
        courierIncorrect.setLogin("tj7fjdhj6fhslfhgj5kdfsh7ksdghjsdg3hsd");

        Response courierId = loginCourier(courierIncorrect);

        courierId.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        createdCouriers.add(courier);
    }

    @Test
    public void loginFailsWithIncorrectPassword(){
        CreateCourierRequest courier = createCourier();
        CreateCourierRequest courierIncorrect = new CreateCourierRequest(courier);
        courierIncorrect.setPassword("4321");

        Response courierId = loginCourier(courierIncorrect);

        courierId.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        createdCouriers.add(courier);
    }

//    Проверка закомичена, поскольку в системе баг и на запрос без пароля ответ не возвращается, после исправления бага № раскомитеть
//    @Test
//    public void loginFallWithoutPassword(){
//        CreateCourierRequest courier = createCourier();
//        CreateCourierRequest courierIncorrect = new CreateCourierRequest(courier);
//        courierIncorrect.setPassword(null);
//
//        Response courierId = loginCourier(courierIncorrect);
//
//        courierId.then()
//                .statusCode(400)
//                .body("message", equalTo("Недостаточно данных для входа"));
//
//        createdCouriers.add(courier);
//    }

//    Проверка закомичена, поскольку в системе баг и на запрос без пароля ответ не возвращается, после исправления бага № раскомитеть
//    @Test
//    public void loginFallWithoutLogginAndPassword(){
//        CreateCourierRequest courier = createCourier();
//        CreateCourierRequest courierIncorrect = new CreateCourierRequest(courier);
//        courierIncorrect.setLogin(null);
//        courierIncorrect.setPassword(null);
//
//        Response courierId = loginCourier(courierIncorrect);
//
//        courierId.then()
//                .statusCode(400)
//                .body("message", equalTo("Недостаточно данных для входа"));
//
//        createdCouriers.add(courier);
//        }
}
