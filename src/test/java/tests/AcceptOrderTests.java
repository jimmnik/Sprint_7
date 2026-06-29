package tests;

import enums.Color;
import io.restassured.response.Response;
import models.request.CreateCourierRequest;
import models.request.OrderRequest;
import models.response.Order;
import models.response.OrderListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.OrderFactory;
import java.util.HashMap;
import java.util.Map;
import static client.BaseHttpClient.doGetRequest;
import static client.BaseHttpClient.doPostRequest;
import static client.Config.PATH_ORDER;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static steps.CourierSteps.*;
import static steps.OrderSteps.*;

public class AcceptOrderTests extends BaseTest{

    @Test
    @DisplayName("Получение списка заказов курьера")
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
        OrderRequest request = OrderFactory.defaultOrder(Color.BLACK);
        Response response = doPostRequest(PATH_ORDER, request);
        response.then()
                .statusCode(201)
                .body("track", greaterThan(0));

        int idOrder = getOrderIdByTrack(response);

        //Приняли заказ на курьера
        Response acceptResponse = acceptOrder(id, idOrder);
        acceptResponse.then()
                .statusCode(200)
                .body("ok", equalTo(true));;

        //Получили список заказов по курьеру
        Map<String, Object> paramsForGet = new HashMap<>();
        paramsForGet.put("courierId", id);
        Response ordersListResponse = doGetRequest(PATH_ORDER, paramsForGet);
        ordersListResponse.then()
                .statusCode(200)
                .body(not(emptyOrNullString()));
        OrderListResponse ordersListBodyResponse = ordersListResponse.as(OrderListResponse.class);

        //Проверка полученного списка заказов
        Order order = ordersListBodyResponse.getOrders()
                .stream()
                .filter(o -> o.getId() == idOrder)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Заказ с id=" + idOrder + " не найден"));
        assertOrderEquals(request, order);
        assertEquals(1, ordersListBodyResponse.getOrders().size(),
                "Количество заказов у курьера отличается от ожидаемого");
    }
}