/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ariya boonchoo
 */
@Entity
@Table(name = "REVIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
    , @NamedQuery(name = "Review.findByReviewid", query = "SELECT r FROM Review r WHERE r.reviewid = :reviewid")
    , @NamedQuery(name = "Review.findByComment", query = "SELECT r FROM Review r WHERE r.comment = :comment")
    , @NamedQuery(name = "Review.findByCommentdate", query = "SELECT r FROM Review r WHERE r.commentdate = :commentdate")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REVIEWID")
    private Integer reviewid;
    @Size(max = 255)
    @Column(name = "Comment")
    private String comment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date commentdate;
    @JoinColumn(name = "CUSTOMER_USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Customer customerUsername;
    @JoinColumn(name = "PRODUCT_PRODUCTID", referencedColumnName = "PRODUCTID")
    @ManyToOne(optional = false)
    private Product productProductid;

    public Review() {
    }

    public Review(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public Review(Integer reviewid, Date commentdate) {
        this.reviewid = reviewid;
        this.commentdate = commentdate;
    }

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
    }

    public Customer getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(Customer customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Product getProductProductid() {
        return productProductid;
    }

    public void setProductProductid(Product productProductid) {
        this.productProductid = productProductid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewid != null ? reviewid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewid == null && other.reviewid != null) || (this.reviewid != null && !this.reviewid.equals(other.reviewid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Review[ reviewid=" + reviewid + " ]";
    }
    
}
