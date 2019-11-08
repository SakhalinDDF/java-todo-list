package com.todo.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends HttpClientErrorException {
  public NotFoundException(HttpStatus statusCode) {
    super(statusCode);
  }

  public NotFoundException(HttpStatus statusCode, String statusText) {
    super(statusCode, statusText);
  }
}
