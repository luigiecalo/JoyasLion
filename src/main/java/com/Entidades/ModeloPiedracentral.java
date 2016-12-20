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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
     @EmbeddedId
    protected ModeloPiedraCentralPK modeloPiedraCentralPk;
    @JoinColumn(name = "idmodelo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    private Modelo modelo;
    @JoinColumn(name = "idpiedra", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    private PiedraCentral piedra;
    @Column(name = "cantidad")
    private int cantidad;

    public ModeloPiedracentral() {
    }

    

    public ModeloPiedracentral(Modelo modelo, PiedraCentral piedra, int cantidad) {
        this.modeloPiedraCentralPk = new ModeloPiedraCentralPK();
        this.modeloPiedraCentralPk.setIdmodelo(modelo.getId());
        this.modeloPiedraCentralPk.setIdpiedra(piedra.getId());
        this.modelo = modelo;
        this.piedra = piedra;
        this.cantidad = cantidad;
    }

      public ModeloPiedracentral(ModeloPiedraCentralPK modeloPiedraCentralPk) {
        this.modeloPiedraCentralPk = modeloPiedraCentralPk;
    }

    public ModeloPiedracentral(long modelo, long piedra, int cantidad) {
        this.modeloPiedraCentralPk = new ModeloPiedraCentralPK(modelo, piedra);
    }

    public ModeloPiedraCentralPK getModeloPiedraCentralPk() {
        return modeloPiedraCentralPk;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modeloPiedraCentralPk != null ? modeloPiedraCentralPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModeloPiedracentral)) {
            return false;
        }
        ModeloPiedracentral other = (ModeloPiedracentral) object;
        if ((this.modeloPiedraCentralPk == null && other.modeloPiedraCentralPk != null) || (this.modeloPiedraCentralPk != null && !this.modeloPiedraCentralPk.equals(other.modeloPiedraCentralPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloPiedracentral{" + "modeloPiedraCentralPk=" + modeloPiedraCentralPk + ", cantidad=" + cantidad + '}';
    }


   
    
}
