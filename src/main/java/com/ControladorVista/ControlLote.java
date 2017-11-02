/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.OrdenDaoimplement;
import com.Dao.RolDaoimplement;
import com.Entidades.Lote;
import com.Entidades.LoteModeloOrden;
import com.Entidades.Orden;
import com.Entidades.OrdenModelo;
import com.Entidades.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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
    private Lote lote = new Lote();
    private LoteModeloOrden loteModeloOrden = new LoteModeloOrden();

    //LISTAS
    private List<Orden> ordenes = new ArrayList<Orden>();
    private List<OrdenModelo> ordenesMoldelos = new ArrayList<OrdenModelo>();
    private List<Lote> lotes = new ArrayList<Lote>();
    private List<LoteModeloOrden> lotesModelosOrdenes = new ArrayList<LoteModeloOrden>();
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
        lote = new Lote();
        lotesModelosOrdenes.clear();
        ordenesMoldelos.clear();
    }
    
    public void buscarOredenesEsra() {
        if (lotesModelosOrdenes.isEmpty()) {
            this.ordenes = ODAO.buscarOrdenEstado("EN ESPERA");
        } else {
            this.ordenes = ordenes;
        }
        
    }
    
    public void agregarOreden(Orden orden) {
        List<OrdenModelo> modelosordenes = orden.getOrdenesModelo();
        for (OrdenModelo modelosorden : modelosordenes) {
            LoteModeloOrden lmo = new LoteModeloOrden();
            lmo.setCantidad(modelosorden.getCantidad());
            lmo.setMaterial(modelosorden.getMaterial());
            lmo.setOrden(modelosorden.getOrden());
            this.lotesModelosOrdenes.add(lmo);
        }
        this.ordenes.remove(orden);
    }

    public void agregarOredenModelo(OrdenModelo ordenmodu) {
        LoteModeloOrden lmo = new LoteModeloOrden();
        lmo.setCantidad(ordenmodu.getCantidad());
        lmo.setMaterial(ordenmodu.getMaterial());
        lmo.setOrden(ordenmodu.getOrden());
        this.lotesModelosOrdenes.add(lmo);
        ordenes.forEach((orden) -> {
            orden.getOrdenesModelo().remove(ordenmodu);
        });

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
    
    public List<LoteModeloOrden> getLotesModelosOrdenes() {
        return lotesModelosOrdenes;
    }
    
    public void setLotesModelosOrdenes(List<LoteModeloOrden> lotesModelosOrdenes) {
        this.lotesModelosOrdenes = lotesModelosOrdenes;
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
