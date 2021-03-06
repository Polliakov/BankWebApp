package com.github.polliakov.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditOfferDto {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal total;
    @NotNull
    private Long creditId;
    @NotNull
    private Long clientId;
}
