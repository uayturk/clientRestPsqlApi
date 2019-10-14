package com.ufuk.clientRestPsqlApi.validator;


import com.ufuk.clientRestPsqlApi.exception.AccountException;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;


public interface Validator<K,V>  {
  void isTrue(@NotNull Boolean condition, @NotNull String errorMessage, int errorCode) throws AccountException;
}
