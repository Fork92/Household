package de.becker.household.application.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.application.port.in.user.RegisterCommand;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.UserRepository;
import de.becker.household.domain.exceptions.RegistrationException;
import de.becker.household.domain.model.User;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceSpec {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserPasswordEncoder userPasswordEncoder;

    private RegisterService registerService;

    @Test
    public void testRegisterNewUser() {
        User expectedUser = new User(1L, "username", "secret");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        when(userPasswordEncoder.encode("password")).thenReturn("secret");
        registerService = new RegisterService(userRepository, userPasswordEncoder);

        RegisterCommand command = new RegisterCommand("username", "password");

        User actual = registerService.execute(command);

        assertThat(actual).isEqualTo(expectedUser);
    }

    @Test
    public void testRegisterExistingUser() {
        User expectedUser = new User(1L, "username", "secret");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(expectedUser));
        registerService = new RegisterService(userRepository, userPasswordEncoder);

        RegisterCommand command = new RegisterCommand("username", "password");
        assertThatThrownBy(() -> registerService.execute(command)).isInstanceOf(RegistrationException.class)
                                                                  .hasMessage("Username already exists");

    }

}
