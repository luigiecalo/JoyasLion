/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.OrdenDaoimplement;
import com.Dao.RolDaoimplement;
import com.Entidades.Material;
import com.Entidades.Modelo;
import com.Entidades.Orden;
import com.Entidades.OrdenModelo;
import com.Entidades.Rol;
import com.Entidades.Usuario;
import java.text.DecimalFormat;
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
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named(value = "controlOrden")
@ManagedBean
@SessionScoped
public class ControlOrden {

    //UTILIDADES
    private String message = "Hello World nuevo!";
    private int cant = 0;
    private Utilidades util = new Utilidades();
    private boolean esCliente = false;
    // ENTIDADES
    private Orden orden = new Orden();
    private Usuario clienteSelect = new Usuario();
    private OrdenModelo oredeModelo;
    //LISTAS
    private List<Orden> ordenes = new ArrayList<Orden>();
    private List<OrdenModelo> ordenesMoldelos = new ArrayList<OrdenModelo>();
    //DAO
    private OrdenDaoimplement ODAO = new OrdenDaoimplement();
    private RolDaoimplement RolDAO = new RolDaoimplement();
    //Session
    @ManagedProperty(value = "#{controlSeccion}")
    private ControlSeccion controlSeccion;

    public ControlOrden() {

    }
/// METODOS
    //Agrega un Modelo a La lista

    public void agregarordenmodelo(Modelo modelo, int cant, Material material, Double peso_material) {
        OrdenModelo om = new OrdenModelo();
        om.setModelo(modelo);
        om.setCantidad(cant);
        om.setDescuento(0.0);
        om.setMaterial(material);
        om.setPeso_material(cant * peso_material);
        om.setValor(peso_material * 100);
        om.setTotal(cant * om.getValor());
        om.setEstado("PENDIENTE");
        if (ordenesMoldelos.size() <= 0) {
            ordenesMoldelos.add(om);
        } else {
            int i = 0;
            int index = 0;
            boolean encontro = false;
            OrdenModelo encontroOm = new OrdenModelo();
            for (OrdenModelo orm : ordenesMoldelos) {
                if (orm.getModelo().equals(om.getModelo()) && orm.getMaterial().equals(om.getMaterial())) {
                    encontro = true;
                    encontroOm = orm;
                    index = i;
                }
                i++;
            }
            if (encontro) {
                encontroOm.setCantidad(encontroOm.getCantidad() + om.getCantidad());
                encontroOm.setTotal(encontroOm.getTotal() + om.getTotal());
                ordenesMoldelos.set(index, encontroOm);
            } else {
                ordenesMoldelos.add(om);
            }
        }

    }

    public void eliminaOrden(OrdenModelo om) {
        ordenesMoldelos.remove(om);
    }

    //Se Genweara Lel Registro De La orden En base De Datos
    public void guardarOrden() {
        Date d = new Date();
        Usuario usu = controlSeccion.getMiembro().getUsuario();
        if (isEsCliente()) {
            orden.setCliente(usu);
        } else {
            clienteSelect = usu;
            orden.setCliente(clienteSelect);
        }
        SimpleDateFormat f = new SimpleDateFormat("ddMMyy");
        orden.setUsuario(usu);
        orden.setCodigo(f.format(d) + ODAO.Ultima(f.format(d)));
        long datelong = d.getTime();
        orden.setFecha(datelong);
        orden.setEstado("EN ESPERA");
        orden.setValor_total(getTotalOrden());
        ODAO.crear(orden);
        orden = ODAO.buscarOrdenCodigoEstado(orden.getCodigo(), orden.getEstado());
        modificar(orden);
        limpiar();
        util.crearmensajes("INFO", "EXITO", "REGISTRO EXITOSO");

    }

    private void limpiar() {
        clienteSelect = new Usuario();
        orden = new Orden();
        ordenesMoldelos.clear();
    }

    public void modificar(Orden orden) {
        List<OrdenModelo> descripcion = new ArrayList<OrdenModelo>();
        for (OrdenModelo ordMod : ordenesMoldelos) {
            OrdenModelo nuevoordMod = ordMod;
            nuevoordMod.setOrdenModeloPK(ordMod.getModelo().getId(), orden.getId(), ordMod.getMaterial().getId());
            descripcion.add(nuevoordMod);
        }
        orden.setOrdenesModelo(descripcion);
        ODAO.modificar(orden);
    }

    public void chooseCar() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("width", 640);
        options.put("height", 340);
        options.put("headerElement", "customheader");
//        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("selectCar", options, null);
    }

    public void onCarChosen(SelectEvent event) {
        Usuario car = (Usuario) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car Selected", "Id:" + car.getId());

        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public String getTotalOrdenString() {
        String total = "0.0";
        DecimalFormat formateador = new DecimalFormat("###,###.##");
//Este daria a la salida 1,000
        total = formateador.format(getTotalOrden());
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
        ordenes = ODAO.buscarOrdenUsuario(controlSeccion.getMiembro().getUsuario());
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

    public boolean isEsCliente() {
        return esCliente;
    }

    public void setEsCliente(boolean esCliente) {
        Rol rolSelect = RolDAO.consultar(Rol.class, controlSeccion.getRolselect());
        this.esCliente = rolSelect.getNombre().equals("CLIENTE");
    }

}
