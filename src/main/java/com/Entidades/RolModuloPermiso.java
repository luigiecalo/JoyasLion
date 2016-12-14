/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "rol_modulo_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolModuloPermiso.findAll", query = "SELECT r FROM RolModuloPermiso r"),
    @NamedQuery(name = RolModuloPermiso.BUSCAR_MODULOS_ROL, query = "SELECT r FROM RolModuloPermiso r WHERE r.rolModuloPermisoPK.idrol = :idrol"),
    @NamedQuery(name = "RolModuloPermiso.findByIdpermiso", query = "SELECT r FROM RolModuloPermiso r WHERE r.rolModuloPermisoPK.idpermiso = :idpermiso"),
    @NamedQuery(name = "RolModuloPermiso.findByIdmodulo", query = "SELECT r FROM RolModuloPermiso r WHERE r.rolModuloPermisoPK.idmodulo = :idmodulo"),
    @NamedQuery(name = RolModuloPermiso.BUSCAR_PERMISOS_MODULO_ROL, query = "SELECT r FROM RolModuloPermiso r WHERE r.rolModuloPermisoPK.idmodulo = :idmodulo "
            + "AND r.rolModuloPermisoPK.idrol = :idrol")})
public class RolModuloPermiso implements Serializable {

    public static final String BUSCAR_PERMISOS_MODULO_ROL = "RolModuloPermiso.buscarpermisos";
     public static final String BUSCAR_MODULOS_ROL = "RolModuloPermiso.buscarmodulos";
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolModuloPermisoPK rolModuloPermisoPK;
    @JoinColumn(name = "idmodulo", referencedColumnName = "idmodulo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modulo modulo;
    @JoinColumn(name = "idpermiso", referencedColumnName = "idpermisos", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Permisos permisos;
    @JoinColumn(name = "idrol", referencedColumnName = "idrol", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;

    public RolModuloPermiso() {
    }

    public RolModuloPermiso(Rol rol, Modulo mod, Permisos per) {
        this.rolModuloPermisoPK = new RolModuloPermisoPK();
        this.rolModuloPermisoPK.setIdmodulo(mod.getIdmodulo());
        this.rolModuloPermisoPK.setIdpermiso(per.getIdpermisos());
        this.rolModuloPermisoPK.setIdrol(rol.getIdrol());
        this.modulo = mod;
        this.rol = rol;
        this.permisos = per;
    }

    public RolModuloPermiso(RolModuloPermisoPK rolModuloPermisoPK) {
        this.rolModuloPermisoPK = rolModuloPermisoPK;
    }

    public RolModuloPermiso(long idrol, long idpermiso, long idmodulo) {
        this.rolModuloPermisoPK = new RolModuloPermisoPK(idrol, idpermiso, idmodulo);
    }

    public RolModuloPermisoPK getRolModuloPermisoPK() {
        return rolModuloPermisoPK;
    }

    public void setRolModuloPermisoPK(RolModuloPermisoPK rolModuloPermisoPK) {
        this.rolModuloPermisoPK = rolModuloPermisoPK;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolModuloPermisoPK != null ? rolModuloPermisoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolModuloPermiso)) {
            return false;
        }
        RolModuloPermiso other = (RolModuloPermiso) object;
        if ((this.rolModuloPermisoPK == null && other.rolModuloPermisoPK != null) || (this.rolModuloPermisoPK != null && !this.rolModuloPermisoPK.equals(other.rolModuloPermisoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.RolModuloPermiso[ rolModuloPermisoPK=" + rolModuloPermisoPK + " ]";
    }

}
