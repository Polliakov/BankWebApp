package com.github.polliakov.bank.services;

import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;

import java.util.List;

public interface CreditService extends CRUDService<CreditEntity> {
    List<BankEntity> getBanks(Long creditId);
    List<CreditOfferEntity> getCreditOffers(Long creditId);
}
