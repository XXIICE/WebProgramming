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
import jpa.model.Orderitem;
import jpa.model.Product;
import jpa.model.Productorder;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class OrderitemJpaController implements Serializable {

    public OrderitemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderitem orderitem) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product productProductid = orderitem.getProductProductid();
            if (productProductid != null) {
                productProductid = em.getReference(productProductid.getClass(), productProductid.getProductid());
                orderitem.setProductProductid(productProductid);
            }
            Productorder productorderOrderid = orderitem.getProductorderOrderid();
            if (productorderOrderid != null) {
                productorderOrderid = em.getReference(productorderOrderid.getClass(), productorderOrderid.getOrderid());
                orderitem.setProductorderOrderid(productorderOrderid);
            }
            em.persist(orderitem);
            if (productProductid != null) {
                productProductid.getOrderitemList().add(orderitem);
                productProductid = em.merge(productProductid);
            }
            if (productorderOrderid != null) {
                productorderOrderid.getOrderitemList().add(orderitem);
                productorderOrderid = em.merge(productorderOrderid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOrderitem(orderitem.getOrderitemid()) != null) {
                throw new PreexistingEntityException("Orderitem " + orderitem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderitem orderitem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orderitem persistentOrderitem = em.find(Orderitem.class, orderitem.getOrderitemid());
            Product productProductidOld = persistentOrderitem.getProductProductid();
            Product productProductidNew = orderitem.getProductProductid();
            Productorder productorderOrderidOld = persistentOrderitem.getProductorderOrderid();
            Productorder productorderOrderidNew = orderitem.getProductorderOrderid();
            if (productProductidNew != null) {
                productProductidNew = em.getReference(productProductidNew.getClass(), productProductidNew.getProductid());
                orderitem.setProductProductid(productProductidNew);
            }
            if (productorderOrderidNew != null) {
                productorderOrderidNew = em.getReference(productorderOrderidNew.getClass(), productorderOrderidNew.getOrderid());
                orderitem.setProductorderOrderid(productorderOrderidNew);
            }
            orderitem = em.merge(orderitem);
            if (productProductidOld != null && !productProductidOld.equals(productProductidNew)) {
                productProductidOld.getOrderitemList().remove(orderitem);
                productProductidOld = em.merge(productProductidOld);
            }
            if (productProductidNew != null && !productProductidNew.equals(productProductidOld)) {
                productProductidNew.getOrderitemList().add(orderitem);
                productProductidNew = em.merge(productProductidNew);
            }
            if (productorderOrderidOld != null && !productorderOrderidOld.equals(productorderOrderidNew)) {
                productorderOrderidOld.getOrderitemList().remove(orderitem);
                productorderOrderidOld = em.merge(productorderOrderidOld);
            }
            if (productorderOrderidNew != null && !productorderOrderidNew.equals(productorderOrderidOld)) {
                productorderOrderidNew.getOrderitemList().add(orderitem);
                productorderOrderidNew = em.merge(productorderOrderidNew);
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
                Integer id = orderitem.getOrderitemid();
                if (findOrderitem(id) == null) {
                    throw new NonexistentEntityException("The orderitem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orderitem orderitem;
            try {
                orderitem = em.getReference(Orderitem.class, id);
                orderitem.getOrderitemid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderitem with id " + id + " no longer exists.", enfe);
            }
            Product productProductid = orderitem.getProductProductid();
            if (productProductid != null) {
                productProductid.getOrderitemList().remove(orderitem);
                productProductid = em.merge(productProductid);
            }
            Productorder productorderOrderid = orderitem.getProductorderOrderid();
            if (productorderOrderid != null) {
                productorderOrderid.getOrderitemList().remove(orderitem);
                productorderOrderid = em.merge(productorderOrderid);
            }
            em.remove(orderitem);
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

    public List<Orderitem> findOrderitemEntities() {
        return findOrderitemEntities(true, -1, -1);
    }

    public List<Orderitem> findOrderitemEntities(int maxResults, int firstResult) {
        return findOrderitemEntities(false, maxResults, firstResult);
    }

    private List<Orderitem> findOrderitemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderitem.class));
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

    public Orderitem findOrderitem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderitem.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderitemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderitem> rt = cq.from(Orderitem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
