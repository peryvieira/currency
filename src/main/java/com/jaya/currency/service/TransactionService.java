package com.jaya.currency.service;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.model.Client;
import com.jaya.currency.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<TransactionDTO> findByClient(Long client);

    List<Transaction> findAll();

    Transaction save (Transaction transaction);

    void delete(Long id);
}
