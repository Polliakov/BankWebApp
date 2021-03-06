package com.github.polliakov.bank.services;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;

import java.util.List;

public interface CreditPaymentService {
    List<CreditPaymentEntity> create(CreditOfferEntity creditOffer, int monthsCount);
}
