package de.becker.household.domain.model;

import java.util.List;

public class Household {
  private long id;
  private String name;
  private List<Budget> budgets;
  private List<User> users;

  public Household(final long id, final String name, final List<Budget> budgets, final List<User> users) {
    this.id = id;
    this.name = name;
    this.budgets = budgets;
    this.users = users;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Budget> getBudgets() {
    return budgets;
  }

  public void setBudgets(List<Budget> budgets) {
    this.budgets = budgets;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

}
