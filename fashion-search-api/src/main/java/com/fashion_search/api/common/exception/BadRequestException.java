package com.fashion_search.api.common.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class BadRequestException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1934071199264822288L;

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(int errorCode) {
    super(String.valueOf(errorCode));
  }

  public BadRequestException(Throwable cause) {
    super(cause);
  }

  public static HttpStatus getHttpStatus(){
    return HttpStatus.BAD_REQUEST;
  }
}
