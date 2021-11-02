package com.jaya.currency.service.impl;

import com.jaya.currency.model.Client;
import com.jaya.currency.repository.ClientRepository;
import com.jaya.currency.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
