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
import it.tss.pw.AbstractEntity;
import it.tss.pw.posts.Post;
import it.tss.pw.posts.PostLinkAdapter;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQueries({
    @NamedQuery(name = Document.FIND_BY_USR_AND_POST, query = "select e from Document e where e.post.owner.id= :userId and e.post.id= :postId order by e.createdOn")
})

@Entity
@Table(name = "document")
public class Document extends AbstractEntity {

    public static final String FIND_BY_USR_AND_POST = "Document.findByUsrAndPost";

    public static enum Type {
        FILE, LINK
    }

    private String title;

    private String description;

    private String file;

    private String thumb;

    @JsonbTypeAdapter(DocumentTypeLinkAdapter.class)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "media_type")
    private String mediaType;

    @JsonbTypeAdapter(PostLinkAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return title;
    }
}
