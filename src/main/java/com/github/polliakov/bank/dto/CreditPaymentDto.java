package com.github.polliakov.bank.dto;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditPaymentDto {
    private Long id;
    private Date date;
    private BigDecimal total;
    private BigDecimal bodyPayment;
    private BigDecimal ratePayment;
    private CreditOfferEntity creditOfferEntityId;
}
