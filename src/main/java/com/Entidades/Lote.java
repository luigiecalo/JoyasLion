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
@Table(name = "lote")
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = Lote.USUARIO_LISTA, query = "SELECT l FROM Lote l Where l.encargado=:encargado"),
    @NamedQuery(name = Lote.LISTAR, query = "SELECT l FROM Lote l"),
    @NamedQuery(name = Lote.BUSCAR_CODIGO_ESTADO, query = "SELECT l FROM Lote l WHERE l.codigo = :codigo AND l.estado =:estado"),
    @NamedQuery(name = Lote.BUSCAR_ESTADO, query = "SELECT l FROM Lote l WHERE l.estado =:estado"),
    @NamedQuery(name = Lote.ULTIMO, query = "SELECT COUNT(l.id)+1 FROM Lote l WHERE l.codigo LIKE :codigo")})
public class Lote implements Serializable {

    public static final String BUSCAR_CODIGO_ESTADO = "Lote.codigoEstado";
    public static final String BUSCAR_ESTADO = "Lote.Estado";
    public static final String LISTAR = "Lote.listar";
    public static final String USUARIO_LISTA = "Lote.usuariolista";
    public static final String ULTIMO = "Lote.ultimo";
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
    @JoinColumn(name = "encargado")
    private Usuario encargado;
    @Basic(optional = false)
    @Column(name = "cantidad_modelos")
    private int cantidad_modelos;
    @Column(name = "valor")
    private Double valor;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "lote")
    private List<LoteModeloOrden> loteModeloOrden;

    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;

    public Lote() {
    }

    public Lote(Long id, String codigo, Long fecha, Usuario usuario,
            Usuario encargado,int cantidad_modelos,Double valor,
            String estado) {
        this.id = id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.usuario = usuario;
        this.encargado = encargado;
        this.cantidad_modelos = cantidad_modelos;
        this.valor = valor;
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

    public Usuario getEncargado() {
        return encargado;
    }

    public void setEncargado(Usuario encargado) {
        this.encargado = encargado;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getCantidad_modelos() {
        return cantidad_modelos;
    }

    public void setCantidad_modelos(int cantidad_modelos) {
        this.cantidad_modelos = cantidad_modelos;
    }
    

    @XmlTransient
    public List<LoteModeloOrden> getLoteModeloOrden() {
        return loteModeloOrden;
    }

    public void setLoteModeloOrden(List<LoteModeloOrden> loteModeloOrden) {
        this.loteModeloOrden = loteModeloOrden;
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
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lote{" + "id=" + id + ", fecha=" + fecha + ", codigo=" + codigo + ", usuario=" + usuario + ", encargado=" + encargado + ", cantidad_modelos=" + cantidad_modelos + ", valor=" + valor + ", estado=" + estado + '}';
    }

}
