package com.jaya.currency.external;

import com.jaya.currency.exception.CurrencyException;
import com.jaya.currency.model.Currency;
import com.jaya.currency.util.CurrencySerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class  ExchangeRatesApi {

    private static final String accessKey = "d05dfef43ddb55051affce79f8a1845d";

    public static BigDecimal getRateByCurrency(Currency currencyFinal){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.exchangeratesapi.io/v1/latest?access_key=" + getAccessKey();
        CurrencySerializer response = restTemplate.getForObject(url, CurrencySerializer.class);

        try {
           return response.getRates().get(currencyFinal.getAbbreviation());
        } catch (Exception e){
            throw new CurrencyException("Final currency cannot be found", HttpStatus.NOT_FOUND);
        }
    }

    private static String getAccessKey(){
        return accessKey;
    }
}
