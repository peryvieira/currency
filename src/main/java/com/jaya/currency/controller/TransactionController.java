package com.jaya.currency.controller;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.service.impl.TransactionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService){
        this.transactionService = transactionService;
    }


    @GetMapping("/client/{id}")
    public ResponseEntity<List<TransactionDTO>> getByClient(@PathVariable Long id){
        return ResponseEntity.ok().body(transactionService.findByClient(id));
    }
}