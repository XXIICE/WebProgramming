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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Orderitem;
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;

/**
 *
 * @author ariya boonchoo
 */
public class SearchServlet extends HttpServlet {

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
//        HttpSession session = request.getSession(false);
        String search = request.getParameter("search");
        Product p = new Product();
        Product productObj = (Product) request.getAttribute("productList");
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
//        product = productJpaCtrl.findProduct(product.getProductname());

//        request.setAttribute("productList", productList);
//         Product product2 = productJpaCtrl.findProduct(productObj.getArtist());
        if (search != null && search.length() > 0) {
            System.out.println("22222222222222");
//            System.out.println(productObj.getProductname());
//            System.out.println(search.length());
//            if (productObj != null) {       

            try {
//                search = productObj.getProductname().substring(0, search.length() - 1);
//                String p1 = productObj.getProductname().substring(0, search.length()-1);
//              p.setProductname(p1);
                search = productObj.getProductname();
                p = productJpaCtrl.findProduct(search);
                
//           System.out.println(search+p.getProductname().substring(search.length()));
//                String prod = p.getProductname().substring(0, search.length() - 1);
//            p.setProductname(prod);
//            p = productJpaCtrl.findProduct(search+p.getProductname().substring(search.length()));
//                System.out.println(search+p.getProductname().substring(search.length()));
                if (p.getProductname().substring(0, search.length() - 1).equalsIgnoreCase(search.substring(0, search.length() - 1))
//                        || p.getArtist().substring(0, search.length() - 1).equalsIgnoreCase(search.substring(0, search.length() - 1))
                        ) {

//            if (p.getArtist().equalsIgnoreCase(search.substring(0, search.length()-1))) {
                    System.out.println("11111111111");
                    List<Product> productList = productJpaCtrl.findProductEntities();
//                request.setAttribute("productList", productList);
                    request.setAttribute("productList", productList);
//                getServletContext().getRequestDispatcher("/resultSearch.jsp").forward(request, response);
                    getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
//            } else if (p.getArtist().equalsIgnoreCase(search)) {
//                System.out.println("11111111111");
//                List<Product> productList = productJpaCtrl.findProductEntities();
////                request.setAttribute("productList", productList);
//                session.setAttribute("productList", productList);
////                getServletContext().getRequestDispatcher("/resultSearch.jsp").forward(request, response);
//                getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
//            }
                }
//                search = productObj.getArtist().substring(0, search.length() - 1);
                p = productJpaCtrl.findProduct(search);
                if (p.getArtist().substring(0, search.length() - 1).equalsIgnoreCase(search.substring(0, search.length() - 1))) {
                    System.out.println("11111111111");
                    List<Product> productList = productJpaCtrl.findProductEntities();
                    request.setAttribute("productList", productList);
                    getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
                }
            } catch (Exception ex) {

            }
        }
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
