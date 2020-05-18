/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.posts;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author claudio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PostStore {

    @PersistenceContext(name = "pw")
    private EntityManager em;

    public List<Post> all() {
        return em.createNamedQuery(Post.FIND_ALL, Post.class)
                .getResultList();
    }

    public Post find(Long id) {
        return em.find(Post.class, id);
    }

    public Post create(Post p) {
        return em.merge(p);
    }

    public Post update(Post p) {
        return em.merge(p);
    }

    public void delete(Long id) {
        em.remove(em.find(Post.class, id));
    }

    public List<Post> findByUsr(Long id) {
        return em.createNamedQuery(Post.FIND_BY_USR, Post.class)
                .setParameter("user_id", id)
                .getResultList();
    }

    public List<Post> search(String search) {
        return em.createNamedQuery(Post.SEARCH)
                .setParameter("search", "%" + search + "%")
                .getResultList();
    }
}