/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ariya boonchoo
 */
@Entity
@Table(name = "LINEITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lineitem.findAll", query = "SELECT l FROM Lineitem l")
    , @NamedQuery(name = "Lineitem.findByLineitemid", query = "SELECT l FROM Lineitem l WHERE l.lineitemid = :lineitemid")
    , @NamedQuery(name = "Lineitem.findByQuantity", query = "SELECT l FROM Lineitem l WHERE l.quantity = :quantity")})
public class Lineitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "LINEITEMID")
    private String lineitemid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @JoinColumn(name = "CART_CARTID", referencedColumnName = "CARTID")
    @ManyToOne(optional = false)
    private Cart cartCartid;

    public Lineitem() {
    }

    public Lineitem(String lineitemid) {
        this.lineitemid = lineitemid;
    }

    public Lineitem(String lineitemid, int quantity) {
        this.lineitemid = lineitemid;
        this.quantity = quantity;
    }

    public String getLineitemid() {
        return lineitemid;
    }

    public void setLineitemid(String lineitemid) {
        this.lineitemid = lineitemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCartCartid() {
        return cartCartid;
    }

    public void setCartCartid(Cart cartCartid) {
        this.cartCartid = cartCartid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineitemid != null ? lineitemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineitem)) {
            return false;
        }
        Lineitem other = (Lineitem) object;
        if ((this.lineitemid == null && other.lineitemid != null) || (this.lineitemid != null && !this.lineitemid.equals(other.lineitemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Lineitem[ lineitemid=" + lineitemid + " ]";
    }
    
}
