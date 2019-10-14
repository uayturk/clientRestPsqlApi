package com.ufuk.clientRestPsqlApi.service;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import java.util.Set;

public interface TransactionService {

  Transaction getTransactionsByAccountId(Long accountId) throws AccountException;
  Transaction createTransaction(Long accountId, TransactionType transactionTypeId,Transaction amount) throws AccountException;
}
