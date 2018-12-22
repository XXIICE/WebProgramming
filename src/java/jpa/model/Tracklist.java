/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yang
 */
@Entity
@Table(name = "TRACKLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tracklist.findAll", query = "SELECT t FROM Tracklist t")
    , @NamedQuery(name = "Tracklist.findBySongname", query = "SELECT t FROM Tracklist t WHERE t.tracklistPK.songname = :songname")
    , @NamedQuery(name = "Tracklist.findByProductProductid", query = "SELECT t FROM Tracklist t WHERE t.tracklistPK.productProductid = :productProductid")})
public class Tracklist implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TracklistPK tracklistPK;
    @JoinColumn(name = "PRODUCT_PRODUCTID", referencedColumnName = "PRODUCTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public Tracklist() {
    }

    public Tracklist(TracklistPK tracklistPK) {
        this.tracklistPK = tracklistPK;
    }

    public Tracklist(String songname, String productProductid) {
        this.tracklistPK = new TracklistPK(songname, productProductid);
    }

    public TracklistPK getTracklistPK() {
        return tracklistPK;
    }

    public void setTracklistPK(TracklistPK tracklistPK) {
        this.tracklistPK = tracklistPK;
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
        hash += (tracklistPK != null ? tracklistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tracklist)) {
            return false;
        }
        Tracklist other = (Tracklist) object;
        if ((this.tracklistPK == null && other.tracklistPK != null) || (this.tracklistPK != null && !this.tracklistPK.equals(other.tracklistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Tracklist[ tracklistPK=" + tracklistPK + " ]";
    }
    
}
