package de.becker.household.adapters.out.persistence.households;

import java.util.ArrayList;
import java.util.List;

import de.becker.household.adapters.out.persistence.users.UserEntity;
import de.becker.household.adapters.out.persistence.users.UserMapper;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class HouseholdMapper {

  public static Household mapToDomain(HouseholdEntity entity) {
    if (entity == null) {
      return null;
    }

    List<User> users = new ArrayList<>();
    if (!entity.getUsers().isEmpty()) {
      users = entity.getUsers().stream().map(UserMapper::mapToDomain).toList();
    }

    return new Household(entity.getId() == null ? 0 : entity.getId(), entity.getName(), null, users);
  }

  public static HouseholdEntity mapToEntity(final Household household) {
    if (household == null) {
      return null;
    }

    List<UserEntity> users = new ArrayList<>();
    if (!household.getUsers().isEmpty()) {
      users = household.getUsers().stream().map(UserMapper::mapToEntity).toList();
    }

    return new HouseholdEntity(household.getId() == 0 ? null : household.getId(), household.getName(), users, null);
  }

}
