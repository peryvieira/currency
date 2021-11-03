package com.jaya.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long idClient;
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amountOrigin;
    @NotNull(message = "Origin currency cannot be null")
    private String currencyAbbreviationOrigin;
    @NotNull(message = "Final currency cannot be null")
    private String currencyAbbreviationFinal;
}
