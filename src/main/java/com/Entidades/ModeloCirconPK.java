/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author usuario
 */
@Embeddable
public class ModeloCirconPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idmodelo")
    private long idmodelo;
    @Basic(optional = false)
    @Column(name = "idcircon")
    private long idcircon;


    public ModeloCirconPK() {
    }

    public ModeloCirconPK(long idrol, long idpermiso) {
        this.idmodelo = idrol;
        this.idcircon = idpermiso;
    }

    public long getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(long idmodelo) {
        this.idmodelo = idmodelo;
    }

    public long getIdcircon() {
        return idcircon;
    }

    public void setIdcircon(long idcircon) {
        this.idcircon = idcircon;
    }

  



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmodelo;
        hash += (int) idcircon;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModeloCirconPK)) {
            return false;
        }
        ModeloCirconPK other = (ModeloCirconPK) object;
        if (this.idmodelo != other.idmodelo) {
            return false;
        }
        if (this.idcircon != other.idcircon) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.MODULO_CIRCONPK[ MOULOCIRCON=" + idmodelo + ", idpermiso=" + idcircon +  " ]";
    }
    
}
