package com.jaya.currency.service.impl;

import com.jaya.currency.controller.ClientController;
import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.exception.CurrencyException;
import com.jaya.currency.external.ExchangeRatesApi;
import com.jaya.currency.model.Client;
import com.jaya.currency.model.Currency;
import com.jaya.currency.model.Transaction;
import com.jaya.currency.repository.TransactionRepository;
import com.jaya.currency.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    private  TransactionRepository transactionRepository;
    private  ClientServiceImpl clientService;
    private  CurrencyServiceImpl currencyService;



    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientServiceImpl clientService, CurrencyServiceImpl currencyService){
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
        this.currencyService = currencyService;
    }

    @Override
    public List<TransactionResponseDTO> findByClient(Long idClient) {
        Client client = this.getClient(idClient);
        List<TransactionResponseDTO> listTransactionResponseDTO = new ArrayList<>();
        logger.info("Searching transactions of Client {}",idClient);
        transactionRepository.findByClient(client).forEach(transaction -> listTransactionResponseDTO.add(TransactionResponseDTO.convertToDTO(transaction)));

        logger.info("Found {} transactions of Client {}",listTransactionResponseDTO.size(), idClient);
        return listTransactionResponseDTO;
    }

    @Override
    public List<TransactionResponseDTO> findAll() {
        List<TransactionResponseDTO> listTransactionResponseDTO = new ArrayList<>();

        logger.info("Searching all transactions on database");
        transactionRepository.findAll().forEach(transaction -> listTransactionResponseDTO.add(TransactionResponseDTO.convertToDTO(transaction)));

        logger.info("Found {} transactions on database", listTransactionResponseDTO.size());
        return listTransactionResponseDTO;
    }

    @Override
    public TransactionResponseDTO convert(TransactionDTO transactionDTO) {
        Currency currencyOrigin = getCurrencyByAbbreviation(transactionDTO.getCurrencyAbbreviationOrigin());
        Currency currencyFinal = getCurrencyByAbbreviation(transactionDTO.getCurrencyAbbreviationFinal());

        logger.info("Converting {} from {} to {}", transactionDTO.getAmountOrigin(),currencyOrigin.getName(), currencyFinal.getName());

        BigDecimal conversionRateOrigin = ExchangeRatesApi.getRateByCurrency(currencyOrigin);
        BigDecimal conversionRateFinal = ExchangeRatesApi.getRateByCurrency(currencyFinal);
        BigDecimal conversionRelative = conversionRateFinal.divide(conversionRateOrigin, MathContext.DECIMAL32);

        logger.info("Conversion rate to apply is {}",conversionRelative);

        Transaction transactionSaved = transactionRepository.save(Transaction.builder()
                .client(this.getClient(transactionDTO.getIdClient()))
                .currencyOrigin(currencyOrigin)
                .currencyFinal(currencyFinal)
                .amountOrigin(transactionDTO.getAmountOrigin())
                .conversionRate(conversionRelative)
                .createdAt(LocalDate.now()).build());

        logger.info("Transaction {} saved successfully.", transactionSaved.getId());

        return TransactionResponseDTO.convertToDTO(transactionSaved);
    }

    private Client getClient(Long id){
        return clientService.findById(id).orElseThrow(() ->  new ClientNotFoundException(id));
    }

    private Currency getCurrencyByAbbreviation(String abbreviation){
        return currencyService.findByAbbreviation(abbreviation).orElseThrow(() -> new CurrencyException("Currency " + abbreviation + " cannot be found", HttpStatus.NOT_FOUND));
    }
}
