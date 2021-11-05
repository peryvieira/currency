package com.jaya.currency.service.impl;

import com.jaya.currency.controller.ClientController;
import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.exception.ClientException;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.entity.Client;
import com.jaya.currency.repository.ClientRepository;
import com.jaya.currency.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        logger.info("Searching all clients on database");
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        logger.info("Searching client {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public Client save(ClientDTO clientDTO) {
        try{
            logger.info("Saving client {} on database",clientDTO.getName());
            return clientRepository.save(ClientDTO.convertToObject(clientDTO));
        } catch (Exception e) {
            logger.error("Error while saving client {}",clientDTO.getName());
            throw new ClientException("Client cannot be saved on database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        Client client = this.findById(id).orElseThrow(() -> {
            logger.error("Error finding client {} to delete",id);
            return new ClientNotFoundException(id);
        });

        logger.info("Deleting client {}", id);

        try{
            clientRepository.delete(client);
            logger.info("Client {} deleted from database", id);
        }catch (Exception e){
            logger.error("Error deleting client {} from database",id);
            throw new ClientException("Client cannot be deleted on database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Client edit(ClientDTO clientDTO) {
        Client client = this.findById(clientDTO.getId()).orElseThrow(() -> {
            logger.error("Error finding client {} to edit", clientDTO.getId());
            return new ClientNotFoundException(clientDTO.getId());
        });
        client.setName(clientDTO.getName());

        try{
            logger.info("Editing client {} on database",clientDTO.getId());
            return clientRepository.save(client);
        } catch (Exception e) {
            logger.error("Error while editing client {}",clientDTO.getId());
            throw new ClientException("Client cannot be edited on database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
