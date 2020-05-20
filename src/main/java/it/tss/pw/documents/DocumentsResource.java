/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.documents;

/**
 *
 * @author claudio
 */
import it.tss.pw.posts.Post;
import it.tss.pw.posts.PostStore;
import it.tss.pw.users.User;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;


@RolesAllowed("users")
public class DocumentsResource {

    @Context
    ResourceContext resource;

    @Inject
    DocumentStore store;

    @Inject
    PostStore postStore;

    private Long userId;
    private Long postId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> all() {
        return store.findByUserAndPost(userId, postId);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@MultipartForm DocumentUploadForm form) {
        Post post = postStore.findByIdAndUsr(postId, userId).orElseThrow(() -> new NotFoundException());
        Document tosave = new Document();
        tosave.setTitle(form.getFileName());
        tosave.setFile(form.getFileName());
        tosave.setType(Document.Type.FILE);
        tosave.setMediaType(form.getMediaType());
        tosave.setPost(post);
        Document saved = store.save(tosave, new ByteArrayInputStream(form.getFileData()));
        return Response.status(200)
                .entity(Json.createObjectBuilder().add("id", saved.getId()).build()).build();

    }

    @GET
    @Path("{id}/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("id") Long id,
            @QueryParam("usr") String usr) {

        Document doc = store.find(id);
        Response.ResponseBuilder response = Response.ok(store.getFile(doc.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + doc.getFile() + "\"");
        response.header("Content-Type", doc.getMediaType());
        return response.build();
    }


    /*
    getter/setter
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}
