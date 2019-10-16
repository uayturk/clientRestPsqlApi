package com.ufuk.clientRestPsqlApi.service.impl;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.exception.ErrorCode;
import com.ufuk.clientRestPsqlApi.exception.ErrorMessage;
import com.ufuk.clientRestPsqlApi.exception.NotValidRequestException;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import com.ufuk.clientRestPsqlApi.repository.TransactionRepository;
import com.ufuk.clientRestPsqlApi.service.AccountService;
import com.ufuk.clientRestPsqlApi.service.TransactionService;
import com.ufuk.clientRestPsqlApi.validator.Validator;
import java.math.BigDecimal;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  TransactionRepository transactionRepository;


  @Autowired
  AccountService accountService;

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


  /**
   * Creating transaction for account.
   * If there is not enough balace on account,throws AccountException.
   *  An account with a debit balance will be increased by a debit operation and decreased by a credit operation.
   *  Conversely an account with a credit balance will be increased by a credit operation and decreased by a debit operation.
   * @param debitAccountId valid debitAccountId
   * @param creditAccountId valid creditAccountId
   * @param transactionTypeId valid transaction type - 'CR' or 'DR'
   * @param amount
   * @return
   * @throws AccountException
   */
  @Override
  @Transactional
  public Transaction saveTransaction(Long debitAccountId,Long creditAccountId,String amount ,@NotBlank TransactionType transactionTypeId,String message) throws AccountException {
    log.info("trying to transfer {} between {} and {} ",amount,debitAccountId,creditAccountId);

    //TODO check given ID's really belong to the creditAccount or debitAccounts
    Account creditAccount = accountService.getAccountById(creditAccountId);
    Account debitAccount = accountService.getAccountById(debitAccountId);

    try {
      String error = String.format(ErrorMessage.NO_ACCOUNT_FOUND);

      validator.isTrue(debitAccount != null,error, ErrorCode.BadRequest.getCode());
      validator.isTrue(creditAccount != null,error, ErrorCode.BadRequest.getCode());

      debitAccount = accountService.updateAccount(debitAccount,amount,transactionTypeId.equals(TransactionType.CR));
      creditAccount = accountService.updateAccount(creditAccount,amount,transactionTypeId.equals(TransactionType.CR));

    }catch (NotValidRequestException e){
      throw new AccountException("No debit/credit account record found.");
    }

    //Creating transaction for credit accounts
    Transaction transaction = new Transaction(transactionTypeId,debitAccount,creditAccount,new BigDecimal(amount),message);
    log.info("successfully complited transaction: {}",transaction);
    return transactionRepository.save(transaction);


  }
}
