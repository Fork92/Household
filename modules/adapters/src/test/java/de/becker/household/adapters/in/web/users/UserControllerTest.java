package de.becker.household.adapters.in.web.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.adapters.out.encoder.JwtService;
import de.becker.household.application.port.in.user.LoginCommand;
import de.becker.household.application.port.in.user.LoginUseCase;
import de.becker.household.application.port.in.user.RegisterCommand;
import de.becker.household.application.port.in.user.RegisterUseCase;
import de.becker.household.domain.exceptions.AuthenticationException;
import de.becker.household.domain.exceptions.RegistrationException;
import de.becker.household.domain.model.User;
import jakarta.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private RegisterUseCase registerUseCase;
  @Mock
  private LoginUseCase loginUseCase;
  @Mock
  private JwtService jwtService;
  @InjectMocks
  private UserController userController;

  @Test
  public void testRegisterUser() {
    RegisterCommand command = new RegisterCommand("username", "password");
    when(registerUseCase.execute(command)).thenReturn(new User(1L, "username", "secret", null));
    when(jwtService.generateToken(Collections.emptyMap(), "username")).thenReturn("token");

    Response response = userController.registerUser(new UserJson("username", "password"));

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.getEntity()).isInstanceOf(TokenJson.class);
  }

  @Test
  public void testRegisterUserFailed() {
    RegisterCommand command = new RegisterCommand("username", "password");
    when(registerUseCase.execute(command)).thenThrow(new RegistrationException("Username already exists"));

    Response response = userController.registerUser(new UserJson("username", "password"));

    assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    assertThat(response.getEntity()).isNull();
  }

  @Test
  public void testLoginUser() {
    LoginCommand command = new LoginCommand("username", "password");
    when(loginUseCase.execute(command)).thenReturn(new User(1L, "username", "secret", null));
    when(jwtService.generateToken(Collections.emptyMap(), "username")).thenReturn("token");

    Response response = userController.loginUser(new UserJson("username", "password"));

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.getEntity()).isInstanceOf(TokenJson.class);
  }

  @Test
  public void testLoginUserFailed() {
    LoginCommand command = new LoginCommand("username", "password");
    when(loginUseCase.execute(command)).thenThrow(new AuthenticationException("Invalid username or password"));

    Response response = userController.loginUser(new UserJson("username", "password"));

    assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    assertThat(response.getEntity()).isNull();
  }

}
