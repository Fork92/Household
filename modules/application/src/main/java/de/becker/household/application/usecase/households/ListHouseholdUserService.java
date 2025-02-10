package de.becker.household.application.usecase.households;

import java.util.List;

import de.becker.household.application.port.in.households.ListHouseholdUserCommand;
import de.becker.household.application.port.in.households.ListHouseholdUserUseCase;
import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class ListHouseholdUserService implements ListHouseholdUserUseCase {

  private HouseholdRepository householdRepository;

  public ListHouseholdUserService(HouseholdRepository householdRepository) {
    this.householdRepository = householdRepository;
  }

  @Override
  public List<User> execute(ListHouseholdUserCommand command) {

    final Household household = householdRepository.findById(command.householdId());
    return household.getUsers();
  }

}
