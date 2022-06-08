package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.repositories.BankRepository;
import com.github.polliakov.bank.repositories.CreditRepository;
import com.github.polliakov.bank.services.CreditService;
import com.github.polliakov.bank.services.DtoMapperService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Service
public class CreditServiceImpl implements CreditService {
    public CreditServiceImpl(BankRepository bankRepository,
                             CreditRepository repository,
                             DtoMapperService dtoMapper) {
        this.bankRepository = bankRepository;
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }

    private final BankRepository bankRepository;
    private final CreditRepository repository;
    private final DtoMapperService dtoMapper;

    @Override
    public void create(CreditDto creditDto) {
        var credit = dtoMapper.entityFromDto(creditDto);
        credit.setId(null);
        repository.save(credit);

        var banks = credit.getBanks();
        if (banks == null) return;
        for (var b : banks)
            b.getCredits().add(credit);
        bankRepository.saveAll(banks);
    }

    @Override
    public CreditEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<CreditEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public List<BankEntity> getBanks(Long creditId) {
        var credit = getById(creditId);
        if (credit == null)
            throw new FindException("Credit with id = " + creditId + "is not found");
        return new LinkedList<>(credit.getBanks());
    }

    @Override
    public List<CreditOfferEntity> getCreditOffers(Long creditId) {
        var credit = getById(creditId);
        if (credit == null)
            throw new FindException("Credit with id = " + creditId + "is not found");
        return new LinkedList<>(credit.getOfferEntities());
    }

    @Override
    public void update(CreditEntity creditEntity) {
        CheckExists(creditEntity.getId());
        repository.save(creditEntity);
    }

    @Override
    public void deleteById(Long id) {
        var credit = repository.findById(id).orElseThrow();
        if (credit.getOfferEntities().isEmpty())
            repository.deleteById(id);
        else
            throw new UnsupportedOperationException();
    }

    private void CheckExists(Long id) {
        if (!repository.existsById(id))
            throw new FindException("Bank with id = " + id + "is not found");
    }
}
