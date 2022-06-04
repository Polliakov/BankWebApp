package com.github.polliakov.bank.repositories;

import com.github.polliakov.bank.entities.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankEntity, Long> {

}
