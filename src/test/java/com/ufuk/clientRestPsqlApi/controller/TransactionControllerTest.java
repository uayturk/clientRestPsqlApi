package com.ufuk.clientRestPsqlApi.controller;


import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ufuk.clientRestPsqlApi.model.Account;
import com.ufuk.clientRestPsqlApi.model.Adresses;
import com.ufuk.clientRestPsqlApi.model.BalanceStatus;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.model.Transaction;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import com.ufuk.clientRestPsqlApi.model.Type;
import com.ufuk.clientRestPsqlApi.service.TransactionService;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
  public void before() throws ParseException {

    client1.setClientId((long)2);
    client1.setFirstName("Ufuk Atakan");
    client1.setLastName("Yüksel");
    client1.setPrimaryAddress(new Adresses((long)1,"62 David Rd.","Coventry","Birmingham","United Kingdom"));
    client1.setSecondaryAddress(new Adresses((long)2,"Gülveren St.","Hürriyet Cd.","Antalya","Turkey"));

    client2.setClientId((long)3);
    client2.setFirstName("Emma");
    client2.setLastName("Watson");
    client2.setPrimaryAddress(new Adresses((long)1,"350 S. Beverly Dr.","Suite 200 Beverly Hills","California","United States"));
    client2.setSecondaryAddress(new Adresses((long)2,"Alwyne Villas.","Canonbury Square","London","United Kingdom"));

    creditAccount.setAccountId((long)4);
    creditAccount.setBalanceStatus(BalanceStatus.CR);
    creditAccount.setType(Type.CURRENT);
    creditAccount.setBalance(new BigDecimal(1000));
    creditAccount.setClient(client1);

    debitAccount.setAccountId((long)5);
    debitAccount.setBalanceStatus(BalanceStatus.DR);
    debitAccount.setType(Type.CURRENT);
    debitAccount.setBalance(new BigDecimal(2000));
    debitAccount.setClient(client2);

    transaction.setTransactionId((long)6);
    transaction.setType(TransactionType.CR);
    transaction.setDebitAccount(debitAccount);
    transaction.setCreditAccount(creditAccount);
    transaction.setAmount(new BigDecimal(3000));
    transaction.setMessage("This is TransactionControllerTest");

  }

  @Test
  public void testForDebitAccountGetTransactionsByAccountId_thenReturnJson() throws Exception {
    log.info("trying to get transaction for debit accounts by accountId test.");
    /**
     * The singletonList() method of java.util.Collections class is used to return an immutable(değişmez) list containing only the specified object.
     * The returned list is serializable.
     */

    List<Transaction> allTransactions = Collections.singletonList(transaction);

    log.info("all transactions for test:{}",allTransactions);

    given(transactionService.getTransactionsByAccountId(transaction.getDebitAccount().getAccountId())).willReturn(allTransactions);

    mockMvc.perform(post("/getTransactionsByAccountId/" + transaction.getDebitAccount().getAccountId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].transactionId",is(transaction.getTransactionId().intValue())))
        .andExpect(jsonPath("$[0].type",is(transaction.getType().toString())))
        .andExpect(jsonPath("$[0].debitAccount.accountId",is(transaction.getDebitAccount().getAccountId().intValue())))
        .andExpect(jsonPath("$[0].creditAccount.accountId",is(transaction.getCreditAccount().getAccountId().intValue())))
        .andExpect(jsonPath("$[0].amount",is(transaction.getAmount().intValue())))
        .andExpect(jsonPath("$[0].message",is(transaction.getMessage())));

    log.info("successfully tested getTransactionsByAccountId controller for debit accounts.");

  }

  @Test
  public void testForCreditAccountGetTransactionsByAccountId_thenReturnJson() throws Exception {
    log.info("trying to get transaction for credit accounts by accountId test.");

    List<Transaction> allTransactions = Collections.singletonList(transaction);

    log.info("all transactions for test:{}",allTransactions);

    given(transactionService.getTransactionsByAccountId(transaction.getCreditAccount().getAccountId())).willReturn(allTransactions);

    mockMvc.perform(post("/getTransactionsByAccountId/" + transaction.getCreditAccount().getAccountId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].transactionId",is(transaction.getTransactionId().intValue())))
        .andExpect(jsonPath("$[0].type",is(transaction.getType().toString())))
        .andExpect(jsonPath("$[0].debitAccount.accountId",is(transaction.getDebitAccount().getAccountId().intValue())))
        .andExpect(jsonPath("$[0].creditAccount.accountId",is(transaction.getCreditAccount().getAccountId().intValue())))
        .andExpect(jsonPath("$[0].amount",is(transaction.getAmount().intValue())))
        .andExpect(jsonPath("$[0].message",is(transaction.getMessage())));

    log.info("successfully tested getTransactionsByAccountId controller for credit accounts.");

  }

  @Test
  public void testSaveTransaction_thenReturnJson() throws Exception {

    log.info("trying to test saveTransaction controller.");

    given(transactionService.saveTransaction(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString(),Mockito.any(TransactionType.class),Mockito.anyString())).willReturn(transaction);

    mockMvc.perform(post("/saveTransaction?amount=300&creditAccountId=4&debitAccountId=5&message=TransactionTestMessage..&transactionTypeId=CR").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactionId",is(transaction.getTransactionId().intValue())))
        .andExpect(jsonPath("$.type",is(transaction.getType().toString())))
        .andExpect(jsonPath("$.debitAccount.accountId",is(transaction.getDebitAccount().getAccountId().intValue())))
        .andExpect(jsonPath("$.creditAccount.accountId",is(transaction.getCreditAccount().getAccountId().intValue())))
        .andExpect(jsonPath("$.amount",is(transaction.getAmount().intValue())))
        .andExpect(jsonPath("$.message",is(transaction.getMessage())));

    log.info("successfully tested to updateAccount controller.");

  }





}
