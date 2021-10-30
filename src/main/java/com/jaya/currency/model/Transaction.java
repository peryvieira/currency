package com.jaya.currency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCurrencyOrigin;
    private BigDecimal amountOrigin;
    private Long idCurrencyFinal;
    private BigDecimal amountFinal;
    private BigDecimal conversionRate;
    private LocalDate date;
}
