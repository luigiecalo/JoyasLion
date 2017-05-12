/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.OrdenDaoimplement;
import com.Dao.RolDaoimplement;
import com.Entidades.Modelo;
import com.Entidades.Orden;
import com.Entidades.OrdenModelo;
import com.Entidades.Orden_;
import com.Entidades.Rol;
import com.Entidades.Usuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@SessionScoped
public class ControlLote {

    //UTILIDADES
    private String message = "Hello World nuevo!";
    private int cant = 0;
    private Utilidades util = new Utilidades();
    private boolean Registrar = false;
    // ENTIDADES
    private Orden orden = new Orden();
    private Usuario clienteSelect = new Usuario();
    private OrdenModelo oredeModelo;
    //LISTAS
    private List<Orden> ordenes = new ArrayList<Orden>();
    private List<OrdenModelo> ordenesMoldelos = new ArrayList<OrdenModelo>();
    private List<Object> Litas = new ArrayList<Object>();
    //DAO
    private OrdenDaoimplement ODAO = new OrdenDaoimplement();
    private RolDaoimplement RolDAO = new RolDaoimplement();
    //Session
    @ManagedProperty(value = "#{controlSeccion}")
    private ControlSeccion controlSeccion;

    public ControlLote() {

    }
/// METODOS
    //Cisualiza registro y consultas

    public void registroModulo() {
        Registrar = true;
        limpiar();
    }
     public void consultaModulo() {
        Registrar = false;
    }

    //Se Genweara Lel Registro De La orden En base De Datos
    public void limpiar() {
        clienteSelect = new Usuario();
        orden = new Orden();
        ordenesMoldelos.clear();
    }
    public void buscarOredenesEsra(){
       this.ordenes = ODAO.buscarOrdenEstado("EN ESPERA");
    }
    
    public void agregarOreden(Orden oreden){
       this.ordenes.remove(oreden);
    }

///GET Y SET
    public Orden getOrden() {
        return orden;
    }

    public Double getTotalOrden() {
        Double total = 0.0;
        for (OrdenModelo ordenesMoldelo : ordenesMoldelos) {
            total = total + ordenesMoldelo.getTotal();
        }
        return total;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public OrdenModelo getOredeModelo() {
        return oredeModelo;
    }

    public void setOredeModelo(OrdenModelo oredeModelo) {
        this.oredeModelo = oredeModelo;
    }

    public List<OrdenModelo> getOrdenesMoldelos() {
        return ordenesMoldelos;
    }

    public void setOrdenesMoldelos(List<OrdenModelo> ordenesMoldelos) {
        this.ordenesMoldelos = ordenesMoldelos;
    }

    public List<Orden> getOrdenes() {
        
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    //SET SECCION
    public void setControlSeccion(ControlSeccion controlSeccion) {
        this.controlSeccion = controlSeccion;
    }

    public Usuario getClienteSelect() {
        return clienteSelect;
    }

    public void setClienteSelect(Usuario clienteSelect) {
        this.clienteSelect = clienteSelect;
    }

    public boolean isRegistrar() {
        return Registrar;
    }

    public void setRegistrar(boolean Registrar) {
        this.Registrar = Registrar;
    }

}
