/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LuisGuillermo
 */
@Entity
@Table(name = "modelo_imagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModeloImagen.findAll", query = "SELECT m FROM ModeloImagen m"),
    @NamedQuery(name = ModeloImagen.BUSCAR_MODULO, query = "SELECT m FROM ModeloImagen m WHERE m.modelo = :idmodelo"),
    @NamedQuery(name = ModeloImagen.ULTIMO, query = "SELECT MAX(m.id)+1 FROM ModeloImagen m")})
public class ModeloImagen implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_MODULO = "ModeloImagen.modulo";
    public static final String ULTIMO = "ModeloImagen.ultimo";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "idmodelo", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Modelo modelo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
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
        if (!(object instanceof ModeloImagen)) {
            return false;
        }
        ModeloImagen other = (ModeloImagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ModeloImagen[ id=" + id + " ]";
    }

}
