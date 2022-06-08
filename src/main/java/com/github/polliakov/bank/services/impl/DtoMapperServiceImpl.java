package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.dto.BankDto;
import com.github.polliakov.bank.dto.ClientDto;
import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;
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
        if (bankDto.creditEntitiesIds != null) {
            var credits = creditRepository.findAllById(bankDto.creditEntitiesIds);
            if (credits.isEmpty()) throw new FindException();
            bank.setCredits(credits);
        }
        if (bankDto.clientEntitiesIds != null) {
            var clients = clientRepository.findAllById(bankDto.clientEntitiesIds);
            if (clients.isEmpty()) throw new FindException();
            bank.setClients(clients);
        }
        bank.setId(bankDto.id);

        return bank;
    }

    @Override
    public ClientEntity entityFromDto(ClientDto clientDto) {
        if (clientDto == null)
            throw new NullPointerException("bankDto");

        var client = new ClientEntity();
        if (clientDto.bankEntitiesIds != null) {
            var banks = bankRepository.findAllById(clientDto.bankEntitiesIds);
            if (banks.isEmpty()) throw new FindException();
            client.setBanks(banks);
        }

        client.setId(clientDto.id);
        client.setName(clientDto.name);
        client.setSurname(clientDto.surname);
        client.setPatronymic(clientDto.patronymic);
        client.setEmail(clientDto.email);
        client.setPhoneNumber(clientDto.phoneNumber);
        client.setPassportNumber(clientDto.passportNumber);

        return client;
    }

    @Override
    public CreditEntity entityFromDto(CreditDto creditDto) {
        if (creditDto == null)
            throw new NullPointerException("creditDto");

        var credit = new CreditEntity();
        if (creditDto.bankEntitiesIds != null) {
            var banks = bankRepository.findAllById(creditDto.bankEntitiesIds);
            if (banks.isEmpty()) throw new FindException();
            credit.setBanks(banks);
        }

        credit.setId(creditDto.id);
        credit.setRate(creditDto.rate);
        credit.setSumLimit(creditDto.sumLimit);

        return credit;
    }

    @Override
    public CreditOfferEntity entityFromDto(CreditOfferDto creditOfferDto) {
        if (creditOfferDto == null)
            throw new NullPointerException("creditOfferDto");

        var credit = creditRepository.findById(creditOfferDto.creditId).orElseThrow();
        var client = clientRepository.findById(creditOfferDto.clientId).orElseThrow();

        var creditOffer = new CreditOfferEntity();
        creditOffer.setId(creditOfferDto.id);
        creditOffer.setTotal(creditOfferDto.total);
        creditOffer.setCredit(credit);
        creditOffer.setClient(client);

        if (creditOffer.getTotal().compareTo(credit.getSumLimit()) > 0)
            throw new IllegalArgumentException("creditOffer.total greater than credit limit.");

        return creditOffer;
    }
}
