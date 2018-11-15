/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.model.Customer;
import jpa.model.Favorite;
import jpa.model.Product;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class FavoriteJpaController implements Serializable {

    public FavoriteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Favorite favorite) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customerUsername = favorite.getCustomerUsername();
            if (customerUsername != null) {
                customerUsername = em.getReference(customerUsername.getClass(), customerUsername.getUsername());
                favorite.setCustomerUsername(customerUsername);
            }
            Product productProductid = favorite.getProductProductid();
            if (productProductid != null) {
                productProductid = em.getReference(productProductid.getClass(), productProductid.getProductid());
                favorite.setProductProductid(productProductid);
            }
            em.persist(favorite);
            if (customerUsername != null) {
                customerUsername.getFavoriteList().add(favorite);
                customerUsername = em.merge(customerUsername);
            }
            if (productProductid != null) {
                productProductid.getFavoriteList().add(favorite);
                productProductid = em.merge(productProductid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFavorite(favorite.getFavoriteid()) != null) {
                throw new PreexistingEntityException("Favorite " + favorite + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Favorite favorite) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Favorite persistentFavorite = em.find(Favorite.class, favorite.getFavoriteid());
            Customer customerUsernameOld = persistentFavorite.getCustomerUsername();
            Customer customerUsernameNew = favorite.getCustomerUsername();
            Product productProductidOld = persistentFavorite.getProductProductid();
            Product productProductidNew = favorite.getProductProductid();
            if (customerUsernameNew != null) {
                customerUsernameNew = em.getReference(customerUsernameNew.getClass(), customerUsernameNew.getUsername());
                favorite.setCustomerUsername(customerUsernameNew);
            }
            if (productProductidNew != null) {
                productProductidNew = em.getReference(productProductidNew.getClass(), productProductidNew.getProductid());
                favorite.setProductProductid(productProductidNew);
            }
            favorite = em.merge(favorite);
            if (customerUsernameOld != null && !customerUsernameOld.equals(customerUsernameNew)) {
                customerUsernameOld.getFavoriteList().remove(favorite);
                customerUsernameOld = em.merge(customerUsernameOld);
            }
            if (customerUsernameNew != null && !customerUsernameNew.equals(customerUsernameOld)) {
                customerUsernameNew.getFavoriteList().add(favorite);
                customerUsernameNew = em.merge(customerUsernameNew);
            }
            if (productProductidOld != null && !productProductidOld.equals(productProductidNew)) {
                productProductidOld.getFavoriteList().remove(favorite);
                productProductidOld = em.merge(productProductidOld);
            }
            if (productProductidNew != null && !productProductidNew.equals(productProductidOld)) {
                productProductidNew.getFavoriteList().add(favorite);
                productProductidNew = em.merge(productProductidNew);
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
                String id = favorite.getFavoriteid();
                if (findFavorite(id) == null) {
                    throw new NonexistentEntityException("The favorite with id " + id + " no longer exists.");
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
            Favorite favorite;
            try {
                favorite = em.getReference(Favorite.class, id);
                favorite.getFavoriteid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The favorite with id " + id + " no longer exists.", enfe);
            }
            Customer customerUsername = favorite.getCustomerUsername();
            if (customerUsername != null) {
                customerUsername.getFavoriteList().remove(favorite);
                customerUsername = em.merge(customerUsername);
            }
            Product productProductid = favorite.getProductProductid();
            if (productProductid != null) {
                productProductid.getFavoriteList().remove(favorite);
                productProductid = em.merge(productProductid);
            }
            em.remove(favorite);
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

    public List<Favorite> findFavoriteEntities() {
        return findFavoriteEntities(true, -1, -1);
    }

    public List<Favorite> findFavoriteEntities(int maxResults, int firstResult) {
        return findFavoriteEntities(false, maxResults, firstResult);
    }

    private List<Favorite> findFavoriteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Favorite.class));
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

    public Favorite findFavorite(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Favorite.class, id);
        } finally {
            em.close();
        }
    }

    public int getFavoriteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Favorite> rt = cq.from(Favorite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
