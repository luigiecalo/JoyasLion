/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.MaterialDaoimplement;
import com.Entidades.Material;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;

@ManagedBean
@RequestScoped
public class ControlPrueba {

    private List<CommandButton> botones;
    private Utilidades util = new Utilidades();
    private MaterialDaoimplement MaterialDao = new MaterialDaoimplement();

    public void agregarBoton() {
        String mesage = "este Es el nuemo mensaje";
        
        crearmensajes("ALERTA", "CUIDADO!!", "Selecione Primero una Piedra central" + mesage);
    }
    /*
     * Crea componente primefaces desde codigo java y devuelve al componente
     *  primefaces xhtml en su propiedad binding
     */
    private TabView tabView;

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

    public TabView getTabView() {
        FacesContext fc = FacesContext.getCurrentInstance();
        tabView = (TabView) fc.getApplication().createComponent("org.primefaces.component.TabView");

        // cargar la lista de objetos para tabview
//        List liscaldimensional = new ArrayList();
       List<Material> liscaldimensional = MaterialDao.consultarTodo(Material.class);

        //Se crean dinamicamente las tabs y en su contenido, unas cajas de texto
        for (Material sub : liscaldimensional) {
            Tab tab = new Tab();
            tab.setTitle(sub.getNombre());
            Random randomGenerator = new Random();
            int total = 4;
            for (int i = 0; i < total; i++) {
                InputText inputtext = new InputText();
                inputtext.setLabel("Label");
                inputtext.setValue("id:" + inputtext.getClientId());
                inputtext.setOnfocus("");
                tab.getChildren().add(inputtext);
            }
            tabView.getChildren().add(tab);
        }
        return tabView;
    }
    
     public void crearmensajes(String estado, String mensagePrincipal, String MensageSegundario) {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (estado.equals("ERROR")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagePrincipal, MensageSegundario));
        } else if (estado.equals("ALERTA")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensagePrincipal, MensageSegundario));
        } else if (estado.equals("INFO")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagePrincipal, MensageSegundario));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagePrincipal, MensageSegundario));
        }
    }

    public List<CommandButton> getBotones() {
        return botones;
    }

    public void setBotones(List<CommandButton> botones) {
        this.botones = botones;
    }

}
