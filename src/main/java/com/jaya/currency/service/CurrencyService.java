package com.jaya.currency.service;

import com.jaya.currency.model.Currency;

import java.util.Optional;

public interface CurrencyService {

    Optional<Currency> findById(Long id);

    Optional<Currency> findByAbbreviation(String abbreviation);

    Currency save(Currency currency);
}
