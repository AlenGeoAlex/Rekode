package me.alenalex.rekode.abstractions.exceptions;

public class FailedDbFactoryException extends Exception {
  public FailedDbFactoryException(String message) {
    super(message);
  }

  public FailedDbFactoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
