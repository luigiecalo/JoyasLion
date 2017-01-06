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
@Table(name = "orden")
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = Orden.USUARIO_LISTA, query = "SELECT o FROM Orden o Where o.cliente=:cliente"),
    @NamedQuery(name = Orden.LISTAR, query = "SELECT o FROM Orden o"),
    @NamedQuery(name = Orden.BUSCAR_CODIGO_ESTADO, query = "SELECT o FROM Orden o WHERE o.codigo = :codigo AND o.estado =:estado"),
    @NamedQuery(name = Orden.ULTIMO, query = "SELECT COUNT(o.id)+1 FROM Orden o WHERE o.codigo LIKE :codigo")})
public class Orden implements Serializable {

    public static final String BUSCAR_CODIGO_ESTADO = "Orden.codigoEstado";
    public static final String LISTAR = "Orden.listar";
    public static final String USUARIO_LISTA = "Orden.usuariolista";
    public static final String ULTIMO = "Orden.ultimo";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "fecha")
    private Long fecha;
    @Column(name = "codigo")
    private String codigo;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "usuario")
    private Usuario usuario;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "cliente")
    private Usuario cliente;
    @Basic(optional = false)
    @Column(name = "valor_total")
    private Double valor_total;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "orden")
    private List<OrdenModelo> ordenesModelo;

    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;

    public Orden() {
    }

    public Orden(Long id, String codigo, Long fecha, Usuario usuario,
            Usuario cliente, Double valor_total,
            String estado) {
        this.id = id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.usuario = usuario;
        this.cliente = cliente;
        this.valor_total = valor_total;
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

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    @XmlTransient
    public List<OrdenModelo> getOrdenesModelo() {
        return ordenesModelo;
    }

    public void setOrdenesModelo(List<OrdenModelo> ordenesModelo) {
        this.ordenesModelo = ordenesModelo;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Orden{" + "id=" + id + ", fecha=" + fecha + ", codigo=" + codigo + ", usuario=" + usuario + ", cliente=" + cliente + ", valor_total=" + valor_total + ", ordenesModelo=" + ordenesModelo + ", estado=" + estado + '}';
    }

}
