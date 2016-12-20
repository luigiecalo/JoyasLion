/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import com.Dao.ModeloDaoimplement;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "modelo_cricon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModeloCircon.findAll", query = "SELECT mc FROM ModeloCircon mc")})
public class ModeloCircon implements Serializable {


    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ModeloCirconPK modeloCirconPk;
    @JoinColumn(name = "idmodelo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    private Modelo modelo;
    @JoinColumn(name = "idcircon", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    private Circon circon;
    @Column(name = "cantidad")
    private int cantidad;

    public ModeloCircon() {
    }

    public ModeloCircon(Modelo modelo, Circon circon, int cantidad) {
        this.modeloCirconPk = new ModeloCirconPK();
        this.modeloCirconPk.setIdmodelo(modelo.getId());
        this.modeloCirconPk.setIdcircon(circon.getId());
        this.modelo = modelo;
        this.circon = circon;
        this.cantidad = cantidad;
    }

    public ModeloCircon(ModeloCirconPK modeloCirconPk) {
        this.modeloCirconPk = modeloCirconPk;
    }

    public ModeloCircon(long modelo, long circon, int cantidad) {
        this.modeloCirconPk = new ModeloCirconPK(modelo, circon);
    }

    public ModeloCirconPK getRolModeloPermisoPK() {
        return modeloCirconPk;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Circon getCircon() {
        return circon;
    }

    public void setCircon(Circon circon) {
        this.circon = circon;
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
        hash += (modeloCirconPk != null ? modeloCirconPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModeloCircon)) {
            return false;
        }
        ModeloCircon other = (ModeloCircon) object;
        if ((this.modeloCirconPk == null && other.modeloCirconPk != null) || (this.modeloCirconPk != null && !this.modeloCirconPk.equals(other.modeloCirconPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.ModeloCircon[ modeloCirconPk=" + modeloCirconPk + " ]";
    }

}
