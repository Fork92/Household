package de.becker.household.domain.model;

import java.util.List;

public record Household(long id, List<Budget> budgets) {
}
