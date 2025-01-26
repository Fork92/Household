package de.becker.household.adapters.in.web.households;

import de.becker.household.adapters.in.web.JWTSecured;
import de.becker.household.adapters.out.encoder.JwtService;
import de.becker.household.application.port.in.households.CreateHouseholdCommand;
import de.becker.household.application.port.in.households.CreateHouseholdUseCase;
import de.becker.household.domain.model.Household;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/households")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HouseholdController {

  @Inject
  private JwtService jwtService;
  @Inject
  private CreateHouseholdUseCase createHouseholdUseCase;

  @Context
  private HttpHeaders headers;

  @POST
  @JWTSecured
  public Response createNewHousehold(CreateHouseholdJson body) {
    final String token = headers.getHeaderString("Authorization");
    final String username = jwtService.getSubject(token.substring("Bearer".length()).trim());

    final CreateHouseholdCommand command = new CreateHouseholdCommand(body.householdName(), username);
    final Household household = this.createHouseholdUseCase.execute(command);
    return Response.ok(household).build();
  }

  @GET
  @JWTSecured
  public Response getUserByHousehold() {
    return Response.ok().build();
  }

  @PUT
  @JWTSecured
  public Response addUser() {
    return Response.ok().build();
  }

  @DELETE
  @JWTSecured
  public Response removeUser() {
    return Response.ok().build();
  }

  @DELETE
  @JWTSecured
  public Response deleteHousehold() {
    return Response.ok().build();
  }
}
