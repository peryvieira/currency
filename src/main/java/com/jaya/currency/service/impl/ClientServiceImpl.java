package com.jaya.currency.service.impl;

import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.model.Client;
import com.jaya.currency.repository.ClientRepository;
import com.jaya.currency.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client save(ClientDTO clientDTO) {
        return clientRepository.save(ClientDTO.convertToObject(clientDTO));
    }

    @Override
    public void delete(Long id) {

    }
}
