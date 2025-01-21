package de.becker.household.application.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.application.port.in.user.LoginCommand;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.UserRepository;
import de.becker.household.domain.exceptions.AuthenticationException;
import de.becker.household.domain.model.User;

@ExtendWith(MockitoExtension.class)
class LoginServiceSpec {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserPasswordEncoder userPasswordEncoder;

  private LoginService loginService;

  @Test
  public void testLoginUser() {
    User expectedUser = new User(1L, "testUser", "secret", null);
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(expectedUser));
    when(userPasswordEncoder.matches("secretPassword", "secret")).thenReturn(true);

    loginService = new LoginService(userRepository, userPasswordEncoder);
    LoginCommand command = new LoginCommand("testUser", "secretPassword");
    User user = loginService.execute(command);

    assertThat(user).isEqualTo(expectedUser);
  }

  @Test
  public void testLoginUserShouldFailWithInvalidUsername() {
    User expectedUser = new User(1L, "testUser", "secret", null);
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(expectedUser));
    when(userPasswordEncoder.matches("secretPassword", "secret")).thenReturn(false);

    loginService = new LoginService(userRepository, userPasswordEncoder);
    LoginCommand command = new LoginCommand("testUser", "secretPassword");

    assertThatThrownBy(() -> loginService.execute(command)).isInstanceOf(AuthenticationException.class)
        .hasMessage("Invalid username or password");
  }

  @Test
  public void testLoginUserShouldFailWithInvalidPassword() {
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

    loginService = new LoginService(userRepository, userPasswordEncoder);
    LoginCommand command = new LoginCommand("testUser", "secretPassword");

    assertThatThrownBy(() -> loginService.execute(command)).isInstanceOf(AuthenticationException.class)
        .hasMessage("Invalid username or password");
  }
}
