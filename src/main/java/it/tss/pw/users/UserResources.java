/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package it.tss.pw.users;

import java.util.Collection;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
@Path("/users")
public class UserResources {

    @Inject
    UserStore store;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> all(@QueryParam("search") String search) {
        return search == null ? store.all() : store.search(search);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") Long id) {
        User found = store.find(id);
        if (found == null) {
            throw new NotFoundException();
        }
        return found;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User update(@PathParam("id") Long id, User u) {
        if (u.getId() == null || !u.getId().equals(id)) {
            throw new BadRequestException();
        }
        return store.update(u);
    }

    @PATCH
    @Path("{id}/firstname")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateFirstName(@PathParam("id") Long id, JsonObject json) {
        User found = store.find(id);
        found.setFirstName(json.getString("firstName"));
        return store.update(found);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        User found = store.find(id);
        if (found == null) {
            throw new NotFoundException();
        }
        store.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}