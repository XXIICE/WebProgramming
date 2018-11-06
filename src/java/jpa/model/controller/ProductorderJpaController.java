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
 * @author ariya boonchoo
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

    public void create(Productorder productorder) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (productorder.getOrderitemList() == null) {
            productorder.setOrderitemList(new ArrayList<Orderitem>());
        }
        List<String> illegalOrphanMessages = null;
        Cart cartCartidOrphanCheck = productorder.getCartCartid();
        if (cartCartidOrphanCheck != null) {
            Productorder oldProductorderOfCartCartid = cartCartidOrphanCheck.getProductorder();
            if (oldProductorderOfCartCartid != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cart " + cartCartidOrphanCheck + " already has an item of type Productorder whose cartCartid column cannot be null. Please make another selection for the cartCartid field.");
            }
        }
        Payment paymentPaymentidOrphanCheck = productorder.getPaymentPaymentid();
        if (paymentPaymentidOrphanCheck != null) {
            Productorder oldProductorderOderidOfPaymentPaymentid = paymentPaymentidOrphanCheck.getProductorderOderid();
            if (oldProductorderOderidOfPaymentPaymentid != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Payment " + paymentPaymentidOrphanCheck + " already has an item of type Productorder whose paymentPaymentid column cannot be null. Please make another selection for the paymentPaymentid field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
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
            Cart cartCartid = productorder.getCartCartid();
            if (cartCartid != null) {
                cartCartid = em.getReference(cartCartid.getClass(), cartCartid.getCartid());
                productorder.setCartCartid(cartCartid);
            }
            Customer customerUserid = productorder.getCustomerUserid();
            if (customerUserid != null) {
                customerUserid = em.getReference(customerUserid.getClass(), customerUserid.getUserid());
                productorder.setCustomerUserid(customerUserid);
            }
            Payment paymentPaymentid = productorder.getPaymentPaymentid();
            if (paymentPaymentid != null) {
                paymentPaymentid = em.getReference(paymentPaymentid.getClass(), paymentPaymentid.getPaymentid());
                productorder.setPaymentPaymentid(paymentPaymentid);
            }
            List<Orderitem> attachedOrderitemList = new ArrayList<Orderitem>();
            for (Orderitem orderitemListOrderitemToAttach : productorder.getOrderitemList()) {
                orderitemListOrderitemToAttach = em.getReference(orderitemListOrderitemToAttach.getClass(), orderitemListOrderitemToAttach.getOrderitemid());
                attachedOrderitemList.add(orderitemListOrderitemToAttach);
            }
            productorder.setOrderitemList(attachedOrderitemList);
            em.persist(productorder);
            if (payment != null) {
                Productorder oldProductorderOderidOfPayment = payment.getProductorderOderid();
                if (oldProductorderOderidOfPayment != null) {
                    oldProductorderOderidOfPayment.setPayment(null);
                    oldProductorderOderidOfPayment = em.merge(oldProductorderOderidOfPayment);
                }
                payment.setProductorderOderid(productorder);
                payment = em.merge(payment);
            }
            if (cartCartid != null) {
                cartCartid.setProductorder(productorder);
                cartCartid = em.merge(cartCartid);
            }
            if (customerUserid != null) {
                customerUserid.getProductorderList().add(productorder);
                customerUserid = em.merge(customerUserid);
            }
            if (paymentPaymentid != null) {
                paymentPaymentid.setProductorderOderid(productorder);
                paymentPaymentid = em.merge(paymentPaymentid);
            }
            for (Orderitem orderitemListOrderitem : productorder.getOrderitemList()) {
                Productorder oldProductorderOderidOfOrderitemListOrderitem = orderitemListOrderitem.getProductorderOderid();
                orderitemListOrderitem.setProductorderOderid(productorder);
                orderitemListOrderitem = em.merge(orderitemListOrderitem);
                if (oldProductorderOderidOfOrderitemListOrderitem != null) {
                    oldProductorderOderidOfOrderitemListOrderitem.getOrderitemList().remove(orderitemListOrderitem);
                    oldProductorderOderidOfOrderitemListOrderitem = em.merge(oldProductorderOderidOfOrderitemListOrderitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductorder(productorder.getOderid()) != null) {
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
            Productorder persistentProductorder = em.find(Productorder.class, productorder.getOderid());
            Payment paymentOld = persistentProductorder.getPayment();
            Payment paymentNew = productorder.getPayment();
            Cart cartCartidOld = persistentProductorder.getCartCartid();
            Cart cartCartidNew = productorder.getCartCartid();
            Customer customerUseridOld = persistentProductorder.getCustomerUserid();
            Customer customerUseridNew = productorder.getCustomerUserid();
            Payment paymentPaymentidOld = persistentProductorder.getPaymentPaymentid();
            Payment paymentPaymentidNew = productorder.getPaymentPaymentid();
            List<Orderitem> orderitemListOld = persistentProductorder.getOrderitemList();
            List<Orderitem> orderitemListNew = productorder.getOrderitemList();
            List<String> illegalOrphanMessages = null;
            if (paymentOld != null && !paymentOld.equals(paymentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Payment " + paymentOld + " since its productorderOderid field is not nullable.");
            }
            if (cartCartidNew != null && !cartCartidNew.equals(cartCartidOld)) {
                Productorder oldProductorderOfCartCartid = cartCartidNew.getProductorder();
                if (oldProductorderOfCartCartid != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cart " + cartCartidNew + " already has an item of type Productorder whose cartCartid column cannot be null. Please make another selection for the cartCartid field.");
                }
            }
            if (paymentPaymentidOld != null && !paymentPaymentidOld.equals(paymentPaymentidNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Payment " + paymentPaymentidOld + " since its productorderOderid field is not nullable.");
            }
            if (paymentPaymentidNew != null && !paymentPaymentidNew.equals(paymentPaymentidOld)) {
                Productorder oldProductorderOderidOfPaymentPaymentid = paymentPaymentidNew.getProductorderOderid();
                if (oldProductorderOderidOfPaymentPaymentid != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Payment " + paymentPaymentidNew + " already has an item of type Productorder whose paymentPaymentid column cannot be null. Please make another selection for the paymentPaymentid field.");
                }
            }
            for (Orderitem orderitemListOldOrderitem : orderitemListOld) {
                if (!orderitemListNew.contains(orderitemListOldOrderitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderitem " + orderitemListOldOrderitem + " since its productorderOderid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paymentNew != null) {
                paymentNew = em.getReference(paymentNew.getClass(), paymentNew.getPaymentid());
                productorder.setPayment(paymentNew);
            }
            if (cartCartidNew != null) {
                cartCartidNew = em.getReference(cartCartidNew.getClass(), cartCartidNew.getCartid());
                productorder.setCartCartid(cartCartidNew);
            }
            if (customerUseridNew != null) {
                customerUseridNew = em.getReference(customerUseridNew.getClass(), customerUseridNew.getUserid());
                productorder.setCustomerUserid(customerUseridNew);
            }
            if (paymentPaymentidNew != null) {
                paymentPaymentidNew = em.getReference(paymentPaymentidNew.getClass(), paymentPaymentidNew.getPaymentid());
                productorder.setPaymentPaymentid(paymentPaymentidNew);
            }
            List<Orderitem> attachedOrderitemListNew = new ArrayList<Orderitem>();
            for (Orderitem orderitemListNewOrderitemToAttach : orderitemListNew) {
                orderitemListNewOrderitemToAttach = em.getReference(orderitemListNewOrderitemToAttach.getClass(), orderitemListNewOrderitemToAttach.getOrderitemid());
                attachedOrderitemListNew.add(orderitemListNewOrderitemToAttach);
            }
            orderitemListNew = attachedOrderitemListNew;
            productorder.setOrderitemList(orderitemListNew);
            productorder = em.merge(productorder);
            if (paymentNew != null && !paymentNew.equals(paymentOld)) {
                Productorder oldProductorderOderidOfPayment = paymentNew.getProductorderOderid();
                if (oldProductorderOderidOfPayment != null) {
                    oldProductorderOderidOfPayment.setPayment(null);
                    oldProductorderOderidOfPayment = em.merge(oldProductorderOderidOfPayment);
                }
                paymentNew.setProductorderOderid(productorder);
                paymentNew = em.merge(paymentNew);
            }
            if (cartCartidOld != null && !cartCartidOld.equals(cartCartidNew)) {
                cartCartidOld.setProductorder(null);
                cartCartidOld = em.merge(cartCartidOld);
            }
            if (cartCartidNew != null && !cartCartidNew.equals(cartCartidOld)) {
                cartCartidNew.setProductorder(productorder);
                cartCartidNew = em.merge(cartCartidNew);
            }
            if (customerUseridOld != null && !customerUseridOld.equals(customerUseridNew)) {
                customerUseridOld.getProductorderList().remove(productorder);
                customerUseridOld = em.merge(customerUseridOld);
            }
            if (customerUseridNew != null && !customerUseridNew.equals(customerUseridOld)) {
                customerUseridNew.getProductorderList().add(productorder);
                customerUseridNew = em.merge(customerUseridNew);
            }
            if (paymentPaymentidNew != null && !paymentPaymentidNew.equals(paymentPaymentidOld)) {
                paymentPaymentidNew.setProductorderOderid(productorder);
                paymentPaymentidNew = em.merge(paymentPaymentidNew);
            }
            for (Orderitem orderitemListNewOrderitem : orderitemListNew) {
                if (!orderitemListOld.contains(orderitemListNewOrderitem)) {
                    Productorder oldProductorderOderidOfOrderitemListNewOrderitem = orderitemListNewOrderitem.getProductorderOderid();
                    orderitemListNewOrderitem.setProductorderOderid(productorder);
                    orderitemListNewOrderitem = em.merge(orderitemListNewOrderitem);
                    if (oldProductorderOderidOfOrderitemListNewOrderitem != null && !oldProductorderOderidOfOrderitemListNewOrderitem.equals(productorder)) {
                        oldProductorderOderidOfOrderitemListNewOrderitem.getOrderitemList().remove(orderitemListNewOrderitem);
                        oldProductorderOderidOfOrderitemListNewOrderitem = em.merge(oldProductorderOderidOfOrderitemListNewOrderitem);
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
                String id = productorder.getOderid();
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productorder productorder;
            try {
                productorder = em.getReference(Productorder.class, id);
                productorder.getOderid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productorder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Payment paymentOrphanCheck = productorder.getPayment();
            if (paymentOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productorder (" + productorder + ") cannot be destroyed since the Payment " + paymentOrphanCheck + " in its payment field has a non-nullable productorderOderid field.");
            }
            Payment paymentPaymentidOrphanCheck = productorder.getPaymentPaymentid();
            if (paymentPaymentidOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productorder (" + productorder + ") cannot be destroyed since the Payment " + paymentPaymentidOrphanCheck + " in its paymentPaymentid field has a non-nullable productorderOderid field.");
            }
            List<Orderitem> orderitemListOrphanCheck = productorder.getOrderitemList();
            for (Orderitem orderitemListOrphanCheckOrderitem : orderitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productorder (" + productorder + ") cannot be destroyed since the Orderitem " + orderitemListOrphanCheckOrderitem + " in its orderitemList field has a non-nullable productorderOderid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cart cartCartid = productorder.getCartCartid();
            if (cartCartid != null) {
                cartCartid.setProductorder(null);
                cartCartid = em.merge(cartCartid);
            }
            Customer customerUserid = productorder.getCustomerUserid();
            if (customerUserid != null) {
                customerUserid.getProductorderList().remove(productorder);
                customerUserid = em.merge(customerUserid);
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

    public Productorder findProductorder(String id) {
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
