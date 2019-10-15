package com.ufuk.clientRestPsqlApi.controller;

import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  @Autowired
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) { this.transactionService = transactionService; }


  @RequestMapping(value = "/getTransactionsByAccountId/{accountId}", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for getTransactionsByAccountId.\n",
      notes = "getTransactionsByAccountId is gets specific transaction from PostgresqlDB.\n "
  )
  public Transaction getAllAccounts(@PathVariable("accountId") Long accountId) throws IOException, JSONException, AccountException {
    return transactionService.getTransactionsByAccountId(accountId);
  }


  @RequestMapping(value = "/saveTransaction", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for saveTransaction.\n",
      notes = "saveTransaction is saves transactions to PostgresqlDB.\n "
  )
  public Transaction saveTransaction(@PathVariable("accountId") Long accountId,@RequestParam String amount,@RequestParam String transactionTypeId,@RequestParam BalanceStatus balanceStatus) throws IOException, JSONException, AccountException {
    return transactionService.saveTransaction(accountId,amount,transactionTypeId,balanceStatus);
  }

}
