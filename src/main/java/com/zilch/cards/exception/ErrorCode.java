package com.zilch.cards.exception;

import com.zilch.cards.model.ApiError;
import org.springframework.http.HttpStatus;

/**
 * Base enum for creating ApiError Instance. status – the HTTP response code (optional) detail – a
 * human-readable explanation of the error
 */
public enum ErrorCode {

  //400
  INVALID_REQUEST_HEADER(
      HttpStatus.BAD_REQUEST,
      "Invalid request header : %s",
      "ERR-" + HttpStatus.BAD_REQUEST.value()),
  INVALID_REQUEST_PARAMETER(
      HttpStatus.BAD_REQUEST,
      "Invalid Request Parameters",
      "ERR-" + HttpStatus.BAD_REQUEST.value()),
  INVALID_REQUEST_BODY(
      HttpStatus.BAD_REQUEST,
      "Invalid Request body : %s",
      "ERR-" + HttpStatus.BAD_REQUEST.value()),
  NOT_FOUND(
      HttpStatus.NOT_FOUND,
      "No Record found for : %s",
      "ERR-" + HttpStatus.NOT_FOUND.value()),
  // 500
  INTERNAL_SERVER_ERROR(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "Internal Server Error: %s",
      "ERR-" + HttpStatus.INTERNAL_SERVER_ERROR.value());


  private final HttpStatus status;

  private final String detail;

  private final String code;


  public ApiError getApiError() {
    var error = new ApiError();
    error.setStatus(status.value());
    error.setDetail(this.detail);
    error.code(this.code);
    return error;
  }

  public ApiError getApiError(Object... v) {
    var apiError = getApiError();
    apiError.setDetail(detail.formatted(v));
    return apiError;
  }

  public String getDescription(Object... v) {
    return detail.formatted(v);
  }

  public HttpStatus getStatus() {
    return this.status;
  }


  ErrorCode(HttpStatus status, String detail, String code) {
    this.status = status;
    this.detail = detail;
    this.code = code;
  }
}
