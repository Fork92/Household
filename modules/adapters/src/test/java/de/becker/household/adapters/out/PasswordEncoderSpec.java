package de.becker.household.adapters.out;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordEncoderSpec {
  private PasswordEncoder passwordEncoder;

  @Test
  public void testHashPassword() {
    passwordEncoder = new PasswordEncoder();
    String hash = passwordEncoder.encode("Test123");
    Assertions.assertThat(passwordEncoder.matches("Test123", hash)).isTrue();
  }
}
