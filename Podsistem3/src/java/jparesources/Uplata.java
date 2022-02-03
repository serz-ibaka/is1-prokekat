/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparesources;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "uplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uplata.findAll", query = "SELECT u FROM Uplata u"),
    @NamedQuery(name = "Uplata.findByIdrac", query = "SELECT u FROM Uplata u WHERE u.uplataPK.idrac = :idrac"),
    @NamedQuery(name = "Uplata.findByRednibroj", query = "SELECT u FROM Uplata u WHERE u.uplataPK.rednibroj = :rednibroj")})
public class Uplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UplataPK uplataPK;
    @JoinColumn(name = "idfil", referencedColumnName = "idfil")
    @ManyToOne(optional = false)
    private Filijala idfil;
    @JoinColumn(name = "idracna", referencedColumnName = "idrac")
    @ManyToOne(optional = false)
    private Racun idracna;
    @JoinColumns({
        @JoinColumn(name = "idrac", referencedColumnName = "idrac", insertable = false, updatable = false),
        @JoinColumn(name = "rednibroj", referencedColumnName = "rednibroj", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Transakcija transakcija;

    public Uplata() {
    }

    public Uplata(UplataPK uplataPK) {
        this.uplataPK = uplataPK;
    }

    public Uplata(int idrac, int rednibroj) {
        this.uplataPK = new UplataPK(idrac, rednibroj);
    }

    public UplataPK getUplataPK() {
        return uplataPK;
    }

    public void setUplataPK(UplataPK uplataPK) {
        this.uplataPK = uplataPK;
    }

    public Filijala getIdfil() {
        return idfil;
    }

    public void setIdfil(Filijala idfil) {
        this.idfil = idfil;
    }

    public Racun getIdracna() {
        return idracna;
    }

    public void setIdracna(Racun idracna) {
        this.idracna = idracna;
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
        hash += (uplataPK != null ? uplataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uplata)) {
            return false;
        }
        Uplata other = (Uplata) object;
        if ((this.uplataPK == null && other.uplataPK != null) || (this.uplataPK != null && !this.uplataPK.equals(other.uplataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Uplata[ uplataPK=" + uplataPK + " ]";
    }
    
}
