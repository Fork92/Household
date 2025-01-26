package de.becker.household.infrastructure.config;

import de.becker.household.application.port.in.households.CreateHouseholdUseCase;
import de.becker.household.application.port.in.user.LoginUseCase;
import de.becker.household.application.port.in.user.RegisterUseCase;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.application.usecase.households.CreateHouseholdService;
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
  @Inject
  private HouseholdRepository householdRepository;

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

  @Produces
  @ApplicationScoped
  public CreateHouseholdUseCase createHouseholdUseCase() {
    return new CreateHouseholdService(householdRepository, userRepository);
  }

}
