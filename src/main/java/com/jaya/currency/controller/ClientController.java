package com.jaya.currency.controller;

import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.model.Client;
import com.jaya.currency.service.impl.ClientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/client")
@Api(value = "Client")
public class ClientController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService){
        this.clientService = clientService;
    }

    @ApiOperation(value = "Get all Clients")
    @GetMapping
    public ResponseEntity<List<Client>> getAll(){
        logger.info("Called API to search all clients");
        List<Client> clientList = clientService.findAll();

        logger.info("Found " + clientList.size() + " clients on database");

        if(clientList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(clientService.findAll());
    }

    @ApiOperation(value = "Get Client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id){
        logger.info("Called API to search Client by ID");

        Client client = clientService.findById(id).orElseThrow(() -> {
            logger.error("Client not found");
            return  new ClientNotFoundException(id);
        });

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @ApiOperation(value = "Save Client")
    @PostMapping
    public ResponseEntity<Client> save(@RequestBody @Valid ClientDTO clientDTO){
        logger.info("Called API to save client");
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(clientDTO));
    }

    @ApiOperation(value = "Delete Client by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        logger.info("Called API to save client");
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client "+ id +" deleted successfully");
    }

}
