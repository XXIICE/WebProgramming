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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;

/**
 *
 * @author ariya boonchoo
 */
public class productDiv1Servlet extends HttpServlet {
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
       ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        Product pro = new Product();
//        String date = request.getParameter("releasedate");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select p FROM Product p where p.releasedate >= '2018-12-05' order by p.releasedate desc");
//        Query q = em.createNamedQuery("select p from Product p order by p.releasedate desc");
//        q.setParameter("p.releasedate", date);
//        List<Product>productList = productJpaCtrl.findProductEntities();
        List<Product>productDiv1 =q.getResultList();
        request.setAttribute("productDiv1", productDiv1);
//         EntityManager em = emf.createEntityManager();
        Query q2 = em.createQuery("select p FROM Product p where p.releasedate <= '2018-11-28' and p.releasedate >='2018-11-14' order by p.releasedate desc");
List<Product>productDiv2 =q2.getResultList();
request.setAttribute("productDiv2", productDiv2);
        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
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
