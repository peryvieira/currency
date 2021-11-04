package com.jaya.currency.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client")
public class Client {
    @ApiModelProperty(value = "Client code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ApiModelProperty(value = "Date of client creation")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;
}
