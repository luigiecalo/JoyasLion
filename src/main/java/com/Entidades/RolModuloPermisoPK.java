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
public class RolModuloPermisoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idrol")
    private long idrol;
    @Basic(optional = false)
    @Column(name = "idpermiso")
    private long idpermiso;
    @Basic(optional = false)
    @Column(name = "idmodulo")
    private long idmodulo;

    public RolModuloPermisoPK() {
    }

    public RolModuloPermisoPK(long idrol, long idpermiso, long idmodulo) {
        this.idrol = idrol;
        this.idpermiso = idpermiso;
        this.idmodulo = idmodulo;
    }

    public long getIdrol() {
        return idrol;
    }

    public void setIdrol(long idrol) {
        this.idrol = idrol;
    }

    public long getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(long idpermiso) {
        this.idpermiso = idpermiso;
    }

    public long getIdmodulo() {
        return idmodulo;
    }

    public void setIdmodulo(long idmodulo) {
        this.idmodulo = idmodulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idrol;
        hash += (int) idpermiso;
        hash += (int) idmodulo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolModuloPermisoPK)) {
            return false;
        }
        RolModuloPermisoPK other = (RolModuloPermisoPK) object;
        if (this.idrol != other.idrol) {
            return false;
        }
        if (this.idpermiso != other.idpermiso) {
            return false;
        }
        if (this.idmodulo != other.idmodulo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.RolModuloPermisoPK[ idrol=" + idrol + ", idpermiso=" + idpermiso + ", idmodulo=" + idmodulo + " ]";
    }
    
}
