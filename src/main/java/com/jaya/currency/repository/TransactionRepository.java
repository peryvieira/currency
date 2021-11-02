package com.jaya.currency.repository;

import com.jaya.currency.model.Client;
import com.jaya.currency.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByClient(Client client);
}
