/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpa.model.Customer;
import jpa.model.controller.CustomerJpaController;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author ariya boonchoo
 */
public class RegisterServlet extends HttpServlet {

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String retypepassword = request.getParameter("retypepassword");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String creditcardnumber = request.getParameter("creditNo");
        Customer customer = new Customer(username, password, firstname, lastname, email, address, creditcardnumber);
        CustomerJpaController customerJpaCtrl = new CustomerJpaController(utx, emf);

        if (username != null && password != null && firstname != null && lastname != null && email != null && address != null && creditcardnumber != null) {
            
            if (retypepassword.equals(password)) {
                customer.setUsername(username);
                customer.setPassword(password);
                customer.setFirstname(firstname);
                customer.setLastname(lastname);
                customer.setEmail(email);
                customer.setAddress(address);
                customer.setCreditcardnumber(creditcardnumber);
                try {
                    customerJpaCtrl.create(customer);
                } catch (Exception ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                
                request.setAttribute("messagere", "password not match.");
                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
            }
//            customer = customerJpaCtrl.findCustomer(email);
//            if (customer.getEmail().equals(email)) {
//                request.setAttribute("messageus", "email already exists.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//            }
          
        }

        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);

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
