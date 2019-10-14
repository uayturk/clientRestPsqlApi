package com.ufuk.clientRestPsqlApi.service.impl;

import com.ufuk.clientRestPsqlApi.exception.ErrorCode;
import com.ufuk.clientRestPsqlApi.exception.ErrorMessage;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.validator.Validator;
import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.repository.AccountRepository;
import com.ufuk.clientRestPsqlApi.service.AccountService;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
public class AccountServiceImp implements AccountService {

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  Validator validator;


  @Transactional
  @Override
  public Set<Account> getAllAccounts(Integer size) {
    log.info("trying to find accounds for size: {}", size);

    Set<Account> accountList = accountRepository.findAll(PageRequest.of(0, size)).stream().collect(Collectors.toSet());

    if (accountList.size() > 0) {
      log.info("successfully fetched account list: {}", accountList.size());
      return accountList;
    } else {
      return new HashSet<>(accountRepository.findAll());
    }

  }
  @Transactional
  @Override
  public Account getAccountById(Long accountId) throws AccountException {
    log.info("trying to find account for accountId: {}", accountId);

    Optional<Account> clientOptional = accountRepository.findById(accountId);

    if(clientOptional.isPresent()) {
      log.info("successfully found account for accountId: {}", accountId);
      return clientOptional.get();
    } else {
      throw new AccountException("No account record exist for given id");
    }
  }


  @Transactional
  @Override
  public Account getAccountByClientId(Long clientId) throws AccountException {
    log.info("trying to find account for clientId: {}", clientId);

    Optional<Account> accountClientOptional = accountRepository.findByClientClientId(clientId);

    if(accountClientOptional.isPresent()) {
      log.info("successfully found account for clientId: {}", clientId);
      return accountClientOptional.get();
    } else {
      throw new AccountException("No account record exist for given clientId");
    }

  }

  @Transactional
  @Override
  public Account saveAccount(Account account) throws AccountException {
    log.info("trying to save account: {}", account);

    Optional<Account> accountOptional = accountRepository.findById(account.getAccountId());

    if(accountOptional.isEmpty()) {
      account = accountRepository.save(account);
      log.info("successfully saved account: {}", account);
    }else{
      throw new AccountException("Account is already exist!");
    }

    return account;

  }

  @Transactional
  @Override
  public Account updateAccount(Account account, String amount,Boolean isCredit) throws AccountException {
    log.info("trying to update account: {}", account);

    Optional<Account> accountOptional = accountRepository.findById(account.getAccountId());

    if(accountOptional.isPresent())
    {
      Account updatedAccount = accountOptional.get();

      /**
       * The java.math.BigDecimal.abs() is used to return a BigDecimal whose value is the absolute value of the BigDecimal and whose scale is this.scale(). (Mutlak değeri döndürür.)
       *The java.math.BigDecimal.negate() method returns a BigDecimal whose value is the negated value of the BigDecimal with which it is used.(50 ise -50 döndürür.)
       */

      /**
       * If balanceStatus is Credit(CR),account with a credit balance will be increased by a credit operation(CR) and decreased by a debit operation(DR).
       */
      if(updatedAccount.getBalanceStatus().equals(BalanceStatus.CR)){
          BigDecimal transactionAmount = (isCredit) ? new BigDecimal(amount).abs() : new BigDecimal(amount).abs().negate(); //Credit BalaceStatus Account: if credit operation,amount increase,if debit operation, amount decrease.

          //checking that there is enough funds on account for transaction
          check(account, amount, isCredit, updatedAccount, transactionAmount);
      }

      /**
       * If balanceStatus is Debit(DR),account with a debit balance will be increased by a debit operation(DR) and decreased by a credit operation(CR).
       */
        else if(account.getBalanceStatus().equals(BalanceStatus.DR)){
        BigDecimal transactionAmount = (isCredit) ? new BigDecimal(amount).abs().negate() : new BigDecimal(amount).abs();//Debit BalaceStatus Account: if credit operation,amount decrease,if debit operation,amount increase.

        //checking that there is enough funds on account for transaction
        check(account, amount, isCredit, updatedAccount, transactionAmount);
      }

      return updatedAccount;

    }else{
      throw new AccountException("No account record exist for given clientId");
    }


  }

  private void check(Account account, String amount, Boolean isCredit, Account updatedAccount, BigDecimal transactionAmount)
      throws AccountException {
    Boolean condition = (isCredit || (account.getBalance().compareTo(transactionAmount.abs()) >= 0) );
    validator.isTrue(condition, String.format(ErrorMessage.NOT_ENOUGH_FUNDS,account.getAccountId(),amount), ErrorCode.BadRequest.getCode());
    updatedAccount.setBalance(updatedAccount.getBalance().add(transactionAmount));
  }

  @Transactional
  @Override
  public void deleteAccountById(Long accountId) throws AccountException {

    log.info("trying to delete account for accountId: {}", accountId);

    Optional<Account> accountOptional = accountRepository.findById(accountId);

    if(accountOptional.isPresent()) {
      accountRepository.deleteById(accountId);
      log.info("successfully to deleted client for accountId: {}", accountId);
    } else {
      throw new AccountException("No account exist for given id");
    }

  }


}
