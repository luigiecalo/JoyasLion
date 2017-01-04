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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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

    public void agregarordenmodelo(Modelo modelo, int cant, String material, Double peso_material) {
        OrdenModelo om = new OrdenModelo();
        om.setModelo(modelo);
        om.setCantidad(cant);
        om.setDescuento(0.0);
        om.setMaterial(material);
        om.setPeso_material(cant * peso_material);
        om.setValor(peso_material * 100);
        om.setTotal(cant * om.getValor());
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
            nuevoordMod.setOrdenModeloPK(ordMod.getModelo().getId(), orden.getId());
            descripcion.add(nuevoordMod);
        }
        orden.setOrdenesModelo(descripcion);
        ODAO.modificar(orden);
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
