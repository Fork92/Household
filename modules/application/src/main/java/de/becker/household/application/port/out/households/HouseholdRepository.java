package de.becker.household.application.port.out.households;

import de.becker.household.domain.model.Household;

public interface HouseholdRepository {
  Household save(final Household household);

  Household findById(final long householdId);
}
