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
public class OrdenModeloPK implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "idorden")
    private long idorden;
    @Basic(optional = false)
    @Column(name = "idmodelo")
    private long idmodelo;
    @Basic(optional = false)
    @Column(name = "idmaterial")
    private long idmaterial;


    public OrdenModeloPK() {
    }

    public OrdenModeloPK(long idorden, long idmodelo,long idmaterial) {
        this.idorden = idorden;
        this.idmodelo = idmodelo;
        this.idmaterial=idmaterial;
    }

    public long getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(long idmodelo) {
        this.idmodelo = idmodelo;
    }

    public long getIdorden() {
        return idorden;
    }

    public void setIdorden(long idorden) {
        this.idorden = idorden;
    }

    public long getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(long idmaterial) {
        this.idmaterial = idmaterial;
    }

  



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmodelo;
        hash += (int) idorden;
        hash += (int) idmaterial;
        
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenModeloPK)) {
            return false;
        }
        OrdenModeloPK other = (OrdenModeloPK) object;
        if (this.idmodelo != other.idmodelo) {
            return false;
        }
        if (this.idorden != other.idorden) {
            return false;
        }
        if (this.idmaterial != other.idmaterial) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "OrdenModeloPK{" + "idorden=" + idorden + ", idmodelo=" + idmodelo + ", idmaterial=" + idmaterial + '}';
    }

   
}
