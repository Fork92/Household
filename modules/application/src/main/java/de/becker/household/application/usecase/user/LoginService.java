package de.becker.household.application.usecase.user;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.becker.household.application.port.in.user.LoginCommand;
import de.becker.household.application.port.in.user.LoginUseCase;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.exceptions.AuthenticationException;
import de.becker.household.domain.model.User;

public class LoginService implements LoginUseCase {

  private final UserRepository userRepository;
  private final UserPasswordEncoder userPasswordEncoder;
  private final Logger LOG = LoggerFactory.getLogger(LoginService.class);

  public LoginService(final UserRepository userRepository, final UserPasswordEncoder userPasswordEncoder) {
    this.userRepository = userRepository;
    this.userPasswordEncoder = userPasswordEncoder;
  }

  @Override
  public User execute(LoginCommand command) {
    User user = checkUsername(command.username());
    checkPassword(command.password(), user.getPasswordHash());
    return user;
  }

  private User checkUsername(final String username) {
    final Optional<User> user = this.userRepository.findByUsername(username);
    if (user.isEmpty()) {
      LOG.info("User is empty for username {}", username);
      throw new AuthenticationException("Invalid username or password");
    }
    LOG.info("Found user for username {}", username);
    return user.get();
  }

  private void checkPassword(final String password, final String hash) {
    if (!this.userPasswordEncoder.matches(password, hash)) {
      LOG.info("Wrong password");
      throw new AuthenticationException("Invalid username or password");
    }

    LOG.info("right password");
  }
}
