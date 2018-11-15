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
import jpa.model.Customer;
import jpa.model.Review;
import jpa.model.Favorite;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (customer.getProductorderList() == null) {
            customer.setProductorderList(new ArrayList<Productorder>());
        }
        if (customer.getReviewList() == null) {
            customer.setReviewList(new ArrayList<Review>());
        }
        if (customer.getFavoriteList() == null) {
            customer.setFavoriteList(new ArrayList<Favorite>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Productorder> attachedProductorderList = new ArrayList<Productorder>();
            for (Productorder productorderListProductorderToAttach : customer.getProductorderList()) {
                productorderListProductorderToAttach = em.getReference(productorderListProductorderToAttach.getClass(), productorderListProductorderToAttach.getOderid());
                attachedProductorderList.add(productorderListProductorderToAttach);
            }
            customer.setProductorderList(attachedProductorderList);
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : customer.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewid());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            customer.setReviewList(attachedReviewList);
            List<Favorite> attachedFavoriteList = new ArrayList<Favorite>();
            for (Favorite favoriteListFavoriteToAttach : customer.getFavoriteList()) {
                favoriteListFavoriteToAttach = em.getReference(favoriteListFavoriteToAttach.getClass(), favoriteListFavoriteToAttach.getFavoriteid());
                attachedFavoriteList.add(favoriteListFavoriteToAttach);
            }
            customer.setFavoriteList(attachedFavoriteList);
            em.persist(customer);
            for (Productorder productorderListProductorder : customer.getProductorderList()) {
                Customer oldCustomerUsernameOfProductorderListProductorder = productorderListProductorder.getCustomerUsername();
                productorderListProductorder.setCustomerUsername(customer);
                productorderListProductorder = em.merge(productorderListProductorder);
                if (oldCustomerUsernameOfProductorderListProductorder != null) {
                    oldCustomerUsernameOfProductorderListProductorder.getProductorderList().remove(productorderListProductorder);
                    oldCustomerUsernameOfProductorderListProductorder = em.merge(oldCustomerUsernameOfProductorderListProductorder);
                }
            }
            for (Review reviewListReview : customer.getReviewList()) {
                Customer oldCustomerUsernameOfReviewListReview = reviewListReview.getCustomerUsername();
                reviewListReview.setCustomerUsername(customer);
                reviewListReview = em.merge(reviewListReview);
                if (oldCustomerUsernameOfReviewListReview != null) {
                    oldCustomerUsernameOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldCustomerUsernameOfReviewListReview = em.merge(oldCustomerUsernameOfReviewListReview);
                }
            }
            for (Favorite favoriteListFavorite : customer.getFavoriteList()) {
                Customer oldCustomerUsernameOfFavoriteListFavorite = favoriteListFavorite.getCustomerUsername();
                favoriteListFavorite.setCustomerUsername(customer);
                favoriteListFavorite = em.merge(favoriteListFavorite);
                if (oldCustomerUsernameOfFavoriteListFavorite != null) {
                    oldCustomerUsernameOfFavoriteListFavorite.getFavoriteList().remove(favoriteListFavorite);
                    oldCustomerUsernameOfFavoriteListFavorite = em.merge(oldCustomerUsernameOfFavoriteListFavorite);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCustomer(customer.getUsername()) != null) {
                throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer persistentCustomer = em.find(Customer.class, customer.getUsername());
            List<Productorder> productorderListOld = persistentCustomer.getProductorderList();
            List<Productorder> productorderListNew = customer.getProductorderList();
            List<Review> reviewListOld = persistentCustomer.getReviewList();
            List<Review> reviewListNew = customer.getReviewList();
            List<Favorite> favoriteListOld = persistentCustomer.getFavoriteList();
            List<Favorite> favoriteListNew = customer.getFavoriteList();
            List<String> illegalOrphanMessages = null;
            for (Productorder productorderListOldProductorder : productorderListOld) {
                if (!productorderListNew.contains(productorderListOldProductorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productorder " + productorderListOldProductorder + " since its customerUsername field is not nullable.");
                }
            }
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewListOldReview + " since its customerUsername field is not nullable.");
                }
            }
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorite " + favoriteListOldFavorite + " since its customerUsername field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Productorder> attachedProductorderListNew = new ArrayList<Productorder>();
            for (Productorder productorderListNewProductorderToAttach : productorderListNew) {
                productorderListNewProductorderToAttach = em.getReference(productorderListNewProductorderToAttach.getClass(), productorderListNewProductorderToAttach.getOderid());
                attachedProductorderListNew.add(productorderListNewProductorderToAttach);
            }
            productorderListNew = attachedProductorderListNew;
            customer.setProductorderList(productorderListNew);
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewid());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            customer.setReviewList(reviewListNew);
            List<Favorite> attachedFavoriteListNew = new ArrayList<Favorite>();
            for (Favorite favoriteListNewFavoriteToAttach : favoriteListNew) {
                favoriteListNewFavoriteToAttach = em.getReference(favoriteListNewFavoriteToAttach.getClass(), favoriteListNewFavoriteToAttach.getFavoriteid());
                attachedFavoriteListNew.add(favoriteListNewFavoriteToAttach);
            }
            favoriteListNew = attachedFavoriteListNew;
            customer.setFavoriteList(favoriteListNew);
            customer = em.merge(customer);
            for (Productorder productorderListNewProductorder : productorderListNew) {
                if (!productorderListOld.contains(productorderListNewProductorder)) {
                    Customer oldCustomerUsernameOfProductorderListNewProductorder = productorderListNewProductorder.getCustomerUsername();
                    productorderListNewProductorder.setCustomerUsername(customer);
                    productorderListNewProductorder = em.merge(productorderListNewProductorder);
                    if (oldCustomerUsernameOfProductorderListNewProductorder != null && !oldCustomerUsernameOfProductorderListNewProductorder.equals(customer)) {
                        oldCustomerUsernameOfProductorderListNewProductorder.getProductorderList().remove(productorderListNewProductorder);
                        oldCustomerUsernameOfProductorderListNewProductorder = em.merge(oldCustomerUsernameOfProductorderListNewProductorder);
                    }
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Customer oldCustomerUsernameOfReviewListNewReview = reviewListNewReview.getCustomerUsername();
                    reviewListNewReview.setCustomerUsername(customer);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldCustomerUsernameOfReviewListNewReview != null && !oldCustomerUsernameOfReviewListNewReview.equals(customer)) {
                        oldCustomerUsernameOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldCustomerUsernameOfReviewListNewReview = em.merge(oldCustomerUsernameOfReviewListNewReview);
                    }
                }
            }
            for (Favorite favoriteListNewFavorite : favoriteListNew) {
                if (!favoriteListOld.contains(favoriteListNewFavorite)) {
                    Customer oldCustomerUsernameOfFavoriteListNewFavorite = favoriteListNewFavorite.getCustomerUsername();
                    favoriteListNewFavorite.setCustomerUsername(customer);
                    favoriteListNewFavorite = em.merge(favoriteListNewFavorite);
                    if (oldCustomerUsernameOfFavoriteListNewFavorite != null && !oldCustomerUsernameOfFavoriteListNewFavorite.equals(customer)) {
                        oldCustomerUsernameOfFavoriteListNewFavorite.getFavoriteList().remove(favoriteListNewFavorite);
                        oldCustomerUsernameOfFavoriteListNewFavorite = em.merge(oldCustomerUsernameOfFavoriteListNewFavorite);
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
                String id = customer.getUsername();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
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
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Productorder> productorderListOrphanCheck = customer.getProductorderList();
            for (Productorder productorderListOrphanCheckProductorder : productorderListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Productorder " + productorderListOrphanCheckProductorder + " in its productorderList field has a non-nullable customerUsername field.");
            }
            List<Review> reviewListOrphanCheck = customer.getReviewList();
            for (Review reviewListOrphanCheckReview : reviewListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Review " + reviewListOrphanCheckReview + " in its reviewList field has a non-nullable customerUsername field.");
            }
            List<Favorite> favoriteListOrphanCheck = customer.getFavoriteList();
            for (Favorite favoriteListOrphanCheckFavorite : favoriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Favorite " + favoriteListOrphanCheckFavorite + " in its favoriteList field has a non-nullable customerUsername field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(customer);
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

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
