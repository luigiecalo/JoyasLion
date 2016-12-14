/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LuisGuillermo
 */
@Named(value = "nuevo")
@ViewScoped
public class Nuevo {

    private String nombre="luigie";

    /**
     * Creates a new instance of Nuevo
     */
    public Nuevo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
