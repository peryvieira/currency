package com.jaya.currency.controller;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.service.impl.TransactionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Transaction")
@RestController
@RequestMapping("v1/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService){
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Convert currency")
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> convertCurrency(@RequestBody @Valid TransactionDTO transactionDTO){
        return  ResponseEntity.ok().body(transactionService.convert(transactionDTO));
    }

    @ApiOperation(value = "Get Transaction by ID")
    @GetMapping("/client/{id}")
    public ResponseEntity<List<TransactionResponseDTO>> getByClient(@PathVariable Long id){
        return ResponseEntity.ok().body(transactionService.findByClient(id));
    }

}
