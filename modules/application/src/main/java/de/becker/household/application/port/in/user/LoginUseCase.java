package de.becker.household.application.port.in.user;

import de.becker.household.domain.model.User;

public interface LoginUseCase {
  User execute(final LoginCommand command);
}
