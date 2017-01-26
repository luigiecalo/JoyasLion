/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import com.Dao.MiembroDaoimplement;
import com.Dao.ModuloDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.RolModuloPermisoDaoimplement;
import com.Dao.UsuarioDaoimplement;
import com.Entidades.Miembro;
import com.Entidades.Modulo;
import com.Entidades.Permisos;
import com.Entidades.Rol;
import com.Entidades.RolModuloPermiso;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import com.Entidades.Usuario;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LuisGuillermo
 */
@ManagedBean
@SessionScoped
public class ControlSeccion implements Serializable {

    private Miembro miembro = null;
    private Rol rolSeccion = new Rol();
    private List<Modulo> ModulosSeccion = new ArrayList<Modulo>();

    private Map<String, Long> rolesSeccion;
    private Long rolTemp;

    private String usu;
    private String pass;
    private String skins = "blue";
    private Long rolselect;
    private UsuarioDaoimplement usuarioDao = new UsuarioDaoimplement();
    private MiembroDaoimplement miembroDao = new MiembroDaoimplement();
    private RolModuloPermisoDaoimplement rolMPDao = new RolModuloPermisoDaoimplement();
    private RolDaoimplement rolDao = new RolDaoimplement();

    private boolean seccion = false;

    /**
     * Creates a new instance of ControlSeccion
     */
    public ControlSeccion() {
        rolselect = toLong(0);
    }

    //METODOS
    public void iniciar() {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Usuario usuario = usuarioDao.login(usu, pass);
        if (usuario == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Primer Mensage", "USUARIO NO ENCONTRADO REGISTRADO"));
        } else {
            this.miembro = miembroDao.BuscarMiembroUsuario(usuario);
            if (usuario.getRoles().size() == 1) {
                rolselect = usuario.getRoles().get(0).getIdrol();
                selecionRol();
            } else {
                requestContext.getCurrentInstance().execute("$('.modalPseudoClass').modal();");

            }
        }

    }

    public void selecionRol() {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (rolselect.equals(toLong(0))) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Primer Mensage", "SELECIONE UN ROL"));
            requestContext.getCurrentInstance().execute("$('.modalPseudoClass').modal('hide');");
        } else {
            rolSeccion = rolDao.consultarC(Rol.class, rolselect);
            if (rolSeccion != null) {
                try {

                    secccion();

                    requestContext.getCurrentInstance().execute("$('.modalPseudoClass').modal('hide');");
//                    if (rolSeccion.getIdrol().equals(toLong(1))) {
//                        context.getExternalContext().redirect("superAdministrador.xhtml");
//                    } else {
                    context.getExternalContext().redirect("inicio.xhtml");
//                    }
                    usu = "";
                    pass = "";
                } catch (IOException ex) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", ex.getLocalizedMessage()));
                }
            }
        }

    }

    public void secccion() {
        if (miembro == null) {
            seccion = false;
            salir();
        } else {
            seccion = true;

            cargarModulos();

        }
    }

    public void validaSeccion() {
        if (miembro == null) {
            seccion = false;
            salir();
        } else {
            seccion = true;
            cargarModulos();
        }
    }

    public void update(String id) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getCurrentInstance().update(id);
//      context.getCurrentInstance().execute("$('#myModal').modal('show');");
    }

    public void modalRol(String id, String estado) {
        RequestContext context = RequestContext.getCurrentInstance();
//      context.getCurrentInstance().update(id);
        context.getCurrentInstance().execute("$('#" + id + "').modal('" + estado + "');");
    }

    public List<Map> getMenu() {
        List<Map> menu = new LinkedList<Map>();
        if (ModulosSeccion != null) {
            for (Modulo m : ModulosSeccion) {
                Modulo modulo = m;
                Map item = new HashMap<String, String>();

                if (modulo.getGrupomodulo() == null) {
                    addItem(item, modulo, menu);

                    menu.add(item);
                } else {
                    item.put("nombre", modulo.getGrupomodulo().getNombre());
                    item.put("icono", modulo.getGrupomodulo().getIcono());
                    if (menu.isEmpty()) {
                        addItemGrup(item, modulo);
                        menu.add(item);
                    } else {
                        boolean encontro = false;
                        Map itemencontrado = null;
                        for (int i = 0; i < menu.size(); i++) {
                            Map itemMenu = menu.get(i);
                            if (itemMenu.get("nombre").equals(item.get("nombre"))) {
                                encontro = true;
                                itemencontrado = itemMenu;
                                break;
                            }
                        }
                        if (!encontro) {
                            addItemGrup(item, modulo);
                            menu.add(item);
                        } else {
                            addItemGrup(item, modulo);
//                        if (item.get("submodulos") == null) {
//                        System.out.println("Item Encontrado" + itemencontrado);
//                        System.out.println("Item -         " + item);
                            List<Modulo> modulosEncontraos = (List<Modulo>) itemencontrado.get("modulos");
                            List<Modulo> modulositem = (List<Modulo>) item.get("modulos");
                            boolean relacion = false;
                            for (Modulo moduloencontrado : modulosEncontraos) {
                                if (moduloencontrado.getSubgrupos() != null) {
                                    for (Modulo moduloitem : modulositem) {
                                        if (moduloitem.getSubgrupos() != null) {
                                            if (moduloitem.getSubgrupos().equals(moduloencontrado.getSubgrupos())) {
                                                Modulo moduloRelacion = moduloencontrado;
                                                moduloRelacion.getSubgrupos().getModulos().add(modulo);
                                                relacion = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!relacion) {
                                modulosEncontraos.addAll(modulositem);
                            }
                        }
                    }
                }
            }
        }
        return menu;
    }

    public void addItem(Map item, Modulo modulo, List<Map> menu) {

        if (modulo.getSubgrupos() == null) {

//            System.out.println("*" + modulo.getNombre() + "*");
            item.put("src", modulo.getSrc());
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getIcono());
            item.put("nombre", modulo.getNombre());
            item.put("modulos", null);
            item.put("tipo", "modulo");
            item.put("clase", null);
        }
    }

    public void addItemGrup(Map item, Modulo modulo) {
        if (modulo.getSubgrupos() == null) {
//            System.out.println("*" + modulo.getGrupomodulo().getNombre() + "*");
            item.put("src", modulo.getGrupomodulo().getNombre());
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getGrupomodulo().getIcono());
            item.put("nombre", modulo.getGrupomodulo().getNombre());
            List<Modulo> modulosvalidos = new ArrayList<Modulo>();

//            System.out.println("-" + modulo.getNombre() + "-");
            modulosvalidos.add(modulo);
            item.put("modulos", modulosvalidos);
            item.put("tipo", "grupo");
            item.put("clase", "fa-angle-left pull-right");
        } else {
            addItemSubGrup(item, modulo);
        }
    }

    public void addItemSubGrup(Map item, Modulo modulo) {
//        System.out.println("-" + modulo.getSubgrupos().getNombre() + "-");
        item.put("src", modulo.getSubgrupos().getNombre());
        item.put("id", modulo.getIdmodulo());
        item.put("icono", modulo.getGrupomodulo().getIcono());
        item.put("nombre", modulo.getGrupomodulo().getNombre());
        List<Modulo> modulosvalidos = new ArrayList<Modulo>();
        List<Modulo> submodulosvalidos = new ArrayList<Modulo>();
        submodulosvalidos.add(modulo);
        modulo.getSubgrupos().setModulos(submodulosvalidos);
        modulosvalidos.add(modulo);
        item.put("modulos", modulosvalidos);
        item.put("tipo", "subgrupo");
        item.put("clase", "fa-angle-left pull-right");
    }

    public void salir() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();

            ExternalContext externalContext = context.getExternalContext();

            Object session = externalContext.getSession(false);
//            context.getCurrentInstance().getExternalContext().invalidateSession();
            HttpSession httpSession = (HttpSession) session;

            httpSession.invalidate();

            context.getExternalContext().redirect("index.xhtml");

            miembro = null;
            seccion = false;
            rolSeccion = null;
            rolselect = toLong(0);
        } catch (IOException ex) {
            Logger.getLogger(ControlSeccion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarModulos() {
        ModulosSeccion = rolMPDao.buscarModulos(rolSeccion.getIdrol());
        rolesSeccion = new HashMap<String, Long>();
        for (Rol rol : miembro.getUsuario().getRoles()) {
            this.rolesSeccion.put(rol.getNombre(), rol.getIdrol());
        }
    }

    public void selecionarrolseccion() {
        rolselect = rolTemp;
        setRolselect(getRolTemp());
        selecionRol();
    }

    public static long toLong(Number number) {
        return number.longValue();
    }

    public void cambiarsikin(String skin) {
        skins = skin;
        selecionRol();
    }

    //GET & SET
    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isSeccion() {
        return seccion;
    }

    public void setSeccion(boolean seccion) {
        this.seccion = seccion;
    }

    public Rol getRol() {
        return rolSeccion;
    }

    public void setRol(Rol rol) {
        this.rolSeccion = rol;
    }

    public Long getRolselect() {
        return rolselect;
    }

    public void setRolselect(Long rolselect) {
        this.rolselect = rolselect;
    }

    public String getSkins() {
        return skins;
    }

    public void setSkins(String skins) {
        this.skins = skins;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public Rol getRolSeccion() {
        return rolSeccion;
    }

    public List<Modulo> getModulosSeccion() {
        return ModulosSeccion;
    }

    public Map<String, Long> getRolesSeccion() {

        return rolesSeccion;
    }

    public Long getRolTemp() {
        rolTemp = getRolselect();
        return rolTemp;
    }

    public void setRolTemp(Long rolTemp) {
        this.rolTemp = rolTemp;
    }
}
