package com.github.polliakov.bank.services;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;

import java.util.List;

public interface PaymentCalculatorService {
    List<CreditPaymentEntity> calculatePayments(CreditOfferEntity creditOffer, int monthsCount);
}
