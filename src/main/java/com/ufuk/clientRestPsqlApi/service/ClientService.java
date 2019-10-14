package com.ufuk.clientRestPsqlApi.service;


import com.ufuk.clientRestPsqlApi.exception.RecordNotFoundException;
import com.ufuk.clientRestPsqlApi.model.Client;
import java.util.Set;

public interface ClientService {

  /**
   * Clientsare unique , but their names may not be distinct.
   * Using a Set would eliminate duplicates, incorrectly removing names of different employees with identical names.
   * Thats why we used Set<> instead of List<> .
   * @return
   */

  Set<Client> getAllClients(Integer size);

  Client getClientById(Long clientId) throws RecordNotFoundException; //read

  Client saveClient(Client client) throws RecordNotFoundException; //create & update

  //Client updateClient(Client client) throws RecordNotFoundException; //update

  void deleteClientById(Long clientId) throws RecordNotFoundException; //delete

}
