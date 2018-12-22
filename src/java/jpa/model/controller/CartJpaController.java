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
import jpa.model.Customer;
import jpa.model.Lineitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Cart;
import jpa.model.CartPK;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Yang
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
        if (cart.getCartPK() == null) {
            cart.setCartPK(new CartPK());
        }
        if (cart.getLineitemList() == null) {
            cart.setLineitemList(new ArrayList<Lineitem>());
        }
        cart.getCartPK().setCustomerUsername(cart.getCustomer().getUsername());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productorder productorder = cart.getProductorder();
            if (productorder != null) {
                productorder = em.getReference(productorder.getClass(), productorder.getOrderid());
                cart.setProductorder(productorder);
            }
            Customer customer = cart.getCustomer();
            if (customer != null) {
                customer = em.getReference(customer.getClass(), customer.getUsername());
                cart.setCustomer(customer);
            }
            List<Lineitem> attachedLineitemList = new ArrayList<Lineitem>();
            for (Lineitem lineitemListLineitemToAttach : cart.getLineitemList()) {
                lineitemListLineitemToAttach = em.getReference(lineitemListLineitemToAttach.getClass(), lineitemListLineitemToAttach.getLineitemid());
                attachedLineitemList.add(lineitemListLineitemToAttach);
            }
            cart.setLineitemList(attachedLineitemList);
            em.persist(cart);
            if (productorder != null) {
                Cart oldCartOfProductorder = productorder.getCart();
                if (oldCartOfProductorder != null) {
                    oldCartOfProductorder.setProductorder(null);
                    oldCartOfProductorder = em.merge(oldCartOfProductorder);
                }
                productorder.setCart(cart);
                productorder = em.merge(productorder);
            }
            if (customer != null) {
                customer.getCartList().add(cart);
                customer = em.merge(customer);
            }
            for (Lineitem lineitemListLineitem : cart.getLineitemList()) {
                Cart oldCartOfLineitemListLineitem = lineitemListLineitem.getCart();
                lineitemListLineitem.setCart(cart);
                lineitemListLineitem = em.merge(lineitemListLineitem);
                if (oldCartOfLineitemListLineitem != null) {
                    oldCartOfLineitemListLineitem.getLineitemList().remove(lineitemListLineitem);
                    oldCartOfLineitemListLineitem = em.merge(oldCartOfLineitemListLineitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCart(cart.getCartPK()) != null) {
                throw new PreexistingEntityException("Cart " + cart + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cart cart) throws NonexistentEntityException, RollbackFailureException, Exception {
        cart.getCartPK().setCustomerUsername(cart.getCustomer().getUsername());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart persistentCart = em.find(Cart.class, cart.getCartPK());
            Productorder productorderOld = persistentCart.getProductorder();
            Productorder productorderNew = cart.getProductorder();
            Customer customerOld = persistentCart.getCustomer();
            Customer customerNew = cart.getCustomer();
            List<Lineitem> lineitemListOld = persistentCart.getLineitemList();
            List<Lineitem> lineitemListNew = cart.getLineitemList();
            if (productorderNew != null) {
                productorderNew = em.getReference(productorderNew.getClass(), productorderNew.getOrderid());
                cart.setProductorder(productorderNew);
            }
            if (customerNew != null) {
                customerNew = em.getReference(customerNew.getClass(), customerNew.getUsername());
                cart.setCustomer(customerNew);
            }
            List<Lineitem> attachedLineitemListNew = new ArrayList<Lineitem>();
            for (Lineitem lineitemListNewLineitemToAttach : lineitemListNew) {
                lineitemListNewLineitemToAttach = em.getReference(lineitemListNewLineitemToAttach.getClass(), lineitemListNewLineitemToAttach.getLineitemid());
                attachedLineitemListNew.add(lineitemListNewLineitemToAttach);
            }
            lineitemListNew = attachedLineitemListNew;
            cart.setLineitemList(lineitemListNew);
            cart = em.merge(cart);
            if (productorderOld != null && !productorderOld.equals(productorderNew)) {
                productorderOld.setCart(null);
                productorderOld = em.merge(productorderOld);
            }
            if (productorderNew != null && !productorderNew.equals(productorderOld)) {
                Cart oldCartOfProductorder = productorderNew.getCart();
                if (oldCartOfProductorder != null) {
                    oldCartOfProductorder.setProductorder(null);
                    oldCartOfProductorder = em.merge(oldCartOfProductorder);
                }
                productorderNew.setCart(cart);
                productorderNew = em.merge(productorderNew);
            }
            if (customerOld != null && !customerOld.equals(customerNew)) {
                customerOld.getCartList().remove(cart);
                customerOld = em.merge(customerOld);
            }
            if (customerNew != null && !customerNew.equals(customerOld)) {
                customerNew.getCartList().add(cart);
                customerNew = em.merge(customerNew);
            }
            for (Lineitem lineitemListOldLineitem : lineitemListOld) {
                if (!lineitemListNew.contains(lineitemListOldLineitem)) {
                    lineitemListOldLineitem.setCart(null);
                    lineitemListOldLineitem = em.merge(lineitemListOldLineitem);
                }
            }
            for (Lineitem lineitemListNewLineitem : lineitemListNew) {
                if (!lineitemListOld.contains(lineitemListNewLineitem)) {
                    Cart oldCartOfLineitemListNewLineitem = lineitemListNewLineitem.getCart();
                    lineitemListNewLineitem.setCart(cart);
                    lineitemListNewLineitem = em.merge(lineitemListNewLineitem);
                    if (oldCartOfLineitemListNewLineitem != null && !oldCartOfLineitemListNewLineitem.equals(cart)) {
                        oldCartOfLineitemListNewLineitem.getLineitemList().remove(lineitemListNewLineitem);
                        oldCartOfLineitemListNewLineitem = em.merge(oldCartOfLineitemListNewLineitem);
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
                CartPK id = cart.getCartPK();
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

    public void destroy(CartPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart cart;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getCartPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cart with id " + id + " no longer exists.", enfe);
            }
            Productorder productorder = cart.getProductorder();
            if (productorder != null) {
                productorder.setCart(null);
                productorder = em.merge(productorder);
            }
            Customer customer = cart.getCustomer();
            if (customer != null) {
                customer.getCartList().remove(cart);
                customer = em.merge(customer);
            }
            List<Lineitem> lineitemList = cart.getLineitemList();
            for (Lineitem lineitemListLineitem : lineitemList) {
                lineitemListLineitem.setCart(null);
                lineitemListLineitem = em.merge(lineitemListLineitem);
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

    public Cart findCart(CartPK id) {
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
