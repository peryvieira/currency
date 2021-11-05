package com.jaya.currency.service.impl;

import com.jaya.currency.controller.ClientController;
import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.external.ExchangeRatesApi;
import com.jaya.currency.entity.Client;
import com.jaya.currency.model.Currency;
import com.jaya.currency.entity.Transaction;
import com.jaya.currency.repository.TransactionRepository;
import com.jaya.currency.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientServiceImpl clientService){
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
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
        Currency currencyOrigin = new Currency();
        Currency currencyFinal = new Currency();
        currencyOrigin.setAbbreviation(transactionDTO.getCurrencyAbbreviationOrigin());
        currencyFinal.setAbbreviation(transactionDTO.getCurrencyAbbreviationFinal());

        logger.info("Converting {} from {} to {}", transactionDTO.getAmountOrigin(),currencyOrigin.getAbbreviation(), currencyFinal.getAbbreviation());

        currencyOrigin.setRate(ExchangeRatesApi.getRateByCurrency(currencyOrigin));
        currencyFinal.setRate(ExchangeRatesApi.getRateByCurrency(currencyFinal));

        /**
        *    The free External API only returns conversion rate based on EUR.
        *    Therefore is applied a rule for conversion on others currencies, dividing the final rate by the original rate.
        */

        BigDecimal conversionRelative = currencyFinal.getRate().divide(currencyOrigin.getRate(), MathContext.DECIMAL32);

        logger.info("Conversion rate to apply is {}",conversionRelative);

        Transaction transactionSaved = transactionRepository.save(Transaction.builder()
                .client(this.getClient(transactionDTO.getIdClient()))
                .currencyOrigin(currencyOrigin.getAbbreviation())
                .currencyFinal(currencyFinal.getAbbreviation())
                .amountOrigin(transactionDTO.getAmountOrigin())
                .conversionRate(conversionRelative)
                .createdAt(LocalDate.now()).build());

        logger.info("Transaction {} saved successfully.", transactionSaved.getId());

        return TransactionResponseDTO.convertToDTO(transactionSaved);
    }

    private Client getClient(Long id){
        return clientService.findById(id).orElseThrow(() ->  new ClientNotFoundException(id));
    }
}
