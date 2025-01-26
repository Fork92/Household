package de.becker.household.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(final String message) {
    super(message);
  }

}
