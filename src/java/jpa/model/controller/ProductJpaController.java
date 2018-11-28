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
import jpa.model.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Tracklist;
import jpa.model.Favorite;
import jpa.model.Orderitem;
import jpa.model.Product;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (product.getReviewList() == null) {
            product.setReviewList(new ArrayList<Review>());
        }
        if (product.getTracklistList() == null) {
            product.setTracklistList(new ArrayList<Tracklist>());
        }
        if (product.getFavoriteList() == null) {
            product.setFavoriteList(new ArrayList<Favorite>());
        }
        if (product.getOrderitemList() == null) {
            product.setOrderitemList(new ArrayList<Orderitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : product.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewid());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            product.setReviewList(attachedReviewList);
            List<Tracklist> attachedTracklistList = new ArrayList<Tracklist>();
            for (Tracklist tracklistListTracklistToAttach : product.getTracklistList()) {
                tracklistListTracklistToAttach = em.getReference(tracklistListTracklistToAttach.getClass(), tracklistListTracklistToAttach.getTracklistPK());
                attachedTracklistList.add(tracklistListTracklistToAttach);
            }
            product.setTracklistList(attachedTracklistList);
            List<Favorite> attachedFavoriteList = new ArrayList<Favorite>();
            for (Favorite favoriteListFavoriteToAttach : product.getFavoriteList()) {
                favoriteListFavoriteToAttach = em.getReference(favoriteListFavoriteToAttach.getClass(), favoriteListFavoriteToAttach.getFavoriteid());
                attachedFavoriteList.add(favoriteListFavoriteToAttach);
            }
            product.setFavoriteList(attachedFavoriteList);
            List<Orderitem> attachedOrderitemList = new ArrayList<Orderitem>();
            for (Orderitem orderitemListOrderitemToAttach : product.getOrderitemList()) {
                orderitemListOrderitemToAttach = em.getReference(orderitemListOrderitemToAttach.getClass(), orderitemListOrderitemToAttach.getOrderitemid());
                attachedOrderitemList.add(orderitemListOrderitemToAttach);
            }
            product.setOrderitemList(attachedOrderitemList);
            em.persist(product);
            for (Review reviewListReview : product.getReviewList()) {
                Product oldProductProductidOfReviewListReview = reviewListReview.getProductProductid();
                reviewListReview.setProductProductid(product);
                reviewListReview = em.merge(reviewListReview);
                if (oldProductProductidOfReviewListReview != null) {
                    oldProductProductidOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldProductProductidOfReviewListReview = em.merge(oldProductProductidOfReviewListReview);
                }
            }
            for (Tracklist tracklistListTracklist : product.getTracklistList()) {
                Product oldProductOfTracklistListTracklist = tracklistListTracklist.getProduct();
                tracklistListTracklist.setProduct(product);
                tracklistListTracklist = em.merge(tracklistListTracklist);
                if (oldProductOfTracklistListTracklist != null) {
                    oldProductOfTracklistListTracklist.getTracklistList().remove(tracklistListTracklist);
                    oldProductOfTracklistListTracklist = em.merge(oldProductOfTracklistListTracklist);
                }
            }
            for (Favorite favoriteListFavorite : product.getFavoriteList()) {
                Product oldProductProductidOfFavoriteListFavorite = favoriteListFavorite.getProductProductid();
                favoriteListFavorite.setProductProductid(product);
                favoriteListFavorite = em.merge(favoriteListFavorite);
                if (oldProductProductidOfFavoriteListFavorite != null) {
                    oldProductProductidOfFavoriteListFavorite.getFavoriteList().remove(favoriteListFavorite);
                    oldProductProductidOfFavoriteListFavorite = em.merge(oldProductProductidOfFavoriteListFavorite);
                }
            }
            for (Orderitem orderitemListOrderitem : product.getOrderitemList()) {
                Product oldProductProductidOfOrderitemListOrderitem = orderitemListOrderitem.getProductProductid();
                orderitemListOrderitem.setProductProductid(product);
                orderitemListOrderitem = em.merge(orderitemListOrderitem);
                if (oldProductProductidOfOrderitemListOrderitem != null) {
                    oldProductProductidOfOrderitemListOrderitem.getOrderitemList().remove(orderitemListOrderitem);
                    oldProductProductidOfOrderitemListOrderitem = em.merge(oldProductProductidOfOrderitemListOrderitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProduct(product.getProductid()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductid());
            List<Review> reviewListOld = persistentProduct.getReviewList();
            List<Review> reviewListNew = product.getReviewList();
            List<Tracklist> tracklistListOld = persistentProduct.getTracklistList();
            List<Tracklist> tracklistListNew = product.getTracklistList();
            List<Favorite> favoriteListOld = persistentProduct.getFavoriteList();
            List<Favorite> favoriteListNew = product.getFavoriteList();
            List<Orderitem> orderitemListOld = persistentProduct.getOrderitemList();
            List<Orderitem> orderitemListNew = product.getOrderitemList();
            List<String> illegalOrphanMessages = null;
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewListOldReview + " since its productProductid field is not nullable.");
                }
            }
            for (Tracklist tracklistListOldTracklist : tracklistListOld) {
                if (!tracklistListNew.contains(tracklistListOldTracklist)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tracklist " + tracklistListOldTracklist + " since its product field is not nullable.");
                }
            }
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorite " + favoriteListOldFavorite + " since its productProductid field is not nullable.");
                }
            }
            for (Orderitem orderitemListOldOrderitem : orderitemListOld) {
                if (!orderitemListNew.contains(orderitemListOldOrderitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderitem " + orderitemListOldOrderitem + " since its productProductid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewid());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            product.setReviewList(reviewListNew);
            List<Tracklist> attachedTracklistListNew = new ArrayList<Tracklist>();
            for (Tracklist tracklistListNewTracklistToAttach : tracklistListNew) {
                tracklistListNewTracklistToAttach = em.getReference(tracklistListNewTracklistToAttach.getClass(), tracklistListNewTracklistToAttach.getTracklistPK());
                attachedTracklistListNew.add(tracklistListNewTracklistToAttach);
            }
            tracklistListNew = attachedTracklistListNew;
            product.setTracklistList(tracklistListNew);
            List<Favorite> attachedFavoriteListNew = new ArrayList<Favorite>();
            for (Favorite favoriteListNewFavoriteToAttach : favoriteListNew) {
                favoriteListNewFavoriteToAttach = em.getReference(favoriteListNewFavoriteToAttach.getClass(), favoriteListNewFavoriteToAttach.getFavoriteid());
                attachedFavoriteListNew.add(favoriteListNewFavoriteToAttach);
            }
            favoriteListNew = attachedFavoriteListNew;
            product.setFavoriteList(favoriteListNew);
            List<Orderitem> attachedOrderitemListNew = new ArrayList<Orderitem>();
            for (Orderitem orderitemListNewOrderitemToAttach : orderitemListNew) {
                orderitemListNewOrderitemToAttach = em.getReference(orderitemListNewOrderitemToAttach.getClass(), orderitemListNewOrderitemToAttach.getOrderitemid());
                attachedOrderitemListNew.add(orderitemListNewOrderitemToAttach);
            }
            orderitemListNew = attachedOrderitemListNew;
            product.setOrderitemList(orderitemListNew);
            product = em.merge(product);
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Product oldProductProductidOfReviewListNewReview = reviewListNewReview.getProductProductid();
                    reviewListNewReview.setProductProductid(product);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldProductProductidOfReviewListNewReview != null && !oldProductProductidOfReviewListNewReview.equals(product)) {
                        oldProductProductidOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldProductProductidOfReviewListNewReview = em.merge(oldProductProductidOfReviewListNewReview);
                    }
                }
            }
            for (Tracklist tracklistListNewTracklist : tracklistListNew) {
                if (!tracklistListOld.contains(tracklistListNewTracklist)) {
                    Product oldProductOfTracklistListNewTracklist = tracklistListNewTracklist.getProduct();
                    tracklistListNewTracklist.setProduct(product);
                    tracklistListNewTracklist = em.merge(tracklistListNewTracklist);
                    if (oldProductOfTracklistListNewTracklist != null && !oldProductOfTracklistListNewTracklist.equals(product)) {
                        oldProductOfTracklistListNewTracklist.getTracklistList().remove(tracklistListNewTracklist);
                        oldProductOfTracklistListNewTracklist = em.merge(oldProductOfTracklistListNewTracklist);
                    }
                }
            }
            for (Favorite favoriteListNewFavorite : favoriteListNew) {
                if (!favoriteListOld.contains(favoriteListNewFavorite)) {
                    Product oldProductProductidOfFavoriteListNewFavorite = favoriteListNewFavorite.getProductProductid();
                    favoriteListNewFavorite.setProductProductid(product);
                    favoriteListNewFavorite = em.merge(favoriteListNewFavorite);
                    if (oldProductProductidOfFavoriteListNewFavorite != null && !oldProductProductidOfFavoriteListNewFavorite.equals(product)) {
                        oldProductProductidOfFavoriteListNewFavorite.getFavoriteList().remove(favoriteListNewFavorite);
                        oldProductProductidOfFavoriteListNewFavorite = em.merge(oldProductProductidOfFavoriteListNewFavorite);
                    }
                }
            }
            for (Orderitem orderitemListNewOrderitem : orderitemListNew) {
                if (!orderitemListOld.contains(orderitemListNewOrderitem)) {
                    Product oldProductProductidOfOrderitemListNewOrderitem = orderitemListNewOrderitem.getProductProductid();
                    orderitemListNewOrderitem.setProductProductid(product);
                    orderitemListNewOrderitem = em.merge(orderitemListNewOrderitem);
                    if (oldProductProductidOfOrderitemListNewOrderitem != null && !oldProductProductidOfOrderitemListNewOrderitem.equals(product)) {
                        oldProductProductidOfOrderitemListNewOrderitem.getOrderitemList().remove(orderitemListNewOrderitem);
                        oldProductProductidOfOrderitemListNewOrderitem = em.merge(oldProductProductidOfOrderitemListNewOrderitem);
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
                String id = product.getProductid();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Review> reviewListOrphanCheck = product.getReviewList();
            for (Review reviewListOrphanCheckReview : reviewListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Review " + reviewListOrphanCheckReview + " in its reviewList field has a non-nullable productProductid field.");
            }
            List<Tracklist> tracklistListOrphanCheck = product.getTracklistList();
            for (Tracklist tracklistListOrphanCheckTracklist : tracklistListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Tracklist " + tracklistListOrphanCheckTracklist + " in its tracklistList field has a non-nullable product field.");
            }
            List<Favorite> favoriteListOrphanCheck = product.getFavoriteList();
            for (Favorite favoriteListOrphanCheckFavorite : favoriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Favorite " + favoriteListOrphanCheckFavorite + " in its favoriteList field has a non-nullable productProductid field.");
            }
            List<Orderitem> orderitemListOrphanCheck = product.getOrderitemList();
            for (Orderitem orderitemListOrphanCheckOrderitem : orderitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Orderitem " + orderitemListOrphanCheckOrderitem + " in its orderitemList field has a non-nullable productProductid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
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

    //add this
     public List<Product> findByProductname(String productName) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Product.findByProductname");
        q.setParameter("productname","%"+ productName.toLowerCase()+"%");
        try{
        return q.getResultList();
        }catch(Exception ex){
            return null;
        }finally{
            em.close();
        }
    }
         public List<Product> findByArtist(String artist) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Product.findByArtist");
        q.setParameter("artist","%"+ artist.toLowerCase()+"%");
        try{
        return q.getResultList();
        }catch(Exception ex){
            return null;
        }finally{
            em.close();
        }
    }
    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
