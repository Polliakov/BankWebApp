package com.github.polliakov.bank.repositories;

import com.github.polliakov.bank.entities.CreditPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditPaymentRepository extends JpaRepository<CreditPaymentEntity, Long> {
}
