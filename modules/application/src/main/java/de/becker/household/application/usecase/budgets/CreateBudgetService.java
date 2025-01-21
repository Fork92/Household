package de.becker.household.application.usecase.budgets;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

import de.becker.household.application.port.in.budgets.CreateBudgetCommand;
import de.becker.household.application.port.in.budgets.CreateBudgetUseCase;
import de.becker.household.application.port.out.budgets.BudgetRepository;
import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.exceptions.AuthenticationException;
import de.becker.household.domain.exceptions.BudgetExistException;
import de.becker.household.domain.model.Budget;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

public class CreateBudgetService implements CreateBudgetUseCase {

  private UserRepository userRepository;
  private BudgetRepository budgetRepository;

  public CreateBudgetService(UserRepository userRepository, BudgetRepository budgetRepository) {
    this.userRepository = userRepository;
    this.budgetRepository = budgetRepository;
  }

  @Override
  public Budget execute(CreateBudgetCommand command) {
    User user = checkIfUserExist(command.username());
    checkBudgetDate(user, command.date());

    return new Budget(0L, command.date(), command.budget(), user.household().id());
  }

  private User checkIfUserExist(final String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    }
    throw new AuthenticationException("User not found");
  }

  private void checkBudgetDate(final User user,
      final LocalDate date) {
    Household household = user.household();
    household.budgets().addAll(budgetRepository.findByHouseholdId(household.id()));

    Optional<Budget> optionalBudget = household.budgets()
        .stream()
        .filter(budget -> budget.getDate()
            .equals(date))
        .findFirst();

    if (optionalBudget.isPresent()) {
      throw new BudgetExistException("Budget for Date already exists");
    }
  }
}
