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
public class JMSTransakcija {

    private int idRac;
    private int redniBroj;
    private double iznos;
    private Date obavljanje;
    private String svrha;

    public int getIdRac() {
        return idRac;
    }

    public void setIdRac(int idRac) {
        this.idRac = idRac;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
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
    
    
    
}
