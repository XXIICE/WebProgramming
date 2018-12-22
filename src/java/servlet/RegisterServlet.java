/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
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
        Customer customer = new Customer();
        CustomerJpaController customerJpaCtrl = new CustomerJpaController(utx, emf);
//
//        if (username != null && password != null) {
//            if (!retypepassword.equals(password)) {
//
//                request.setAttribute("messagere", "password not match.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                return;
//            }
//
//            Customer  custObj = customerJpaCtrl.findCustomer(username);
//            if (username.equals(custObj.getUsername())) {
//                request.setAttribute("messageus", "this username already exists.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                return;
//            }
////            getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//        } else {
        if (username != null && password != null && firstname != null && lastname != null && email != null && address != null && creditcardnumber != null) {
//             if(!retypepassword.equals(password)) {
//              
//                request.setAttribute("messagere", "password not match.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                return;
//            }
            Customer  custObj = customerJpaCtrl.findCustomer(username);
            if (custObj!=null) {
                if (custObj.getUsername().equals(username)) {
                request.setAttribute("messageus", "this username already exists.");
                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            }
           
//            customer = customerJpaCtrl.findCustomer(username);
//            System.out.println(1);
//            if (username.equalsIgnoreCase(customer.getUsername())) {
//                System.out.println(customer.getUsername());
//                request.setAttribute("messageus", "this username already exists.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                return;
//            }
            if (retypepassword.equals(password)) {

                customer.setUsername(username);
                password = cryptWithMD5(password);
                customer.setPassword(password);
                customer.setFirstname(firstname);
                customer.setLastname(lastname);
                customer.setEmail(email);
                customer.setAddress(address);
                customer.setCreditcardnumber(creditcardnumber);
                customer.setPoint(0);
//                 List<Customer> customerList = customerJpaCtrl.findCustomerEntities();
//                List<Customer> customerListAccount = new ArrayList<>();
//                for (Customer customer1 : customerListAccount) {
//                   if (customer1.getUsername().equalsIgnoreCase(username)) {
//                    System.out.println("111111111");
//                    request.setAttribute("messageus", "this username already exists.");
////                    response.sendRedirect("register.jsp");
//                    getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                    return;
//                }
//                }
                try {
//                    customer = customerJpaCtrl.findCustomer(username);
//                    if (customer.getUsername().equals(username)) {
//                        request.setAttribute("messageus", "this username already exists.");
//                        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                        return;
//                    }
//                    password = cryptWithMD5(password);
//                    retypepassword=cryptWithMD5(retypepassword);
                    customerJpaCtrl.create(customer);
                } catch (Exception ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

//                    }
            } if (!retypepassword.equals(password)) {

                    request.setAttribute("messagere", "Your password is not match.");
                    getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
//                 EntityManager em = emf.createEntityManager();
//                 Query q = em.createQuery("select c.username FROM Customer c ");
//                 List<Customer> customerList = q.getResultList();

//                Customer custObj = customerJpaCtrl.findCustomer(username);
//                if (custObj.getUsername().equalsIgnoreCase(username)) {
//                    System.out.println("111111111");
//                    request.setAttribute("messageus", "this username already exists.");
////                    response.sendRedirect("register.jsp");
//                    getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                    return;
//                }
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
            }
//            }
//            Customer custObj = customerJpaCtrl.findCustomer(username);
//            if (custObj.getUsername().equals(username)) {
//                request.setAttribute("messageus", "this username already exists.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                return;
//            }
//            if (!retypepassword.equals(password)) {
//                request.setAttribute("messagere", "password not match.");
//                getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
//                return;
//            }
//        
       // }
        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);

    }

    public static String cryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        return null;
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
