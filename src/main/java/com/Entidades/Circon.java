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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "circon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Circon.findAll", query = "SELECT c FROM Circon c")})
public class Circon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "muestra")
    @Basic(optional = false)
    private Double muestra;
    @Column(name = "referencia")
    private String referencia;
    @Basic(optional = false)
    @Column(name = "precio")
    private Double precio;
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "circon")
    private List<ModeloCircon> modelo_Circon;

    public Circon() {
    }

    public Circon(Long idpermisos) {
        this.id = idpermisos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getMuestra() {
        return muestra;
    }

    public void setMuestra(Double muestra) {
        this.muestra = muestra;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<ModeloCircon> getModelo_Circon() {
        return modelo_Circon;
    }

    public void setModelo_Circon(List<ModeloCircon> modelo_Circon) {
        this.modelo_Circon = modelo_Circon;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Circon)) {
            return false;
        }
        Circon other = (Circon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.Circon[ idCircon=" + id + " ]";
    }

}
