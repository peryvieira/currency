package com.jaya.currency.service.impl;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.exception.CurrencyException;
import com.jaya.currency.model.Client;
import com.jaya.currency.model.Currency;
import com.jaya.currency.model.Transaction;
import com.jaya.currency.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ClientServiceImpl clientService;
    @Mock
    private CurrencyServiceImpl currencyService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTransactionByClientSuccess(){
        BDDMockito.given(clientService.findById(1L)).willReturn(Optional.of(this.clientFound()));
        BDDMockito.given(transactionRepository.findByClient(this.clientFound())).willReturn(this.transactionListFound());

        List<TransactionResponseDTO> transactionResponseDTO =  transactionService.findByClient(1L);

        BDDMockito.then(transactionRepository).should(Mockito.times(1)).findByClient(this.clientFound());
        Assertions.assertThat(transactionResponseDTO).isNotNull();
        Assertions.assertThat(transactionResponseDTO.get(0).getAmountFinal()).isNotZero();
    }

    @Test(expected = ClientNotFoundException.class)
    public void testFindTransactionByClientException(){
        BDDMockito.given(clientService.findById(1L)).willReturn(Optional.empty());

        transactionService.findByClient(1L);
    }

    @Test
    public void testFindAllTransactionSuccess(){
        BDDMockito.given(transactionRepository.findAll()).willReturn(this.transactionListFound());

        List<TransactionResponseDTO> transactionResponseDTO =  transactionService.findAll();

        BDDMockito.then(transactionRepository).should(Mockito.times(1)).findAll();
        Assertions.assertThat(transactionResponseDTO).isNotNull();
        Assertions.assertThat(transactionResponseDTO.get(0).getAmountFinal()).isNotZero();
    }

    //Evitar chamada da Api Externa
    @Ignore
    @Test
    public void testConvertCurrencySuccess(){
        TransactionDTO transactionDTO = TransactionDTO.builder().idClient(1L).amountOrigin(transactionFound().getAmountOrigin()).currencyAbbreviationOrigin(transactionFound().getCurrencyOrigin().getAbbreviation())
                .currencyAbbreviationFinal(transactionFound().getCurrencyFinal().getAbbreviation()).build();
        BDDMockito.given(currencyService.findByAbbreviation("BRL")).willReturn(Optional.of(Currency.builder().id(1L).abbreviation("BRL").name("Real").build()));
        BDDMockito.given(currencyService.findByAbbreviation("EUR")).willReturn(Optional.of(Currency.builder().id(2L).abbreviation("EUR").name("Euro").build()));
        BDDMockito.given(transactionRepository.save(Mockito.any(Transaction.class))).willReturn(this.transactionFound());
        BDDMockito.given(clientService.findById(1L)).willReturn(Optional.of(this.clientFound()));

        TransactionResponseDTO transactionResponseDTO = transactionService.convert(transactionDTO);

        BDDMockito.then(transactionRepository).should(Mockito.times(1)).save(this.transactionFound());
        Assertions.assertThat(transactionResponseDTO.getAmountFinal()).isNotZero();
    }

    @Test(expected = CurrencyException.class)
    public void testConvertNullCurrencyException(){
        BDDMockito.given(currencyService.findByAbbreviation("BR")).willReturn(Optional.empty());

        transactionService.convert(TransactionDTO.builder().currencyAbbreviationOrigin("BR").build());
    }

    private Client clientFound(){
        return Client.builder()
                .id(1L)
                .name("Pery")
                .createdAt(LocalDate.now()).build();
    }

    private Transaction transactionFound(){
        return Transaction.builder()
                .client(this.clientFound())
                .currencyOrigin(Currency.builder().id(1L).abbreviation("BRL").name("Real").build())
                .currencyFinal(Currency.builder().id(2L).abbreviation("EUR").name("Euro").build())
                .amountOrigin(BigDecimal.TEN)
                .conversionRate(BigDecimal.valueOf(0.1550877))
                .createdAt(LocalDate.now()).build();
    }

    private List<Transaction> transactionListFound(){
        List<Transaction> list = new ArrayList<>();

        list.add(Transaction.builder()
                .id(1L)
                .client(this.clientFound())
                .currencyOrigin(Currency.builder().abbreviation("EUR").build())
                .currencyFinal(Currency.builder().abbreviation("BRL").build())
                .amountOrigin(BigDecimal.TEN)
                .conversionRate(BigDecimal.TEN)
                .createdAt(LocalDate.now()).build());

        list.add(Transaction.builder()
                .id(2L)
                .client(this.clientFound())
                .currencyOrigin(Currency.builder().abbreviation("BRL").build())
                .currencyFinal(Currency.builder().abbreviation("JPY").build())
                .amountOrigin(BigDecimal.TEN)
                .conversionRate(BigDecimal.valueOf(5))
                .createdAt(LocalDate.now()).build());

        return list;
    }

}