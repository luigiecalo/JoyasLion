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
import net.bootsfaces.render.A;
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
    private boolean btneditar = false;

    private Long idgrup = 0l;
    private String estadocreargrupo = "grupo";

    private Long rolselection = 0l;
    private Long moduloselect = 0l;
    private Permisos[] selectedPermisos = new Permisos[10];
    private Long[] permisosSelected;

    //Entidades
    private Rol rolselect = new Rol();
    private Modulo mdoSelect = new Modulo();
    private Modulo mdoSelectEdit = new Modulo();
    private Grupo grupoSelecion = new Grupo();
    private SubGrupo subgrupoSelecion = new SubGrupo();

    //Listas
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Modulo> modulos = new ArrayList<Modulo>();
    private List<Map> menu = new ArrayList<Map>();
    private Map<String, Long> rolesLista;
    private List<Grupo> grupos = new ArrayList<Grupo>();
    private List<SubGrupo> subgrupos = new ArrayList<SubGrupo>();
    private List<Map> modulosSelect = new ArrayList<Map>();
    private List<Permisos> permisos = new ArrayList<Permisos>();

    //DAO
    private RolDaoimplement RolDAO = new RolDaoimplement();
    private RolModuloPermisoDaoimplement RolMOduloPermisoDAO = new RolModuloPermisoDaoimplement();
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

    private void ListarPermisos() {
        permisos = PerDao.consultarTodo(Permisos.class);
    }

    public void onRolSelect() {
        rolselect = RolDAO.consultar(Rol.class, rolselection);
        if (rolselection != 0l) {
            modulosSelect = ModulosrolesListaMap(rolselect.getRolModuloPermisoList());
            eliminarAgregados();
        } else {
            limpiar();
            modulos.clear();
            modulosSelect.clear();

        }

    }

    private List<Map> ModulosrolesListaMap(List<RolModuloPermiso> rolesmodulos) {
        List<Map> listamapa = new ArrayList<Map>();
        for (RolModuloPermiso rolmp : rolesmodulos) {
            Map mapa = new HashMap<String, String>();
            mapa.put("modulo", rolmp.getModulo());
            mapa.put("rol", rolmp.getRol());

            if (listamapa.isEmpty()) {
                List<Permisos> permisos = new ArrayList<Permisos>();
                permisos.add(rolmp.getPermisos());
                mapa.put("permisos", permisos);
                listamapa.add(mapa);
            } else {
                boolean encontro = false;
                int i = 0;
                int index = 0;
                Map encontroOm = new HashMap<String, String>();
                for (Map map : listamapa) {
                    if (map.get("modulo").equals(mapa.get("modulo")) && map.get("rol").equals(mapa.get("rol"))) {
                        encontro = true;
                        encontroOm = map;
                        index = i;
                    }
                    i++;
                }
                if (encontro) {
                    List<Permisos> permisos = (List<Permisos>) encontroOm.get("permisos");
                    permisos.add(rolmp.getPermisos());
                    mapa.put("permisos", permisos);
                    listamapa.set(index, mapa);
                } else {
                    List<Permisos> permisos = new ArrayList<Permisos>();
                    permisos.add(rolmp.getPermisos());
                    mapa.put("permisos", permisos);
                    listamapa.add(mapa);
                }

            }

        }

        return listamapa;
    }

    public void guardarCambios() {
        if (rolselection != 0l) {
            for (Map rolmodulo : modulosSelect) {
                Rol r = (Rol) rolmodulo.get("rol");
                Modulo m = (Modulo) rolmodulo.get("modulo");
//                List<Permisos> ps = (List<Permisos>) rolmodulo.get("permisos");
//                for (Permisos p : ps) {
//                    RolModuloPermiso rmp= new RolModuloPermiso(r, m, p);
//                    RolMOduloPermisoDAO.crear(rmp);
//                }
                List<Permisos> ps = (List<Permisos>) rolmodulo.get("permisos");
                RolMOduloPermisoDAO.registrarRolModuloPermisos(r, m, ps);

            }
            util.crearmensajes("INFO", "EXITO", "selecione guardo rigistro exitoxamente");
        } else {
            util.crearmensajes("ALERT", "ALERTA", "selecione un rol a modificar");
        }
    }

    public void addModulo() {
        RolModuloPermiso rmp = new RolModuloPermiso();
        Map mapa = new HashMap<String, String>();

        mapa.put("rol", rolselect);
        List<Permisos> permisos = new ArrayList<Permisos>();
        if (permisosSelected.length > 0) {
            for (int i = 0; i < permisosSelected.length; i++) {
                permisos.add(PerDao.consultar(Permisos.class, permisosSelected[i]));
            };
        }
        mapa.put("permisos", permisos);

        if (!btneditar) {
            mapa.put("modulo", mdoSelect);
            modulosSelect.add(mapa);
            eliminarAgregados();
        } else {
            mapa.put("modulo", mdoSelectEdit);
            int i = 0;
            for (Map map : modulosSelect) {
                Modulo m = (Modulo) map.get("modulo");
                if (m.equals(mdoSelectEdit)) {
                    modulosSelect.set(i, mapa);
                }
                i++;
            }

        }
        deSelecionMenu();
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
        modelosboolean = false;
        permisosSelected = null;
        rolselection = 0l;
        modulosSelect.clear();
        modulos.clear();
    }

    public void eliminarrolmoduloperimso(Map map) {
        modulosSelect.remove(map);
    }

    public void editarrolmoduloperimso(Map map) {
        btneditar = true;
        modelosboolean = true;
        mdoSelectEdit = (Modulo) map.get("modulo");
        List<Permisos> permisos = (List<Permisos>) map.get("permisos");
        int i = 0;
        permisosSelected = new Long[permisos.size()];
        for (Permisos permiso : permisos) {
            permisosSelected[i] = permiso.getIdpermisos();
            i++;
        }
//        eliminarrolmoduloperimso(map);

    }

    private void ListarTodo() {
        listarRoles();
        ListarPermisos();
    }

    public void listarRoles() {
        roles = RolDAO.Listar();
    }

    //Lista todos los roles
    public void listarModulos() {
        modulos = ModDAO.listar();
    }

    public void selecionMenu() {
        modelosboolean = true;
        btneditar = false;
    }

    public void deSelecionMenu() {
        modelosboolean = false;
        mdoSelect = new Modulo();
        permisosSelected = null;
        mdoSelectEdit = new Modulo();
    }

    private void eliminarAgregados() {
        listarModulos();
        for (Modulo mod : ModDAO.listar()) {
            for (Map rolmod : modulosSelect) {
                Modulo modul = (Modulo) rolmod.get("modulo");
                if (mod.getIdmodulo().equals(modul.getIdmodulo())) {
                    modulos.remove(mod);
                }

            }
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

    public List<Map> getModulosSelect() {
        return modulosSelect;
    }

    public void setModulosSelect(List<Map> modulosSelect) {
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

    public Long[] getPermisosSelected() {
        return permisosSelected;
    }

    public void setPermisosSelected(Long[] permisosSelected) {
        this.permisosSelected = permisosSelected;
    }

    public Long getRolselection() {
        return rolselection;
    }

    public void setRolselection(Long rolselection) {
        this.rolselection = rolselection;
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }

    public Permisos[] getSelectedPermisos() {
        return selectedPermisos;
    }

    public void setSelectedPermisos(Permisos[] selectedPermisos) {
        this.selectedPermisos = selectedPermisos;
    }

    public boolean isBtneditar() {
        return btneditar;
    }

    public void setBtneditar(boolean btneditar) {
        this.btneditar = btneditar;
    }

    public Modulo getMdoSelectEdit() {
        return mdoSelectEdit;
    }

    public void setMdoSelectEdit(Modulo mdoSelectEdit) {
        this.mdoSelectEdit = mdoSelectEdit;
    }

}
