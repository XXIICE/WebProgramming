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
import jpa.model.Productorder;
import jpa.model.Lineitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Cart;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class CartJpaController implements Serializable {

    public CartJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cart cart) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (cart.getLineitemList() == null) {
            cart.setLineitemList(new ArrayList<Lineitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productorder productorder = cart.getProductorder();
            if (productorder != null) {
                productorder = em.getReference(productorder.getClass(), productorder.getOrderid());
                cart.setProductorder(productorder);
            }
            List<Lineitem> attachedLineitemList = new ArrayList<Lineitem>();
            for (Lineitem lineitemListLineitemToAttach : cart.getLineitemList()) {
                lineitemListLineitemToAttach = em.getReference(lineitemListLineitemToAttach.getClass(), lineitemListLineitemToAttach.getLineitemid());
                attachedLineitemList.add(lineitemListLineitemToAttach);
            }
            cart.setLineitemList(attachedLineitemList);
            em.persist(cart);
            if (productorder != null) {
                Cart oldCartCartidOfProductorder = productorder.getCartCartid();
                if (oldCartCartidOfProductorder != null) {
                    oldCartCartidOfProductorder.setProductorder(null);
                    oldCartCartidOfProductorder = em.merge(oldCartCartidOfProductorder);
                }
                productorder.setCartCartid(cart);
                productorder = em.merge(productorder);
            }
            for (Lineitem lineitemListLineitem : cart.getLineitemList()) {
                Cart oldCartCartidOfLineitemListLineitem = lineitemListLineitem.getCartCartid();
                lineitemListLineitem.setCartCartid(cart);
                lineitemListLineitem = em.merge(lineitemListLineitem);
                if (oldCartCartidOfLineitemListLineitem != null) {
                    oldCartCartidOfLineitemListLineitem.getLineitemList().remove(lineitemListLineitem);
                    oldCartCartidOfLineitemListLineitem = em.merge(oldCartCartidOfLineitemListLineitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCart(cart.getCartid()) != null) {
                throw new PreexistingEntityException("Cart " + cart + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cart cart) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart persistentCart = em.find(Cart.class, cart.getCartid());
            Productorder productorderOld = persistentCart.getProductorder();
            Productorder productorderNew = cart.getProductorder();
            List<Lineitem> lineitemListOld = persistentCart.getLineitemList();
            List<Lineitem> lineitemListNew = cart.getLineitemList();
            List<String> illegalOrphanMessages = null;
            if (productorderOld != null && !productorderOld.equals(productorderNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Productorder " + productorderOld + " since its cartCartid field is not nullable.");
            }
            for (Lineitem lineitemListOldLineitem : lineitemListOld) {
                if (!lineitemListNew.contains(lineitemListOldLineitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lineitem " + lineitemListOldLineitem + " since its cartCartid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productorderNew != null) {
                productorderNew = em.getReference(productorderNew.getClass(), productorderNew.getOrderid());
                cart.setProductorder(productorderNew);
            }
            List<Lineitem> attachedLineitemListNew = new ArrayList<Lineitem>();
            for (Lineitem lineitemListNewLineitemToAttach : lineitemListNew) {
                lineitemListNewLineitemToAttach = em.getReference(lineitemListNewLineitemToAttach.getClass(), lineitemListNewLineitemToAttach.getLineitemid());
                attachedLineitemListNew.add(lineitemListNewLineitemToAttach);
            }
            lineitemListNew = attachedLineitemListNew;
            cart.setLineitemList(lineitemListNew);
            cart = em.merge(cart);
            if (productorderNew != null && !productorderNew.equals(productorderOld)) {
                Cart oldCartCartidOfProductorder = productorderNew.getCartCartid();
                if (oldCartCartidOfProductorder != null) {
                    oldCartCartidOfProductorder.setProductorder(null);
                    oldCartCartidOfProductorder = em.merge(oldCartCartidOfProductorder);
                }
                productorderNew.setCartCartid(cart);
                productorderNew = em.merge(productorderNew);
            }
            for (Lineitem lineitemListNewLineitem : lineitemListNew) {
                if (!lineitemListOld.contains(lineitemListNewLineitem)) {
                    Cart oldCartCartidOfLineitemListNewLineitem = lineitemListNewLineitem.getCartCartid();
                    lineitemListNewLineitem.setCartCartid(cart);
                    lineitemListNewLineitem = em.merge(lineitemListNewLineitem);
                    if (oldCartCartidOfLineitemListNewLineitem != null && !oldCartCartidOfLineitemListNewLineitem.equals(cart)) {
                        oldCartCartidOfLineitemListNewLineitem.getLineitemList().remove(lineitemListNewLineitem);
                        oldCartCartidOfLineitemListNewLineitem = em.merge(oldCartCartidOfLineitemListNewLineitem);
                    }
                }
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
                Integer id = cart.getCartid();
                if (findCart(id) == null) {
                    throw new NonexistentEntityException("The cart with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart cart;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getCartid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cart with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Productorder productorderOrphanCheck = cart.getProductorder();
            if (productorderOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cart (" + cart + ") cannot be destroyed since the Productorder " + productorderOrphanCheck + " in its productorder field has a non-nullable cartCartid field.");
            }
            List<Lineitem> lineitemListOrphanCheck = cart.getLineitemList();
            for (Lineitem lineitemListOrphanCheckLineitem : lineitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cart (" + cart + ") cannot be destroyed since the Lineitem " + lineitemListOrphanCheckLineitem + " in its lineitemList field has a non-nullable cartCartid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cart);
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

    public List<Cart> findCartEntities() {
        return findCartEntities(true, -1, -1);
    }

    public List<Cart> findCartEntities(int maxResults, int firstResult) {
        return findCartEntities(false, maxResults, firstResult);
    }

    private List<Cart> findCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cart.class));
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

    public Cart findCart(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cart.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cart> rt = cq.from(Cart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
