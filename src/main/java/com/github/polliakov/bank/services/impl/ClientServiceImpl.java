package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.repositories.BankRepository;
import com.github.polliakov.bank.repositories.ClientRepository;
import com.github.polliakov.bank.services.ClientService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    public ClientServiceImpl(ClientRepository repository, BankRepository bankRepository) {
        this.repository = repository;
        this.bankRepository = bankRepository;
    }

    private final BankRepository bankRepository;
    private final ClientRepository repository;

    @Override
    public void create(ClientEntity client) {
        client.setId(null);
        repository.save(client);

        var banks = client.getBanks();
        if (banks == null) return;
        for (var b : banks)
            b.getClients().add(client);
        bankRepository.saveAll(banks);
    }

    @Override
    public ClientEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ClientEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public List<BankEntity> getBanks(Long clientId) {
        var client = getById(clientId);
        if (client == null)
            throw new FindException("Client with id = " + clientId + "is not found");
        return new ArrayList<>(client.getBanks());
    }

    @Override
    public List<CreditOfferEntity> getCreditOffers(Long clientId){
        var client = getById(clientId);
        if (client == null)
            throw new FindException("Client with id = " + clientId + "is not found");
        return new ArrayList<>(client.getCreditOffers());
    }

    @Override
    public void update(ClientEntity client) {
        var creditOffers = getCreditOffers(client.getId());
        client.setCreditOffers(creditOffers);
        repository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        CheckExists(id);
        repository.deleteById(id);
    }

    private void CheckExists(Long id) {
        if (!repository.existsById(id))
            throw new FindException("Client with id = " + id + "is not found");
    }
}
