package de.becker.household.application.port.in.households;

import de.becker.household.domain.model.Household;

public interface AddUserToHouseholdUseCase {
  Household execute(AddUserToHouseholdCommand command);
}
