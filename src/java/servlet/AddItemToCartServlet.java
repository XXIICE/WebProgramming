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
import java.util.Random;
import java.util.UUID;
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
import jpa.model.CartPK;
import jpa.model.Customer;
import jpa.model.Lineitem;
import jpa.model.Orderitem;
import jpa.model.Orderitem;
import jpa.model.Product;
import jpa.model.Productorder;
import jpa.model.controller.CartJpaController;
import jpa.model.controller.CustomerJpaController;
import jpa.model.controller.LineitemJpaController;
import jpa.model.controller.OrderitemJpaController;
import jpa.model.controller.ProductJpaController;
import jpa.model.controller.ProductorderJpaController;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;
import model.LineItem;
import model.ShoppingCart2;

/**
 *
 * @author INT303
 */
public class AddItemToCartServlet extends HttpServlet {

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
                CartPK caPk = new CartPK();
                int idC = cartJpaCtrl.getCartCount()+1;
                caPk.setCartid(idC);
                caPk.setCustomerUsername(custom.getUsername());
                ca.setCartPK(caPk);
                ca.setCustomer(custom);
                
                
                try {
                    cartJpaCtrl.create(ca);
                } catch (Exception ex) {
                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                Lineitem line = new Lineitem();
                LineitemJpaController lineJpaCtrl = new LineitemJpaController(utx, emf);
                line.setCart(ca);
                int idL = lineJpaCtrl.getLineitemCount() + 1;
                line.setLineitemid(idL);
                LineItem lines= new LineItem();
                line.setQuantity(lines.getQuantity()+1);
                line.setTotalprice(lines.getTotalPrice()+p.getPrice());
                line.setProductProductid(p);
                List<Lineitem> lineList = lineJpaCtrl.findLineitemEntities();
                    for (Lineitem lineitem : lineList) {
                        if (productid.equals(line.getProductProductid())) {
//                            line.setQuantity(lines.getQuantity()+1);
//                            try {
//                                lineJpaCtrl.edit(line);
//                            } catch (RollbackFailureException ex) {
//                                Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                            } catch (Exception ex) {
//                                Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            System.out.println(lines.getQuantity());

                       
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
            getServletContext().getRequestDispatcher("/ProductList").forward(request, response);

        }

    }
//        HttpSession session = request.getSession(false);
//        String productid = request.getParameter("productid");
//        ShoppingCart2 cart = (ShoppingCart2) session.getAttribute("cart");
//        Customer custom = (Customer) session.getAttribute("custom");
//        if (cart == null) {
//            cart = new ShoppingCart2();
//            session.setAttribute("cart", cart);
//        }
//        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
//        Product p = productJpaCtrl.findProduct(productid);
//
//        if (productid != null) {
//            cart.add(p);
//            session.setAttribute("cart", cart);
//            Orderitem orderItem = new Orderitem();
//            OrderitemJpaController orderJpaCtrl = new OrderitemJpaController(utx, emf);
//            int orderId = orderJpaCtrl.getOrderitemCount() + 1;
//            orderItem.setOrderitemid(orderId);
//            int quantity = orderItem.getQuantity() + 1;
//            orderItem.setQuantity(quantity);
//            Cart ca = new Cart(1);
//            CartJpaController cartJpaCtrl = new CartJpaController(utx, emf);
//            orderItem.setCartCartid(ca);
//            //cartjpa add for..
//            List<Orderitem> lineList = orderJpaCtrl.findOrderitemEntities();
//            List<Orderitem> lineListCart = new ArrayList<>();
//            for (Orderitem lineitem : lineListCart) {
//                if (ca.getCartid() == orderItem.getCartCartid().getCartid()) {
//                    lineListCart.add(lineitem);
//                }
//            }
//            ca.setOrderitemList(lineListCart);
//            request.setAttribute("cart", ca);
//        getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
//       
//        }
//        HttpSession session = request.getSession(false);
//        String productid = request.getParameter("productid");
//        ShoppingCart2 cart = (ShoppingCart2) session.getAttribute("cart");
//        Customer custom = (Customer) session.getAttribute("custom");
//        CustomerJpaController customJpaCtrl = new CustomerJpaController(utx, emf);
//        if (cart == null) {
//            cart = new ShoppingCart2();
//            session.setAttribute("cart", cart);
//        }
//        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
//        Product p = productJpaCtrl.findProduct(productid);
//        request.setAttribute("cart", cart);
//        if (productid != null) {
//            cart.add(p);
//            session.setAttribute("cart", cart);
//            
//            getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
//        }
//        if (custom != null) {
//            if (productid != null) {
//                cart.add(p);
//                session.setAttribute("cart", cart);
//
//                Cart ca = new Cart();
//                ca.setCartid(1);
//                CartJpaController cartJpaCtrl = new CartJpaController(utx, emf);
//                try {
//                    cartJpaCtrl.create(ca);
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                Productorder productOrder = new Productorder();
//                ProductorderJpaController productOrderJpaCtrl = new ProductorderJpaController(utx, emf);
//                int idproorder = productOrderJpaCtrl.getProductorderCount() + 1;
//                productOrder.setOrderid(idproorder);
////                productOrder.setTotalprice(cart.getTotalPrice());
//                productOrder.setProductstatus("Preparing for shipping");
////                productOrder.setCustomerUsername(custom);
//
//                
//                try {
//                    productOrderJpaCtrl.create(productOrder);
//
//                } catch (PreexistingEntityException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                Orderitem orderItem = new Orderitem();
//                OrderitemJpaController orderJpaCtrl = new OrderitemJpaController(utx, emf);
//                int orderId = orderJpaCtrl.getOrderitemCount() + 1;
//                orderItem.setOrderitemid(orderId);
////                int quantity = orderItem.getQuantity() + 1;
////                orderItem.setQuantity(quantity);
//                
////                orderItem.setProductorderOrderid(productOrder);
//                try {
//                    orderJpaCtrl.create(orderItem);
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                
//                List<Productorder> productorderList = productOrderJpaCtrl.findProductorderEntities();
//                List<Productorder> productorderListCart = new ArrayList<>();
//                for (Productorder productorder : productorderList) {
//                    if (productorder.getCustomerUsername().getUsername() == custom.getUsername()) {
//                        productorderListCart.add(productorder);
//                    }
//                }
//                custom.setProductorderList(productorderListCart);
//                try {
//                    customJpaCtrl.edit(custom);
//                    session.setAttribute("custom", custom);
//                    request.setAttribute("cart", ca);
//                    getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
//
//                } catch (NonexistentEntityException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddItemToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
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
