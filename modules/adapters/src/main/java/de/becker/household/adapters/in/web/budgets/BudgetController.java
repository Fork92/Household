package de.becker.household.adapters.in.web.budgets;

import de.becker.household.adapters.in.web.JWTSecured;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/budgets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BudgetController {

  @GET
  @JWTSecured
  public Response getBudgets() {
    return Response.ok("Hallo Welt!").build();
  }
}
