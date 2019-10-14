package com.ufuk.clientRestPsqlApi.validator;

import com.ufuk.clientRestPsqlApi.exception.AccountException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
@Component
public class ValidatorImpl implements Validator<String,String> {
  @Override
  public void isTrue(@NotNull Boolean condition,@NotNull String errorMessage, int errorCode) throws AccountException{
    if(!condition){
      throw new AccountException(errorMessage,errorCode);
    }
  }
}
