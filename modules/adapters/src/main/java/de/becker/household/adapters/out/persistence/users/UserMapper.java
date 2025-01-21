package de.becker.household.adapters.out.persistence.users;

import de.becker.household.adapters.out.persistence.households.HouseholdEntity;
import de.becker.household.adapters.out.persistence.households.HouseholdMapper;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class UserMapper {

  static User mapToDomain(final UserEntity entity) {
    if (entity == null) {
      return null;
    }

    long id = 0;
    if (entity.getId() != null) {
      id = entity.getId();
    }

    Household household = null;
    if (entity.getHousehold() != null) {
      household = HouseholdMapper.mapToDomain(entity.getHousehold());
    }

    return new User(id, entity.getUsername(), entity.getPasswordHash(), household);
  }

  static UserEntity mapToEntity(final User user) {
    if (user == null) {
      return null;
    }

    Long id = null;
    if (user.getId() > 0) {
      id = user.getId();
    }

    HouseholdEntity householdEntity = null;
    if (user.getHousehold() != null) {
      householdEntity = HouseholdMapper.mapToEntity(user.getHousehold());
    }

    return new UserEntity(id, user.getUsername(), user.getPasswordHash(), householdEntity);
  }
}
