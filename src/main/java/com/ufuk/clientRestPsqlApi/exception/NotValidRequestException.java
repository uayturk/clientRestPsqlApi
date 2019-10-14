package com.ufuk.clientRestPsqlApi.exception;

/**
 * Wraps validation errors.
 *
 * <p>
 * Extends {@link RuntimeException}
 * </p>
 */
public class NotValidRequestException extends RuntimeException {

  private final String message;

  public NotValidRequestException(String message) {
    super(message);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}