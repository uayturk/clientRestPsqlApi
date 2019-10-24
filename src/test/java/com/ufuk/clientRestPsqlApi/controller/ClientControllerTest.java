package com.ufuk.clientRestPsqlApi.controller;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ufuk.clientRestPsqlApi.model.Adresses;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.service.ClientService;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  private Client client = new Client();

  @Before
  public void before() {

    client.setClientId((long)2);
    client.setFirstName("Ufuk Atakan");
    client.setLastName("Yüksel");
    client.setPrimaryAddress(new Adresses((long)1,"62 David Rd.","Coventry","Birmingham","United Kingdom"));
    client.setSecondaryAddress(new Adresses((long)2,"Gülveren St.","Hürriyet Cd.","Antalya","Turkey"));

  }

  @Test
  public void testGetAllAccounts_getAccount_thenReturnJson() throws Exception {
    log.info("trying to get all clients test.");
    Set<Client> allClients = Stream.of(client).collect(Collectors.toSet()); //Creates Set<account>

    given(clientService.getAllClients(Mockito.anyInt())).willReturn(allClients); //For Example just get 40 size account. It can be 50,60.70...

    log.info("all clients for test:{}",allClients);

    log.info("response as string type:{}",mockMvc.perform(post("/getAllClients?size=40").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString());
    mockMvc.perform(post("/getAllClients?size=40").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].clientId",is(client.getClientId().intValue())));

    log.info("successfully tested getAllClients controller.");

  }

  @Test
  public void testGetClientById_thenReturnJson() throws Exception{
    log.info("trying to get client by clientId for test.");

    given(clientService.getClientById(client.getClientId())).willReturn(client);

    mockMvc.perform(post("/getClientById/" + client.getClientId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clientId",is(client.getClientId().intValue())))
        .andExpect(jsonPath("$.firstName",is(client.getFirstName())))
        .andExpect(jsonPath("$.lastName",is(client.getLastName())))
        .andExpect(jsonPath("$.primaryAddress.addressId",is(client.getPrimaryAddress().getAddressId().intValue())))
        .andExpect(jsonPath("$.secondaryAddress.addressId",is(client.getSecondaryAddress().getAddressId().intValue() )));

    log.info("successfully tested getClientById controller.");
  }

  @Test
  public void testSaveClient_thenReturnJson() throws Exception {

    log.info("trying to save client test:"+ client);

    given(clientService.saveClient(Mockito.any(Client.class))).willReturn(client);

    String validClientJson = "{\"clientId\":\"" + client.getClientId()
                          + "\",\"firstName\":\"" + client.getFirstName()
                          + "\",\"lastName\":\"" + client.getLastName()
                          + "\",\"primaryAddress\":" + "{\"addressId\":\"" + client.getPrimaryAddress().getAddressId()
                                                  +  "\",\"addressLine1\":\"" + client.getPrimaryAddress().getAddressLine1()
                                                  +  "\",\"addressLine2\":\"" + client.getPrimaryAddress().getAddressLine2()
                                                  +  "\",\"city\":\"" + client.getPrimaryAddress().getCity()
                                                  +  "\",\"country\":\"" + client.getPrimaryAddress().getCountry()+"\"}"
                          + ",\"secondaryAddress\":" + "{\"addressId\":\"" + client.getSecondaryAddress().getAddressId()
                                                  +  "\",\"addressLine1\":\"" + client.getSecondaryAddress().getAddressLine1()
                                                  +  "\",\"addressLine2\":\"" + client.getSecondaryAddress().getAddressLine2()
                                                  +  "\",\"city\":\"" + client.getSecondaryAddress().getCity()
                                                  +  "\",\"country\":\"" + client.getSecondaryAddress().getCountry()+"\"}"+"}";

    mockMvc.perform(post("/saveClient").content(validClientJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clientId",is(client.getClientId().intValue())))
        .andExpect(jsonPath("$.firstName",is(client.getFirstName())))
        .andExpect(jsonPath("$.lastName",is(client.getLastName())))
        .andExpect(jsonPath("$.primaryAddress.addressId",is(client.getPrimaryAddress().getAddressId().intValue())))
        .andExpect(jsonPath("$.secondaryAddress.addressId",is(client.getSecondaryAddress().getAddressId().intValue() )));

    log.info("successfully tested getClientById controller.");

  }

  @Test
  public void testDeleteClientById_thenReturnJson() throws Exception{
    log.info("trying to delete client by clientId for test.");

    given(clientService.deleteClientById(client.getClientId())).willReturn(client);

    mockMvc.perform(post("/deleteClientById/" + client.getClientId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clientId",is(client.getClientId().intValue())))
        .andExpect(jsonPath("$.firstName",is(client.getFirstName())))
        .andExpect(jsonPath("$.lastName",is(client.getLastName())))
        .andExpect(jsonPath("$.primaryAddress.addressId",is(client.getPrimaryAddress().getAddressId().intValue())))
        .andExpect(jsonPath("$.secondaryAddress.addressId",is(client.getSecondaryAddress().getAddressId().intValue() )));

    log.info("successfully tested deleteClientById controller.");
  }

}
