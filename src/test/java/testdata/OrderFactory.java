package testdata;

import enums.Color;
import models.request.OrderRequest;

public final class OrderFactory {

    private OrderFactory() {
    }

    public static OrderRequest defaultOrder(Color color) {

        String[] colors;

        switch (color) {
            case BLACK:
                colors = new String[]{"BLACK"};
                break;
            case GREY:
                colors = new String[]{"GREY"};
                break;
            case BOTH:
                colors = new String[]{"BLACK", "GREY"};
                break;
            case ANY:
                colors = new String[]{};
                break;
            default:
                throw new IllegalArgumentException("Unknown color: " + color);
        }

        return new OrderRequest(
                "Иван",
                "Иванов",
                "г. Москва ул. Советская д. 1",
                "26",
                "+79999999999",
                1,
                "2026-06-25T21:00:00.000Z",
                "Тестовый комментарий для курьера",
                colors
        );
    }
}
