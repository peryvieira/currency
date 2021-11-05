package com.jaya.currency.external;

import com.jaya.currency.exception.CurrencyException;
import com.jaya.currency.model.Currency;
import com.jaya.currency.util.CurrencySerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class  ExchangeRatesApi {

    private static final Logger logger = LogManager.getLogger(ExchangeRatesApi.class);
    private static final String accessKey = "d05dfef43ddb55051affce79f8a1845d";

    public static CurrencySerializer loadApiExchanges(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.exchangeratesapi.io/v1/latest?access_key=" + getAccessKey();
        return restTemplate.getForObject(url, CurrencySerializer.class);
    }

    public static BigDecimal getRateByCurrency(Currency currencyFinal){
        CurrencySerializer currencySerializer = loadApiExchanges();
        BigDecimal rateFound;

        try {
            rateFound = currencySerializer.getRates().get(currencyFinal.getAbbreviation());
        } catch (Exception e){
            throw new CurrencyException("Error while searching for rate by currency abbreviation", HttpStatus.NOT_FOUND);
        }

        if(rateFound == null || rateFound.equals(0.00)){
            logger.error("Currency {} cannot be found",currencyFinal.getAbbreviation());
            throw new CurrencyException("Invalid currency", HttpStatus.NOT_FOUND);
        }

        return rateFound;
    }

    private static String getAccessKey(){
        return accessKey;
    }
}
