package de.becker.household.domain.model;

public record User(long id, String username, String passwordHash, Household household) {
}
