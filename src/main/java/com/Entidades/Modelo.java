/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LuisGuillermo
 */
@Entity
@Table(name = "modelo")
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = Modelo.LISTAR, query = "SELECT m FROM Modelo m"),
    @NamedQuery(name = Modelo.BUSCAR_CODIGO_ESTADO, query = "SELECT m FROM Modelo m WHERE m.codigo = :codigo AND m.estado =:estado"),
    @NamedQuery(name = Modelo.ULTIMO, query = "SELECT COUNT(m.id)+1 FROM Modelo m")})
public class Modelo implements Serializable {

    public static final String BUSCAR_CODIGO_ESTADO = "Modelo.codigoEstado";
    public static final String LISTAR = "Modelo.listar";
    public static final String ULTIMO = "Modelo.ultimo";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "imagen")
    private String imagen;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "tipo_modelo")
    private Tipo tipo_modelo;
    @Basic(optional = false)
    @Column(name = "peso_modelo")
    private Double peso_modelo;
    @Basic(optional = false)
    @Column(name = "peso_circones")
    private Double peso_circones;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "modelo")
    private List<ModeloPiedraCentral> piedra_centrales;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "modelo")
    private List<ModeloCircon> modelo_circon;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "modelo")
    private List<OrdenModelo> orden_modelo;

    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;

    public Modelo() {
    }

    public Modelo(Long id, String codigo, String imagen,
            Tipo tipo_modelo, Double peso_modelo, Double peso_circones,
            String estado) {
        this.id = id;
        this.codigo = codigo;
        this.imagen = imagen;
        this.tipo_modelo = tipo_modelo;
        this.peso_modelo = peso_modelo;
        this.peso_circones = peso_circones;
        this.estado = estado;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Tipo getTipo_modelo() {
        return tipo_modelo;
    }

    public void setTipo_modelo(Tipo tipo_modelo) {
        this.tipo_modelo = tipo_modelo;
    }

    public Double getPeso_modelo() {
        return peso_modelo;
    }

    public void setPeso_modelo(Double peso_modelo) {
        this.peso_modelo = peso_modelo;
    }

    public Double getPeso_circones() {
        return peso_circones;
    }

    public void setPeso_circones(Double peso_circones) {
        this.peso_circones = peso_circones;
    }

    @XmlTransient
    public List<ModeloCircon> getModelo_circon() {
        return modelo_circon;
    }

    public void setModelo_circon(List<ModeloCircon> modelo_circon) {
        this.modelo_circon = modelo_circon;
    }

    public List<OrdenModelo> getOrden_modelo() {
        return orden_modelo;
    }

    public void setOrden_modelo(List<OrdenModelo> orden_modelo) {
        this.orden_modelo = orden_modelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ModeloPiedraCentral> getPiedra_centrales() {
        return piedra_centrales;
    }

    public void setPiedra_centrales(List<ModeloPiedraCentral> piedra_centrales) {
        this.piedra_centrales = piedra_centrales;
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
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo{" + "id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", tipo_modelo=" + tipo_modelo + ", peso_modelo=" + peso_modelo + ", peso_circones=" + peso_circones + ", piedra_centrales=" + piedra_centrales + ", modelo_circon=" + modelo_circon + ", estado=" + estado + '}';
    }

}
