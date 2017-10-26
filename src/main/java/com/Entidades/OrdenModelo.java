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
@Table(name = "orden_modelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = OrdenModelo.LISTAR, query = "SELECT om FROM OrdenModelo om")})
public class OrdenModelo implements Serializable {

    public static final String BUSCAR_CODIGO_ESTADO = "OrdenModelo.codigoEstado";
    public static final String LISTAR = "OrdenModelo.listar";
    public static final String ULTIMO = "OrdenModelo.ultimo";
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrdenModeloPK ordenModeloPK;
    @JoinColumn(name = "idorden", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Orden orden;
    @JoinColumn(name = "idmodelo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Modelo modelo;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "total")
    private Double total;
    @Column(name = "material")
    private String material;
    @Column(name = "peso_material")
    private Double peso_material;
    @Column(name = "estado")
    private String estado;

    public OrdenModelo() {
    }

    public OrdenModelo(Modelo modelo, Orden orden, int cantidad, Double descuento, Double valor, Double total, String material, Double peso_material,String estado) {
        this.ordenModeloPK = new OrdenModeloPK();
        this.ordenModeloPK.setIdmodelo(modelo.getId());
        this.ordenModeloPK.setIdmodelo(orden.getId());
        this.modelo = modelo;
        this.orden = orden;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.valor = valor;
        this.material = material;
        this.peso_material = peso_material;
        this.total = total;
        this.estado= estado;

    }

    public OrdenModelo(OrdenModeloPK ordenModeloPK) {
        this.ordenModeloPK = ordenModeloPK;
    }

    public void setOrdenModeloPK(long modelo, long orden) {
        this.ordenModeloPK = new OrdenModeloPK(orden, modelo);
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getPeso_material() {
        return peso_material;
    }

    public void setPeso_material(Double peso_material) {
        this.peso_material = peso_material;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordenModeloPK != null ? ordenModeloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenModelo)) {
            return false;
        }
        OrdenModelo other = (OrdenModelo) object;
        if ((this.ordenModeloPK == null && other.ordenModeloPK != null) || (this.ordenModeloPK != null && !this.ordenModeloPK.equals(other.ordenModeloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrdenModelo[ ordenModeloPk=" + ordenModeloPK + " ]";
    }

}
