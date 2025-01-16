package de.becker.household.application.usecase.user;

import de.becker.household.application.port.in.user.RegisterCommand;
import de.becker.household.application.port.in.user.RegisterUseCase;
import de.becker.household.application.port.out.UserPasswordEncoder;
import de.becker.household.application.port.out.UserRepository;
import de.becker.household.domain.exceptions.RegistrationException;
import de.becker.household.domain.model.User;

public class RegisterService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;

    RegisterService(UserRepository userRepository,
                    UserPasswordEncoder userPasswordEncoder) {
        this.userRepository = userRepository;
        this.userPasswordEncoder = userPasswordEncoder;
    }

    @Override
    public User execute(RegisterCommand command) {
        checkIfUserAlreadyExists(command.username());
        String encodedPassword = encodePassword(command.password());
        return saveUser(command.username(), encodedPassword);
    }

    private String encodePassword(final String password) {
        return this.userPasswordEncoder.encode(password);
    }

    private void checkIfUserAlreadyExists(final String username) {
        this.userRepository.findByUsername(username)
                           .ifPresent(_ -> {throw new RegistrationException("Username already exists");});
    }

    private User saveUser(final String username, final String encodedPassword) {
        User toSave = new User(0, username, encodedPassword);
        return this.userRepository.save(toSave);
    }

}
