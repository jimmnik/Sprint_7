package steps;

import enums.Color;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static client.Config.*;
import java.util.HashMap;
import java.util.Map;
import static client.BaseHttpClient.*;
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
}
