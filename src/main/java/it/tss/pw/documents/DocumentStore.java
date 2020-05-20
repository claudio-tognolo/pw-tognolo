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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author alfonso
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentStore {

    @Inject
    @ConfigProperty(name = "documents.folder")
    private String folder;

    @PersistenceContext(name = "pw")
    EntityManager em;

    @PostConstruct
    public void init() {

    }

    public List<Document> findByUserAndPost(Long userId, Long postId) {
        return em.createNamedQuery(Document.FIND_BY_USR_AND_POST, Document.class)
                .setParameter("userId", userId)
                .setParameter("postId", postId)
                .getResultList();
    }

    public Document find(Long id) {
        return em.find(Document.class, id);
    }

    public Document save(Document d, InputStream is) {
        Document saved = em.merge(d);
        try {
            Files.copy(is, documentPath(saved.getFile()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new EJBException("save document failed...");
        }
        return saved;
    }

    public void remove(Long id) {
        Document saved = find(id);
        try {
            Files.delete(documentPath(saved.getFile()));
        } catch (IOException ex) {
            throw new EJBException("delete document failed...");
        }
        em.remove(saved);
    }

    private Path documentPath(String file) {
        return Paths.get(folder + file);
    }

    public File getFile(String fileName) {
        return documentPath(fileName).toFile();
    }

}
