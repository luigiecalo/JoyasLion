/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author LuisGuillermo
 */
@ManagedBean
@Dependent
public class ControlUtilidades {
    
    
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
