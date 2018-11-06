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
@Table(name = "ORDERITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderitem.findAll", query = "SELECT o FROM Orderitem o")
    , @NamedQuery(name = "Orderitem.findByOrderitemid", query = "SELECT o FROM Orderitem o WHERE o.orderitemid = :orderitemid")})
public class Orderitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ORDERITEMID")
    private String orderitemid;
    @JoinColumn(name = "PRODUCT_PRODUCTID", referencedColumnName = "PRODUCTID")
    @ManyToOne(optional = false)
    private Product productProductid;
    @JoinColumn(name = "PRODUCTORDER_ODERID", referencedColumnName = "ODERID")
    @ManyToOne(optional = false)
    private Productorder productorderOderid;

    public Orderitem() {
    }

    public Orderitem(String orderitemid) {
        this.orderitemid = orderitemid;
    }

    public String getOrderitemid() {
        return orderitemid;
    }

    public void setOrderitemid(String orderitemid) {
        this.orderitemid = orderitemid;
    }

    public Product getProductProductid() {
        return productProductid;
    }

    public void setProductProductid(Product productProductid) {
        this.productProductid = productProductid;
    }

    public Productorder getProductorderOderid() {
        return productorderOderid;
    }

    public void setProductorderOderid(Productorder productorderOderid) {
        this.productorderOderid = productorderOderid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderitemid != null ? orderitemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderitem)) {
            return false;
        }
        Orderitem other = (Orderitem) object;
        if ((this.orderitemid == null && other.orderitemid != null) || (this.orderitemid != null && !this.orderitemid.equals(other.orderitemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Orderitem[ orderitemid=" + orderitemid + " ]";
    }
    
}
