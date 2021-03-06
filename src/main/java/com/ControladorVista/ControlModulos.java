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
import com.Dao.RolModuloPermisoDaoimplement;
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
public class ControlModulos implements Serializable {

    //Utilidades
    private Utilidades util = new Utilidades();
    private boolean modelosboolean = false;
    private Long grupoSelect = 0l;
    private Long subgrupoSelect = 0l;
    private String paginaStrin = "";
    private String nombre = "";
    private String icono = "";
    private Long idgrup = 0l;
    private String estadocreargrupo = "grupo";
    private String estadogrupo = "R";

    private Long moduloselect = 0l;
    private String estado = "R";

    //Entidades
    private Rol rolselect = new Rol();
    private Modulo mdoSelect = new Modulo();
    private Grupo grupoSelecion = new Grupo();
    private SubGrupo subgrupoSelecion = new SubGrupo();

    //Listas
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Modulo> modulos = new ArrayList<Modulo>();
    private List<Map> menu = new ArrayList<Map>();
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
    RequestContext contex;

    /**
     * Creates a new instance of ControlUtilidades
     */
    public ControlModulos() {
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

    public boolean verBtnRegistro() {
        if (estado.equals("R")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verBtngrupo() {
        if (estadogrupo.equals("R")) {
            return true;
        } else {
            return false;
        }
    }

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
            Grupo g = GrupoDao.consultar(Grupo.class, grupoSelect);
            SubGrupo sg = SubGrupoDao.consultar(SubGrupo.class, subgrupoSelect);
            mdoSelect.setGrupomodulo(g);
            mdoSelect.setSubgrupos(sg);
            if (estado.equals("R")) {
                ModDAO.crear(mdoSelect);
                util.crearmensajes("INFO", "REGISTRO EXITOSO", "SE REGISTRO EXITOSAMENTE EL MODULO");
            } else {
                ModDAO.modificar(mdoSelect);
                util.crearmensajes("INFO", "MODIFICACION EXITOSA", "SE MODifiCO EXITOSAMENTE EL MODULO");
            }

            limpiar();

        }

    }

    public void eliminarModulo(Modulo mod) {
        ModDAO.eliminar(mod);
        util.crearmensajes("INFO", "ELIMINACION EXITOSA", "SE ELIMINO EXITOSAMENTE EL MODULO");
    }

    public void limpiar() {
        mdoSelect = new Modulo();
        grupoSelect = 0l;
        subgrupoSelect = 0l;
        paginaStrin = "";
        estado = "R";
        ListarTodo();
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
        modulos = ModDAO.listar();
    }

    //Lista todos los grupos
    public void listargrupos() {
        grupos = GrupoDao.consultarTodo(Grupo.class);
    }

    //Lista todos los subGrupos
    public void listarSubgrupos() {
        subgrupos = SubGrupoDao.consultarTodo(SubGrupo.class);
    }

    public void selecionMenu(Long idmod) {
        estado = "A";
        Modulo moduloSel = ModDAO.consultarC(Modulo.class, idmod);
        mdoSelect = moduloSel;
        if (moduloSel != null) {
            if (mdoSelect.getGrupomodulo() != null) {
                grupoSelect = mdoSelect.getGrupomodulo().getIgrupo();
            } else {
                grupoSelect = 0l;
            }
            if (mdoSelect.getSubgrupos() != null) {
                subgrupoSelect = mdoSelect.getSubgrupos().getIdsubgrupo();
            } else {
                subgrupoSelect = 0l;
            }
        }

    }

    public void savegrupo() {
        if (nombre.isEmpty()) {
            util.crearmensajes("ALERTA", "INGRESE NOMBRE", "ALERTA");
        } else if (icono.isEmpty()) {
            util.crearmensajes("ALERTA", "INGRESE ICONO", "INGRERSE EL ICONO");
        } else {

            if (estadocreargrupo.equals("GRUPO")) {
                Grupo grup = new Grupo();
                grup.setNombre(nombre);
                grup.setIcono(icono);

                if (estadogrupo.equals("R")) {
                    GrupoDao.crear(grup);
                } else {
                    grup.setIgrupo(idgrup);
                    GrupoDao.modificar(grup);
                }
            } else {
                SubGrupo sgrup = new SubGrupo();
                sgrup.setNombre(nombre);
                sgrup.setIcono(icono);
                if (estadogrupo.equals("R")) {
                    SubGrupoDao.crear(sgrup);
                } else {
                    sgrup.setIdsubgrupo(idgrup);
                    SubGrupoDao.modificar(sgrup);
                }
            }
            util.crearmensajes("INFO", "MENSAGE", "REgistro Exitoso del " + estadocreargrupo + "");

            limpiargrupos();
            listargrupos();
            listarSubgrupos();
        }
    }

    public void selecionGrup(Long id) {
        contex = RequestContext.getCurrentInstance();
        this.estadogrupo = "A";
        if (estadocreargrupo.equals("GRUPO")) {
            Grupo grup = GrupoDao.consultarC(Grupo.class, id);
            if (grup != null) {
                this.nombre = grup.getNombre();
                this.icono = grup.getIcono();
                this.idgrup = grup.getIgrupo();
            }

        } else {
            SubGrupo subgrup = SubGrupoDao.consultar(SubGrupo.class, id);
            if (subgrup != null) {
                this.nombre = subgrup.getNombre();
                this.icono = subgrup.getIcono();
                this.idgrup = subgrup.getIdsubgrupo();
            }
        }
        contex.update(":form:creargrup");
    }

    public void eliminarMenu() {
        Modulo moduloSel = ModDAO.consultarC(Modulo.class, moduloselect);
        RolModuloPermisoDaoimplement RMPDAo = new RolModuloPermisoDaoimplement();
        List<RolModuloPermiso> rolesModulos = RMPDAo.buscarRolModulosPermisos(moduloSel.getIdmodulo());
        if (rolesModulos==null || rolesModulos.isEmpty()) {
            ModDAO.eliminar(moduloSel);
            util.crearmensajes("INFO", "MODuLO ELIMINADO EXITOSAMENTE", " MODULO ELIMINADO EXITOSAMENTE");
            limpiar();
        } else {
            util.crearmensajes("ALERT", "ADVERTENCIA", "NO SE PUEDE ELIMINAR ESTE MODULO POR QUE HAY ROLES RELACIONADOS A EL");
        }

    }

    public void eliminarGrupo() {
        if (estadocreargrupo.equals("GRUPO")) {
            Grupo grup = GrupoDao.consultarC(Grupo.class, idgrup);
            if (grup != null) {
                GrupoDao.eliminar(grup);
                listargrupos();
            }

        } else {
            SubGrupo subgrup = SubGrupoDao.consultar(SubGrupo.class, idgrup);
            if (subgrup != null) {
                SubGrupoDao.eliminar(subgrup);
                listarSubgrupos();
            }
        }
        limpiargrupos();
        util.crearmensajes("INFO", estadocreargrupo + " ELIMINADO EXITOSAMENTE", estadocreargrupo + " ELIMINADO EXITOSAMENTE");

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
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
        this.nombre = "";
        this.icono = "";
        this.idgrup = 0l;
        this.estadogrupo = "R";
    }

}
