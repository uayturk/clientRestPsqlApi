package com.ufuk.clientRestPsqlApi.controller;


import com.ufuk.clientRestPsqlApi.service.ClientService;
import com.ufuk.clientRestPsqlApi.model.Client;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * check {@link ClientService}
 */

@RestController
/*@Controller*/ //If you use it,you should use @ResponseBody every method's header.
public class ClientController {


  @Autowired
  private final ClientService clientService;

  public ClientController(ClientService clientService) { this.clientService = clientService; }

   //Bakılacak linkler:
   // https://github.com/emedvede/wallet-microservice
   // https://o7planning.org/en/11661/spring-boot-jpa-and-spring-transaction-tutorial
  /**
   * Check {@link ClientService}.
   */
  @RequestMapping(value = "/getAllClients", method = {RequestMethod.POST}, produces = "application/json")
  /*@ResponseBody*/
  @ApiOperation(value = "Necessary doc is the below for getAllClients.\n",
      notes = "getAllClients is gets specific client from PostgresqlDB.\n "
  )
  public Set<Client> getAllClients(@RequestParam(required = false) Integer size) throws IOException, JSONException {
    return clientService.getAllClients(size);
  }


  /**
   * Check {@link ClientService}.
   */
  @RequestMapping(value = "/getClientById/{clientId}", method = {RequestMethod.POST}, produces = "application/json")
  /*@ResponseBody*/
  @ApiOperation(value = "Necessary doc is the below for getClientById.\n",
      notes = "getClientById is gets clients from PostgresqlDB.\n "
  )
  public Client getClientById(@PathVariable("clientId") Long clientId) throws IOException, JSONException {
    return clientService.getClientById(clientId);
  }


  /**
   * Check {@link ClientService}.
   */
  @RequestMapping(value = "/saveClient", method = {RequestMethod.POST}, produces = "application/json")
  /*@ResponseBody*/
  @ApiOperation(value = "Necessary doc is the below for saveClient.\n",
      notes = "saveClient is saves clients to PostgresqlDB.\n "
  )
  public Client saveClient(@RequestBody(required = false) Client client) throws IOException, JSONException {
    return clientService.saveClient(client);
  }


  /**
   * Check {@link ClientService}.
   */
  @RequestMapping(value = "/deleteClientById/{clientId}", method = {RequestMethod.POST}, produces = "application/json")
  @ApiOperation(value = "Necessary doc is the below for saveClient.\n",
      notes = "deleteClientById is deletes clients from PostgresqlDB.\n "
  )
  public Client deleteClientById(@PathVariable("clientId") Long clientId) throws IOException, JSONException {
    return clientService.deleteClientById(clientId);
  }




}
