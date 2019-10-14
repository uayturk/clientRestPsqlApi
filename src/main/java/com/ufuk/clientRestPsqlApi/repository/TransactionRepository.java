package com.ufuk.clientRestPsqlApi.repository;

import com.ufuk.clientRestPsqlApi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
