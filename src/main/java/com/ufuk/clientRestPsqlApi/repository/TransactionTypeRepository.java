package com.ufuk.clientRestPsqlApi.repository;

import com.ufuk.clientRestPsqlApi.exception.AccountException;
import com.ufuk.clientRestPsqlApi.model.TransactionType;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional(rollbackOn = AccountException.class)
@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType,String> {

}