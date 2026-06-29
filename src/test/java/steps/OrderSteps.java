package steps;

import enums.Color;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.request.OrderRequest;
import models.response.Order;

import static client.Config.*;
import java.util.HashMap;
import java.util.Map;
import static client.BaseHttpClient.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static testdata.OrderFactory.defaultOrder;

public class OrderSteps {

    @Step("Отправка запроса на создание заказа")
    public static Response createOrder(Color color) {
        return doPostRequest(PATH_ORDER, defaultOrder(color));
    }

    @Step("Получение id заказа по его track")
    public static int getOrderIdByTrack(Response order){
        //По трекеру нашли id заказа
        Map<String, Object> paramsGet = new HashMap<>();
        paramsGet.put("t", order.path("track"));
        Response ordersTrack = doGetRequest(PATH_ORDER_TRACK, paramsGet);
        ordersTrack.then()
                .statusCode(200);
        return ordersTrack.path("order.id");
    }

    @Step("Назначение заказа на курьера")
    public static Response acceptOrder(int courierId, int orderId){
        //Приняли заказ на курьере
        String path = PATH_ACCEPT_ORDER + orderId;
        Map<String, Object> paramsForPut = new HashMap<>();
        paramsForPut.put("courierId", courierId);
        return doPutRequest(path, paramsForPut);
    }

    @Step("Сверка данных из запроса и ответа")
    public static void assertOrderEquals(OrderRequest expected, Order actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getMetroStation(), actual.getMetroStation());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getRentTime(), actual.getRentTime());
        assertEquals(expected.getDeliveryDate(), actual.getDeliveryDate());
        assertEquals(expected.getComment(), actual.getComment());
        assertArrayEquals(expected.getColor(), actual.getColor());
    }
}
