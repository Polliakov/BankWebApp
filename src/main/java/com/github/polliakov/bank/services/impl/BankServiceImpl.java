package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.repositories.BankRepository;
import com.github.polliakov.bank.services.BankService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    public BankServiceImpl(BankRepository repository) {
        this.repository = repository;
    }

    private final BankRepository repository;

    @Override
    public void create(BankEntity bank) {
        bank.setId(null);
        repository.save(bank);
    }

    @Override
    public BankEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<BankEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ClientEntity> getClients(Long bankId) {
        var bank = getById(bankId);
        if (bank == null)
            throw new FindException("Bank with id = " + bankId + "is not found");
        return new ArrayList<>(bank.getClients());
    }

    @Override
    public List<CreditEntity> getCredits(Long bankId){
        var bank = getById(bankId);
        if (bank == null)
            throw new FindException("Bank with id = " + bankId + "is not found");
        return new ArrayList<>(bank.getCredits());
    }

    @Override
    public void update(BankEntity bank) {
        CheckExists(bank.getId());
        repository.save(bank);
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
