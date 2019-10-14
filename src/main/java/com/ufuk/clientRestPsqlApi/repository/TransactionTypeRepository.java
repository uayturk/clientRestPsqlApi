package com.ufuk.clientRestPsqlApi.repository;

import com.ufuk.clientRestPsqlApi.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType,Enum> {

}