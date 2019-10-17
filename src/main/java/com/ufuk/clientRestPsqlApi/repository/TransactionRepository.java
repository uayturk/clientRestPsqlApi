package com.ufuk.clientRestPsqlApi.repository;

import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

  List<Transaction> findByCreditAccount(Account account);
  List<Transaction> findByDebitAccount(Account account);
}
