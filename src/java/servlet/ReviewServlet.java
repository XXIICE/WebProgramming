/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Customer;
import jpa.model.Product;
import jpa.model.Review;
import jpa.model.controller.CustomerJpaController;
import jpa.model.controller.ProductJpaController;
import jpa.model.controller.ReviewJpaController;
import jpa.model.controller.exceptions.RollbackFailureException;
import model.ShoppingCart2;

/**
 *
 * @author ariya boonchoo
 */
public class ReviewServlet extends HttpServlet {

    @PersistenceUnit(unitName = "ImaginePU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        ShoppingCart2 order = (ShoppingCart2) session.getAttribute("order");
        String review = request.getParameter("review");
        Customer custom = (Customer) session.getAttribute("custom");
        Review re = new Review();
        String productid = request.getParameter("productid");

        if (productid != null) {
            ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
            Product product = productJpaCtrl.findProduct(productid);
            session.setAttribute("product", product);
            if (review != null) {
                if (custom != null) {
                    CustomerJpaController customJpa = new CustomerJpaController(utx, emf);
                    Date d = new Date();
                    session.setAttribute("date", d);
                    re.setComment(review);
                    re.setCommentdate(d);
                    re.setCustomerUsername(custom);
                    re.setProductProductid(product);
                    ReviewJpaController reJpaCtrl = new ReviewJpaController(utx, emf);
                    int idRe = reJpaCtrl.getReviewCount()+1;
                    re.setReviewid(idRe);
                    try {
                        reJpaCtrl.create(re);
                        session.setAttribute("review", re);
                        getServletContext().getRequestDispatcher("/GetProductDatail").forward(request, response);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(ReviewServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ReviewServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
        getServletContext().getRequestDispatcher("/reviews.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
