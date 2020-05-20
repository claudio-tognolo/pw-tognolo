/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.users;

/**
 *
 * @author claudio
 */
import java.security.Principal;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;


@Path("/users")
@DenyAll
public class UsersResource {

    @Context
    ResourceContext resource;

    @Inject
    @Claim(standard = Claims.upn)
    private String upn;
    @Inject
    @Claim(standard = Claims.sub)
    private Long subjectId;

    @Inject
    private Principal principal;

    @Inject
    JsonWebToken jwt;

    @Inject
    UserStore store;

    @PostConstruct
    public void init() {
        logAuth();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("users")
    public Collection<User> all(@QueryParam("search") String search) {
        return search == null ? store.all() : store.search(search);
    }

    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("users")
    public UserResource find(@PathParam("id") Long id) {
        if (!id.equals(subjectId)) {
            throw new ForbiddenException();
        }
        UserResource sub = resource.getResource(UserResource.class);
        sub.setId(id);
        return sub;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response create(User u) {
        User saved = store.create(u);
        return Response
                .status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response create(
            @FormParam("firstName") String fname,
            @FormParam("lastName") String lname,
            @FormParam("usr") String usr,
            @FormParam("pwd") String pwd) {

        User user = new User(fname, lname, usr, pwd);
        User saved = store.create(user);
        return Response
                .status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

    private void logAuth() {
        System.out.println("************************** UPN ********************************");
        System.out.println("");
        System.out.println("username: " + upn);
        System.out.println("");
        System.out.println("************************** Principal ********************************");
        System.out.println("");
        System.out.println("principal: " + principal);
        System.out.println("");
        System.out.println("************************** JWT Token ********************************");
        System.out.println("");
        System.out.println("jwt: " + jwt);
        System.out.println("************************** JWT Token RAW ********************************");
        System.out.println("");
        System.out.println("jwt: " + jwt.getRawToken());
    }
}
