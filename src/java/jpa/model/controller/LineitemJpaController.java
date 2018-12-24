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
import jpa.model.Cart;
import jpa.model.Lineitem;
import jpa.model.Product;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class LineitemJpaController implements Serializable {

    public LineitemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lineitem lineitem) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart cart = lineitem.getCart();
            if (cart != null) {
                cart = em.getReference(cart.getClass(), cart.getCartPK());
                lineitem.setCart(cart);
            }
            Product productProductid = lineitem.getProductProductid();
            if (productProductid != null) {
                productProductid = em.getReference(productProductid.getClass(), productProductid.getProductid());
                lineitem.setProductProductid(productProductid);
            }
            em.persist(lineitem);
            if (cart != null) {
                cart.getLineitemList().add(lineitem);
                cart = em.merge(cart);
            }
            if (productProductid != null) {
                productProductid.getLineitemList().add(lineitem);
                productProductid = em.merge(productProductid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLineitem(lineitem.getLineitemid()) != null) {
                throw new PreexistingEntityException("Lineitem " + lineitem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lineitem lineitem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Lineitem persistentLineitem = em.find(Lineitem.class, lineitem.getLineitemid());
            Cart cartOld = persistentLineitem.getCart();
            Cart cartNew = lineitem.getCart();
            Product productProductidOld = persistentLineitem.getProductProductid();
            Product productProductidNew = lineitem.getProductProductid();
            if (cartNew != null) {
                cartNew = em.getReference(cartNew.getClass(), cartNew.getCartPK());
                lineitem.setCart(cartNew);
            }
            if (productProductidNew != null) {
                productProductidNew = em.getReference(productProductidNew.getClass(), productProductidNew.getProductid());
                lineitem.setProductProductid(productProductidNew);
            }
            lineitem = em.merge(lineitem);
            if (cartOld != null && !cartOld.equals(cartNew)) {
                cartOld.getLineitemList().remove(lineitem);
                cartOld = em.merge(cartOld);
            }
            if (cartNew != null && !cartNew.equals(cartOld)) {
                cartNew.getLineitemList().add(lineitem);
                cartNew = em.merge(cartNew);
            }
            if (productProductidOld != null && !productProductidOld.equals(productProductidNew)) {
                productProductidOld.getLineitemList().remove(lineitem);
                productProductidOld = em.merge(productProductidOld);
            }
            if (productProductidNew != null && !productProductidNew.equals(productProductidOld)) {
                productProductidNew.getLineitemList().add(lineitem);
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
                Integer id = lineitem.getLineitemid();
                if (findLineitem(id) == null) {
                    throw new NonexistentEntityException("The lineitem with id " + id + " no longer exists.");
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
            Lineitem lineitem;
            try {
                lineitem = em.getReference(Lineitem.class, id);
                lineitem.getLineitemid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineitem with id " + id + " no longer exists.", enfe);
            }
            Cart cart = lineitem.getCart();
            if (cart != null) {
                cart.getLineitemList().remove(lineitem);
                cart = em.merge(cart);
            }
            Product productProductid = lineitem.getProductProductid();
            if (productProductid != null) {
                productProductid.getLineitemList().remove(lineitem);
                productProductid = em.merge(productProductid);
            }
            em.remove(lineitem);
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

    public List<Lineitem> findLineitemEntities() {
        return findLineitemEntities(true, -1, -1);
    }

    public List<Lineitem> findLineitemEntities(int maxResults, int firstResult) {
        return findLineitemEntities(false, maxResults, firstResult);
    }

    private List<Lineitem> findLineitemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lineitem.class));
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

    public Lineitem findLineitem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lineitem.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineitemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lineitem> rt = cq.from(Lineitem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
