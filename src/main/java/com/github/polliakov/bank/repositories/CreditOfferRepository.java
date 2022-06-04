package com.github.polliakov.bank.repositories;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditOfferRepository extends JpaRepository<CreditOfferEntity, Long> {
}
