package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

class CreditPaymentServiceImplTest {
    @Test
    void calculatePayments() {
        var service = new CreditPaymentServiceImpl(new PaymentCalculatorServiceImpl());
        var credit = new CreditEntity();
        credit.setRate(0.1);
        var creditOffer = new CreditOfferEntity();
        creditOffer.setCredit(credit);
        creditOffer.setTotal(BigDecimal.valueOf(10000L));

        var payments = service.create(creditOffer, 12);

        var payed = BigDecimal.ZERO;
        for (var p : payments){
            payed = payed.add(p.getBodyPayment());
        }
        Assert.isTrue(payed.compareTo(creditOffer.getTotal()) == 0);
    }
}