package de.becker.household.adapters.out.encoder;

import com.password4j.Argon2Function;
import com.password4j.Password;
import com.password4j.types.Argon2;

import de.becker.household.application.port.out.UserPasswordEncoder;

public class PasswordEncoder implements UserPasswordEncoder {

  private final Argon2Function ARGON2_FUNCTION = Argon2Function.getInstance(
      1024,
      3,
      1,
      32,
      Argon2.ID);

  @Override
  public String encode(CharSequence rawPassword) {
    return Password.hash(rawPassword)
        .addRandomSalt(32)
        .with(ARGON2_FUNCTION)
        .getResult();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return Password.check(rawPassword, encodedPassword).with(ARGON2_FUNCTION);
  }

}
