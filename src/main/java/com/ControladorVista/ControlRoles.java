/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.GrupoDaoimplement;
import com.Dao.ModuloDaoimplement;
import com.Dao.PermisosDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.SubGrupoDaoimplement;
import com.Entidades.Grupo;
import com.Entidades.Modulo;
import com.Entidades.Permisos;
import com.Entidades.Rol;
import com.Entidades.RolModuloPermiso;
import com.Entidades.SubGrupo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author LuisGuillermo
 */
@ManagedBean
@ViewScoped
public class ControlRoles {

    //Utilidades
    private Utilidades util = new Utilidades();
    private boolean modelosboolean = false;

    //Entidades
    private Rol rolselect = new Rol();
    private Modulo mdoSelect = new Modulo();

    //Listas
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Modulo> modulos = new ArrayList<Modulo>();
    private List<Grupo> grupos = new ArrayList<Grupo>();
    private List<SubGrupo> subgrupos = new ArrayList<SubGrupo>();
    private List<RolModuloPermiso> modulosSelect = new ArrayList<RolModuloPermiso>();

    //DAO
    private RolDaoimplement RolDAO = new RolDaoimplement();
    private ModuloDaoimplement ModDAO = new ModuloDaoimplement();
    private PermisosDaoimplement PerDao = new PermisosDaoimplement();
    private GrupoDaoimplement GrupoDao = new GrupoDaoimplement();
    private SubGrupoDaoimplement SubGrupoDao = new SubGrupoDaoimplement();

    /**
     * Creates a new instance of ControlUtilidades
     */
    public ControlRoles() {
        listarRoles();
        listarModulos();
        listargrupos();
        listarSubgrupos();
    }
    //METODOS

    public void onRolSelect(SelectEvent event) {
        this.modelosboolean = true;
        modulosSelect = rolselect.getRolModuloPermisoList();
    }

    public void onModuloSelect(SelectEvent event) {

    }

    public void addModulo() {
        RolModuloPermiso rmp = new RolModuloPermiso();
        rmp.setModulo(mdoSelect);
        rmp.setRol(rolselect);
        rmp.setPermisos(PerDao.consultar(Permisos.class, 1L));
        modulosSelect.add(rmp);
    }
//METODOS
    //Lista todos los roles

    public void listarRoles() {
        roles = RolDAO.Listar();
    }

    //Lista todos los roles
    public void listarModulos() {
        modulos = ModDAO.consultarTodo(Modulo.class);
    }

    //Lista todos los grupos
    public void listargrupos() {
        grupos = GrupoDao.consultarTodo(Grupo.class);
    }
    //Lista todos los subGrupos
    public void listarSubgrupos() {
        subgrupos = SubGrupoDao.consultarTodo(SubGrupo.class);
    }

    //GET AND SET
    public Rol getRolselect() {
        return rolselect;
    }

    public void setRolselect(Rol rolselect) {
        this.rolselect = rolselect;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public List<RolModuloPermiso> getModulosSelect() {
        return modulosSelect;
    }

    public void setModulosSelect(List<RolModuloPermiso> modulosSelect) {
        this.modulosSelect = modulosSelect;
    }

    public Modulo getMdoSelect() {
        return mdoSelect;
    }

    public void setMdoSelect(Modulo mdoSelect) {
        this.mdoSelect = mdoSelect;
    }

    public boolean isModelosboolean() {
        return modelosboolean;
    }

    public void setModelosboolean(boolean modelosboolean) {
        this.modelosboolean = modelosboolean;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<SubGrupo> getSubgrupos() {
        return subgrupos;
    }

    public void setSubgrupos(List<SubGrupo> subgrupos) {
        this.subgrupos = subgrupos;
    }

}
