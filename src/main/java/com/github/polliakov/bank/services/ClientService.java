package com.github.polliakov.bank.services;

import com.github.polliakov.bank.dto.ClientDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;

import java.util.List;

public interface ClientService extends CRUDService<ClientEntity, ClientDto> {
    List<BankEntity> getBanks(Long clientId);
    List<CreditOfferEntity> getCreditOffers(Long clientId);
}
