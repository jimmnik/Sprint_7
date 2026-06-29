package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {

    private static final RequestSpecification REQUEST_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri(Config.BASE_URL)
                    .addHeader("Content-Type", "application/json")
                    .setRelaxedHTTPSValidation()
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();

        //.addFilter(new ErrorLoggingFilter())

    public static Response doPostRequest(String path, Object body){
        return given()
                .spec(REQUEST_SPEC)
                .body(body)
                .post(path)
                .thenReturn();
    }

    public static Response doPutRequest(String path, Map<String, Object> params){
        return given()
                .spec(REQUEST_SPEC)
                .params(params)
                .put(path)
                .thenReturn();
    }

    public static Response doGetRequest(String path, Map<String, Object> params){
        return given()
                .spec(REQUEST_SPEC)
                .params(params)
                .get(path);
    }

    public static Response doDeleteRequest(String path, int id){
        return given()
                .spec(REQUEST_SPEC)
                .delete(path + id);
    }

}
