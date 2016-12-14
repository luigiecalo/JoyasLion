/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import static com.ControladorVista.ControlSeccion.toLong;
import com.Dao.ModuloDaoimplement;
import com.Entidades.Miembro;
import com.Entidades.Modulo;
import com.Entidades.Rol;
import java.awt.Panel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luigi
 */
@ManagedBean
@ViewScoped
public class Controlplantillas implements Serializable {

    String INICIO = "usuarios.xhtml";

    @ManagedProperty("#{controlSeccion}")
    private ControlSeccion cs = new ControlSeccion();

    private String modulo = "principal.xhtml";
    private Long moduloid = Long.parseLong("0");
    private String grupo = "principal.xhtml";
    private String icono = "fa-home";
    private String nombre = "INICIO";
    private String subGrupo = "principal.xhtml";
    private String active = "";
    private String contenido = INICIO;
    private List<Modulo> Modulos = new ArrayList<Modulo>();
    private Modulo moduloSelecionado = new Modulo();
    private ModuloDaoimplement ModuloDao = new ModuloDaoimplement();
    public String data = "1";
    private Miembro miembro = new Miembro();
    FacesContext context = FacesContext.getCurrentInstance();

    /**
     * Creates a new instance of controlplantillas
     */
    public Controlplantillas() {
//        panel= context.setCurrentPhaseId();
//        FacesContext fc = FacesContext.getCurrentInstance();
//        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
////        UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("miembro");
//        data = params.get("miembro");
//        ELContext contextoEL = context.getELContext();
//        Application appli = context.getApplication();
//        String miPropiedad = (String) appli.evaluateExpressionGet(context, "#{controlSeccion.pass}", String.class);
//        System.out.println(cs.getMiembro());

    }

    public static long toLong(Number number) {
        return number.longValue();
    }
//metodos

    public void selecionarmenu(Modulo modu, Map ver) {
        System.out.println(ver);
        modulo = modu.getSrc();
        nombre= modu.getNombre();
        icono=modu.getIcono();
        moduloSelecionado = modu;
        moduloid = modu.getIdmodulo();
        if (modu.getGrupomodulo() != null) {
            grupo = modu.getGrupomodulo().getNombre();
        } else if (ver.get("tipo").equals("modulo")) {
            grupo = modu.getNombre();
        } else {
            grupo = "";
        }
        if (modu.getSubgrupos() != null) {
            subGrupo = modu.getSubgrupos().getNombre();
        } else {
            subGrupo = "";
        }

        if (Modulos.isEmpty()) {
            Modulos.add(modu);
        } else {
            boolean encontro = false;
            for (Modulo Modulo1 : Modulos) {
                if (Modulo1.equals(modu)) {
                    encontro = true;
                }
            }
            if (!encontro) {
                Modulos.add(modu);
//                RequestContext context = RequestContext.getCurrentInstance();
//        context.getCurrentInstance().execute("$(#).dropdown('toggle');");

            }
        }

    }

    public void selecionarmenuGrupo(Map ver) {
        grupo = (String) ver.get("nombre");
        Modulo mod = ModuloDao.consultar(Modulo.class, ver.get("id"));
        if (ver.get("tipo").equals("modulo")) {
            selecionarmenu(mod, ver);
        }
    }

    public void selecionarmoduloString(String pagina) {
        this.modulo = pagina;
    }

    public void selecionarmenuSubGrupo(Modulo modu, Map ver) {
        subGrupo = modu.getSubgrupos().getNombre();
    }

    public void cerrarmodulo(Modulo modu) {
        Modulos.remove(modu);
    }

    public String cargarmodulo(Modulo modu) {
        RequestContext context = RequestContext.getCurrentInstance();
        modulo = modu.getSrc();
        moduloid = modu.getIdmodulo();
        if (modu.getGrupomodulo() != null) {
            grupo = modu.getGrupomodulo().getNombre();
        } else {
            grupo = modu.getNombre();
        }
        if (modu.getSubgrupos() != null) {
            subGrupo = modu.getSubgrupos().getNombre();
        } else {
            subGrupo = "";
        }
        return modulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getActive(Long mod) {
        String active1 = null;
        if (mod.equals(moduloid)) {
            active1 = "active";
        }
        return active1;
    }

    public String getActiveGrupo(Map ver) {
        String active1 = null;
        if (ver.get("nombre").equals(grupo)) {
            active1 = "active";
        }
        return active1;
    }

    public String getDatatogleGrupo(Map ver) {
        String active1 = null;

        if (ver.get("tipo").equals("modulo")) {
            active1 = "offcanvas";
        }
        return active1;
    }

    public String getActiveSubgrupo(String subgrup) {
        String active1 = null;
        if (subgrup.equals(subGrupo)) {
            active1 = "active";
        }
        return active1;
    }

    public String getActiveSubgrupomodulo(Long modid) {
        String active1 = null;
        if (modid.equals(moduloid)) {
            active1 = "active";
        }
        return active1;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public ControlSeccion getCs() {
        return cs;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCs(ControlSeccion cs) {
        this.cs = cs;
    }

    public List<Modulo> getModulos() {
        return Modulos;
    }

    public void setModulos(List<Modulo> Modulos) {
        this.Modulos = Modulos;
    }

    public Modulo getModuloSelecionado() {
        return moduloSelecionado;
    }

    public void setModuloSelecionado(Modulo moduloSelecionado) {
        this.moduloSelecionado = moduloSelecionado;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getSubGrupo() {
        return subGrupo;
    }

    public Long getModuloid() {
        return moduloid;
    }

    public void setModuloid(Long moduloid) {
        this.moduloid = moduloid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    @Override
    public String toString() {
        return "Controlplantillas{" + "INICIO=" + INICIO + ", cs=" + cs + ", modulo=" + modulo + ", moduloid=" + moduloid + ", grupo=" + grupo + ", subGrupo=" + subGrupo + ", active=" + active + ", contenido=" + contenido + ", Modulos=" + Modulos + ", moduloSelecionado=" + moduloSelecionado + ", ModuloDao=" + ModuloDao + ", data=" + data + ", miembro=" + miembro + '}';
    }

}
