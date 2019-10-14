package com.ufuk.clientRestPsqlApi.service.impl;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import com.ufuk.clientRestPsqlApi.repository.TransactionRepository;
import com.ufuk.clientRestPsqlApi.service.TransactionService;
import com.ufuk.clientRestPsqlApi.validator.Validator;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  Validator validator;


  @Transactional
  @Override
  public Transaction getTransactionsByAccountId(Long accountId) throws AccountException {
    log.info("trying to find transaction for accountId: {}", accountId);

    Optional<Transaction> transactionAccountOptionalDebit = transactionRepository.findByDebitAccount_AccountId(accountId);
    Optional<Transaction> transactionAccountOptionalCredit = transactionRepository.findByCreditAccount_AccountId(accountId);

    if(transactionAccountOptionalDebit.isPresent()) {
      log.info("successfully found Debit account for accountId: {}", accountId);
      return transactionAccountOptionalDebit.get();
    } if(transactionAccountOptionalCredit.isPresent()){
      log.info("successfully found Credit account for accountId: {}", accountId);
      return transactionAccountOptionalCredit.get();
    }else {
      throw new AccountException("No transaction record exist for given accountId");
    }
  }

  @Override
  public Transaction createTransaction(Long accountId, TransactionType transactionTypeId, Transaction amount) throws AccountException {
    return null;
  }
}
