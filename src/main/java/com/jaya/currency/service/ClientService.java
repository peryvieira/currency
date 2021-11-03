package com.jaya.currency.service;

import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();

    Optional<Client> findById(Long id);

    Client save(ClientDTO clientDTO);

    void delete(Long id);
}
