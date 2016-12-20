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
public class ModeloPiedraCentralPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idmodelo")
    private long idmodelo;
    @Basic(optional = false)
    @Column(name = "idpiedra")
    private long idpiedra;


    public ModeloPiedraCentralPK() {
    }

    public ModeloPiedraCentralPK(long idrol, long idpermiso) {
        this.idmodelo = idrol;
        this.idpiedra = idpermiso;
    }

    public long getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(long idmodelo) {
        this.idmodelo = idmodelo;
    }

    public long getIdpiedra() {
        return idpiedra;
    }

    public void setIdpiedra(long idpiedra) {
        this.idpiedra = idpiedra;
    }

  



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmodelo;
        hash += (int) idpiedra;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModeloPiedraCentralPK)) {
            return false;
        }
        ModeloPiedraCentralPK other = (ModeloPiedraCentralPK) object;
        if (this.idmodelo != other.idmodelo) {
            return false;
        }
        if (this.idpiedra != other.idpiedra) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.MODULO_PIEDRACENTRALPK[ MOULO=" + idmodelo + ", idpiedra=" + idpiedra +  " ]";
    }
    
}
