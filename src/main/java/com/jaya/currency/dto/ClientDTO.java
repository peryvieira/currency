package com.jaya.currency.dto;

import com.jaya.currency.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClientDTO {

    private Long id;
    @NotNull(message = "Client name cannot be null")
    private String name;

    public static Client convertToObject(ClientDTO clientDTO){
        return Client.builder()
                .name(clientDTO.getName()).build();
    }
}
