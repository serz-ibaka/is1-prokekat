/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparesources;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sergej
 */
@Entity
@Table(name = "filijala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filijala.findAll", query = "SELECT f FROM Filijala f"),
    @NamedQuery(name = "Filijala.findByIdfil", query = "SELECT f FROM Filijala f WHERE f.idfil = :idfil"),
    @NamedQuery(name = "Filijala.findByNaziv", query = "SELECT f FROM Filijala f WHERE f.naziv = :naziv"),
    @NamedQuery(name = "Filijala.findByAdresa", query = "SELECT f FROM Filijala f WHERE f.adresa = :adresa")})
public class Filijala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfil")
    private Integer idfil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adresa")
    private String adresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idfil")
    private List<Isplata> isplataList;
    @JoinColumn(name = "idmes", referencedColumnName = "idmes")
    @ManyToOne(optional = false)
    private Mesto idmes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idfil")
    private List<Uplata> uplataList;

    public Filijala() {
    }

    public Filijala(Integer idfil) {
        this.idfil = idfil;
    }

    public Filijala(Integer idfil, String naziv, String adresa) {
        this.idfil = idfil;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    public Integer getIdfil() {
        return idfil;
    }

    public void setIdfil(Integer idfil) {
        this.idfil = idfil;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @XmlTransient
    public List<Isplata> getIsplataList() {
        return isplataList;
    }

    public void setIsplataList(List<Isplata> isplataList) {
        this.isplataList = isplataList;
    }

    public Mesto getIdmes() {
        return idmes;
    }

    public void setIdmes(Mesto idmes) {
        this.idmes = idmes;
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
        hash += (idfil != null ? idfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filijala)) {
            return false;
        }
        Filijala other = (Filijala) object;
        if ((this.idfil == null && other.idfil != null) || (this.idfil != null && !this.idfil.equals(other.idfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Filijala[ idfil=" + idfil + " ]";
    }
    
}
