package com.github.polliakov.bank.repositories;

import com.github.polliakov.bank.entities.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
