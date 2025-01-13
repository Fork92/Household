package de.becker.household.application.port.in.user;


public interface LoginUseCase {
  User execute(final LoginCommand command);
}
