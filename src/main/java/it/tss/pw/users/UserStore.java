/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.users;

import it.tss.pw.security.Credential;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
public class UserStore {

    @PersistenceContext(name = "pw")
    EntityManager em;

    @PostConstruct
    public void init() {

    }

    public Collection<User> all() {
        return em.createNamedQuery(User.FIND_ALL)
                .getResultList();
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public User create(User u) {
        if (findByUsr(u.getUsr()).isPresent()) {
            throw new UserAlreadyExistException(u.getUsr());
        }
        return em.merge(u);
    }

    public User update(User u) {
        return em.merge(u);
    }

    public void delete(Long id) {
        em.remove(em.find(User.class, id));
    }

    public Optional<User> findByUsr(String usr) {
        return em.createNamedQuery(User.FIND_BY_USR, User.class)
                .setParameter("usr", usr)
                .getResultStream()
                .findFirst();
    }

    public Collection<User> search(String search) {
        return em.createNamedQuery(User.SEARCH)
                .setParameter("fname", "%" + search + "%")
                .setParameter("lname", "%" + search + "%")
                .setParameter("usr", "%" + search + "%")
                .getResultList();
    }

    public Optional<User> search(Credential credential) {
        try {
            User found = em.createNamedQuery(User.FIND_BY_USR_PWD, User.class)
                    .setParameter("usr", credential.getUsr())
                    .setParameter("pwd", credential.getPwd())
                    .getSingleResult();
            return Optional.of(found);
        } catch (Exception ex) {
            Logger.getLogger(UserStore.class.getName()).log(Level.SEVERE, null, ex);
            return Optional.empty();
        }
    }
}