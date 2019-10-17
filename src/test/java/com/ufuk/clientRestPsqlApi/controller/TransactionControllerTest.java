package com.ufuk.clientRestPsqlApi.controller;

import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Adresses;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.service.AccountService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //Annotation that can be applied to a test class to enable and configure auto-configuration of MockMvc.
public class TransactionControllerTest {

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


  private Adresses addresses;
  private Client client;
  private Account account;

  @Before
  public void before() {
    client = new Client(CLIENT_ID, FIRST_NAME, LAST_NAME,
        new Adresses(P_ADDRESSES_ID, P_ADDRESS_LINE1, P_ADDRESS_LINE2, P_CITY, P_COUNTRY),
        new Adresses(S_ADDRESSES_ID, S_ADDRESS_LINE1, S_ADDRESS_LINE2, S_CITY, S_COUNTRY));

  }


}
