package com.fashion_search.api.common.exception;

import com.fashion_search.api.common.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public CommonResponse<Object> handleNoHandlerFound() {
    return CommonResponse.fail(ErrorCode.COMMON_NOT_FOUND_ERROR);
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public CommonResponse<Object> commonResNotFoundException(Throwable objThrowable) {
    if (log.isDebugEnabled()) {
      log.error("[NotFoundException] errorMsg = {}",
          NestedExceptionUtils.getMostSpecificCause(objThrowable).getMessage());
    }

    return CommonResponse.fail(ErrorCode.COMMON_NOT_FOUND_ERROR);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public CommonResponse<Object> commonResBadRequestException(Throwable objThrowable) {
    log.error("[BadRequestException] errorMsg = {}",
        NestedExceptionUtils.getMostSpecificCause(objThrowable).getMessage());

    return CommonResponse.fail(ErrorCode.COMMON_BAD_REQUEST_ERROR);
  }

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<Object> commonResBusinessException(Throwable objThrowable) {
    log.info("[BusinessException] errorMsg = {}",
            NestedExceptionUtils.getMostSpecificCause(objThrowable).getMessage());

    return CommonResponse.fail(((BusinessException) objThrowable).getErrorCode());
  }
}
