package com.example.shopapp.Responses;

import lombok.Data;

@Data
public class ErrorResponse {

  private Integer status;

  private String error;

  private String message;

  public static ErrorResponse of(Integer status, String error, String message) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(status);
    errorResponse.setError(error);
    errorResponse.setMessage(message);

    return errorResponse;
  }

}
