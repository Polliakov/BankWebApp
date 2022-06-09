package com.github.polliakov.bank.services;

import com.github.polliakov.bank.dto.*;
import com.github.polliakov.bank.entities.*;

public interface DtoMapperService {
    BankEntity entityFromDto(BankDto bankDto);
    ClientEntity entityFromDto(ClientDto clientDto);
    CreditEntity entityFromDto(CreditDto creditDto);
    CreditOfferEntity entityFromDto(CreditOfferDto creditOfferDto);
    BankDto dtoFromEntity(BankEntity bank);
    ClientDto dtoFromEntity(ClientEntity client);
    CreditDto dtoFromEntity(CreditEntity credit);
    CreditOfferDto dtoFromEntity(CreditOfferEntity creditOffer);
    CreditPaymentDto dtoFromEntity(CreditPaymentEntity creditPayment);
}
