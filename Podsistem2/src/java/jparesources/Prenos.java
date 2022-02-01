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
@Table(name = "prenos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prenos.findAll", query = "SELECT p FROM Prenos p"),
    @NamedQuery(name = "Prenos.findByIdrac", query = "SELECT p FROM Prenos p WHERE p.prenosPK.idrac = :idrac"),
    @NamedQuery(name = "Prenos.findByRednibroj", query = "SELECT p FROM Prenos p WHERE p.prenosPK.rednibroj = :rednibroj")})
public class Prenos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrenosPK prenosPK;
    @JoinColumn(name = "idracna", referencedColumnName = "idrac")
    @ManyToOne(optional = false)
    private Racun idracna;
    @JoinColumn(name = "idracsa", referencedColumnName = "idrac")
    @ManyToOne(optional = false)
    private Racun idracsa;
    @JoinColumns({
        @JoinColumn(name = "idrac", referencedColumnName = "idrac", insertable = false, updatable = false),
        @JoinColumn(name = "rednibroj", referencedColumnName = "rednibroj", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Transakcija transakcija;

    public Prenos() {
    }

    public Prenos(PrenosPK prenosPK) {
        this.prenosPK = prenosPK;
    }

    public Prenos(int idrac, int rednibroj) {
        this.prenosPK = new PrenosPK(idrac, rednibroj);
    }

    public PrenosPK getPrenosPK() {
        return prenosPK;
    }

    public void setPrenosPK(PrenosPK prenosPK) {
        this.prenosPK = prenosPK;
    }

    public Racun getIdracna() {
        return idracna;
    }

    public void setIdracna(Racun idracna) {
        this.idracna = idracna;
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
        hash += (prenosPK != null ? prenosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prenos)) {
            return false;
        }
        Prenos other = (Prenos) object;
        if ((this.prenosPK == null && other.prenosPK != null) || (this.prenosPK != null && !this.prenosPK.equals(other.prenosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Prenos[ prenosPK=" + prenosPK + " ]";
    }
    
}
