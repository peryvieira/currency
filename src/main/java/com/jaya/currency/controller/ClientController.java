package com.jaya.currency.controller;

import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.model.Client;
import com.jaya.currency.service.impl.ClientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        List<Client> clientList = clientService.findAll();

        if(clientList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id){
        Client client = clientService.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody @Valid ClientDTO clientDTO){

        return ResponseEntity.ok().body(clientService.save(clientDTO));
    }

}
