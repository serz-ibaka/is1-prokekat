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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdmes", query = "SELECT m FROM Mesto m WHERE m.idmes = :idmes"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv"),
    @NamedQuery(name = "Mesto.findByPostanskibroj", query = "SELECT m FROM Mesto m WHERE m.postanskibroj = :postanskibroj")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmes")
    private Integer idmes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "postanskibroj")
    private int postanskibroj;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmes", fetch = FetchType.EAGER)
    private List<Filijala> filijalaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmes", fetch = FetchType.EAGER)
    private List<Komitent> komitentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmes", fetch = FetchType.EAGER)
    private List<Racun> racunList;

    public Mesto() {
    }

    public Mesto(Integer idmes) {
        this.idmes = idmes;
    }

    public Mesto(Integer idmes, String naziv, int postanskibroj) {
        this.idmes = idmes;
        this.naziv = naziv;
        this.postanskibroj = postanskibroj;
    }

    public Integer getIdmes() {
        return idmes;
    }

    public void setIdmes(Integer idmes) {
        this.idmes = idmes;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getPostanskibroj() {
        return postanskibroj;
    }

    public void setPostanskibroj(int postanskibroj) {
        this.postanskibroj = postanskibroj;
    }

    @XmlTransient
    public List<Filijala> getFilijalaList() {
        return filijalaList;
    }

    public void setFilijalaList(List<Filijala> filijalaList) {
        this.filijalaList = filijalaList;
    }

    @XmlTransient
    public List<Komitent> getKomitentList() {
        return komitentList;
    }

    public void setKomitentList(List<Komitent> komitentList) {
        this.komitentList = komitentList;
    }

    @XmlTransient
    public List<Racun> getRacunList() {
        return racunList;
    }

    public void setRacunList(List<Racun> racunList) {
        this.racunList = racunList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmes != null ? idmes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idmes == null && other.idmes != null) || (this.idmes != null && !this.idmes.equals(other.idmes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jparesources.Mesto[ idmes=" + idmes + " ]";
    }
    
}
