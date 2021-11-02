package com.jaya.currency.service.impl;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.model.Client;
import com.jaya.currency.model.Currency;
import com.jaya.currency.model.Transaction;
import com.jaya.currency.repository.TransactionRepository;
import com.jaya.currency.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientServiceImpl clientService;
    private final CurrencyServiceImpl currencyService;



    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientServiceImpl clientService, CurrencyServiceImpl currencyService){
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
        this.currencyService = currencyService;
    }

    @Override
    public List<TransactionResponseDTO> findByClient(Long idClient) {
        Client client = this.getClient(idClient);
        List<TransactionResponseDTO> listTransactionResponseDTO = new ArrayList<>();

        transactionRepository.findByClient(client).forEach(transaction -> listTransactionResponseDTO.add(TransactionResponseDTO.convertToDTO(transaction)));

        return listTransactionResponseDTO;
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }

    @Override
    public TransactionResponseDTO convert(TransactionDTO transactionDTO) {
        Currency currencyOrigin = getCurrencyByAbbreviation(transactionDTO.getCurrencyAbbreviationOrigin());
        Currency currencyFinal = getCurrencyByAbbreviation(transactionDTO.getCurrencyAbbreviationFinal());


        //TODO Buscar o Rate pela API
        BigDecimal conversionRate = BigDecimal.TEN;

        Transaction transactionReturn =   transactionRepository.save(Transaction.builder()
                .client(this.getClient(transactionDTO.getIdClient()))
                .currencyOrigin(currencyOrigin)
                .currencyFinal(currencyFinal)
                .amountOrigin(transactionDTO.getAmountOrigin())
                .conversionRate(conversionRate)
                .createdAt(LocalDate.now()).build());

        return TransactionResponseDTO.convertToDTO(transactionReturn);
    }

    @Override
    public void delete(Long id) {

    }

    private Client getClient(Long id){
        return clientService.findById(id).orElseThrow(NullPointerException::new);
    }

    private Currency getCurrencyByAbbreviation(String abbreviation){
        return currencyService.findByAbbreviation(abbreviation).orElseThrow(NullPointerException::new);
    }
}
