package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.services.CreditPaymentService;
import com.github.polliakov.bank.services.PaymentCalculatorService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreditPaymentServiceImpl implements CreditPaymentService {
    public CreditPaymentServiceImpl(PaymentCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    private final PaymentCalculatorService calculatorService;

    @Override
    public List<CreditPaymentEntity> create(CreditOfferEntity creditOffer, int monthsCount) {
        var payments = calculatorService.calculatePayments(creditOffer, monthsCount);
        payments.forEach(p -> p.setCreditOffer(creditOffer));
        return payments;
    }

}
