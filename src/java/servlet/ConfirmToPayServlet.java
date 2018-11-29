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
import jpa.model.Payment;
import jpa.model.Productorder;
import jpa.model.controller.CustomerJpaController;
import jpa.model.controller.PaymentJpaController;
import jpa.model.controller.ProductorderJpaController;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class ConfirmToPayServlet extends HttpServlet {

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
        if (session != null) {
            Customer custom = (Customer) session.getAttribute("custom");
            if (custom != null) {
                CustomerJpaController customJpa = new CustomerJpaController(utx, emf);
                int point = custom.getPoint() + 10;
                custom.setPoint(point);
                Productorder productorder = new Productorder();
                ProductorderJpaController productorderJpaCtrl = new ProductorderJpaController(utx, emf);
                productorder.setCustomerUsername(custom);
                int orderid = productorderJpaCtrl.getProductorderCount() + 1;
                productorder.setOrderid(orderid);
                Cart cart = (Cart) session.getAttribute("cart");
                productorder.setCartCartid(cart);
                Payment pay = new Payment();
                PaymentJpaController payJpaCtrl = new PaymentJpaController(utx, emf);
                pay.setPaymentstatus("Confirm");
                productorder.setPayment(pay);
                productorder.setProductstatus("Shipped");
                try {
                    customJpa.edit(custom);
                    session.setAttribute("custom", custom);
                    getServletContext().getRequestDispatcher("/Payment.jsp").forward(request, response);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(NewAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(NewAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(NewAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
