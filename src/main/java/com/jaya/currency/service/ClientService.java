package com.jaya.currency.service;

import com.jaya.currency.model.Client;

import java.util.Optional;

public interface ClientService {

    Optional<Client> findById(Long id);

    Client save(Client client);

    void delete(Long id);
}
