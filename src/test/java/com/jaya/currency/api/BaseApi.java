package com.jaya.currency.api;

import org.junit.BeforeClass;
import static io.restassured.RestAssured.*;

public class BaseApi {

    @BeforeClass
    public static void preCondition(){

        baseURI = "http://localhost";
        port = 8080;
    }
}