package de.becker.household.adapters.out.persistence.households;

import java.util.ArrayList;
import java.util.List;

import de.becker.household.adapters.out.persistence.users.UserEntity;
import de.becker.household.adapters.out.persistence.users.UserMapper;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class HouseholdMapper {

  public static Household mapToDomain(final HouseholdEntity entity, final boolean withUser) {
    if (entity == null) {
      return null;
    }

    List<User> users = new ArrayList<>();
    if (!entity.getUsers().isEmpty() && withUser) {
      for (UserEntity user : entity.getUsers()) {
        users.add(UserMapper.mapToDomain(user, false));
      }
    }

    return new Household(entity.getId() == null ? 0 : entity.getId(), entity.getName(), null, users);
  }

  public static HouseholdEntity mapToEntity(final Household household, final boolean withUser) {
    if (household == null) {
      return null;
    }

    List<UserEntity> users = new ArrayList<>();
    if (!household.getUsers().isEmpty() && withUser) {
      for (User user : household.getUsers()) {
        users.add(UserMapper.mapToEntity(user, false));
      }
    }

    return new HouseholdEntity(household.getId() == 0 ? null : household.getId(), household.getName(), users, null);
  }

}
