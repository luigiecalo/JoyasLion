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
public class LoteModeloOrdenPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idorden")
    private long idorden;
    @Basic(optional = false)
    @Column(name = "idmodelo")
    private long idmodelo;
    @Column(name = "idlote")
    private long idlote;

    public LoteModeloOrdenPK() {
    }

    public LoteModeloOrdenPK(long idorden, long idmodelo, long idlote) {
        this.idorden = idorden;
        this.idmodelo = idmodelo;
        this.idlote = idlote;
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

    public long getIdlote() {
        return idlote;
    }

    public void setIdlote(long idlote) {
        this.idlote = idlote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmodelo;
        hash += (int) idorden;
        hash += (int) idlote;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoteModeloOrdenPK)) {
            return false;
        }
        LoteModeloOrdenPK other = (LoteModeloOrdenPK) object;
        if (this.idmodelo != other.idmodelo) {
            return false;
        }
        if (this.idorden != other.idorden) {
            return false;
        }
        if (this.idlote != other.idlote) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "LoteModeloOrdenPK{" + "idorden=" + idorden + ", idmodelo=" + idmodelo + ", idlote=" + idlote + '}';
    }

   

}
