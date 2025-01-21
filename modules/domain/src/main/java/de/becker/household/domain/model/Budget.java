package de.becker.household.domain.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;
import java.math.BigDecimal;

public class Budget {
  private long id;
  private LocalDate date;
  private long householdId;
  private Set<Category> categories;
  private BigDecimal amount;

  public Budget(long id,
                LocalDate date,
                BigDecimal amount,
                long budgetId) {
    this.id = id;
    this.date = date;
    this.amount = amount;
    this.householdId = budgetId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public long getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(long householdId) {
    this.householdId = householdId;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}
