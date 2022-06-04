package com.github.polliakov.bank.repositories;

import com.github.polliakov.bank.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
