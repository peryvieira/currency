package com.jaya.currency.controller;

import com.jaya.currency.dto.TransactionDTO;
import com.jaya.currency.dto.TransactionResponseDTO;
import com.jaya.currency.service.impl.TransactionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Transaction")
@RestController
@RequestMapping("v1/transaction")
public class TransactionController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService){
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Convert currency")
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> convertCurrency(@RequestBody @Valid TransactionDTO transactionDTO){
        logger.info("Called API to convert currency");
        return  ResponseEntity.ok().body(transactionService.convert(transactionDTO));
    }

    @ApiOperation(value = "Get Transactions by Client")
    @GetMapping("/client/{id}")
    public ResponseEntity<List<TransactionResponseDTO>> getByClient(@PathVariable Long id){
        logger.info("Called API to search transaction by Client");
        return ResponseEntity.ok().body(transactionService.findByClient(id));
    }

    @ApiOperation(value = "Get All Transactions")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAll(){
        logger.info("Called API to search all transactions on database");
        return ResponseEntity.ok().body(transactionService.findAll());
    }

}
