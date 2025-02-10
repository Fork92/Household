package de.becker.household.application.port.in.households;

public record AddUserToHouseholdCommand(String username, long householdId) {

}
