/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import com.Dao.MiembroDaoimplement;
import com.Entidades.Miembro;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LuisGuillermo
 */
@Named(value = "dFCarsView")
@ViewScoped
public class DFCarsView {

    private List<Miembro> cars;
 
    private MiembroDaoimplement miembroDao = new MiembroDaoimplement();
    
    @PostConstruct
    public void init() {
        cars = miembroDao.consultarTodo(Miembro.class);
    }
     
    public List<Miembro> getCars() {
        return cars;
    }
 
    public void setService(MiembroDaoimplement service) {
        this.miembroDao = service;
    }
     
    public void selectCarFromDialog(Miembro car) {
        RequestContext.getCurrentInstance().closeDialog(car);
    }
}
