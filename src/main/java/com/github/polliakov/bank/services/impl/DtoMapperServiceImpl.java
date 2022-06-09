package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.dto.*;
import com.github.polliakov.bank.entities.*;
import com.github.polliakov.bank.repositories.BankRepository;
import com.github.polliakov.bank.repositories.ClientRepository;
import com.github.polliakov.bank.repositories.CreditRepository;
import com.github.polliakov.bank.services.DtoMapperService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;

@Service
public class DtoMapperServiceImpl implements DtoMapperService {
    public DtoMapperServiceImpl(CreditRepository creditRepository,
                                ClientRepository clientRepository,
                                BankRepository bankRepository) {
        this.creditRepository = creditRepository;
        this.clientRepository = clientRepository;
        this.bankRepository = bankRepository;
    }

    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final BankRepository bankRepository;

    @Override
    public BankEntity entityFromDto(BankDto bankDto) {
        if (bankDto == null)
            throw new NullPointerException("bankDto");

        var bank = new BankEntity();
        if (bankDto.getCreditEntitiesIds() != null) {
            var credits = creditRepository.findAllById(bankDto.getCreditEntitiesIds());
            if (credits.isEmpty()) throw new FindException();
            bank.setCredits(credits);
        }
        if (bankDto.getClientEntitiesIds() != null) {
            var clients = clientRepository.findAllById(bankDto.getClientEntitiesIds());
            if (clients.isEmpty()) throw new FindException();
            bank.setClients(clients);
        }
        bank.setId(bankDto.getId());

        return bank;
    }

    @Override
    public ClientEntity entityFromDto(ClientDto clientDto) {
        if (clientDto == null)
            throw new NullPointerException("bankDto");

        var client = new ClientEntity();
        if (clientDto.getBankEntitiesIds() != null) {
            var banks = bankRepository.findAllById(clientDto.getBankEntitiesIds());
            if (banks.isEmpty()) throw new FindException();
            client.setBanks(banks);
        }

        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setSurname(clientDto.getSurname());
        client.setPatronymic(clientDto.getPatronymic());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setPassportNumber(clientDto.getPassportNumber());

        return client;
    }

    @Override
    public CreditEntity entityFromDto(CreditDto creditDto) {
        if (creditDto == null)
            throw new NullPointerException("creditDto");

        var credit = new CreditEntity();
        if (creditDto.getBankEntitiesIds() != null) {
            var banks = bankRepository.findAllById(creditDto.getBankEntitiesIds());
            if (banks.isEmpty()) throw new FindException();
            credit.setBanks(banks);
        }

        credit.setId(creditDto.getId());
        credit.setRate(creditDto.getRate());
        credit.setSumLimit(creditDto.getSumLimit());

        return credit;
    }

    @Override
    public CreditOfferEntity entityFromDto(CreditOfferDto creditOfferDto) {
        if (creditOfferDto == null)
            throw new NullPointerException("creditOfferDto");

        var credit = creditRepository.findById(creditOfferDto.getCreditId()).orElseThrow();
        var client = clientRepository.findById(creditOfferDto.getClientId()).orElseThrow();

        var creditOffer = new CreditOfferEntity();
        creditOffer.setId(creditOfferDto.getId());
        creditOffer.setTotal(creditOfferDto.getTotal());
        creditOffer.setCredit(credit);
        creditOffer.setClient(client);

        if (creditOffer.getTotal().compareTo(credit.getSumLimit()) > 0)
            throw new IllegalArgumentException("creditOffer.total greater than credit limit.");

        return creditOffer;
    }

    @Override
    public BankDto dtoFromEntity(BankEntity bank) {
        return BankDto.builder()
                .id(bank.getId())
                .build();
    }

    @Override
    public ClientDto dtoFromEntity(ClientEntity client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .patronymic(client.getPatronymic())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .passportNumber(client.getPassportNumber())
                .build();
    }

    @Override
    public CreditDto dtoFromEntity(CreditEntity credit) {
        return CreditDto.builder()
                .id(credit.getId())
                .rate(credit.getRate())
                .sumLimit(credit.getSumLimit())
                .build();
    }

    @Override
    public CreditOfferDto dtoFromEntity(CreditOfferEntity creditOffer) {
        return CreditOfferDto.builder()
                .id(creditOffer.getId())
                .total(creditOffer.getTotal())
                .build();
    }

    @Override
    public CreditPaymentDto dtoFromEntity(CreditPaymentEntity creditPayment) {
        return CreditPaymentDto.builder()
                .id(creditPayment.getId())
                .date(creditPayment.getDate())
                .bodyPayment(creditPayment.getBodyPayment())
                .ratePayment(creditPayment.getRatePayment())
                .build();
    }
}
