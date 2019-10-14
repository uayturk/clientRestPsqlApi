package com.ufuk.clientRestPsqlApi.exception;


public class AccountException extends Exception {

  private int errorCode;

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public AccountException(String message, int errorCode){
    super(message);
    this.errorCode = errorCode;
  }

  public AccountException(){
    super();

  }

  public AccountException(String message){
    super(message);
  }

  public AccountException(Exception e){
    super(e);
  }

}