/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ariya boonchoo
 */
@Entity
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByPaymentid", query = "SELECT p FROM Payment p WHERE p.paymentid = :paymentid")
    , @NamedQuery(name = "Payment.findByPaymentstatus", query = "SELECT p FROM Payment p WHERE p.paymentstatus = :paymentstatus")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "PAYMENTID")
    private String paymentid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PAYMENTSTATUS")
    private String paymentstatus;
    @JoinColumn(name = "PRODUCTORDER_ODERID", referencedColumnName = "ODERID")
    @OneToOne(optional = false)
    private Productorder productorderOderid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "paymentPaymentid")
    private Productorder productorder;

    public Payment() {
    }

    public Payment(String paymentid) {
        this.paymentid = paymentid;
    }

    public Payment(String paymentid, String paymentstatus) {
        this.paymentid = paymentid;
        this.paymentstatus = paymentstatus;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public Productorder getProductorderOderid() {
        return productorderOderid;
    }

    public void setProductorderOderid(Productorder productorderOderid) {
        this.productorderOderid = productorderOderid;
    }

    public Productorder getProductorder() {
        return productorder;
    }

    public void setProductorder(Productorder productorder) {
        this.productorder = productorder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentid != null ? paymentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentid == null && other.paymentid != null) || (this.paymentid != null && !this.paymentid.equals(other.paymentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Payment[ paymentid=" + paymentid + " ]";
    }
    
}
