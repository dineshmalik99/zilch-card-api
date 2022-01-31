package com.zilch.cards.exception;

import com.zilch.cards.model.ApiError;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.flogger.Flogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Flogger
public class CardExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
    log.atSevere().withCause(ex).log("Exception thrown %s", ex.getMessage());
    var errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    var apiError = errorCode.getApiError();
    apiError.detail(ex.getMessage());
    return new ResponseEntity<>(apiError, errorCode.getStatus());
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<Object> handleBaseException(BaseException e, HttpServletRequest request) {
    return handleApiError(e.getApiError(), request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpServletRequest httpServletRequest) {
    var apiError = ErrorCode.INVALID_REQUEST_PARAMETER.getApiError();
    apiError.setDetail("Error in field: " + e.getBindingResult().getFieldErrors());
    apiError.setDetail(e.getBindingResult().getFieldErrors().stream().map(fieldError -> {
      String apiErrorDetail = "Error in field: " + fieldError.getField() + ", description: "
          + fieldError.getDefaultMessage();
      return apiErrorDetail;
    }).toString());
    log.atInfo().withCause(e).log("MethodArgumentNotValid Error %s", apiError);
    return handleApiError(apiError, httpServletRequest);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<Object> handleMissingRequestHeaderExceptionException(
      MissingRequestHeaderException e, HttpServletRequest request) {
    var detail = "MissingRequestHeaderException thrown";
    log.atWarning().withCause(e).log(detail);
    return handleApiError(ErrorCode.INVALID_REQUEST_HEADER.getApiError(detail), request);
  }

  private ResponseEntity<Object> handleApiError(ApiError apiError, HttpServletRequest request) {
    return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
  }

}
