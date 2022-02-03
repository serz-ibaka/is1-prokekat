/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparesources;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sergej
 */
@Embeddable
public class PrenosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idrac")
    private int idrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rednibroj")
    private int rednibroj;

    public PrenosPK() {
    }

    public PrenosPK(int idrac, int rednibroj) {
        this.idrac = idrac;
        this.rednibroj = rednibroj;
    }

    public int getIdrac() {
        return idrac;
    }

    public void setIdrac(int idrac) {
        this.idrac = idrac;
    }

    public int getRednibroj() {
        return rednibroj;
    }

    public void setRednibroj(int rednibroj) {
        this.rednibroj = rednibroj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idrac;
        hash += (int) rednibroj;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrenosPK)) {
            return false;
        }
        PrenosPK other = (PrenosPK) object;
        if (this.idrac != other.idrac) {
            return false;
        }
        if (this.rednibroj != other.rednibroj) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.PrenosPK[ idrac=" + idrac + ", rednibroj=" + rednibroj + " ]";
    }
    
}
