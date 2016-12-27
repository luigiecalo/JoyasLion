/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import com.Entidades.Modelo;
import com.Entidades.Orden;
import com.Entidades.OrdenModelo;
import java.util.ArrayList;
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
    private Orden orden;
    private OrdenModelo oredeModelo;
    //LISTAS
    private List<OrdenModelo> ordenesMoldelos = new ArrayList<OrdenModelo>();

    public ControlOrden() {
    }
/// METODOS
    //Agrega un Modelo a La lista
    public void agregarordenmodelo(Modelo modelo,int cant,String material,Double peso_material){
    OrdenModelo om= new OrdenModelo();
    om.setModelo(modelo);
    om.setCantidad(cant);
    om.setDescuento(0.0);
    om.setMaterial(material);
    om.setPeso_material(peso_material);
    om.setValor(2200.0);
    om.setTotal(cant*peso_material);
    ordenesMoldelos.add(om);
    }
///GET Y SET

    public Orden getOrden() {
        return orden;
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
    
   

   
    
}
