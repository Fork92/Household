package de.becker.household.application.usecase.budgets;

import java.time.LocalDate;
import java.util.Optional;

import de.becker.household.application.port.in.budgets.CreateBudgetCommand;
import de.becker.household.application.port.in.budgets.CreateBudgetUseCase;
import de.becker.household.application.port.out.budgets.BudgetRepository;
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

    return new Budget(0L, command.date(), command.budget(), user.getHousehold().getId());
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
    Household household = user.getHousehold();
    household.getBudgets().addAll(budgetRepository.findByHouseholdId(household.getId()));

    Optional<Budget> optionalBudget = household.getBudgets()
        .stream()
        .filter(budget -> budget.getDate()
            .equals(date))
        .findFirst();

    if (optionalBudget.isPresent()) {
      throw new BudgetExistException("Budget for Date already exists");
    }
  }
}
