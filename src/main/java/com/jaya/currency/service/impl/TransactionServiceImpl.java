package com.jaya.currency.service.impl;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.model.Client;
import com.jaya.currency.model.Transaction;
import com.jaya.currency.repository.TransactionRepository;
import com.jaya.currency.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientServiceImpl clientService;



    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientServiceImpl clientService){
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
    }

    @Override
    public List<TransactionDTO> findByClient(Long idClient) {
        Client client = this.getClient(idClient);
        List<TransactionDTO> listTransactionDTO = new ArrayList<>();

        transactionRepository.findByClient(client).forEach(transaction -> listTransactionDTO.add(TransactionDTO.convertToDTO(transaction)));

        return listTransactionDTO;
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private Client getClient(Long id){
        return clientService.findById(id).orElseThrow(NullPointerException::new);
    }
}
