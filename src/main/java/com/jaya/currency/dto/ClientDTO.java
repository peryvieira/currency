package com.jaya.currency.dto;

import com.jaya.currency.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long idClient;
    @NotNull(message = "Client name cannot be null")
    private String name;

    public static Client convertToObject(ClientDTO clientDTO){
        return Client.builder()
                .name(clientDTO.getName()).build();
    }
}
