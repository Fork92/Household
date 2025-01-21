package de.becker.household.adapters.out.encoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordEncoderTest {
  private PasswordEncoder passwordEncoder;

  @Test
  public void testHashPassword() {
    passwordEncoder = new PasswordEncoder();
    String hash = passwordEncoder.encode("Test123");
    assertThat(passwordEncoder.matches("Test123", hash)).isTrue();
  }
}
