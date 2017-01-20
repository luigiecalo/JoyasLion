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
import com.Dao.TipoDaoimplement;
import com.Entidades.Grupo;
import com.Entidades.Modulo;
import com.Entidades.Permisos;
import com.Entidades.Rol;
import com.Entidades.RolModuloPermiso;
import com.Entidades.SubGrupo;
import com.Entidades.Tipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author LuisGuillermo
 */
@ManagedBean
@ViewScoped
public class ControlRoles implements Serializable {

    //Utilidades
    private Utilidades util = new Utilidades();
    private boolean modelosboolean = false;

    private Long idgrup = 0l;
    private String estadocreargrupo = "grupo";

    private Long rolselection = 0l;
    private Long moduloselect = 0l;

    //Entidades
    private Rol rolselect = new Rol();
    private Modulo mdoSelect = new Modulo();
    private Grupo grupoSelecion = new Grupo();
    private SubGrupo subgrupoSelecion = new SubGrupo();

    //Listas
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Modulo> modulos = new ArrayList<Modulo>();
    private List<Map> menu = new ArrayList<Map>();
    private Map<String, Long> rolesLista;
    private List<Grupo> grupos = new ArrayList<Grupo>();
    private List<SubGrupo> subgrupos = new ArrayList<SubGrupo>();
    private List<RolModuloPermiso> modulosSelect = new ArrayList<RolModuloPermiso>();

    //DAO
    private RolDaoimplement RolDAO = new RolDaoimplement();
    private ModuloDaoimplement ModDAO = new ModuloDaoimplement();
    private PermisosDaoimplement PerDao = new PermisosDaoimplement();
    private GrupoDaoimplement GrupoDao = new GrupoDaoimplement();
    private SubGrupoDaoimplement SubGrupoDao = new SubGrupoDaoimplement();
    private TipoDaoimplement TipoDao = new TipoDaoimplement();

    FacesContext context;
    RequestContext contex;

    /**
     * Creates a new instance of ControlUtilidades
     */
    public ControlRoles() {
        ListarTodo();
    }
    //METODOS

    public void onRolSelect() {
        this.modelosboolean = true;
        rolselect=RolDAO.consultar(Rol.class, rolselection);
        modulosSelect = rolselect.getRolModuloPermisoList();
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

    public Map<String, Long> getRolesLista() {
        rolesLista = new HashMap<String, Long>();
        for (Rol rol : getRoles()) {
            rolesLista.put(rol.getNombre(), rol.getIdrol());
        }
        return rolesLista;
    }

    public void limpiar() {
        mdoSelect = new Modulo();

    }

    private void ListarTodo() {
        listarRoles();
        listarModulos();

    }

    public void listarRoles() {
        roles = RolDAO.Listar();
    }

    //Lista todos los roles
    public void listarModulos() {
        modulos = ModDAO.listar();
    }

    public void selecionMenu(Long idmod) {

        Modulo moduloSel = ModDAO.consultarC(Modulo.class, idmod);
        mdoSelect = moduloSel;
        if (moduloSel != null) {
        }
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

    public Grupo getGrupoSelecion() {
        return grupoSelecion;
    }

    public void setGrupoSelecion(Grupo grupoSelecion) {
        this.grupoSelecion = grupoSelecion;
    }

    public SubGrupo getSubgrupoSelecion() {
        return subgrupoSelecion;
    }

    public void setSubgrupoSelecion(SubGrupo subgrupoSelecion) {
        this.subgrupoSelecion = subgrupoSelecion;
    }

    public Long getModuloselect() {
        return moduloselect;
    }

    public void setModuloselect(Long moduloselect) {
        this.moduloselect = moduloselect;
    }

    public List<Map> getMenu() {
        menu = util.getMenu(getModulos());
        return menu;
    }

    public String getEstadocreargrupo() {
        return estadocreargrupo;
    }

    public void setEstadocreargrupo(String estadocreargrupo) {
        this.estadocreargrupo = estadocreargrupo;
    }

    public void limpiargrupos() {
        this.idgrup = 0l;

    }

    public Long getIdgrup() {
        return idgrup;
    }

    public void setIdgrup(Long idgrup) {
        this.idgrup = idgrup;
    }

    public Long getRolselection() {
        return rolselection;
    }

    public void setRolselection(Long rolselection) {
        this.rolselection = rolselection;
    }

}
