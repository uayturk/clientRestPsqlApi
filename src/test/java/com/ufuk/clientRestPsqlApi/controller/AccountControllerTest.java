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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //Annotation that can be applied to a test class to enable and configure auto-configuration of MockMvc.
public class AccountControllerTest {

  //For Address model(primary address)
  public static final Long P_ADDRESSES_ID = Long.valueOf("1");
  public static final String P_ADDRESS_LINE1 = "62 David Rd.";
  public static final String P_ADDRESS_LINE2 = "Coventry";
  public static final String P_CITY = "Birmingham";
  public static final String P_COUNTRY = "United Kingdom";

  //For Address model(secondary address)
  public static final Long S_ADDRESSES_ID = Long.valueOf("2");
  public static final String S_ADDRESS_LINE1 = "Gülveren St.";
  public static final String S_ADDRESS_LINE2 = "Hürriyet Cd.";
  public static final String S_CITY = "Antalya";
  public static final String S_COUNTRY = "Turkey";

  //For Client model
  public static final Long CLIENT_ID = Long.valueOf("2");
  public static final String FIRST_NAME = "Ufuk Atakan";
  public static final String LAST_NAME = "Yüksel";

  //For Account model
  public static final Long ACCOUNT_ID = Long.valueOf("3");



  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;


  private Client client;
  private Account account;

  @Before
  public void before() {

    client = new Client(CLIENT_ID, FIRST_NAME, LAST_NAME,
        new Adresses(P_ADDRESSES_ID, P_ADDRESS_LINE1, P_ADDRESS_LINE2, P_CITY, P_COUNTRY),
        new Adresses(S_ADDRESSES_ID, S_ADDRESS_LINE1, S_ADDRESS_LINE2, S_CITY, S_COUNTRY));
    account = new Account(ACCOUNT_ID, BalanceStatus.CR, Type.CURRENT, new BigDecimal(1000),client);

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

    given(accountService.getAllAccounts(40)).willReturn(allAccounts); //For Example just get 40 size account. It can be 50,60.70...

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

    log.info("successfully tested to get all accounts.");

  }

  @Test
  public void testGetAccountById_thenReturnJson() throws Exception {
    log.info("trying to get account by accountId for test.");

    given(accountService.getAccountById(account.getAccountId())).willReturn(account);

    mockMvc.perform(post("/getAccountById/" + account.getAccountId().toString()).contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
    .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
    .andExpect(jsonPath("$.type",is(account.getType().toString())))
    .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
    .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue()))); //if you use just getClient(),you will get Json error. Use like this when you geeting Json object from any model.

  }

  @Test
  public void testSaveAccount_thenReturnJson() throws Exception {
    log.info("trying to save account test:{}."+ account);
    given(accountService.saveAccount(account)).willReturn(account);
    String validAccountJson = "{\"accountId\":\"" + ACCOUNT_ID
        + "\",\"balance\":\"" + new BigDecimal(1000)
        + "\",\"balanceStatus\":\"" + BalanceStatus.CR
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
        + ",\"type\":\"" + Type.CURRENT+"\"}";

    log.info("jhgsdyhgdwegtwsdysdwybxyswx6xsw6xwf:{}",validAccountJson);
    mockMvc.perform(post("/saveAccount").content(validAccountJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountId",is(account.getAccountId().intValue())))
        .andExpect(jsonPath("$.balanceStatus",is(account.getBalanceStatus().toString())))
        .andExpect(jsonPath("$.type",is(account.getType().toString())))
        .andExpect(jsonPath("$.balance",is(account.getBalance().intValue())))
        .andExpect(jsonPath("$.client.clientId",is(account.getClient().getClientId().intValue())));

//mockMvc.perform(post("/saveAccount").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validAccountJson))).andDo(print())


  }


}
