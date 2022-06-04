package com.github.polliakov.bank.services;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CreditPaymentService {
    List<CreditPaymentEntity> calculatePayments(CreditOfferEntity creditOffer, int monthsCount);
}
