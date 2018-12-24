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
public class TracklistPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SONGNAME")
    private String songname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "PRODUCT_PRODUCTID")
    private String productProductid;

    public TracklistPK() {
    }

    public TracklistPK(String songname, String productProductid) {
        this.songname = songname;
        this.productProductid = productProductid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (songname != null ? songname.hashCode() : 0);
        hash += (productProductid != null ? productProductid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TracklistPK)) {
            return false;
        }
        TracklistPK other = (TracklistPK) object;
        if ((this.songname == null && other.songname != null) || (this.songname != null && !this.songname.equals(other.songname))) {
            return false;
        }
        if ((this.productProductid == null && other.productProductid != null) || (this.productProductid != null && !this.productProductid.equals(other.productProductid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.TracklistPK[ songname=" + songname + ", productProductid=" + productProductid + " ]";
    }
    
}
