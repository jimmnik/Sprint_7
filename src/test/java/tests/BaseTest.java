package tests;

import models.request.CreateCourierRequest;
import org.junit.jupiter.api.AfterEach;
import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.List;

import static steps.CourierSteps.deleteCourier;

public abstract class BaseTest {

    protected final List<CreateCourierRequest> createdCouriers = new ArrayList<>();

    @AfterEach
    void tearDown() {
        for (CreateCourierRequest courier : createdCouriers) {
            deleteCourier(courier)
                    .then()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
        createdCouriers.clear();
    }
}
