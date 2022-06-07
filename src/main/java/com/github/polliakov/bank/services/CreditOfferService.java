package com.github.polliakov.bank.services;

import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;

import java.util.List;

public interface CreditOfferService {
    void create(CreditOfferDto creditOfferDto);
    CreditOfferEntity getById(Long id);
    List<CreditOfferEntity> getAll();
    List<CreditPaymentEntity> getPayments(Long creditOfferId);
    void update(CreditOfferDto creditOfferDto);
    void deleteById(Long id);
}
