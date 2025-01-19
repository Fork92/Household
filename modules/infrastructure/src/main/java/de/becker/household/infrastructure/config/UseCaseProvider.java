package de.becker.household.infrastructure.config;

import de.becker.household.application.port.in.user.LoginUseCase;
import de.becker.household.application.port.in.user.RegisterUseCase;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.UserRepository;
import de.becker.household.application.usecase.user.LoginService;
import de.becker.household.application.usecase.user.RegisterService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class UseCaseProvider {

  @Inject
  private UserRepository userRepository;
  @Inject
  private UserPasswordEncoder userPasswordEncoder;

  @Produces
  @ApplicationScoped
  public LoginUseCase loginUseCase() {
    return new LoginService(userRepository, userPasswordEncoder);
  }

  @Produces
  @ApplicationScoped
  public RegisterUseCase registerUseCase() {
    return new RegisterService(userRepository, userPasswordEncoder);
  }

}
