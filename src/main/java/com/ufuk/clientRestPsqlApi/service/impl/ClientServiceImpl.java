package com.ufuk.clientRestPsqlApi.service.impl;


import com.ufuk.clientRestPsqlApi.exception.RecordNotFoundException;
import com.ufuk.clientRestPsqlApi.service.ClientService;
import com.ufuk.clientRestPsqlApi.model.Client;
import com.ufuk.clientRestPsqlApi.repository.ClientRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

  @Autowired
  ClientRepository clientRepository;

  @Override
  public Set<Client> getAllClients(Integer size) {
    log.info("trying to find clients for size: {}", size);

    Set<Client> clientList = clientRepository.findAll(PageRequest.of(0, size)).stream().collect(Collectors.toSet());

    if (clientList.size() > 0) {
      log.info("successfully fetched client list: {}", clientList.size());
      return clientList;
    } else {
      return new HashSet<>(clientRepository.findAll());
    }


    //İf you use List<> this method should be like below:
    //public List<Client> getAllClients(Integer size) (method type in ClientServiceImpl)
    //List<Client> getAllClients(Integer size);       (method type in ClientService)


    /*log.info("trying to find clients for size: {}", size);
    List<Client> clientList = clientRepository.findAll();

    if(clientList.size() > 0) {
      log.info("successfully fetched client list: {}", clientList.size());
      return clientList;
    } else {
      return new ArrayList<Client>();
    }*/

  }

  @Override
  public Client getClientById(Long clientId) throws RecordNotFoundException {
    log.info("trying to find client for clientId: {}", clientId);

    Optional<Client> clientOptional = clientRepository.findById(clientId);

    if(clientOptional.isPresent()) {
      log.info("successfully found client for clientId: {}", clientId);
      return clientOptional.get();
    } else {
      throw new RecordNotFoundException("No client record exist for given id");
    }
  }

  @Override
  public Client saveClient(Client client) throws RecordNotFoundException {
    log.info("trying to save client: {}", client);

    Optional<Client> clientOptional = clientRepository.findById(client.getClientId());

    if(clientOptional.isPresent())
    {
      Client savedClient = clientOptional.get();

      savedClient.setPrimaryAddress(client.getPrimaryAddress());
      savedClient.setSecondaryAddress(client.getSecondaryAddress());
      savedClient.setFirstName(client.getFirstName());
      savedClient.setLastName(client.getLastName());
      savedClient = clientRepository.save(savedClient);

      log.info("successfully updated savedClient: {}", savedClient);

      return savedClient;

    }else{
      client = clientRepository.save(client);

      log.info("successfully saved client: {}", client);

      return client;
    }

  }

  @Override
  public void deleteClientById(Long clientId) throws RecordNotFoundException {

    log.info("trying to delete client for clientId: {}", clientId);

    Optional<Client> clientOptional = clientRepository.findById(clientId);

    if(clientOptional.isPresent()) {
      clientRepository.deleteById(clientId);
      log.info("successfully to deleted client for clientId: {}", clientId);
    } else {
      throw new RecordNotFoundException("No employee record exist for given id");
    }
  }

}
