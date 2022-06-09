package com.github.polliakov.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDto {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal sumLimit;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double rate;
    private List<Long> bankEntitiesIds;
}
