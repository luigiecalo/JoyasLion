/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import com.Entidades.Miembro;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author LuisGuillermo
 */
@Named(value = "dFView")
public class DFView {

      public void chooseCar() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("selectCar", options, null);
    }
     
    public void onCarChosen(SelectEvent event) {
        Miembro mie = (Miembro) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car Selected", "Id:" + mie.getDocumento());
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
