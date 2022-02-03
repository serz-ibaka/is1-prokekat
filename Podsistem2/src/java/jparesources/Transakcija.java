/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparesources;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sergej
 */
@Entity
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIdrac", query = "SELECT t FROM Transakcija t WHERE t.transakcijaPK.idrac = :idrac"),
    @NamedQuery(name = "Transakcija.findByRednibroj", query = "SELECT t FROM Transakcija t WHERE t.transakcijaPK.rednibroj = :rednibroj"),
    @NamedQuery(name = "Transakcija.findByIznos", query = "SELECT t FROM Transakcija t WHERE t.iznos = :iznos"),
    @NamedQuery(name = "Transakcija.findByObavljanje", query = "SELECT t FROM Transakcija t WHERE t.obavljanje = :obavljanje"),
    @NamedQuery(name = "Transakcija.findBySvrha", query = "SELECT t FROM Transakcija t WHERE t.svrha = :svrha")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransakcijaPK transakcijaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iznos")
    private float iznos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obavljanje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date obavljanje = new Date(System.currentTimeMillis());
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "svrha")
    private String svrha;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transakcija", fetch = FetchType.EAGER)
    private Isplata isplata;
    @JoinColumn(name = "idrac", referencedColumnName = "idrac", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Racun racun;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transakcija", fetch = FetchType.EAGER)
    private Prenos prenos;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transakcija", fetch = FetchType.EAGER)
    private Uplata uplata;

    public Transakcija() {
    }

    public Transakcija(TransakcijaPK transakcijaPK) {
        this.transakcijaPK = transakcijaPK;
    }

    public Transakcija(TransakcijaPK transakcijaPK, float iznos, Date obavljanje, String svrha) {
        this.transakcijaPK = transakcijaPK;
        this.iznos = iznos;
        this.obavljanje = obavljanje;
        this.svrha = svrha;
    }

    public Transakcija(int idrac, int rednibroj) {
        this.transakcijaPK = new TransakcijaPK(idrac, rednibroj);
    }

    public TransakcijaPK getTransakcijaPK() {
        return transakcijaPK;
    }

    public void setTransakcijaPK(TransakcijaPK transakcijaPK) {
        this.transakcijaPK = transakcijaPK;
    }

    public float getIznos() {
        return iznos;
    }

    public void setIznos(float iznos) {
        this.iznos = iznos;
    }

    public Date getObavljanje() {
        return obavljanje;
    }

    public void setObavljanje(Date obavljanje) {
        this.obavljanje = obavljanje;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public Isplata getIsplata() {
        return isplata;
    }

    public void setIsplata(Isplata isplata) {
        this.isplata = isplata;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Prenos getPrenos() {
        return prenos;
    }

    public void setPrenos(Prenos prenos) {
        this.prenos = prenos;
    }

    public Uplata getUplata() {
        return uplata;
    }

    public void setUplata(Uplata uplata) {
        this.uplata = uplata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transakcijaPK != null ? transakcijaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.transakcijaPK == null && other.transakcijaPK != null) || (this.transakcijaPK != null && !this.transakcijaPK.equals(other.transakcijaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Transakcija[ transakcijaPK=" + transakcijaPK + " ]";
    }
    
}
