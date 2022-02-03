/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparesources;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sergej
 */
@Entity
@Table(name = "isplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Isplata.findAll", query = "SELECT i FROM Isplata i"),
    @NamedQuery(name = "Isplata.findByIdrac", query = "SELECT i FROM Isplata i WHERE i.isplataPK.idrac = :idrac"),
    @NamedQuery(name = "Isplata.findByRednibroj", query = "SELECT i FROM Isplata i WHERE i.isplataPK.rednibroj = :rednibroj")})
public class Isplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IsplataPK isplataPK;
    @JoinColumn(name = "idfil", referencedColumnName = "idfil")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Filijala idfil;
    @JoinColumn(name = "idracsa", referencedColumnName = "idrac")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Racun idracsa;
    @JoinColumns({
        @JoinColumn(name = "idrac", referencedColumnName = "idrac", insertable = false, updatable = false),
        @JoinColumn(name = "rednibroj", referencedColumnName = "rednibroj", insertable = false, updatable = false)})
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Transakcija transakcija;

    public Isplata() {
    }

    public Isplata(IsplataPK isplataPK) {
        this.isplataPK = isplataPK;
    }

    public Isplata(int idrac, int rednibroj) {
        this.isplataPK = new IsplataPK(idrac, rednibroj);
    }

    public IsplataPK getIsplataPK() {
        return isplataPK;
    }

    public void setIsplataPK(IsplataPK isplataPK) {
        this.isplataPK = isplataPK;
    }

    public Filijala getIdfil() {
        return idfil;
    }

    public void setIdfil(Filijala idfil) {
        this.idfil = idfil;
    }

    public Racun getIdracsa() {
        return idracsa;
    }

    public void setIdracsa(Racun idracsa) {
        this.idracsa = idracsa;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isplataPK != null ? isplataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Isplata)) {
            return false;
        }
        Isplata other = (Isplata) object;
        if ((this.isplataPK == null && other.isplataPK != null) || (this.isplataPK != null && !this.isplataPK.equals(other.isplataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Isplata[ isplataPK=" + isplataPK + " ]";
    }
    
}
