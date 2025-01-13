package de.becker.household.domain.model;

import java.util.Set;

public class Household {
  private long id;
  private Set<Budget> budgets;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Set<Budget> getBudgets() {
    return budgets;
  }

  public void setBudgets(Set<Budget> budgets) {
    this.budgets = budgets;
  }

}
