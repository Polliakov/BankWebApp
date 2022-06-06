package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.repositories.CreditRepository;
import com.github.polliakov.bank.services.CreditService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;
@Service
public class CreditServiceImpl implements CreditService {
    public CreditServiceImpl(CreditRepository repository) {
        this.repository = repository;
    }

    private final CreditRepository repository;

    @Override
    public void create(CreditEntity creditEntity) {
        // TODO: If entity width this id is already exists?
        repository.save(creditEntity);
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
    public List<BankEntity> getBanks(Long creditId){
        var credit = getById(creditId);
        if (credit == null)
            throw new FindException("Credit with id = " + creditId + "is not found");
        return new ArrayList<>(credit.getBanks());
    }

    @Override
    public void update(CreditEntity creditEntity) {
        CheckExists(creditEntity.getId());
        repository.save(creditEntity);
    }

    @Override
    public void deleteById(Long id) {
        CheckExists(id);
        repository.deleteById(id);
    }

    private void CheckExists(Long id) {
        if (!repository.existsById(id))
            throw new FindException("Bank with id = " + id + "is not found");
    }
}
