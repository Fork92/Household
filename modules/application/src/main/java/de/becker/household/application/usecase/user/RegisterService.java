package de.becker.household.application.usecase.user;

import de.becker.household.application.port.in.user.RegisterCommand;
import de.becker.household.application.port.in.user.RegisterUseCase;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.domain.exceptions.RegistrationException;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class RegisterService implements RegisterUseCase {

  private final UserRepository userRepository;
  private final HouseholdRepository householdRepository;
  private final UserPasswordEncoder userPasswordEncoder;

  public RegisterService(final UserRepository userRepository,
      final UserPasswordEncoder userPasswordEncoder,
      final HouseholdRepository householdRepository) {
    this.userRepository = userRepository;
    this.userPasswordEncoder = userPasswordEncoder;
    this.householdRepository = householdRepository;
  }

  @Override
  public User execute(RegisterCommand command) {
    checkIfUserAlreadyExists(command.username());
    Household household = createHousehold();
    String encodedPassword = encodePassword(command.password());
    return saveUser(command.username(), encodedPassword, household);
  }

  private String encodePassword(final String password) {
    return this.userPasswordEncoder.encode(password);
  }

  private Household createHousehold() {
    return this.householdRepository.save(new Household(0, null));
  }

  private void checkIfUserAlreadyExists(final String username) {
    this.userRepository.findByUsername(username)
        .ifPresent(_ -> {
          throw new RegistrationException("Username already exists");
        });
  }

  private User saveUser(final String username, final String encodedPassword, final Household household) {
    User toSave = new User(0, username, encodedPassword, household);
    return this.userRepository.save(toSave);
  }

}
