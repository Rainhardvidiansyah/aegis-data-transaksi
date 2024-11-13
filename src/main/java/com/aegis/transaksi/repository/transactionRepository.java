package com.aegis.transaksi.repository;

import com.aegis.transaksi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface transactionRepository extends JpaRepository<Transaction, UUID> {



}
