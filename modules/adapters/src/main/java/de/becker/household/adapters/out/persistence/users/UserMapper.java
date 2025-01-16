package de.becker.household.adapters.out.persistence.users;

import de.becker.household.domain.model.User;

public class UserMapper {

  static User mapToDomain(UserEntity entity) {
    long id = 0;
    if (entity.getId() != null) {
      id = entity.getId();
    }

    return new User(id, entity.getUsername(), entity.getPasswordHash());
  }

  static UserEntity mapToEntity(User user) {
    Long id = null;
    if (user.getId() > 0) {
      id = user.getId();
    }

    return new UserEntity(id, user.getUsername(), user.getPasswordHash());
  }
}
