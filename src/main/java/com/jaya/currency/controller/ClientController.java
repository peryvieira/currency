package com.jaya.currency.controller;

import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.model.Client;
import com.jaya.currency.service.impl.ClientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/client")
@Api(value = "Client")
public class ClientController {

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService){
        this.clientService = clientService;
    }

    @ApiOperation(value = "Get all Clients")
    @GetMapping
    public ResponseEntity<List<Client>> getAll(){
        List<Client> clientList = clientService.findAll();

        if(clientList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(clientService.findAll());
    }

    @ApiOperation(value = "Get Client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id){
        Client client = clientService.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @ApiOperation(value = "Save Client")
    @PostMapping
    public ResponseEntity<Client> save(@RequestBody @Valid ClientDTO clientDTO){

        return ResponseEntity.ok().body(clientService.save(clientDTO));
    }

}
