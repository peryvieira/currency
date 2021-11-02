package com.jaya.currency.service;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionResponseDTO> findByClient(Long client);

    List<Transaction> findAll();

    TransactionResponseDTO convert(TransactionDTO transactionDTO);

    void delete(Long id);
}
