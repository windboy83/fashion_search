package com.fashion_search.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());

    this.errorCode = errorCode;
  }

  public static HttpStatus getHttpStatus() {
    return HttpStatus.OK;
  }
}
