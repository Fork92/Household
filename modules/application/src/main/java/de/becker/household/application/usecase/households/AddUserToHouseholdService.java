package de.becker.household.application.usecase.households;

import de.becker.household.application.port.in.households.AddUserToHouseholdCommand;
import de.becker.household.application.port.in.households.AddUserToHouseholdUseCase;
import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.exceptions.UserNotFoundException;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class AddUserToHouseholdService implements AddUserToHouseholdUseCase {

  private final UserRepository userRepository;
  private final HouseholdRepository householdRepository;

  public AddUserToHouseholdService(final UserRepository userRepository, final HouseholdRepository householdRepository) {
    this.userRepository = userRepository;
    this.householdRepository = householdRepository;
  }

  public Household execute(final AddUserToHouseholdCommand command) {
    User user = findByUsername(command);
    Household household = findHouseholdById(command);

    household.addUser(user);

    return householdRepository.save(household);

  }

  private Household findHouseholdById(final AddUserToHouseholdCommand command) {
    return householdRepository.findById(command.householdId());
  }

  private User findByUsername(final AddUserToHouseholdCommand command) {
    return userRepository.findByUsername(command.username()).orElseThrow(() -> {
      throw new UserNotFoundException("user with name " + command.username() + " not found");
    });
  }

}
