package com.jaya.currency.entity;

import com.jaya.currency.entity.Client;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ApiModelProperty(value = "Currency to be converted")
    private String currencyOrigin;

    @ApiModelProperty(value = "Currency amount to be converted")
    private BigDecimal amountOrigin;

    @ApiModelProperty(value = "Currency target")
    private String currencyFinal;

    @ApiModelProperty(value = "Base rate to convert")
    private BigDecimal conversionRate;

    @ApiModelProperty(value = "Date of Transaction processed")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;
}
