package com.jaya.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long idClient;
    private BigDecimal amountOrigin;
    private String currencyAbbreviationOrigin;
    private String currencyAbbreviationFinal;
}
