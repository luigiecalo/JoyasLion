/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LuisGuillermo
 */
@Entity
@Table(name = "miembro")
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Miembro.findAll", query = "SELECT m FROM Miembro m"),
    @NamedQuery(name = "Miembro.findByIdmiembro", query = "SELECT m FROM Miembro m WHERE m.idmiembro = :idmiembro"),
    @NamedQuery(name = Miembro.BUSCAR_USUARIO, query = "SELECT m FROM Miembro m WHERE m.usuario = :usuario")})
public class Miembro implements Serializable {

    
    public static final String BUSCAR_USUARIO = "Miembro.buscarDocumento";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @Basic(optional = false)
    @Column(name = "idmiembro")
    private Long idmiembro;
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "nombre1")
    private String nombre1;
    @Column(name = "nombre2")
    private String nombre2;
    @Basic(optional = false)
    @Column(name = "apellido1")
    private String apellido1;
    @Column(name = "apellido2")
    private String apellido2;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "idusuarios")
    private Usuario usuario;

    public Miembro() {
    }

    public Miembro(Long idmiembro) {
        this.idmiembro = idmiembro;
    }

    public Miembro(Long idmiembro, String nombre1, String apellido1, String estado, Usuario usuarios) {
        this.idmiembro = idmiembro;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
        this.estado = estado;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getIdmiembro() {
        return idmiembro;
    }

    public void setIdmiembro(Long idmiembro) {
        this.idmiembro = idmiembro;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
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
        hash += (idmiembro != null ? idmiembro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembro)) {
            return false;
        }
        Miembro other = (Miembro) object;
        if ((this.idmiembro == null && other.idmiembro != null) || (this.idmiembro != null && !this.idmiembro.equals(other.idmiembro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidades.Miembro[ idmiembro=" + idmiembro + " ]";
    }

}
