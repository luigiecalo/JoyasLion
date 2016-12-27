/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LuisGuillermo
 */
@Entity
@Table(name = "piedra_central")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PiedraCentral.findAll", query = "SELECT pc FROM PiedraCentral pc"),
    @NamedQuery(name = "PiedraCentral.findById", query = "SELECT pc FROM PiedraCentral pc WHERE pc.id = :id"),
    @NamedQuery(name = "PiedraCentral.findByCodigo", query = "SELECT pc FROM PiedraCentral pc WHERE pc.codigo = :codigo"),
    @NamedQuery(name = "PiedraCentral.findByDescripcion", query = "SELECT pc FROM PiedraCentral pc WHERE pc.descripcion = :descripcion"),
    @NamedQuery(name = "PiedraCentral.findByNombre", query = "SELECT pc FROM PiedraCentral pc WHERE pc.nombre = :nombre")})
public class PiedraCentral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "peso")
    private Double peso;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "tipo")
    private Tipo tipo;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "forma")
    private Tipo forma;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="piedra")
    private List<ModeloPiedraCentral> modelos;

    public PiedraCentral() {
    }

    public PiedraCentral(Long id) {
        this.id = id;
    }

    public PiedraCentral(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getForma() {
        return forma;
    }

    public void setForma(Tipo forma) {
        this.forma = forma;
    }

    public List<ModeloPiedraCentral> getModelos() {
        return modelos;
    }

    public void setModelos(List<ModeloPiedraCentral> modelos) {
        this.modelos = modelos;
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
        if (!(object instanceof PiedraCentral)) {
            return false;
        }
        PiedraCentral other = (PiedraCentral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.PiedraCentral[ id=" + id + " ]";
    }
    
}
