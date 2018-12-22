/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;

/**
 *
 * @author ariya boonchoo
 */
public class RecentViewServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        String productid = request.getParameter("productid");
        if (productid != null && productid.trim().length() > 0) {
//        Cookie[] cookie = request.getCookies();
            ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
            Product product = productJpaCtrl.findProduct(productid);
            session.setAttribute("product", product);
            Cookie ck = new Cookie("product", productid);
            ck.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(ck);
            Cookie[] theCookies = request.getCookies();
            
            session.setAttribute("product", ck);
//        if (theCookies!=null) {
            for (int i = 0; i < theCookies.length; i++) {
//                Cookie theCooky = theCookies[i];
//                theCooky.getValue();
                session.setAttribute("product",theCookies[i].getValue() );
                 getServletContext().getRequestDispatcher("/GetProductDetail").forward(request, response);
//                ck.setMaxAge(7 * 24 * 60 * 60);
//                response.addCookie(ck);
            }

//                if ("productid".equals(theCooky.getName())) {
//                    productid=theCooky.getValue();
//                    break;
//                request.setAttribute("products", ck);
//                response.sendRedirect("home.");
//            }
//        } 
//        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
//     List<Product> products = productJpaCtrl.findProductEntities();
//            getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        }
     getServletContext().getRequestDispatcher("/GetProductDetail").forward(request, response);
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
