package com.github.polliakov.bank.services;

import com.github.polliakov.bank.entities.BankEntity;

import java.util.List;

public interface CRUDService<TEntity> {
    void create(TEntity entity);
    TEntity getById(Long id);
    List<TEntity> getAll();
    void update(TEntity entity);
    void deleteById(Long id);
}
