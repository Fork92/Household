package de.becker.household.application.usecase.households;

import java.util.Collections;

import de.becker.household.application.port.in.households.CreateHouseholdCommand;
import de.becker.household.application.port.in.households.CreateHouseholdUseCase;
import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.exceptions.UserNotFoundException;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class CreateHouseholdService implements CreateHouseholdUseCase {

  private final HouseholdRepository householdRepository;
  private final UserRepository userRepository;

  public CreateHouseholdService(final HouseholdRepository householdRepository, final UserRepository userRepository) {
    this.householdRepository = householdRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Household execute(CreateHouseholdCommand command) {
    final User user = determineUser(command.username());
    final Household household = createHousehold(command, user);
    this.updateUser(user, household);

    return household;
  }

  private User determineUser(final String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
  }

  private Household createHousehold(CreateHouseholdCommand command, final User user) {
    final Household household = new Household(0, command.name(), Collections.emptyList(),
        Collections.singletonList(user));
    return householdRepository.save(household);
  }

  private void updateUser(final User user, final Household household) {
    user.setHousehold(household);
    userRepository.save(user);
  }

}
