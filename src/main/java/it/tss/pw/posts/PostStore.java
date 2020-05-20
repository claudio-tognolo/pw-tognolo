/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.posts;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public List<Post> findByUsr(Long userId) {
        return em.createNamedQuery(Post.FIND_BY_USR, Post.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    public Optional<Post> findByIdAndUsr(Long id, Long userId) {
        try {
            Post result = em.createNamedQuery(Post.FIND_BY_ID_AND_USR, Post.class)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Post> search(Long id, String search) {
        return em.createNamedQuery(Post.SEARCH)
                .setParameter("user_id", id)
                .setParameter("search", "%" + search + "%")
                .getResultList();
    }
}