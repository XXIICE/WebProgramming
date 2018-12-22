/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yang
 */
@Entity
@Table(name = "PRODUCTORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productorder.findAll", query = "SELECT p FROM Productorder p")
    , @NamedQuery(name = "Productorder.findByOrderid", query = "SELECT p FROM Productorder p WHERE p.orderid = :orderid")
    , @NamedQuery(name = "Productorder.findByTotalprice", query = "SELECT p FROM Productorder p WHERE p.totalprice = :totalprice")
    , @NamedQuery(name = "Productorder.findByProductstatus", query = "SELECT p FROM Productorder p WHERE p.productstatus = :productstatus")
    , @NamedQuery(name = "Productorder.findByTrackingno", query = "SELECT p FROM Productorder p WHERE p.trackingno = :trackingno")
    , @NamedQuery(name = "Productorder.findByAddress", query = "SELECT p FROM Productorder p WHERE p.address = :address")})
public class Productorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERID")
    private Integer orderid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTALPRICE")
    private Double totalprice;
    @Size(max = 50)
    @Column(name = "PRODUCTSTATUS")
    private String productstatus;
    @Size(max = 50)
    @Column(name = "TRACKINGNO")
    private String trackingno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ADDRESS")
    private String address;
    @OneToOne(mappedBy = "productorderOrderid")
    private Payment payment;
    @JoinColumns({
        @JoinColumn(name = "CART_CARTID", referencedColumnName = "CARTID")
        , @JoinColumn(name = "CART_USERNAME", referencedColumnName = "CUSTOMER_USERNAME")})
    @OneToOne
    private Cart cart;
    @JoinColumn(name = "CUSTOMER_USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Customer customerUsername;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productorderOrderid")
    private List<Orderitem> orderitemList;

    public Productorder() {
    }

    public Productorder(Integer orderid) {
        this.orderid = orderid;
    }

    public Productorder(Integer orderid, String address) {
        this.orderid = orderid;
        this.address = address;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public String getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(String productstatus) {
        this.productstatus = productstatus;
    }

    public String getTrackingno() {
        return trackingno;
    }

    public void setTrackingno(String trackingno) {
        this.trackingno = trackingno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Customer getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(Customer customerUsername) {
        this.customerUsername = customerUsername;
    }

    @XmlTransient
    public List<Orderitem> getOrderitemList() {
        return orderitemList;
    }

    public void setOrderitemList(List<Orderitem> orderitemList) {
        this.orderitemList = orderitemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productorder)) {
            return false;
        }
        Productorder other = (Productorder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Productorder[ orderid=" + orderid + " ]";
    }
    
}
