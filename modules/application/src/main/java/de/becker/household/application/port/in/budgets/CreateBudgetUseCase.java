package de.becker.household.application.port.in.budgets;

import de.becker.household.domain.model.Budget;

public interface CreateBudgetUseCase {

    Budget execute(final CreateBudgetCommand command);

}
