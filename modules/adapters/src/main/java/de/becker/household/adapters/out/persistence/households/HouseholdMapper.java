package de.becker.household.adapters.out.persistence.households;

import de.becker.household.domain.model.Household;

public class HouseholdMapper {

  public static Household mapToDomain(HouseholdEntity entity) {
    if (entity == null) {
      return null;
    }
    return new Household(entity.getId() == null ? 0 : entity.getId(), null);
  }

  public static HouseholdEntity mapToEntity(final Household household) {
    if (household == null) {
      return null;
    }

    return new HouseholdEntity(household.id() == 0 ? null : household.id());
  }

}
