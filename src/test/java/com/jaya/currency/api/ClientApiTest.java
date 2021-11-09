package com.jaya.currency.api;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;


public class ClientApiTest extends BaseApi{

    @Test
    public void saveClientSuccess() throws JSONException {

        JSONObject requestParams = new JSONObject();
        requestParams.put("name","Pery");

        given().body(requestParams.toString())
                .contentType(ContentType.JSON)
                .when().post("/v1/client")
                .then().assertThat().statusCode(201).body(containsString("id")).extract().response();

    }

    @Test
    public void saveClientException() throws JSONException{
        JSONObject requestParams = new JSONObject();
        requestParams.put("nome","Pery");

        given().body(requestParams.toString())
                .contentType(ContentType.JSON)
                .when().post("/v1/client")
                .then().assertThat().statusCode(400).body(containsString("Client name cannot be null"));
    }

    @Test
    public void findClientSuccess() {
        Long idClient = 1L;

        given().when().get("/v1/client/"+idClient)
                .then().assertThat().statusCode(200)
                .body(containsString("id"),containsString("name"), containsString("createdAt"));
    }

    @Test
    public void findAllClientsSuccess(){
        given().when().get("/v1/client")
                .then().assertThat().statusCode(200)
                .body(containsString("id"),containsString("name"), containsString("createdAt"));
    }
}

