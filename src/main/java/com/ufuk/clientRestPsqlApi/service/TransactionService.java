package com.ufuk.clientRestPsqlApi.service;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import java.util.List;
import java.util.Set;

public interface TransactionService {

  List<Transaction> getTransactionsByAccountId(Long accountId) throws AccountException;
  Transaction saveTransaction(Long debitAccountId,Long creditAccountId,String amount,TransactionType transactionTypeId,String message) throws AccountException;
}
