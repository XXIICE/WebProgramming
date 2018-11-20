/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jpa.model.Orderitem;
import jpa.model.Product;

/**
 *
 * @author INT303
 */
public class ShoppingCart implements Serializable {

    private Map<String, Orderitem> cart;

    public ShoppingCart() {
        cart = new HashMap();
    }

    public void add(Product p) {
//        LineItem line = cart.get(p.getProductid());
        Orderitem order = cart.get(p.getProductid());
        if (order == null) {
//            cart.put(p.getProductid(), new LineItem(p));
            cart.put(p.getProductid(), new Orderitem(p));
        } else {
            order.setQuantity(order.getQuantity() + 1);
        }

    }

    public void remove(Product p) {
        this.remove(p.getProductid());

    }

    public void remove(String productId) {

        cart.remove(productId);

    }

    public double getTotalPrice() {
        double sum = 0;
        Collection<Orderitem> orderItems = cart.values();
        for (Orderitem orderItem : orderItems) {
            sum += orderItem.getTotalPrice();
        }
        return sum;
    }

    public int getTotalQuantity() {
        int sum = 0;
        Collection<Orderitem> orderItems = cart.values();
        for (Orderitem orderItem : orderItems) {
            sum += orderItem.getQuantity();
        }
        return sum;
    }

    public List<Orderitem> getOrderItems() {
        return new ArrayList(cart.values());
    }

    public void delete(Product p) {
        Orderitem order = cart.get(p.getProductid());
        if (order.getQuantity() == 1) {
            this.remove(p.getProductid());
        } else if (order.getQuantity() > 1) {
            order.setQuantity(order.getQuantity() - 1);
        }
    }

}
