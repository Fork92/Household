package de.becker.household.application.port.in.budgets;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBudgetCommand(LocalDate date, BigDecimal budget, String username) {

}
