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
    , @NamedQuery(name = "Orderitem.findByOrderitemid", query = "SELECT o FROM Orderitem o WHERE o.orderitemid = :orderitemid")
    , @NamedQuery(name = "Orderitem.findByQuantity", query = "SELECT o FROM Orderitem o WHERE o.quantity = :quantity")
    , @NamedQuery(name = "Orderitem.findByPrice", query = "SELECT o FROM Orderitem o WHERE o.price = :price")})
public class Orderitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERITEMID")
    private Integer orderitemid;
    @Column(name = "QUANTITY")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICE")
    private Double price;
    @JoinColumn(name = "PRODUCT_PRODUCTID", referencedColumnName = "PRODUCTID")
    @ManyToOne(optional = false)
    private Product productProductid;
    @JoinColumn(name = "PRODUCTORDER_ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Productorder productorderOrderid;

    public Orderitem() {
    }

    public Orderitem(Integer orderitemid) {
        this.orderitemid = orderitemid;
    }

    public Orderitem(Product productid) {
        this(productid, 1);
    }

    public Orderitem(Product product, int quantity) {
        this.productProductid = product;
        this.quantity = quantity;
        this.price=price;
    }
    public Integer getOrderitemid() {
        return orderitemid;
    }

    public void setOrderitemid(Integer orderitemid) {
        this.orderitemid = orderitemid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return this.price*this.quantity;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProductProductid() {
        return productProductid;
    }

    public void setProductProductid(Product productProductid) {
        this.productProductid = productProductid;
    }

    public Productorder getProductorderOrderid() {
        return productorderOrderid;
    }

    public void setProductorderOrderid(Productorder productorderOrderid) {
        this.productorderOrderid = productorderOrderid;
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
