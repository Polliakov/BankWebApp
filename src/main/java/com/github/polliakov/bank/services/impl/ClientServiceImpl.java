package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.repositories.ClientRepository;
import com.github.polliakov.bank.services.ClientService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    private final ClientRepository repository;

    @Override
    public void create(ClientEntity client) {
        repository.save(client);
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
    public void update(ClientEntity client) {
        CheckExists(client.getId());
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
