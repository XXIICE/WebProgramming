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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Payment;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class PaymentJpaController implements Serializable {

    public PaymentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Payment payment) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Productorder productorderOrderidOrphanCheck = payment.getProductorderOrderid();
        if (productorderOrderidOrphanCheck != null) {
            Payment oldPaymentOfProductorderOrderid = productorderOrderidOrphanCheck.getPayment();
            if (oldPaymentOfProductorderOrderid != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Productorder " + productorderOrderidOrphanCheck + " already has an item of type Payment whose productorderOrderid column cannot be null. Please make another selection for the productorderOrderid field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productorder productorderOrderid = payment.getProductorderOrderid();
            if (productorderOrderid != null) {
                productorderOrderid = em.getReference(productorderOrderid.getClass(), productorderOrderid.getOrderid());
                payment.setProductorderOrderid(productorderOrderid);
            }
            Productorder productorder = payment.getProductorder();
            if (productorder != null) {
                productorder = em.getReference(productorder.getClass(), productorder.getOrderid());
                payment.setProductorder(productorder);
            }
            em.persist(payment);
            if (productorderOrderid != null) {
                productorderOrderid.setPayment(payment);
                productorderOrderid = em.merge(productorderOrderid);
            }
            if (productorder != null) {
                Payment oldPaymentPaymentidOfProductorder = productorder.getPaymentPaymentid();
                if (oldPaymentPaymentidOfProductorder != null) {
                    oldPaymentPaymentidOfProductorder.setProductorder(null);
                    oldPaymentPaymentidOfProductorder = em.merge(oldPaymentPaymentidOfProductorder);
                }
                productorder.setPaymentPaymentid(payment);
                productorder = em.merge(productorder);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPayment(payment.getPaymentid()) != null) {
                throw new PreexistingEntityException("Payment " + payment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Payment payment) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Payment persistentPayment = em.find(Payment.class, payment.getPaymentid());
            Productorder productorderOrderidOld = persistentPayment.getProductorderOrderid();
            Productorder productorderOrderidNew = payment.getProductorderOrderid();
            Productorder productorderOld = persistentPayment.getProductorder();
            Productorder productorderNew = payment.getProductorder();
            List<String> illegalOrphanMessages = null;
            if (productorderOrderidNew != null && !productorderOrderidNew.equals(productorderOrderidOld)) {
                Payment oldPaymentOfProductorderOrderid = productorderOrderidNew.getPayment();
                if (oldPaymentOfProductorderOrderid != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Productorder " + productorderOrderidNew + " already has an item of type Payment whose productorderOrderid column cannot be null. Please make another selection for the productorderOrderid field.");
                }
            }
            if (productorderOld != null && !productorderOld.equals(productorderNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Productorder " + productorderOld + " since its paymentPaymentid field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productorderOrderidNew != null) {
                productorderOrderidNew = em.getReference(productorderOrderidNew.getClass(), productorderOrderidNew.getOrderid());
                payment.setProductorderOrderid(productorderOrderidNew);
            }
            if (productorderNew != null) {
                productorderNew = em.getReference(productorderNew.getClass(), productorderNew.getOrderid());
                payment.setProductorder(productorderNew);
            }
            payment = em.merge(payment);
            if (productorderOrderidOld != null && !productorderOrderidOld.equals(productorderOrderidNew)) {
                productorderOrderidOld.setPayment(null);
                productorderOrderidOld = em.merge(productorderOrderidOld);
            }
            if (productorderOrderidNew != null && !productorderOrderidNew.equals(productorderOrderidOld)) {
                productorderOrderidNew.setPayment(payment);
                productorderOrderidNew = em.merge(productorderOrderidNew);
            }
            if (productorderNew != null && !productorderNew.equals(productorderOld)) {
                Payment oldPaymentPaymentidOfProductorder = productorderNew.getPaymentPaymentid();
                if (oldPaymentPaymentidOfProductorder != null) {
                    oldPaymentPaymentidOfProductorder.setProductorder(null);
                    oldPaymentPaymentidOfProductorder = em.merge(oldPaymentPaymentidOfProductorder);
                }
                productorderNew.setPaymentPaymentid(payment);
                productorderNew = em.merge(productorderNew);
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
                Integer id = payment.getPaymentid();
                if (findPayment(id) == null) {
                    throw new NonexistentEntityException("The payment with id " + id + " no longer exists.");
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
            Payment payment;
            try {
                payment = em.getReference(Payment.class, id);
                payment.getPaymentid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The payment with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Productorder productorderOrphanCheck = payment.getProductorder();
            if (productorderOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Payment (" + payment + ") cannot be destroyed since the Productorder " + productorderOrphanCheck + " in its productorder field has a non-nullable paymentPaymentid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Productorder productorderOrderid = payment.getProductorderOrderid();
            if (productorderOrderid != null) {
                productorderOrderid.setPayment(null);
                productorderOrderid = em.merge(productorderOrderid);
            }
            em.remove(payment);
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

    public List<Payment> findPaymentEntities() {
        return findPaymentEntities(true, -1, -1);
    }

    public List<Payment> findPaymentEntities(int maxResults, int firstResult) {
        return findPaymentEntities(false, maxResults, firstResult);
    }

    private List<Payment> findPaymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Payment.class));
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

    public Payment findPayment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Payment.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Payment> rt = cq.from(Payment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
