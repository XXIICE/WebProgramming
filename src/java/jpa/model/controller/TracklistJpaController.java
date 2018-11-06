/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Tracklist;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class TracklistJpaController implements Serializable {

    public TracklistJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tracklist tracklist) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Product productOrphanCheck = tracklist.getProduct();
        if (productOrphanCheck != null) {
            Tracklist oldTracklistOfProduct = productOrphanCheck.getTracklist();
            if (oldTracklistOfProduct != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Product " + productOrphanCheck + " already has an item of type Tracklist whose product column cannot be null. Please make another selection for the product field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product product = tracklist.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductid());
                tracklist.setProduct(product);
            }
            em.persist(tracklist);
            if (product != null) {
                product.setTracklist(tracklist);
                product = em.merge(product);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTracklist(tracklist.getProductProductid()) != null) {
                throw new PreexistingEntityException("Tracklist " + tracklist + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tracklist tracklist) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tracklist persistentTracklist = em.find(Tracklist.class, tracklist.getProductProductid());
            Product productOld = persistentTracklist.getProduct();
            Product productNew = tracklist.getProduct();
            List<String> illegalOrphanMessages = null;
            if (productNew != null && !productNew.equals(productOld)) {
                Tracklist oldTracklistOfProduct = productNew.getTracklist();
                if (oldTracklistOfProduct != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Product " + productNew + " already has an item of type Tracklist whose product column cannot be null. Please make another selection for the product field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductid());
                tracklist.setProduct(productNew);
            }
            tracklist = em.merge(tracklist);
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.setTracklist(null);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.setTracklist(tracklist);
                productNew = em.merge(productNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tracklist.getProductProductid();
                if (findTracklist(id) == null) {
                    throw new NonexistentEntityException("The tracklist with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tracklist tracklist;
            try {
                tracklist = em.getReference(Tracklist.class, id);
                tracklist.getProductProductid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tracklist with id " + id + " no longer exists.", enfe);
            }
            Product product = tracklist.getProduct();
            if (product != null) {
                product.setTracklist(null);
                product = em.merge(product);
            }
            em.remove(tracklist);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tracklist> findTracklistEntities() {
        return findTracklistEntities(true, -1, -1);
    }

    public List<Tracklist> findTracklistEntities(int maxResults, int firstResult) {
        return findTracklistEntities(false, maxResults, firstResult);
    }

    private List<Tracklist> findTracklistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tracklist.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tracklist findTracklist(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tracklist.class, id);
        } finally {
            em.close();
        }
    }

    public int getTracklistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tracklist> rt = cq.from(Tracklist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
