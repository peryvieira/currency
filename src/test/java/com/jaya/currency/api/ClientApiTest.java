package com.jaya.currency.api;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;


public class ClientApiTest extends BaseApi{


//    @Test
//    public void saveClient() throws JSONException {
//
//        given().body("{\n" +
//                "   \"name\": \"Pery\"}")
//                .contentType(ContentType.JSON)
//                .when().post("/v1/client")
//                .then().statusCode(201);
//    }
}
