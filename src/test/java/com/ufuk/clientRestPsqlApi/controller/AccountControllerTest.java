package com.ufuk.clientRestPsqlApi.controller;

import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Adresses;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.model.Type;
import com.ufuk.clientRestPsqlApi.service.AccountService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //Annotation that can be applied to a test class to enable and configure auto-configuration of MockMvc.
public class AccountControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;


 /* private Client client;
  private Account account;*/

  private Client client = new Client();
  private Account account = new Account();

  @Before
  public void before() {

    client.setClientId((long)2);
    client.setFirstName("Ufuk Atakan");
    client.setLastName("Yüksel");
    client.setPrimaryAddress(new Adresses((long)1,"62 David Rd.","Coventry","Birmingham","United Kingdom"));
    client.setSecondaryAddress(new Adresses((long)2,"Gülveren St.","Hürriyet Cd.","Antalya","Turkey"));

    account.setAccountId((long)2);
    account.setBalanceStatus(BalanceStatus.CR);
    account.setType(Type.CURRENT);
    account.setBalance(new BigDecimal(1000));
    account.setClient(client);


  }

  /**
   * Some useful notes for BDDMockito(Behavior-Driven Development)
   * The 'given' part entitles the test case setup. The assumptions, the preconditions, the requirements for this use case.
   * The 'when' part is the action that you want to test. Normally, it’s also the smallest part of the test since the execution we want to test is typically one or two lines of code.
   * The 'then' part is used to very what should happen after the execution of the action, which is represented usually by assertions to mocked classes and validation of returned results.
   * @throws Exception
   */


  @Test
  public void testGetAllAccounts_getAccount_thenReturnJson() throws Exception {
    log.info("trying to get all accounts test.");
    Set<Account> allAccounts = Stream.of(account).collect(Collectors.toSet()); //Creates Set<account>

    given(accountService.getAllAccounts(Mockito.anyInt())).willReturn(allAccounts); //For Example just get 40 size account. It can be 50,60.70...

    log.info("all accounts for test:{}",allAccounts);

    /**
     * To test size of array: "jsonPath("$", hasSize(4))"  4 is just for example.
     *
     * To count members of object: "jsonPath("$.*", hasSize(4))"
     *
     * For the see response as string we can use : .andReturn().getResponse().getContentAsString()
     */
    log.info("response as string type:{}",mockMvc.perform(post("/getAllAccounts?size=40").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString());
    mockMvc.perform(post("/getAllAccounts?size=40").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].accountId",is(account.getAccountId().intValue())));

    log.info("successfully tested getAllAccounts controller.");

  }

  @Test
  public void testGetAccountById_thenReturnJson() throws Exception {
    log.info("trying to get account by accountId for test.");

    given(accountService.getAccountById(account.getAccountId())).willReturn(account);

    mockMvc.perform(post("/getAccountById/" + account.getAccountId()).contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
    .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
    .andExpect(jsonPath("$.type",is(account.getType().toString())))
    .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
    .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue()))); //if you use just getClient(),you will get Json error. Use like this when you geeting Json object from any model.

    log.info("successfully tested getAccountById controller.");
  }

  @Test
  public void testGetAccountByClientId_thenReturnJson() throws Exception{
    log.info("trying to get account by clientId for test.");

    given(accountService.getAccountByClientId(client.getClientId())).willReturn(account);
    //log.info("response as string type:{}", mockMvc.perform(post("/getAccountByClientId/" + client.getClientId()).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString());
    mockMvc.perform(post("/getAccountByClientId/" + client.getClientId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
        .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
        .andExpect(jsonPath("$.type",is(account.getType().toString())))
        .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
        .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue())));

    log.info("successfully tested getAccountByClientId controller.");
  }

  @Test
  public void testSaveAccount_thenReturnJson() throws Exception {
    log.info("trying to save account test:"+ account);
    given(accountService.saveAccount(Mockito.any(Account.class))).willReturn(account);
    String validAccountJson = "{\"accountId\":\"" + account.getAccountId()
        + "\",\"balance\":\"" + account.getBalance()
        + "\",\"balanceStatus\":\"" + account.getBalanceStatus()
        + "\",\"client\":" + "{\"clientId\":\"" + account.getClient().getClientId()
                             + "\",\"firstName\":\"" + account.getClient().getFirstName()
                             + "\",\"lastName\":\"" + account.getClient().getLastName()
                             + "\",\"primaryAddress\":" + "{\"addressId\":\"" + account.getClient().getPrimaryAddress().getAddressId()
                                                          +  "\",\"addressLine1\":\"" + account.getClient().getPrimaryAddress().getAddressLine1()
                                                          +  "\",\"addressLine2\":\"" + account.getClient().getPrimaryAddress().getAddressLine2()
                                                          +  "\",\"city\":\"" + account.getClient().getPrimaryAddress().getCity()
                                                          +  "\",\"country\":\"" + account.getClient().getPrimaryAddress().getCountry()+"\"}"
                             + ",\"secondaryAddress\":" + "{\"addressId\":\"" + account.getClient().getSecondaryAddress().getAddressId()
                                                          +  "\",\"addressLine1\":\"" + account.getClient().getSecondaryAddress().getAddressLine1()
                                                          +  "\",\"addressLine2\":\"" + account.getClient().getSecondaryAddress().getAddressLine2()
                                                          +  "\",\"city\":\"" + account.getClient().getSecondaryAddress().getCity()
                                                          +  "\",\"country\":\"" + account.getClient().getSecondaryAddress().getCountry()+"\"}"+"}"
        + ",\"type\":\"" + account.getType()+"\"}";

    mockMvc.perform(post("/saveAccount").content(validAccountJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
        .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
        .andExpect(jsonPath("$.type",is(account.getType().toString())))
        .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
        .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue())));

    log.info("successfully tested to saveAccount controller.");

  }

  @Test
  public void testUpdateAccount_thenReturnJson() throws Exception {
    log.info("trying to update account test:"+ account);

    given(accountService.updateAccount(Mockito.any(Account.class),Mockito.anyString(),Mockito.anyBoolean())).willReturn(account);
    String validAccountJson = "{\"accountId\":\"" + account.getAccountId()
        + "\",\"balance\":\"" + account.getBalance()
        + "\",\"balanceStatus\":\"" + account.getBalanceStatus()
        + "\",\"client\":" + "{\"clientId\":\"" + account.getClient().getClientId()
                                 + "\",\"firstName\":\"" + account.getClient().getFirstName()
                                 + "\",\"lastName\":\"" + account.getClient().getLastName()
                                  + "\",\"primaryAddress\":" + "{\"addressId\":\"" + account.getClient().getPrimaryAddress().getAddressId()
                                                    +  "\",\"addressLine1\":\"" + account.getClient().getPrimaryAddress().getAddressLine1()
                                                    +  "\",\"addressLine2\":\"" + account.getClient().getPrimaryAddress().getAddressLine2()
                                                    +  "\",\"city\":\"" + account.getClient().getPrimaryAddress().getCity()
                                                    +  "\",\"country\":\"" + account.getClient().getPrimaryAddress().getCountry()+"\"}"
                                  + ",\"secondaryAddress\":" + "{\"addressId\":\"" + account.getClient().getSecondaryAddress().getAddressId()
                                                    +  "\",\"addressLine1\":\"" + account.getClient().getSecondaryAddress().getAddressLine1()
                                                    +  "\",\"addressLine2\":\"" + account.getClient().getSecondaryAddress().getAddressLine2()
                                                    +  "\",\"city\":\"" + account.getClient().getSecondaryAddress().getCity()
                                                    +  "\",\"country\":\"" + account.getClient().getSecondaryAddress().getCountry()+"\"}"+"}"
        + ",\"type\":\"" + account.getType()+"\"}";
    //For ex. amount = 500 , isCredit=true
    mockMvc.perform(post("/updateAccount?amount=500&isCredit=true").content(validAccountJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
        .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
        .andExpect(jsonPath("$.type",is(account.getType().toString())))
        .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
        .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue())));

    log.info("successfully tested to updateAccount controller.");

  }

  @Test
  public void testDeleteAccountById_thenReturnJson() throws Exception {
    log.info("trying to delete account by Id test:" + account);

    given(accountService.deleteAccountById(account.getAccountId())).willReturn(account);

    mockMvc.perform(post("/deleteAccountById/" + account.getAccountId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
        .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
        .andExpect(jsonPath("$.type",is(account.getType().toString())))
        .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
        .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue()))); //if you use just getClient(),you will get Json error. Use like this when you geeting Json object from any model.

    log.info("successfully tested getAccountById controller.");
  }


}
