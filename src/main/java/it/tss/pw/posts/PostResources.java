/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.posts;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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
 * @author claudio
 */
@Path("/posts")
public class PostResources {

    @Inject
    PostStore store;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> all(@QueryParam("search") String search) {
        return search == null ? store.all() : store.search(search);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post find(@PathParam("id") Long id) {
        Post found = store.find(id);
        if (found == null) {
            throw new NotFoundException();
        }
        return found;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Post p) {
        Post saved = store.create(p);
        return Response
                .status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Post update(@PathParam("id") Long id, Post p) {
        if (p.getId() == null || !p.getId().equals(id)) {
            throw new BadRequestException();
        }
        return store.update(p);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Post found = store.find(id);
        if (found == null) {
            throw new NotFoundException();
        }
        store.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
