package com.github.polliakov.bank.services;

import com.github.polliakov.bank.dto.BankDto;
import com.github.polliakov.bank.dto.ClientDto;
import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;

public interface DtoMapperService {
    BankEntity entityFromDto(BankDto bankDto);
    ClientEntity entityFromDto(ClientDto clientDto);
    CreditEntity entityFromDto(CreditDto creditDto);
    CreditOfferEntity entityFromDto(CreditOfferDto creditOfferDto);
}
