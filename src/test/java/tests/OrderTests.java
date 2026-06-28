package tests;

import io.restassured.response.Response;
import enums.Color;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.hamcrest.Matchers.greaterThan;
import static steps.OrderSteps.createOrder;

public class OrderTests {


    @ParameterizedTest(name = "Color = {0}")
    @EnumSource(Color.class)
    public void createOrderSuccessfully(Color color){
        Response response = createOrder(color);
        response.then()
                .statusCode(201)
                .body("track", greaterThan(0));
    }

}
