package de.becker.household.application.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.application.port.in.user.LoginCommand;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.exceptions.AuthenticationException;
import de.becker.household.domain.model.User;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserPasswordEncoder userPasswordEncoder;
  @InjectMocks
  private LoginService loginService;

  @Test
  public void testLoginUser() {
    User expectedUser = new User(1L, "testUser", "secret", null);
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(expectedUser));
    when(userPasswordEncoder.matches("secretPassword", "secret")).thenReturn(true);

    LoginCommand command = new LoginCommand("testUser", "secretPassword");
    User user = loginService.execute(command);

    assertThat(user).isEqualTo(expectedUser);
  }

  @Test
  public void testLoginUserShouldFailWithInvalidUsername() {
    User expectedUser = new User(1L, "testUser", "secret", null);
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(expectedUser));
    when(userPasswordEncoder.matches("secretPassword", "secret")).thenReturn(false);

    LoginCommand command = new LoginCommand("testUser", "secretPassword");

    assertThatThrownBy(() -> loginService.execute(command)).isInstanceOf(AuthenticationException.class)
        .hasMessage("Invalid username or password");
  }

  @Test
  public void testLoginUserShouldFailWithInvalidPassword() {
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

    LoginCommand command = new LoginCommand("testUser", "secretPassword");

    assertThatThrownBy(() -> loginService.execute(command)).isInstanceOf(AuthenticationException.class)
        .hasMessage("Invalid username or password");
  }
}
