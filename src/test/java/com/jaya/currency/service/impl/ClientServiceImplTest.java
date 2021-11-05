package com.jaya.currency.service.impl;

import com.jaya.currency.dto.ClientDTO;
import com.jaya.currency.exception.ClientException;
import com.jaya.currency.exception.ClientNotFoundException;
import com.jaya.currency.entity.Client;
import com.jaya.currency.repository.ClientRepository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.inOrder;


@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreationClientSuccess(){
        ClientDTO clientDTO = ClientDTO.builder().name(this.clientFound().getName()).build();
        BDDMockito.given(clientRepository.save(ClientDTO.convertToObject(clientDTO))).willReturn(this.clientFound());

        clientService.save(clientDTO);

        BDDMockito.then(clientRepository).should(Mockito.times(1)).save(ClientDTO.convertToObject(clientDTO));
    }

    @Test(expected = ClientException.class)
    public void testCreationClientException(){
        ClientDTO clientDTO = ClientDTO.builder().name(this.clientFound().getName()).build();
        BDDMockito.given(clientRepository.save(ClientDTO.convertToObject(clientDTO))).willThrow(new ClientException("Client cannot be saved on database", HttpStatus.INTERNAL_SERVER_ERROR));

        clientService.save(clientDTO);
    }

    @Test
    public void testFindClientByIdSuccess(){
        BDDMockito.given(clientRepository.findById(1L)).willReturn(Optional.of(this.clientFound()));

        clientService.findById(1L);

        BDDMockito.then(clientRepository).should(Mockito.times(1)).findById(1L);
    }

    @Test
    public void testFindAllClientsSuccess(){
        BDDMockito.given(clientRepository.findAll()).willReturn(this.clientListFound());

        clientService.findAll();

        BDDMockito.then(clientRepository).should(Mockito.times(1)).findAll();
    }

    @Test
    public void testDeleteClientSuccess(){
        BDDMockito.given(clientRepository.findById(1L)).willReturn(Optional.of(this.clientFound()));
        BDDMockito.willDoNothing().given(clientRepository).delete(this.clientFound());

        clientService.delete(1L);

        InOrder inOrder = inOrder(clientRepository);
        inOrder.verify(clientRepository, Mockito.times(1)).findById(1L);
        inOrder.verify(clientRepository, Mockito.times(1)).delete(this.clientFound());
    }

    @Test(expected = ClientNotFoundException.class)
    public void testDeleteClientException(){
        BDDMockito.given(clientRepository.findById(1L)).willReturn(Optional.empty());

        clientService.delete(1L);
    }

    @Test
    public void testEditClientSuccess() {
        Client client = Client.builder().id(1L).name("Teste").build();
        BDDMockito.given(clientRepository.findById(1L)).willReturn(Optional.of(this.clientFound()));
        BDDMockito.given(clientRepository.save(client)).willReturn(client);

        clientService.edit(ClientDTO.builder().id(1L).name("Teste").build());

        InOrder inOrder = inOrder(clientRepository);
        inOrder.verify(clientRepository, Mockito.times(1)).findById(1L);
        inOrder.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
    }

    @Test(expected = ClientNotFoundException.class)
    public void testEditClientNotFoundException(){
        ClientDTO clientDTO = ClientDTO.builder().id(1L).name(this.clientFound().getName()).build();
        BDDMockito.given(clientRepository.findById(1L)).willReturn(Optional.empty());

        clientService.edit(clientDTO);
    }

    @Test(expected = ClientException.class)
    public void testEditClientDBException(){
        Client client = Client.builder().id(1L).name("Teste").build();
        BDDMockito.given(clientRepository.findById(1L)).willReturn(Optional.of(this.clientFound()));
        BDDMockito.given(clientRepository.save(Mockito.any(Client.class))).willThrow(new ClientException("Client cannot be edited on database", HttpStatus.INTERNAL_SERVER_ERROR));

        clientService.edit(ClientDTO.builder().id(1L).name("Teste").build());
    }


    private Client clientFound(){
        return Client.builder()
                .id(1L)
                .name("Pery")
                .createdAt(LocalDate.now()).build();
    }

    private List<Client> clientListFound(){
        List<Client> list = new ArrayList<>();

        list.add(Client.builder()
                .id(1L)
                .name("Pery")
                .createdAt(LocalDate.now()).build());

        list.add(Client.builder()
                .id(2L)
                .name("Paulo")
                .createdAt(LocalDate.now()).build());

        return list;
    }


}
