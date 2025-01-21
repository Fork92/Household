package de.becker.household.adapters.out.persistence.households;

import de.becker.household.domain.model.Household;

public class HouseholdMapper {

  public static Household mapToDomain(HouseholdEntity entity) {
    if (entity == null) {
      return null;
    }
    return new Household(entity.getId() == null ? 0 : entity.getId());
  }

  public static HouseholdEntity mapToEntity(final Household household) {
    if (household == null) {
      return null;
    }

    return new HouseholdEntity(household.getId() == 0 ? null : household.getId());
  }

}
