/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author LuisGuillermo
 */
@ManagedBean
@ViewScoped
public class ControlUtilidades implements  Serializable{
    
    
    private Utilidades util = new Utilidades();

    /**
     * Creates a new instance of ControlUtilidades
     */
    public ControlUtilidades() {
    }
    
    public String format(Double valor,String formato){
    return util.formatoDecimal(valor, formato);
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }
    
    
    
    
    
}
