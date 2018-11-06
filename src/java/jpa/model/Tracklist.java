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
@Table(name = "TRACKLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tracklist.findAll", query = "SELECT t FROM Tracklist t")
    , @NamedQuery(name = "Tracklist.findBySongname", query = "SELECT t FROM Tracklist t WHERE t.songname = :songname")
    , @NamedQuery(name = "Tracklist.findByProductProductid", query = "SELECT t FROM Tracklist t WHERE t.productProductid = :productProductid")})
public class Tracklist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SONGNAME")
    private String songname;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "PRODUCT_PRODUCTID")
    private String productProductid;
    @JoinColumn(name = "PRODUCT_PRODUCTID", referencedColumnName = "PRODUCTID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Product product;

    public Tracklist() {
    }

    public Tracklist(String productProductid) {
        this.productProductid = productProductid;
    }

    public Tracklist(String productProductid, String songname) {
        this.productProductid = productProductid;
        this.songname = songname;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getProductProductid() {
        return productProductid;
    }

    public void setProductProductid(String productProductid) {
        this.productProductid = productProductid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productProductid != null ? productProductid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tracklist)) {
            return false;
        }
        Tracklist other = (Tracklist) object;
        if ((this.productProductid == null && other.productProductid != null) || (this.productProductid != null && !this.productProductid.equals(other.productProductid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Tracklist[ productProductid=" + productProductid + " ]";
    }
    
}
