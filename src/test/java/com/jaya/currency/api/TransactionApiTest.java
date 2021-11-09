package com.jaya.currency.api;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TransactionApiTest extends BaseApi{

    @Test
    public void convertSuccess() throws JSONException {

        JSONObject requestParams = new JSONObject();
        requestParams.put("idClient",1);
        requestParams.put("amountOrigin","10.00");
        requestParams.put("currencyAbbreviationOrigin","BRL");
        requestParams.put("currencyAbbreviationFinal","EUR");


        given().body(requestParams.toString())
                .contentType(ContentType.JSON)
                .when().post("/v1/transaction")
                .then().assertThat().statusCode(200).body(  containsString("id"),
                                                            containsString("idClient"),
                                                            containsString("amountFinal"),
                                                            containsString("conversionRate"));

    }
}
