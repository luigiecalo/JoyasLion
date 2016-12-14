/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.CirconDaoimplement;
import com.Dao.ModeloDaoimplement;
import com.Dao.PiedraCentralDaoimplement;
import com.Dao.TipoDaoimplement;
import com.Entidades.Circon;
import com.Entidades.Modelo;
import com.Entidades.Modulo;
import com.Entidades.PiedraCentral;
import com.Entidades.Tipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luigi
 */
@ManagedBean
@ViewScoped
public final class ControlModelos implements Serializable {

    //UTILIDADES
    private Long idmod;
    private Utilidades util = new Utilidades();
    private String estado = "R";
    private boolean Registrar = false;
    private boolean estadoModelo = true;
    private Map<String, Long> piedrasCentralesLista;
    private Map<String, Long> circonesLista;
    private Map<String, Long> tiposModeloLista;

    //OBJECTOS
    private Modelo modeloSelecionado = new Modelo();
    private Circon circon = new Circon();
    private Tipo tipo = new Tipo();
    private PiedraCentral piedracentral = new PiedraCentral();
    //LISTAS
    private List<Modelo> modelos = new ArrayList<Modelo>();
    private List<Modelo> circones = new ArrayList<Modelo>();
    private List<Modelo> circonesSelect = new ArrayList<Modelo>();
    private List<Tipo> tiposModelo = new ArrayList<Tipo>();
    private List<PiedraCentral> piedracentrales = new ArrayList<PiedraCentral>();
    private List<PiedraCentral> piedracentralesSelect = new ArrayList<PiedraCentral>();
    //DAOS

    private ModeloDaoimplement ModeloDao = new ModeloDaoimplement();
    private CirconDaoimplement CirconDao = new CirconDaoimplement();
    private TipoDaoimplement TipoDao = new TipoDaoimplement();
    private PiedraCentralDaoimplement PiedraCentralDao = new PiedraCentralDaoimplement();
//    RequestContext Requescontext = RequestContext.getCurrentInstance();

    /**
     * Creates a new instance of controlplantillas
     */
    public ControlModelos() {

    }
////METODOS
    //validar los permisos De Los botones
    public boolean butompermisos(Long idrol, Modulo mod, String permiso) {
        return util.permisos(idrol, mod, permiso);
    }

    //cambia de vista a la vista De consulta
    public void registroModulo() {
        Registrar = true;
        limpiar();
    }

    //Limpia la vista
    private void limpiar() {
        
    }
    //Lista todos Los Modelos de La vista De Consulta
     public void listarModelos() {
        modelos = ModeloDao.consultarTodo(Modelo.class);
    }
     
     //Envia Los datos De la consulta Al registro
      public void enviarselecionado(Modelo m) {
        estado = "A";
        Registrar = true;
        RequestContext.getCurrentInstance().reset("form:panel");
        modeloSelecionado = m;
        piedracentralesSelect=m.getPiedra_centrales();
    }
      //pasa Del modulo De registro la modulo De consulta
      public void consultaModulo() {
        Registrar = false;
    }

////GET AND SET
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isRegistrar() {
        return Registrar;
    }

    public void setRegistrar(boolean Registrar) {
        this.Registrar = Registrar;
    }

    public boolean isEstadoModelo() {
        return estadoModelo;
    }

    public void setEstadoModelo(boolean estadoModelo) {
        this.estadoModelo = estadoModelo;
    }

    public Map<String, Long> getPiedrasCentralesLista() {
        return piedrasCentralesLista;
    }

    public void setPiedrasCentralesLista(Map<String, Long> piedrasCentralesLista) {
        this.piedrasCentralesLista = piedrasCentralesLista;
    }

    public Map<String, Long> getCirconesLista() {
        return circonesLista;
    }

    public void setCirconesLista(Map<String, Long> circonesLista) {
        this.circonesLista = circonesLista;
    }

    public Map<String, Long> getTiposModeloLista() {
        return tiposModeloLista;
    }

    public void setTiposModeloLista(Map<String, Long> tiposModeloLista) {
        this.tiposModeloLista = tiposModeloLista;
    }

    public Circon getCircon() {
        return circon;
    }

    public void setCircon(Circon circon) {
        this.circon = circon;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public PiedraCentral getPiedracentral() {
        return piedracentral;
    }

    public void setPiedracentral(PiedraCentral piedracentral) {
        this.piedracentral = piedracentral;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public List<Modelo> getCircones() {
        return circones;
    }

    public void setCircones(List<Modelo> circones) {
        this.circones = circones;
    }

    public List<Modelo> getCirconesSelect() {
        return circonesSelect;
    }

    public void setCirconesSelect(List<Modelo> circonesSelect) {
        this.circonesSelect = circonesSelect;
    }

    public List<Tipo> getTiposModelo() {
        return tiposModelo;
    }

    public void setTiposModelo(List<Tipo> tiposModelo) {
        this.tiposModelo = tiposModelo;
    }

    public List<PiedraCentral> getPiedracentralesSelect() {
        return piedracentralesSelect;
    }

    public void setPiedracentralesSelect(List<PiedraCentral> piedracentralesSelect) {
        this.piedracentralesSelect = piedracentralesSelect;
    }

}
