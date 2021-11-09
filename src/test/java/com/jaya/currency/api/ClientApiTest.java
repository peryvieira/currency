package com.jaya.currency.api;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import static io.restassured.RestAssured.*;


public class ClientApiTest extends BaseApi{


    @Test
    public void saveClient() throws JSONException {

        given().body("{\n" +
                "   \"name\": \"Pery\"}")
                .contentType(ContentType.JSON)
                .when().post("/v1/client")
                .then().assertThat().statusCode(201);
    }
}
