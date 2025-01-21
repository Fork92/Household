package de.becker.household.domain.exceptions;

public class BudgetExistException extends RuntimeException {

    public BudgetExistException(String message) {
        super(message);
    }
}
