/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

/**
 *
 * @author LuisGuillermo
 */
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author softcomputo
 */
@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = Usuario.LISTAR, query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = Usuario.BUSCAR_USUARIO, query = "SELECT u FROM Usuario u WHERE u.login = :usu AND u.password =:pass"),
    @NamedQuery(name = Usuario.BUSCAR_POR_ID, query = "SELECT u FROM Usuario u WHERE u.id = :idusuario")})
public class Usuario implements Serializable, Comparable<Usuario> {

    @Column(name = "estado")
    private Integer estado;

    public static final String LISTAR = "Usuario.listar";
    public static final String BUSCAR_USUARIO = "Usuario.usuario";
    public static final String BUSCAR_POR_ID = "Usuario.buscarid";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @Column(name = "idusuario")
    private Long id;

    @Column(name = "login", length = 150, nullable = false,unique=true)
    private String login;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "usr_rol",
            joinColumns = {
                @JoinColumn(name = "usr_id", referencedColumnName = "idusuario")},
            inverseJoinColumns = {
                @JoinColumn(name = "rol_id", referencedColumnName = "idrol")})
    private List<Rol> roles;

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Usuario o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", login=" + login + ", roles=" + roles.size() + '}';
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

}
