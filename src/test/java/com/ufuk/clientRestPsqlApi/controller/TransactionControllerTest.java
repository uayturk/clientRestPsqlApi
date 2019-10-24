package com.ufuk.clientRestPsqlApi.controller;


import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Adresses;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.Type;
import com.ufuk.clientRestPsqlApi.service.TransactionService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransactionService transactionService;

  private Transaction transaction = new Transaction();
  private Client client1 = new Client();
  private Client client2 = new Client();
  private Account creditAccount = new Account();
  private Account debitAccount = new Account();


  @Before
  public void before() {

    client1.setClientId((long)2);
    client1.setFirstName("Ufuk Atakan");
    client1.setLastName("Yüksel");
    client1.setPrimaryAddress(new Adresses((long)1,"62 David Rd.","Coventry","Birmingham","United Kingdom"));
    client1.setSecondaryAddress(new Adresses((long)2,"Gülveren St.","Hürriyet Cd.","Antalya","Turkey"));

    client2.setClientId((long)2);
    client2.setFirstName("Emma");
    client2.setLastName("Watson");
    client2.setPrimaryAddress(new Adresses((long)1,"350 S. Beverly Dr.","Suite 200 Beverly Hills","California","United States"));
    client2.setSecondaryAddress(new Adresses((long)2,"Alwyne Villas.","Canonbury Square","London","United Kingdom"));

    creditAccount.setAccountId((long)2);
    creditAccount.setBalanceStatus(BalanceStatus.CR);
    creditAccount.setType(Type.CURRENT);
    creditAccount.setBalance(new BigDecimal(1000));
    creditAccount.setClient(client1);

    debitAccount.setAccountId((long)5);
    debitAccount.setBalanceStatus(BalanceStatus.DR);
    debitAccount.setType(Type.CURRENT);
    debitAccount.setBalance(new BigDecimal(2000));
    debitAccount.setClient(client2);


  }

}
