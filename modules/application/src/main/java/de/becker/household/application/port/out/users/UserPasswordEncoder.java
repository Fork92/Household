package de.becker.household.application.port.out.users;

public interface UserPasswordEncoder {
  String encode(CharSequence rawPassword);

  boolean matches(CharSequence rawPassword, String encodedPassword);
}
