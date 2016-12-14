/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "modulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.findByIdmodulo", query = "SELECT m FROM Modulo m WHERE m.idmodulo = :idmodulo"),
    @NamedQuery(name = "Modulo.findByNombre", query = "SELECT m FROM Modulo m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Modulo.findByDescripcion", query = "SELECT m FROM Modulo m WHERE m.src = :src"),
    @NamedQuery(name = "Modulo.findByIcono", query = "SELECT m FROM Modulo m WHERE m.icono = :icono")})
public class Modulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmodulo")
    private Long idmodulo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "src")
    private String src;
    @Column(name = "icono")
    private String icono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    private List<RolModuloPermiso> rolModuloPermisoList;
    @Column(name = "posicion")
    private int posicion;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "idgrupo")
    private Grupo grupomodulo ;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "idsubgrupos")
    private SubGrupo subgrupos ;

    public Modulo() {
    }
    

    public Modulo(Modulo mo) {
        this.idmodulo = mo.idmodulo;
        this.nombre = mo.nombre;
        this.src = mo.src;
        this.icono = mo.icono;
        this.rolModuloPermisoList = mo.rolModuloPermisoList;
        this.posicion = mo.posicion;
        this.grupomodulo = mo.grupomodulo;
        this.subgrupos = mo.subgrupos;
    }

    public Modulo(Long idmodulo) {
        this.idmodulo = idmodulo;
    }

    public Long getIdmodulo() {
        return idmodulo;
    }

    public void setIdmodulo(Long idmodulo) {
        this.idmodulo = idmodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    @XmlTransient
    public List<RolModuloPermiso> getRolModuloPermisoList() {
        return rolModuloPermisoList;
    }

    public void setRolModuloPermisoList(List<RolModuloPermiso> rolModuloPermisoList) {
        this.rolModuloPermisoList = rolModuloPermisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodulo != null ? idmodulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.idmodulo == null && other.idmodulo != null) || (this.idmodulo != null && !this.idmodulo.equals(other.idmodulo))) {
            return false;
        }
        return true;
    }

    public Grupo getGrupomodulo() {
        return grupomodulo;
    }

    public void setGrupomodulo(Grupo grupomodulo) {
        this.grupomodulo = grupomodulo;
    }

    public SubGrupo getSubgrupos() {
        return subgrupos;
    }

    public void setSubgrupos(SubGrupo subgrupos) {
        this.subgrupos = subgrupos;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "Modulo{" + "idmodulo=" + idmodulo + ", nombre=" + nombre + ", src=" + src + ", icono=" + icono + ", posicion=" + posicion + ", grupomodulo=" + grupomodulo + ", subgrupos=" + subgrupos + '}';
    }
    

   
    
}
