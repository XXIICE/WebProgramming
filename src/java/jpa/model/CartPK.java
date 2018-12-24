/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ariya boonchoo
 */
@Embeddable
public class CartPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CARTID")
    private int cartid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CUSTOMER_USERNAME")
    private String customerUsername;

    public CartPK() {
    }

    public CartPK(int cartid, String customerUsername) {
        this.cartid = cartid;
        this.customerUsername = customerUsername;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cartid;
        hash += (customerUsername != null ? customerUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartPK)) {
            return false;
        }
        CartPK other = (CartPK) object;
        if (this.cartid != other.cartid) {
            return false;
        }
        if ((this.customerUsername == null && other.customerUsername != null) || (this.customerUsername != null && !this.customerUsername.equals(other.customerUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.CartPK[ cartid=" + cartid + ", customerUsername=" + customerUsername + " ]";
    }
    
}
