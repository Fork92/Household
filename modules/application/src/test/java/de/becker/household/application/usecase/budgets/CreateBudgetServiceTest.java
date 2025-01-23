package de.becker.household.application.usecase.budgets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.application.port.in.budgets.CreateBudgetCommand;
import de.becker.household.application.port.out.budgets.BudgetRepository;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.exceptions.BudgetExistException;
import de.becker.household.domain.model.Budget;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

@ExtendWith(MockitoExtension.class)
class CreateBudgetServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private BudgetRepository budgetRepository;
  @InjectMocks
  private CreateBudgetService createBudgetService;

  @Test
  public void testCreateNewBudget() {
    Household expectedHousehold = new Household(1L, Collections.emptyList());
    User expectedUser = new User(1L, "testUser", "secret", expectedHousehold);

    CreateBudgetCommand command = new CreateBudgetCommand(LocalDate.of(2025, 1, 1), BigDecimal.valueOf(1337),
        "testUser");
    when(userRepository.findByUsername(command.username())).thenReturn(Optional.of(expectedUser));
    when(budgetRepository.findByHouseholdId(expectedHousehold.id())).thenReturn(Collections.emptyList());

    Budget actual = createBudgetService.execute(command);
    assertThat(actual).isNotNull();
    assertThat(actual.getDate()).isEqualTo(command.date());
    assertThat(actual.getAmount()).isEqualTo(BigDecimal.valueOf(1337));
    assertThat(actual.getHouseholdId()).isEqualTo(expectedHousehold.id());
  }

  @Test
  public void testFailedCreateNewBudgetWhenBudgetForMonthAndYearExistsInHousehold() {
    Budget budget = new Budget(1L, LocalDate.of(2025, 1, 1), BigDecimal.valueOf(1337), 1L);
    List<Budget> budgets = new ArrayList<>();
    budgets.add(budget);
    Household household = new Household(1L, new ArrayList<>());
    User user = new User(1L, "testUser", "secret", household);

    CreateBudgetCommand command = new CreateBudgetCommand(LocalDate.of(2025, 1, 1), BigDecimal.valueOf(1337),
        "testUser");
    when(userRepository.findByUsername(command.username())).thenReturn(Optional.of(user));
    when(budgetRepository.findByHouseholdId(household.id())).thenReturn(budgets);

    assertThatThrownBy(() -> createBudgetService.execute(command)).isInstanceOf(BudgetExistException.class)
        .hasMessage("Budget for Date already exists");

  }

}
