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
import jpa.model.Product;

/**
 *
 * @author ariya boonchoo
 */
public class Favorites implements Serializable {
     private Map<String,LineItem> itemFav;

    public Favorites() {
        itemFav = new HashMap();
    }

    public void add(Product p) {
        LineItem line = itemFav.get(p.getProductid());
        if (line == null) {
            itemFav.put(p.getProductid(), new LineItem(p));
        }

    }

     public void remove(String productId) {
        
        itemFav.remove(productId);

    }
    
     public int getTotalQuantity(){
        int sum = 0;
        Collection<LineItem>lineItems = itemFav.values();
         for (LineItem lineItem : lineItems) {
            sum+= lineItem.getQuantity();
        }
       return sum;
    }
     public List<LineItem> getLineItems(){
        return new ArrayList(itemFav.values());
    }

}
