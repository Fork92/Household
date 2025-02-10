package de.becker.household.application.port.in.households;

import java.util.List;

import de.becker.household.domain.model.User;

public interface ListHouseholdUserUseCase {
  List<User> execute(final ListHouseholdUserCommand command);
}
