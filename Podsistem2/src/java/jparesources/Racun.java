/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparesources;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sergej
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdrac", query = "SELECT r FROM Racun r WHERE r.idrac = :idrac"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByDozvoljeniminus", query = "SELECT r FROM Racun r WHERE r.dozvoljeniminus = :dozvoljeniminus"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByOtvaranje", query = "SELECT r FROM Racun r WHERE r.otvaranje = :otvaranje"),
    @NamedQuery(name = "Racun.findByBrojtransakcija", query = "SELECT r FROM Racun r WHERE r.brojtransakcija = :brojtransakcija")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrac")
    private Integer idrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stanje")
    private float stanje = 0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dozvoljeniminus")
    private float dozvoljeniminus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Character status = 'A';
    @Basic(optional = false)
    @NotNull
    @Column(name = "otvaranje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otvaranje = new Date(System.currentTimeMillis());
    @Basic(optional = false)
    @NotNull
    @Column(name = "brojtransakcija")
    private int brojtransakcija = 0;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idracsa")
    private List<Isplata> isplataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "racun")
    private List<Transakcija> transakcijaList;
    @JoinColumn(name = "idkom", referencedColumnName = "idkom")
    @ManyToOne(optional = false)
    private Komitent idkom;
    @JoinColumn(name = "idmes", referencedColumnName = "idmes")
    @ManyToOne(optional = false)
    private Mesto idmes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idracna")
    private List<Prenos> prenosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idracsa")
    private List<Prenos> prenosList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idracna")
    private List<Uplata> uplataList;

    public Racun() {
    }

    public Racun(Integer idrac) {
        this.idrac = idrac;
    }

    public Racun(Integer idrac, float stanje, float dozvoljeniminus, Character status, Date otvaranje, int brojtransakcija) {
        this.idrac = idrac;
        this.stanje = stanje;
        this.dozvoljeniminus = dozvoljeniminus;
        this.status = status;
        this.otvaranje = otvaranje;
        this.brojtransakcija = brojtransakcija;
    }

    public Integer getIdrac() {
        return idrac;
    }

    public void setIdrac(Integer idrac) {
        this.idrac = idrac;
    }

    public float getStanje() {
        return stanje;
    }

    public void setStanje(float stanje) {
        this.stanje = stanje;
    }

    public float getDozvoljeniminus() {
        return dozvoljeniminus;
    }

    public void setDozvoljeniminus(float dozvoljeniminus) {
        this.dozvoljeniminus = dozvoljeniminus;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Date getOtvaranje() {
        return otvaranje;
    }

    public void setOtvaranje(Date otvaranje) {
        this.otvaranje = otvaranje;
    }

    public int getBrojtransakcija() {
        return brojtransakcija;
    }

    public void setBrojtransakcija(int brojtransakcija) {
        this.brojtransakcija = brojtransakcija;
    }

    @XmlTransient
    public List<Isplata> getIsplataList() {
        return isplataList;
    }

    public void setIsplataList(List<Isplata> isplataList) {
        this.isplataList = isplataList;
    }

    @XmlTransient
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    public Komitent getIdkom() {
        return idkom;
    }

    public void setIdkom(Komitent idkom) {
        this.idkom = idkom;
    }

    public Mesto getIdmes() {
        return idmes;
    }

    public void setIdmes(Mesto idmes) {
        this.idmes = idmes;
    }

    @XmlTransient
    public List<Prenos> getPrenosList() {
        return prenosList;
    }

    public void setPrenosList(List<Prenos> prenosList) {
        this.prenosList = prenosList;
    }

    @XmlTransient
    public List<Prenos> getPrenosList1() {
        return prenosList1;
    }

    public void setPrenosList1(List<Prenos> prenosList1) {
        this.prenosList1 = prenosList1;
    }

    @XmlTransient
    public List<Uplata> getUplataList() {
        return uplataList;
    }

    public void setUplataList(List<Uplata> uplataList) {
        this.uplataList = uplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrac != null ? idrac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idrac == null && other.idrac != null) || (this.idrac != null && !this.idrac.equals(other.idrac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Racun[ idrac=" + idrac + " ]";
    }
    
}
