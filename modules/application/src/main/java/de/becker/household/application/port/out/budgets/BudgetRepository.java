package de.becker.household.application.port.out.budgets;

import java.util.List;

import de.becker.household.domain.model.Budget;

public interface BudgetRepository {
    List<Budget> findByHouseholdId(final long householdId);
}
