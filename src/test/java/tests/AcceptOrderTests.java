package tests;

import enums.Color;
import io.restassured.response.Response;
import models.request.CreateCourierRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static client.BaseHttpClient.doGetRequest;
import static client.Config.PATH_ORDER;
import static org.hamcrest.Matchers.*;
import static steps.CourierSteps.*;
import static steps.OrderSteps.*;

public class AcceptOrderTests extends BaseTest{

    @Test
    public void getOrderList() {
        // Создали курьера
        CreateCourierRequest courier = createCourier();
        createdCouriers.add(courier);
        //Залогинись
        Response loginResponse = loginCourier(courier);
        assertSuccessfulLogin(loginResponse);
        // Получили id курьера
        int id = loginResponse.path("id");

        //создали заказ
        Response order = createOrder(Color.BLACK);
        order.then()
                .statusCode(201)
                .body("track", greaterThan(0));

        int idOrder = getOrderIdByTrack(order);

        //Приняли заказ на курьера
        Response acceptResponse = acceptOrder(id, idOrder);
        acceptResponse.then()
                .statusCode(200)
                .body("ok", equalTo(true));;

        //Получили список заказов по курьеру
        Map<String, Object> paramsForGet = new HashMap<>();
        paramsForGet.put("courierId", id);

        Response orderList = doGetRequest(PATH_ORDER, paramsForGet);

        orderList.then()
                .statusCode(200)
                .body("orders.id", hasItem(idOrder));
    }
}