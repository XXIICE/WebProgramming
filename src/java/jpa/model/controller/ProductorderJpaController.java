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
import jpa.model.Payment;
import jpa.model.Cart;
import jpa.model.Customer;
import jpa.model.Orderitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Productorder;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Yang
 */
public class ProductorderJpaController implements Serializable {

    public ProductorderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productorder productorder) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (productorder.getOrderitemList() == null) {
            productorder.setOrderitemList(new ArrayList<Orderitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Payment payment = productorder.getPayment();
            if (payment != null) {
                payment = em.getReference(payment.getClass(), payment.getPaymentid());
                productorder.setPayment(payment);
            }
            Cart cart = productorder.getCart();
            if (cart != null) {
                cart = em.getReference(cart.getClass(), cart.getCartPK());
                productorder.setCart(cart);
            }
            Customer customerUsername = productorder.getCustomerUsername();
            if (customerUsername != null) {
                customerUsername = em.getReference(customerUsername.getClass(), customerUsername.getUsername());
                productorder.setCustomerUsername(customerUsername);
            }
            List<Orderitem> attachedOrderitemList = new ArrayList<Orderitem>();
            for (Orderitem orderitemListOrderitemToAttach : productorder.getOrderitemList()) {
                orderitemListOrderitemToAttach = em.getReference(orderitemListOrderitemToAttach.getClass(), orderitemListOrderitemToAttach.getOrderitemid());
                attachedOrderitemList.add(orderitemListOrderitemToAttach);
            }
            productorder.setOrderitemList(attachedOrderitemList);
            em.persist(productorder);
            if (payment != null) {
                Productorder oldProductorderOrderidOfPayment = payment.getProductorderOrderid();
                if (oldProductorderOrderidOfPayment != null) {
                    oldProductorderOrderidOfPayment.setPayment(null);
                    oldProductorderOrderidOfPayment = em.merge(oldProductorderOrderidOfPayment);
                }
                payment.setProductorderOrderid(productorder);
                payment = em.merge(payment);
            }
            if (cart != null) {
                Productorder oldProductorderOfCart = cart.getProductorder();
                if (oldProductorderOfCart != null) {
                    oldProductorderOfCart.setCart(null);
                    oldProductorderOfCart = em.merge(oldProductorderOfCart);
                }
                cart.setProductorder(productorder);
                cart = em.merge(cart);
            }
            if (customerUsername != null) {
                customerUsername.getProductorderList().add(productorder);
                customerUsername = em.merge(customerUsername);
            }
            for (Orderitem orderitemListOrderitem : productorder.getOrderitemList()) {
                Productorder oldProductorderOrderidOfOrderitemListOrderitem = orderitemListOrderitem.getProductorderOrderid();
                orderitemListOrderitem.setProductorderOrderid(productorder);
                orderitemListOrderitem = em.merge(orderitemListOrderitem);
                if (oldProductorderOrderidOfOrderitemListOrderitem != null) {
                    oldProductorderOrderidOfOrderitemListOrderitem.getOrderitemList().remove(orderitemListOrderitem);
                    oldProductorderOrderidOfOrderitemListOrderitem = em.merge(oldProductorderOrderidOfOrderitemListOrderitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductorder(productorder.getOrderid()) != null) {
                throw new PreexistingEntityException("Productorder " + productorder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productorder productorder) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productorder persistentProductorder = em.find(Productorder.class, productorder.getOrderid());
            Payment paymentOld = persistentProductorder.getPayment();
            Payment paymentNew = productorder.getPayment();
            Cart cartOld = persistentProductorder.getCart();
            Cart cartNew = productorder.getCart();
            Customer customerUsernameOld = persistentProductorder.getCustomerUsername();
            Customer customerUsernameNew = productorder.getCustomerUsername();
            List<Orderitem> orderitemListOld = persistentProductorder.getOrderitemList();
            List<Orderitem> orderitemListNew = productorder.getOrderitemList();
            List<String> illegalOrphanMessages = null;
            for (Orderitem orderitemListOldOrderitem : orderitemListOld) {
                if (!orderitemListNew.contains(orderitemListOldOrderitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderitem " + orderitemListOldOrderitem + " since its productorderOrderid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paymentNew != null) {
                paymentNew = em.getReference(paymentNew.getClass(), paymentNew.getPaymentid());
                productorder.setPayment(paymentNew);
            }
            if (cartNew != null) {
                cartNew = em.getReference(cartNew.getClass(), cartNew.getCartPK());
                productorder.setCart(cartNew);
            }
            if (customerUsernameNew != null) {
                customerUsernameNew = em.getReference(customerUsernameNew.getClass(), customerUsernameNew.getUsername());
                productorder.setCustomerUsername(customerUsernameNew);
            }
            List<Orderitem> attachedOrderitemListNew = new ArrayList<Orderitem>();
            for (Orderitem orderitemListNewOrderitemToAttach : orderitemListNew) {
                orderitemListNewOrderitemToAttach = em.getReference(orderitemListNewOrderitemToAttach.getClass(), orderitemListNewOrderitemToAttach.getOrderitemid());
                attachedOrderitemListNew.add(orderitemListNewOrderitemToAttach);
            }
            orderitemListNew = attachedOrderitemListNew;
            productorder.setOrderitemList(orderitemListNew);
            productorder = em.merge(productorder);
            if (paymentOld != null && !paymentOld.equals(paymentNew)) {
                paymentOld.setProductorderOrderid(null);
                paymentOld = em.merge(paymentOld);
            }
            if (paymentNew != null && !paymentNew.equals(paymentOld)) {
                Productorder oldProductorderOrderidOfPayment = paymentNew.getProductorderOrderid();
                if (oldProductorderOrderidOfPayment != null) {
                    oldProductorderOrderidOfPayment.setPayment(null);
                    oldProductorderOrderidOfPayment = em.merge(oldProductorderOrderidOfPayment);
                }
                paymentNew.setProductorderOrderid(productorder);
                paymentNew = em.merge(paymentNew);
            }
            if (cartOld != null && !cartOld.equals(cartNew)) {
                cartOld.setProductorder(null);
                cartOld = em.merge(cartOld);
            }
            if (cartNew != null && !cartNew.equals(cartOld)) {
                Productorder oldProductorderOfCart = cartNew.getProductorder();
                if (oldProductorderOfCart != null) {
                    oldProductorderOfCart.setCart(null);
                    oldProductorderOfCart = em.merge(oldProductorderOfCart);
                }
                cartNew.setProductorder(productorder);
                cartNew = em.merge(cartNew);
            }
            if (customerUsernameOld != null && !customerUsernameOld.equals(customerUsernameNew)) {
                customerUsernameOld.getProductorderList().remove(productorder);
                customerUsernameOld = em.merge(customerUsernameOld);
            }
            if (customerUsernameNew != null && !customerUsernameNew.equals(customerUsernameOld)) {
                customerUsernameNew.getProductorderList().add(productorder);
                customerUsernameNew = em.merge(customerUsernameNew);
            }
            for (Orderitem orderitemListNewOrderitem : orderitemListNew) {
                if (!orderitemListOld.contains(orderitemListNewOrderitem)) {
                    Productorder oldProductorderOrderidOfOrderitemListNewOrderitem = orderitemListNewOrderitem.getProductorderOrderid();
                    orderitemListNewOrderitem.setProductorderOrderid(productorder);
                    orderitemListNewOrderitem = em.merge(orderitemListNewOrderitem);
                    if (oldProductorderOrderidOfOrderitemListNewOrderitem != null && !oldProductorderOrderidOfOrderitemListNewOrderitem.equals(productorder)) {
                        oldProductorderOrderidOfOrderitemListNewOrderitem.getOrderitemList().remove(orderitemListNewOrderitem);
                        oldProductorderOrderidOfOrderitemListNewOrderitem = em.merge(oldProductorderOrderidOfOrderitemListNewOrderitem);
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
                Integer id = productorder.getOrderid();
                if (findProductorder(id) == null) {
                    throw new NonexistentEntityException("The productorder with id " + id + " no longer exists.");
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
            Productorder productorder;
            try {
                productorder = em.getReference(Productorder.class, id);
                productorder.getOrderid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productorder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Orderitem> orderitemListOrphanCheck = productorder.getOrderitemList();
            for (Orderitem orderitemListOrphanCheckOrderitem : orderitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productorder (" + productorder + ") cannot be destroyed since the Orderitem " + orderitemListOrphanCheckOrderitem + " in its orderitemList field has a non-nullable productorderOrderid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Payment payment = productorder.getPayment();
            if (payment != null) {
                payment.setProductorderOrderid(null);
                payment = em.merge(payment);
            }
            Cart cart = productorder.getCart();
            if (cart != null) {
                cart.setProductorder(null);
                cart = em.merge(cart);
            }
            Customer customerUsername = productorder.getCustomerUsername();
            if (customerUsername != null) {
                customerUsername.getProductorderList().remove(productorder);
                customerUsername = em.merge(customerUsername);
            }
            em.remove(productorder);
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

    public List<Productorder> findProductorderEntities() {
        return findProductorderEntities(true, -1, -1);
    }

    public List<Productorder> findProductorderEntities(int maxResults, int firstResult) {
        return findProductorderEntities(false, maxResults, firstResult);
    }

    private List<Productorder> findProductorderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productorder.class));
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

    public Productorder findProductorder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productorder.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductorderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productorder> rt = cq.from(Productorder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
