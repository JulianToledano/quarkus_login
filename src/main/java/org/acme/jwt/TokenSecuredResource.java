package org.acme.jwt;

import java.security.Principal;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.runtime.Application;

import org.jboss.logging.Logger;

/**
 * Version 2 of the TokenSecuredResource
 */
@Path("/login")
@RequestScoped
public class TokenSecuredResource {

    private static final Logger LOGGER = Logger.getLogger(TokenSecuredResource.class);

    @Inject
    JsonWebToken jwt;

    @GET()
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme());
        return helloReply;
    }

    @GET()
    @Path("roles-allowed") 
    @RolesAllowed({"Echoer", "Subscriber"}) 
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt.getClaimNames() != null;
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
        return helloReply;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) throws Exception{
        if (!user.username.equals("pepe")){
            LOGGER.info("User " + user.username + " found.");
            // Find in database
                // Create JWT
            GenerateToken.newJWT();
            return Response.ok().build();
        }
        LOGGER.info("User " + user.username + " not found.");
        return Response.status(401).build();
    }
}
