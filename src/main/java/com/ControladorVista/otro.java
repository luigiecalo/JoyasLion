/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import com.Dao.ModeloDaoimplement;
import com.Entidades.Modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author luigi
 */
@ManagedBean
@ViewScoped
public final class otro implements Serializable {

    //UTILIDADES
    private Long idmod;
    private String estado = "R";
    private List<Modelo> modelos = new ArrayList<Modelo>();
    private Modelo modeloSelecionado = new Modelo();
    private ModeloDaoimplement ModeloDao = new ModeloDaoimplement();
//    RequestContext Requescontext = RequestContext.getCurrentInstance();

    /**
     * Creates a new instance of controlplantillas
     */
    public otro() {

    }
////METODOS


    

    //Limpia la vista
    private void limpiar() {
        
    }
  


////GET AND SET
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public Modelo getModeloSelecionado() {
        return modeloSelecionado;
    }

    public void setModeloSelecionado(Modelo modeloSelecionado) {
        this.modeloSelecionado = modeloSelecionado;
    }

    
    

}
