package de.becker.household.application.port.in.user;

import de.becker.household.domain.model.User;

public interface RegisterUseCase {
    User execute(final RegisterCommand command);
}
