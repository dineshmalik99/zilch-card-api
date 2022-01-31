package com.zilch.cards.exception;

import com.zilch.cards.model.ApiError;
import java.io.Serial;

public class BaseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -821834626317313276L;

  // Encapsulated error data object
  private ApiError apiError;

  public BaseException(ApiError apiError) {
    super(apiError.getDetail());
    this.apiError = apiError;
  }

  public BaseException(ApiError apiError, Throwable cause) {
    super(apiError.getDetail(), cause);
    this.apiError = apiError;
  }

  public ApiError getApiError() {
    return apiError;
  }

}
