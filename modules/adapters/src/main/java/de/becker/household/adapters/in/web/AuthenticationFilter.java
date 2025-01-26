package de.becker.household.adapters.in.web;

import java.io.IOException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.becker.household.adapters.out.encoder.JwtService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
@JWTSecured
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  @Inject
  private JwtService jwtService;
  private Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {

    String authHeader = requestContext.getHeaderString("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer")) {
      abortRequest(requestContext, "Authorization header missing");
      return;
    }

    String token = authHeader.substring("Bearer".length()).trim();

    LOG.info(jwtService.parseToken(token).getSubject());

    if (!jwtService.validateToken(token)) {
      abortRequest(requestContext, "Token invalid");
      return;
    }

    try {
      SecurityContext originalContext = requestContext.getSecurityContext();
      requestContext.setSecurityContext(new SecurityContext() {

        @Override
        public Principal getUserPrincipal() {
          return () -> jwtService.parseToken(token).getSubject();
        }

        @Override
        public boolean isUserInRole(String role) {
          return false;
        }

        @Override
        public boolean isSecure() {
          return originalContext.isSecure();
        }

        @Override
        public String getAuthenticationScheme() {
          return "Bearer";
        }
      });

    } catch (Exception ex) {
      abortRequest(requestContext, "Token Invalid");
    }
  }

  private void abortRequest(ContainerRequestContext requestContext, String message) {
    requestContext.abortWith(
        Response.status(Response.Status.UNAUTHORIZED)
            .entity("{\"error\": \"" + message + "\"}")
            .build());
  }

}
