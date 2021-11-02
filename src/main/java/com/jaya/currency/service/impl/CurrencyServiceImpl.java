package com.jaya.currency.service.impl;

import com.jaya.currency.model.Currency;
import com.jaya.currency.repository.CurrencyRepository;
import com.jaya.currency.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }

    @Override
    public Optional<Currency> findByAbbreviation(String abbreviation) {
        return Optional.empty();
    }

    @Override
    public Currency save(Currency currency) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
