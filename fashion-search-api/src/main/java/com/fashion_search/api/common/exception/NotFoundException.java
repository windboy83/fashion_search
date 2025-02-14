package com.fashion_search.api.common.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class NotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 8074778358268837188L;

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(int errorCode) {
    super(String.valueOf(errorCode));
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }

  public static HttpStatus getHttpStatus(){
    return HttpStatus.NOT_FOUND;
  }
}
