/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.posts;

//import it.tss.pw.documents.DocumentsResource;
import it.tss.pw.users.UserStore;
import it.tss.pw.users.User;
import it.tss.pw.documents.DocumentsResource;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author claudio
 */
@RolesAllowed("users")
public class PostResource {

    @Context
    ResourceContext resource;

    @Inject
    PostStore store;

    @Inject
    UserStore userStore;

    private Long userId;
    private Long id;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Post find() {
        return store.findByIdAndUsr(id, userId).orElseThrow(() -> new NotFoundException());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Post update(Post p) {
        if (p.getId() == null || !p.getId().equals(id) || !store.findByIdAndUsr(id, userId).isPresent()) {
            throw new BadRequestException();
        }
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        p.setOwner(user);
        return store.update(p);
    }

    @DELETE
    public Response delete() {
        Optional<Post> optional = store.findByIdAndUsr(id, userId);
        Post found = optional.orElseThrow(() -> new NotFoundException());
        store.delete(found.getId());
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("documents")
    public DocumentsResource documents() {
        DocumentsResource sub = resource.getResource(DocumentsResource.class);
        sub.setUserId(userId);
        sub.setPostId(id);
        return sub;
    }

    /*
    getter/setter
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}