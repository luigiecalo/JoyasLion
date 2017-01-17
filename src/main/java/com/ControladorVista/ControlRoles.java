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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private Long grupoSelect = 0l;
    private Long subgrupoSelect = 0l;
    private String paginaStrin = "";
    private String nombremodulo = "";
    private String iconomodulo = "";

    //Entidades
    private Rol rolselect = new Rol();
    private Modulo mdoSelect = new Modulo();
    private Grupo grupoSelecion = new Grupo();
    private SubGrupo subgrupoSelecion = new SubGrupo();

    //Listas
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Modulo> modulos = new ArrayList<Modulo>();
    private Map<String, Long> gruposLista;
    private Map<String, Long> subgruposLista;
    private Map<String, String> paginasLista;
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

    /**
     * Creates a new instance of ControlUtilidades
     */
    public ControlRoles() {
        ListarTodo();
    }
    //METODOS

    public void onRolSelect(SelectEvent event) {
        this.modelosboolean = true;
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

    public Map<String, Long> getGruposLista() {
        gruposLista = new HashMap<String, Long>();
        for (Grupo grup : getGrupos()) {
            gruposLista.put(grup.getNombre(), grup.getIgrupo());
        }
        return gruposLista;
    }

    public Map<String, Long> getSubgruposLista() {
        subgruposLista = new HashMap<String, Long>();
        for (SubGrupo subgrup : getSubgrupos()) {
            subgruposLista.put(subgrup.getNombre(), subgrup.getIdsubgrupo());
        }
        return subgruposLista;
    }

    public Map<String, String> getPaginas() {
        paginasLista = new HashMap<String, String>();
        for (Tipo tipo : TipoDao.ListarDescripcion("paginas")) {
            paginasLista.put(tipo.getNombre(), tipo.getNombre());
        }
        return paginasLista;
    }

    public void guardarModulo() {
        context = FacesContext.getCurrentInstance();
        if (grupoSelect == 0l && subgrupoSelect != 0l) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "INGRESE PRIMERO UN GRUPO", "INGRESE PRIMERO UN GRUPO"));
        } else {
            Modulo modulo = new Modulo();
            Grupo g = GrupoDao.consultar(Grupo.class, grupoSelect);
            SubGrupo sg = SubGrupoDao.consultar(SubGrupo.class, subgrupoSelect);
            modulo.setNombre(nombremodulo);
            modulo.setIcono(iconomodulo);
            modulo.setGrupomodulo(g);
            modulo.setSubgrupos(sg);
            modulo.setSrc(paginaStrin);
            ModDAO.crear(modulo);
            Limpiar();
            util.crearmensajes("INFO", "REGISTRO EXITOSO", "SE REGISTRO EXITOSA MENTE EL MODULO");
        }

    }
    
    public void eliminarModulo(Modulo mod){
        ModDAO.eliminar(mod);
    }

    public void Limpiar() {
        nombremodulo = "";
        iconomodulo = "";
        grupoSelect = 0l;
        subgrupoSelect = 0l;
        paginaStrin = "";
    }

    private void ListarTodo() {
        listarRoles();
        listarModulos();
        listargrupos();
        listarSubgrupos();
    }

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

    public Long getGrupoSelect() {
        return grupoSelect;
    }

    public void setGrupoSelect(Long grupoSelect) {
        this.grupoSelect = grupoSelect;
    }

    public Long getSubgrupoSelect() {
        return subgrupoSelect;
    }

    public void setSubgrupoSelect(Long subgrupoSelect) {
        this.subgrupoSelect = subgrupoSelect;
    }

    public void setGruposLista(Map<String, Long> gruposLista) {
        this.gruposLista = gruposLista;
    }

    public void setSubgruposLista(Map<String, Long> subgruposLista) {
        this.subgruposLista = subgruposLista;
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

    public String getPaginaStrin() {
        return paginaStrin;
    }

    public void setPaginaStrin(String paginaStrin) {
        this.paginaStrin = paginaStrin;
    }

    public String getNombremodulo() {
        return nombremodulo;
    }

    public void setNombremodulo(String nombremodulo) {
        this.nombremodulo = nombremodulo;
    }

    public String getIconomodulo() {
        return iconomodulo;
    }

    public void setIconomodulo(String iconomodulo) {
        this.iconomodulo = iconomodulo;
    }

}
