package com.jaya.currency.dto;

import com.jaya.currency.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long id;
    @NotBlank(message = "id do cliente não informado")
    private Long idClient;
    private String currencyOrigin;
    private BigDecimal amountOrigin;
    private String currencyFinal;
    private BigDecimal amountFinal;
    private BigDecimal conversionRate;
    private LocalDate date;

    public static TransactionResponseDTO convertToDTO(Transaction transaction){
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .idClient(transaction.getClient().getId())
                .currencyOrigin(transaction.getCurrencyOrigin())
                .amountOrigin(transaction.getAmountOrigin())
                .currencyFinal(transaction.getCurrencyFinal())
                .amountFinal(calculateAmountFinal(transaction.getAmountOrigin(), transaction.getConversionRate()))
                .conversionRate(transaction.getConversionRate())
                .date(transaction.getCreatedAt()).build();
    }

    private static BigDecimal calculateAmountFinal(BigDecimal amountOrigin, BigDecimal conversionRate){
        return amountOrigin.multiply(conversionRate);
    }
}
