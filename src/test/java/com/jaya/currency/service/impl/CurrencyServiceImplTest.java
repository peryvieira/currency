package com.jaya.currency.service.impl;

import com.jaya.currency.model.Currency;
import com.jaya.currency.repository.CurrencyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    @Mock
    CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindCurrencyByIdSuccess(){
        BDDMockito.given(currencyRepository.findById(1L)).willReturn(Optional.of(this.currencyFound()));

        currencyService.findById(1L);

        BDDMockito.then(currencyRepository).should(Mockito.times(1)).findById(1L);
    }

    @Test
    public void testFindCurrencyByAbbreviationSuccess(){
        BDDMockito.given(currencyRepository.findByAbbreviation("BRL")).willReturn(Optional.of(this.currencyFound()));

        currencyService.findByAbbreviation("BRL");

        BDDMockito.then(currencyRepository).should(Mockito.times(1)).findByAbbreviation("BRL");
    }

    @Test
    public void testSaveCurrencySuccess(){
        BDDMockito.given(currencyRepository.save(Currency.builder().name("Real").abbreviation("BRL").build())).willReturn(this.currencyFound());

        currencyService.save(Currency.builder().name("Real").abbreviation("BRL").build());

        BDDMockito.then(currencyRepository).should(Mockito.times(1)).save(Mockito.any(Currency.class));
    }

    private Currency currencyFound(){
        return Currency.builder().id(1L).name("Real").abbreviation("BRL").build();
    }
}
