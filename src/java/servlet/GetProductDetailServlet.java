/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Product;
import jpa.model.Tracklist;
import jpa.model.controller.ProductJpaController;
import jpa.model.controller.TracklistJpaController;

/**
 *
 * @author ariya boonchoo
 */
public class GetProductDetailServlet extends HttpServlet {

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
        String productid = request.getParameter("productid");
//        Product productObj = (Product) session.getAttribute("product");
//        if (productObj != null) {

        if (productid != null) {
//         EntityManager em = emf.createEntityManager();
//         Query q = em.createQuery("select * from Tracklist t group by t.songname,t.product_productid");
            ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
            Product product = productJpaCtrl.findProduct(productid);
            session.setAttribute("product", product);
//            List<Tracklist> tracklist = product.getTracklistList();
//            session.setAttribute("tracklist", tracklist);
            getServletContext().getRequestDispatcher("/productDetail.jsp").forward(request, response);
//TracklistJpaController trackJpaCrl = new TracklistJpaController(utx, emf);
//List<Tracklist> tracklist = trackJpaCrl.findTracklistEntities();
//List<Tracklist> tracklistList = new ArrayList<>();
//            for (Tracklist tracklist1 : tracklistList) {
//                if (tracklist1.getProduct().getProductid().equals(product.getProductid())) {
//                  tracklistList.add(tracklist1);
//                }
//            }
//            product.getTracklistList();
//            product.setTracklistList(tracklistList);
//            session.setAttribute("product", product);
//            getServletContext().getRequestDispatcher("/GetTracklist").forward(request, response);
//            if (product != null) {
//                List<Tracklist> tracklist = product.getTracklistList();
//                request.setAttribute("tracklist", tracklist);
//                getServletContext().getRequestDispatcher("/productDetail.jsp").forward(request, response);
//            }
        }
//          getServletContext().getRequestDispatcher("/productDetail.jsp").forward(request, response);
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
