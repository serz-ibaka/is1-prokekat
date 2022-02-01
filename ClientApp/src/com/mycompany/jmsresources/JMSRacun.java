/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsresources;

import java.util.Date;

/**
 *
 * @author Sergej
 */
public class JMSRacun {
    
    private int idRac;
    private double stanje;
    private double dozvoljeniMinus;
    private Date otvaranje;
    private char status;
    private int brojTransakcija;
    private int idKom;
    private int idMes;

    public int getIdRac() {
        return idRac;
    }

    public void setIdRac(int idRac) {
        this.idRac = idRac;
    }

    public double getStanje() {
        return stanje;
    }

    public void setStanje(double stanje) {
        this.stanje = stanje;
    }

    public double getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(double dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public Date getOtvaranje() {
        return otvaranje;
    }

    public void setOtvaranje(Date otvaranje) {
        this.otvaranje = otvaranje;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(int brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    public int getIdKom() {
        return idKom;
    }

    public void setIdKom(int idKom) {
        this.idKom = idKom;
    }

    public int getIdMes() {
        return idMes;
    }

    public void setIdMes(int idMes) {
        this.idMes = idMes;
    }
    
    
    
}
