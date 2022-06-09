package com.github.polliakov.bank.services;

import com.github.polliakov.bank.dto.BankDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditEntity;

import java.util.List;

public interface BankService extends CRUDService<BankEntity> {
    List<ClientEntity> getClients(Long bankId);
    List<CreditEntity> getCredits(Long bankId);
}
