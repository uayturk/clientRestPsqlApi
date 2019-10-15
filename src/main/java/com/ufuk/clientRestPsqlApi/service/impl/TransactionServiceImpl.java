package com.ufuk.clientRestPsqlApi.service.impl;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.exception.ErrorCode;
import com.ufuk.clientRestPsqlApi.exception.ErrorMessage;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import com.ufuk.clientRestPsqlApi.repository.TransactionRepository;
import com.ufuk.clientRestPsqlApi.repository.TransactionTypeRepository;
import com.ufuk.clientRestPsqlApi.service.AccountService;
import com.ufuk.clientRestPsqlApi.service.TransactionService;
import com.ufuk.clientRestPsqlApi.validator.Validator;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  TransactionTypeRepository transactionTypeRepository;

  @Autowired
  AccountService accountService;

  @Autowired
  Validator validator;

  @Value("CR")
  @Getter
  @Setter
  private String transactionTypeCredit;

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
   * @param accountId valid accountId
   * @param transactionTypeId valid transaction type - 'CR' or 'DR'
   * @param amount
   * @return
   * @throws AccountException
   */
  @Override
  public Transaction saveTransaction(Long accountId,String amount ,@NotBlank String transactionTypeId,BalanceStatus balanceStatus) throws AccountException {
    log.info("aaaaaaaaaaaaaaa:{}",transactionTypeId);
    log.info("DDDDDDDDDDDDDDDDDDDDDDDDd:{}",transactionTypeRepository.getOne(transactionTypeId));

    //Getting transactionType reference
    TransactionType transactionType = transactionTypeRepository.getOne(transactionTypeId);

    Account creditAccount = null;
    Account debitAccount = null;

    //Checking account is present or not.
    if(balanceStatus.equals(BalanceStatus.DR)){
      log.info("Debit(DR) Balace Status account transaction side.");
      debitAccount = accountService.getAccountById(accountId,BalanceStatus.DR);
      String error = String.format(ErrorMessage.NO_ACCOUNT_FOUND);
      validator.isTrue(debitAccount != null,error, ErrorCode.BadRequest.getCode());

      //We are giving default CR (Credit operation)
      debitAccount = accountService.updateAccount(debitAccount,amount,transactionTypeId.equalsIgnoreCase(transactionTypeCredit));


    }else if(balanceStatus.equals(BalanceStatus.CR)){
      log.info("Credit(CR) Balace Status  account transaction side.");
      creditAccount = accountService.getAccountById(accountId,BalanceStatus.CR);
      String error = String.format(ErrorMessage.NO_ACCOUNT_FOUND);
      validator.isTrue(creditAccount != null,error, ErrorCode.BadRequest.getCode());

      //We are giving default CR (Credit operation)
      creditAccount = accountService.updateAccount(creditAccount,amount,transactionTypeId.equalsIgnoreCase(transactionTypeCredit));

    }else{
      throw new AccountException("No debit/credit account record found.");
    }

    //Creating transaction for credit accounts
    Transaction transaction = new Transaction(transactionType,debitAccount,creditAccount,new BigDecimal(amount));

    return transactionRepository.save(transaction);


  }
}
