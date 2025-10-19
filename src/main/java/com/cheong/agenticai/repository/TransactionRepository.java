package com.cheong.agenticai.repository;

import com.cheong.agenticai.model.Transaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TransactionRepository extends R2dbcRepository<Transaction, String> {
}
