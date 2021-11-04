package com.jaya.currency.util;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencySerializer implements Serializable {
    private String base;
    private Map<String, BigDecimal> rates;
}