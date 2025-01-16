package de.becker.household.application.port.out;

import java.util.Optional;

import de.becker.household.domain.model.User;

public interface UserRepository {
    User save(final User user);
    Optional<User> findByUsername(final String username);
}
