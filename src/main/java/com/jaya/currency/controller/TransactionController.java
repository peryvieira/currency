package com.jaya.currency.controller;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.service.impl.TransactionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> convertCurrency(@RequestBody TransactionDTO transactionDTO){
        return  ResponseEntity.ok().body(transactionService.convert(transactionDTO));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<TransactionResponseDTO>> getByClient(@PathVariable Long id){
        return ResponseEntity.ok().body(transactionService.findByClient(id));
    }

}
