package com.ufuk.clientRestPsqlApi.repository;

import com.ufuk.clientRestPsqlApi.model.Client;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{
  //Client findByFirstNameAndLastName(String firstName,String lastName);
}
