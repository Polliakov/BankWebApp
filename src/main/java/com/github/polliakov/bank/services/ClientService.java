package com.github.polliakov.bank.services;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;

import java.util.List;

public interface ClientService extends CRUDService<ClientEntity> {
    List<BankEntity> getBanks(Long clientId);
}
