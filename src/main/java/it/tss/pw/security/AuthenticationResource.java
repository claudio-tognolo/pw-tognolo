/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.security;

/**
 *
 * @author claudio
 */
import it.tss.pw.users.User;
import it.tss.pw.users.UserStore;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/authentication")
public class AuthenticationResource {

    @Inject
    UserStore store;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Credential credential) {
        Optional<User> user = store.search(credential);
        if (user.isPresent()) {
            return Response.status(Response.Status.OK)
                    .header("token", "ok")
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("token", "ko")
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("usr") String usr, @FormParam("pwd") String pwd) {
        Optional<User> user = store.search(new Credential(usr, pwd));
        if (user.isPresent()) {
            return Response.status(Response.Status.OK)
                    .header("token", "ok")
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("token", "ko")
                .build();
    }
}
