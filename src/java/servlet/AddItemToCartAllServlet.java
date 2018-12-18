/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import jpa.model.Lineitem;
import jpa.model.Product;
import jpa.model.controller.CartJpaController;
import jpa.model.controller.LineitemJpaController;
import jpa.model.controller.ProductJpaController;
import model.LineItem;
import model.ShoppingCart2;

/**
 *
 * @author waran
 */
public class AddItemToCartAllServlet extends HttpServlet {
    

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
        ShoppingCart2 cart = (ShoppingCart2) session.getAttribute("cart");
        String productid = request.getParameter("productid");
        Customer custom = (Customer) session.getAttribute("custom");
        if (cart == null) {
            cart = new ShoppingCart2();
            session.setAttribute("cart", cart);
        }
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        Product p = productJpaCtrl.findProduct(productid);
        if (productid != null) {
            cart.add(p);
            if (custom != null) {
                Cart ca = new Cart();
                CartJpaController cartJpaCtrl = new CartJpaController(utx, emf);
                ca.setCartid(1);
                try {
                    cartJpaCtrl.create(ca);
                } catch (Exception ex) {
                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                Lineitem line = new Lineitem();
                LineitemJpaController lineJpaCtrl = new LineitemJpaController(utx, emf);
                line.setCartCartid(ca);
                int idL = lineJpaCtrl.getLineitemCount() + 1;
                line.setLineitemid(idL);
                LineItem lines= new LineItem();
                line.setQuantity(lines.getQuantity()+1);
                line.setTotalprice(lines.getTotalPrice()+p.getPrice());
                line.setProductProductid(p);
                List<Lineitem> lineList = lineJpaCtrl.findLineitemEntities();
                    for (Lineitem lineitem : lineList) {
                        if (productid.equals(line.getProductProductid())) {
                       lineList.add(line);
                    }
                    }
                try {
                    lineJpaCtrl.create(line);
                    ca.setLineitemList(lineList);
                     
                } catch (Exception ex) {
                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
//                List<Lineitem> lineList = lineJpaCtrl.findLineitemEntities();
//                List<Lineitem> lines = new ArrayList<>();
//                for (Lineitem line1 : lines) {
//                    if (line1.getCartCartid().getCartid() == ca.getCartid()) {
//                        lines.add(line);
//                    }
//                }

//                ca.setLineitemList(lines);
//                try {
//                    cartJpaCtrl.create(ca); 
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
            session.setAttribute("cart", cart);
            getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);

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
