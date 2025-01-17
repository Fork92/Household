package de.becker.household.adapters.in.web.users;

import java.util.Collections;

import de.becker.household.adapters.out.encoder.JwtService;
import de.becker.household.application.port.in.user.LoginCommand;
import de.becker.household.application.port.in.user.LoginUseCase;
import de.becker.household.application.port.in.user.RegisterCommand;
import de.becker.household.application.port.in.user.RegisterUseCase;
import de.becker.household.domain.exceptions.AuthenticationException;
import de.becker.household.domain.exceptions.RegistrationException;
import de.becker.household.domain.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private RegisterUseCase registerUseCase;
    @Inject
    private LoginUseCase loginUseCase;
    @Inject
    private JwtService jwtService;

    @POST
    @Path("/register")
    public Response registerUser(UserJson body) {
        RegisterCommand command = new RegisterCommand(body.username(), body.password());
        try {
            User user = registerUseCase.execute(command);

            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .build();
            }

            String token = jwtService.generateToken(Collections.emptyMap(), user.getUsername());

            return Response.ok(new TokenJson(token))
                           .build();
        } catch (RegistrationException re) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .build();
        }
    }

    @POST
    @Path("/login")
    public Response loginUser(UserJson body) {
        LoginCommand command = new LoginCommand(body.username(), body.password());
        try {
            User user = loginUseCase.execute(command);

            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .build();
            }

            String token = jwtService.generateToken(Collections.emptyMap(), user.getUsername());

            return Response.ok(new TokenJson(token))
                           .build();
        } catch (AuthenticationException ae) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .build();
        }
    }
}
