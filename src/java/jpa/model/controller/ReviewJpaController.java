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
import jpa.model.Product;
import jpa.model.Review;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Yang
 */
public class ReviewJpaController implements Serializable {

    public ReviewJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Review review) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customerUsername = review.getCustomerUsername();
            if (customerUsername != null) {
                customerUsername = em.getReference(customerUsername.getClass(), customerUsername.getUsername());
                review.setCustomerUsername(customerUsername);
            }
            Product productProductid = review.getProductProductid();
            if (productProductid != null) {
                productProductid = em.getReference(productProductid.getClass(), productProductid.getProductid());
                review.setProductProductid(productProductid);
            }
            em.persist(review);
            if (customerUsername != null) {
                customerUsername.getReviewList().add(review);
                customerUsername = em.merge(customerUsername);
            }
            if (productProductid != null) {
                productProductid.getReviewList().add(review);
                productProductid = em.merge(productProductid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findReview(review.getReviewid()) != null) {
                throw new PreexistingEntityException("Review " + review + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Review review) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Review persistentReview = em.find(Review.class, review.getReviewid());
            Customer customerUsernameOld = persistentReview.getCustomerUsername();
            Customer customerUsernameNew = review.getCustomerUsername();
            Product productProductidOld = persistentReview.getProductProductid();
            Product productProductidNew = review.getProductProductid();
            if (customerUsernameNew != null) {
                customerUsernameNew = em.getReference(customerUsernameNew.getClass(), customerUsernameNew.getUsername());
                review.setCustomerUsername(customerUsernameNew);
            }
            if (productProductidNew != null) {
                productProductidNew = em.getReference(productProductidNew.getClass(), productProductidNew.getProductid());
                review.setProductProductid(productProductidNew);
            }
            review = em.merge(review);
            if (customerUsernameOld != null && !customerUsernameOld.equals(customerUsernameNew)) {
                customerUsernameOld.getReviewList().remove(review);
                customerUsernameOld = em.merge(customerUsernameOld);
            }
            if (customerUsernameNew != null && !customerUsernameNew.equals(customerUsernameOld)) {
                customerUsernameNew.getReviewList().add(review);
                customerUsernameNew = em.merge(customerUsernameNew);
            }
            if (productProductidOld != null && !productProductidOld.equals(productProductidNew)) {
                productProductidOld.getReviewList().remove(review);
                productProductidOld = em.merge(productProductidOld);
            }
            if (productProductidNew != null && !productProductidNew.equals(productProductidOld)) {
                productProductidNew.getReviewList().add(review);
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
                String id = review.getReviewid();
                if (findReview(id) == null) {
                    throw new NonexistentEntityException("The review with id " + id + " no longer exists.");
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
            Review review;
            try {
                review = em.getReference(Review.class, id);
                review.getReviewid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The review with id " + id + " no longer exists.", enfe);
            }
            Customer customerUsername = review.getCustomerUsername();
            if (customerUsername != null) {
                customerUsername.getReviewList().remove(review);
                customerUsername = em.merge(customerUsername);
            }
            Product productProductid = review.getProductProductid();
            if (productProductid != null) {
                productProductid.getReviewList().remove(review);
                productProductid = em.merge(productProductid);
            }
            em.remove(review);
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

    public List<Review> findReviewEntities() {
        return findReviewEntities(true, -1, -1);
    }

    public List<Review> findReviewEntities(int maxResults, int firstResult) {
        return findReviewEntities(false, maxResults, firstResult);
    }

    private List<Review> findReviewEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Review.class));
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

    public Review findReview(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Review.class, id);
        } finally {
            em.close();
        }
    }

    public int getReviewCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Review> rt = cq.from(Review.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
