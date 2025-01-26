package de.becker.household.domain.model;

public class User {
  private long id;
  private String username;
  private String passwordHash;
  private Household household;

  public User(long id, String username, String passwordHash, Household household) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.household = household;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public Household getHousehold() {
    return household;
  }

  public void setHousehold(Household household) {
    this.household = household;
  }

}
