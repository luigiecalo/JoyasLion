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
@Table(name = "modelo_piedraCentral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModeloPiedracentral.findAll", query = "SELECT mp FROM ModeloPiedracentral mp")})
public class ModeloPiedracentral implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "idmodelo")
    private Modelo modelo;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "idpiedra")
    private PiedraCentral piedra;

    public ModeloPiedracentral() {
    }

    public ModeloPiedracentral(Long idrol) {
        this.id = idrol;
    }

    public ModeloPiedracentral(Long idrol, Modelo id_modelo, PiedraCentral id_piedra) {
        this.id = idrol;
        this.modelo = id_modelo;
        this.piedra = id_piedra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public PiedraCentral getPiedra() {
        return piedra;
    }

    public void setPiedra(PiedraCentral piedra) {
        this.piedra = piedra;
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
        if (!(object instanceof ModeloPiedracentral)) {
            return false;
        }
        ModeloPiedracentral other = (ModeloPiedracentral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.ModuloPiedra[ idrol=" + id + " ]";
    }
    
}
