package com.eteration.simplebanking.model.repository;

import com.eteration.simplebanking.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
}
