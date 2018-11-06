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
 * @author ariya boonchoo
 */
@Entity
@Table(name = "PRODUCTORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productorder.findAll", query = "SELECT p FROM Productorder p")
    , @NamedQuery(name = "Productorder.findByOderid", query = "SELECT p FROM Productorder p WHERE p.oderid = :oderid")
    , @NamedQuery(name = "Productorder.findByProductstatus", query = "SELECT p FROM Productorder p WHERE p.productstatus = :productstatus")
    , @NamedQuery(name = "Productorder.findByTotalprice", query = "SELECT p FROM Productorder p WHERE p.totalprice = :totalprice")
    , @NamedQuery(name = "Productorder.findByTrackingno", query = "SELECT p FROM Productorder p WHERE p.trackingno = :trackingno")})
public class Productorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ODERID")
    private String oderid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTSTATUS")
    private String productstatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALPRICE")
    private int totalprice;
    @Size(max = 50)
    @Column(name = "TRACKINGNO")
    private String trackingno;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "productorderOderid")
    private Payment payment;
    @JoinColumn(name = "CART_CARTID", referencedColumnName = "CARTID")
    @OneToOne(optional = false)
    private Cart cartCartid;
    @JoinColumn(name = "CUSTOMER_USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Customer customerUsername;
    @JoinColumn(name = "PAYMENT_PAYMENTID", referencedColumnName = "PAYMENTID")
    @OneToOne(optional = false)
    private Payment paymentPaymentid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productorderOderid")
    private List<Orderitem> orderitemList;

    public Productorder() {
    }

    public Productorder(String oderid) {
        this.oderid = oderid;
    }

    public Productorder(String oderid, String productstatus, int totalprice) {
        this.oderid = oderid;
        this.productstatus = productstatus;
        this.totalprice = totalprice;
    }

    public String getOderid() {
        return oderid;
    }

    public void setOderid(String oderid) {
        this.oderid = oderid;
    }

    public String getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(String productstatus) {
        this.productstatus = productstatus;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public String getTrackingno() {
        return trackingno;
    }

    public void setTrackingno(String trackingno) {
        this.trackingno = trackingno;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Cart getCartCartid() {
        return cartCartid;
    }

    public void setCartCartid(Cart cartCartid) {
        this.cartCartid = cartCartid;
    }

    public Customer getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(Customer customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Payment getPaymentPaymentid() {
        return paymentPaymentid;
    }

    public void setPaymentPaymentid(Payment paymentPaymentid) {
        this.paymentPaymentid = paymentPaymentid;
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
        hash += (oderid != null ? oderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productorder)) {
            return false;
        }
        Productorder other = (Productorder) object;
        if ((this.oderid == null && other.oderid != null) || (this.oderid != null && !this.oderid.equals(other.oderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Productorder[ oderid=" + oderid + " ]";
    }
    
}
