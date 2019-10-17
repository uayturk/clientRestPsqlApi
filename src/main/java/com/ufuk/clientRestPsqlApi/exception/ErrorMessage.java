package com.ufuk.clientRestPsqlApi.exception;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorMessage {
  //Simple messages for users
  public static final String NO_ACCOUNT_FOUND = "No account record found for given accountId.";
  public static final String NOT_ENOUGH_FUNDS = "Account %d has not enough funds to perform debit transaction with amount %s";

  //Simple template messages for compare
  public static final String CURRENCY_TOO_LONG = "value too long";
  public static final String ACCOUNT_FK_VIOLATES_TRANSACTION = "violates foreign key constraint \"transaction_wallet_id_fkey\"";

  public static final String [][] ERRORS = {
      //{DUPLICATE_KEY,                         TRANSACTION_WITH_GLOBAL_ID_PRESENT},
      {ACCOUNT_FK_VIOLATES_TRANSACTION,        NO_ACCOUNT_FOUND}
  };

  /**
   * Generates error message besed on DataIntegrityViolationException error message using ErrorMessage.ERRORS
   * @param errorMessage from exception from DataIntegrityViolationException
   * @return Generated message for user
   */
  public static String generateErrorMessageForDataIntegrityViolationException(String errorMessage){
    String bodyOfResponse = errorMessage;
    //create map of array ERRORS
    Map<String, String> errors =
        Arrays.stream(ERRORS).collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
    //check if error message contains any of ERRORS keys and generate corresponding response
    for(String key:errors.keySet()){
      if(errorMessage.contains(key)){
        if(!key.equals(CURRENCY_TOO_LONG)) {
          String id = getId(errorMessage);
          bodyOfResponse = String.format(errors.get(key),id);
        } else {
          bodyOfResponse = errors.get(key);
        }
      }
    }
    return bodyOfResponse;
  }


  private static String getId(String error){
    return error.substring(error.lastIndexOf("(") + 1,error.lastIndexOf(")"));
  }


}