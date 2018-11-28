/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import jpa.model.Cart;
import jpa.model.Customer;
import jpa.model.Product;
import jpa.model.controller.CartJpaController;
import jpa.model.controller.ProductJpaController;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;
import model.ShoppingCart2;

/**
 *
 * @author INT303
 */
public class PlusItem extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        ShoppingCart2 cart = (ShoppingCart2) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart2();
            session.setAttribute("cart", cart);
        }
        Customer custom = (Customer) session.getAttribute("custom");

        String productid = request.getParameter("productid");
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        Product p = productJpaCtrl.findProduct(productid);
        if (productid != null) {
            cart.add(p);
//            if (custom != null) {
//                Cart ca = new Cart();
//                CartJpaController cartJpaCtrl = new CartJpaController(utx, emf);
//                int idC = cartJpaCtrl.getCartCount() + 1;
//                ca.setCartid(idC);
////                    ca.setCartid(1);
////                    ca.setLineitemList(lineitemList);
//                try {
//                    cartJpaCtrl.create(ca);
//                } catch (PreexistingEntityException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }

            session.setAttribute("cart", cart);
            getServletContext().getRequestDispatcher("/ShowCart").forward(request, response);

        }
//    }

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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
