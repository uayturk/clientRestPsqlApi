package com.ufuk.clientRestPsqlApi.service;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.model.Type;
import java.util.Set;


public interface AccountService {

  Set<Account> getAllAccounts(Integer size);

  Account getAccountById(Long accountId) throws AccountException; // read (according to accountId)

  Account getAccountByClientId(Long clientId) throws AccountException; // read (according to clientId)

  Account saveAccount(Account account) throws AccountException; // create

  Account updateAccount(Account account, String amount, Boolean isFund) throws AccountException; // update

  void deleteAccountById(Long accountId) throws AccountException; // delete



}
