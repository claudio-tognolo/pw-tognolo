/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.posts;

import it.tss.pw.AbstractEntity;
import it.tss.pw.users.User;
import it.tss.pw.users.UserLinkAdapter;
import java.time.LocalDate;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 *
 * @author claudio
 */

@NamedQueries({
    @NamedQuery(name = Post.FIND_ALL, query = "select e from Post e order by e.createdOn DESC"),
    @NamedQuery(name = Post.FIND_BY_USR, query = "select e from Post e where e.owner.id= :user_id order by e.createdOn DESC"),
    @NamedQuery(name = Post.FIND_BY_ID_AND_USR, query = "select e from Post e where e.id= :id and e.owner.id= :user_id"),
    @NamedQuery(name = Post.SEARCH, query = "select e from Post e where e.owner.id= :user_id and e.title like :search or e.body like :search order by e.createdOn DESC"),})

@Entity
@Table(name = "post")
public class Post extends AbstractEntity {

    public static final String FIND_ALL = "Post.findAll";
    public static final String FIND_BY_USR = "Post.findByUser";
    public static final String FIND_BY_ID_AND_USR = "Post.findByIdAndUser";
    public static final String SEARCH = "Post.search";

    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty
    @Column(name = "body", nullable = false)
    private String body;

    @JsonbTypeAdapter(UserLinkAdapter.class)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "end_date")
    @JsonbDateFormat("dd/MM/yyyy")
    private LocalDate endDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", body=" + body + ", owner=" + owner + ", endDate=" + endDate + '}';
    }

}