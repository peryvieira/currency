package com.jaya.currency.controller;

import com.jaya.currency.model.Client;
import com.jaya.currency.service.impl.ClientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService){
        this.clientService = clientService;
    }

    //TODO Paginação
    @GetMapping
    public ResponseEntity<List<Client>> getAll(){
        return ResponseEntity.ok().body(clientService.findAll());
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client){
        return ResponseEntity.ok().body(clientService.save(client));
    }

}
