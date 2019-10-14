package com.ufuk.clientRestPsqlApi.repository;

import com.ufuk.clientRestPsqlApi.model.Transaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

  Optional<Transaction> findByCreditAccount_AccountId(Long accountId);
  Optional<Transaction> findByDebitAccount_AccountId(Long accountId);
}
