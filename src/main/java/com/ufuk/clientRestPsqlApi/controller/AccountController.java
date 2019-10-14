package com.ufuk.clientRestPsqlApi.controller;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.service.AccountService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @Autowired
  private final AccountService accountService;

  public AccountController(AccountService accountService) {this.accountService = accountService;}

  @RequestMapping(value = "/getAllAccounts", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for getAllAccounts.\n",
      notes = "getAllAccounts is gets specific account from PostgresqlDB.\n "
  )
  public Set<Account> getAllAccounts(@RequestParam(required = false) Integer size) throws IOException, JSONException,AccountException {
    return accountService.getAllAccounts(size);
  }

  /**
   * Check {@link AccountService}.
   */
  @RequestMapping(value = "/getAccountById/{accountId}", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for getAccountById.\n",
      notes = "getAccountById is gets accounts from PostgresqlDB.\n "
  )
  public Account getAccountById(@PathVariable("accountId") Long accountId) throws IOException, JSONException, AccountException {
    return accountService.getAccountById(accountId);
  }

  /**
   * Check {@link AccountService}.
   */
  @RequestMapping(value = "/getAccountByClientId/{clientId}", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for getAccountByClientId.\n",
      notes = "getAccountByClientId is gets accounts from PostgresqlDB.\n "
  )
  public Account getAccountByClientId(@PathVariable("clientId") Long clientId) throws IOException, JSONException, AccountException {
    return accountService.getAccountByClientId(clientId);
  }

  /**
   * Check {@link AccountService}.
   */
  @RequestMapping(value = "/saveAccount", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for saveAccount.\n",
      notes = "saveAccount is saves accounts to PostgresqlDB.\n "
  )
  public Account saveAccount(@RequestBody(required = false) Account account) throws IOException, JSONException, AccountException {
    return accountService.saveAccount(account);
  }

  /**
   * Check {@link AccountService}
   */
  @RequestMapping(value = "/updateAccount", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for updateAccount.\n",
      notes = "updateAccount is updates accounts on PostgresqlDB.\n "
  )
  public Account updateAccount(@RequestBody(required = false) Account account,@RequestParam String amount,@RequestParam Boolean isFund) throws IOException, JSONException, AccountException {
    return accountService.updateAccount(account,amount,isFund);
  }


  /**
   * Check {@link AccountService}.
   */
  @RequestMapping(value = "/deleteAccountById/{accountId}", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for saveClient.\n",
      notes = "deleteAccountById is deletes accounts from PostgresqlDB.\n "
  )
  public void deleteAccountById(@PathVariable("accountId") Long accountId) throws IOException, JSONException, AccountException {
    accountService.deleteAccountById(accountId);
  }

}
