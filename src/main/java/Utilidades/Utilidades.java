/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Entidades.Modulo;
import com.Entidades.RolModuloPermiso;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author usuario
 */
public class Utilidades implements Serializable {

    private String focus = "null";

    public boolean permisos(Long idRol, Modulo modulo, String permiso) {
        boolean result = false;
        for (RolModuloPermiso rolmodulospermiso : modulo.getRolModuloPermisoList()) {
            if (rolmodulospermiso.getRol().getIdrol().equals(idRol)) {
                if (rolmodulospermiso.getPermisos().getNombrePermiso().equals(permiso) || rolmodulospermiso.getPermisos().getNombrePermiso().equals("T")) {
                    result = true;
                }
            }

        }
        return result;
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
        requestContext.getCurrentInstance().execute("$('.modalPseudoClass').modal('hide');");
    }

    public String formatoDecimal(Double numero, String format) {
        DecimalFormat decimales = new DecimalFormat(format);
        return decimales.format(numero);
    }

    public void modal(String id, String estado) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getCurrentInstance().execute("$('#" + id + "').modal('" + estado + "');");
    }

    public long toLong(Number number) {
        return number.longValue();
    }

    public String LongToDateFormat(Long fecha, String formato) {
        Date d = new Date(fecha);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (formato.equals("") || formato.isEmpty() || formato == null) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            sdf = new SimpleDateFormat(formato);
        }
        String date = sdf.format(d);
        return date;
    }
    
         public List<Map> getMenu(List<Modulo> modulos) {
        List<Map> menu = new LinkedList<Map>();
        for (Modulo m : modulos) {
            Modulo modulo = m;
            Map item = new HashMap<String, String>();
            
            if (modulo.getGrupomodulo() == null) {
                addItem(item, modulo, menu);

                menu.add(item);
            } else {
                item.put("nombre", modulo.getGrupomodulo().getNombre());
                item.put("icono", modulo.getGrupomodulo().getIcono());
                if (menu.isEmpty()) {
                    addItemGrup(item, modulo);
                    menu.add(item);
                } else {
                    boolean encontro = false;
                    Map itemencontrado = null;
                    for (int i = 0; i < menu.size(); i++) {
                        Map itemMenu = menu.get(i);
                        if (itemMenu.get("nombre").equals(item.get("nombre"))) {
                            encontro = true;
                            itemencontrado = itemMenu;
                            break;
                        }
                    }
                    if (!encontro) {
                        addItemGrup(item, modulo);
                        menu.add(item);
                    } else {
                       addItemGrup(item, modulo);
//                        if (item.get("submodulos") == null) {
                        System.out.println("Item Encontrado" + itemencontrado);
                        System.out.println("Item -         " + item);
                        List<Modulo> modulosEncontraos = (List<Modulo>) itemencontrado.get("modulos");
                        List<Modulo> modulositem = (List<Modulo>) item.get("modulos");
                        boolean relacion = false;
                        for (Modulo moduloencontrado : modulosEncontraos) {
                            if (moduloencontrado.getSubgrupos() != null) {
                                for (Modulo moduloitem : modulositem) {
                                    if (moduloitem.getSubgrupos() != null) {
                                        if (moduloitem.getSubgrupos().equals(moduloencontrado.getSubgrupos())) {
                                            Modulo moduloRelacion = moduloencontrado;
                                            moduloRelacion.getSubgrupos().getModulos().add(modulo);
                                            relacion = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (!relacion) {
                            modulosEncontraos.addAll(modulositem);
                        }
                    }
                }
            }
        }

        return menu;
    }

    public void addItem(Map item, Modulo modulo, List<Map> menu) {

        if (modulo.getSubgrupos() == null) {

            System.out.println("*" + modulo.getNombre() + "*");
            item.put("src", modulo.getSrc());
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getIcono());
            item.put("nombre", modulo.getNombre());
            item.put("modulos", null);
            item.put("tipo","modulo");
            item.put("clase",null);
        }
    }

    public void addItemGrup(Map item, Modulo modulo) {
        if (modulo.getSubgrupos() == null) {
            System.out.println("*" + modulo.getGrupomodulo().getNombre() + "*");
            item.put("src", modulo.getGrupomodulo().getNombre());
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getGrupomodulo().getIcono());
            item.put("nombre", modulo.getGrupomodulo().getNombre());
            List<Modulo> modulosvalidos = new ArrayList<Modulo>();

            System.out.println("-" + modulo.getNombre() + "-");
            modulosvalidos.add(modulo);
            item.put("modulos", modulosvalidos);
            item.put("tipo","grupo");
            item.put("clase","fa-angle-left pull-right");
        } else {
            addItemSubGrup(item, modulo);
        }
    }

    public void addItemSubGrup(Map item, Modulo modulo) {
        System.out.println("-" + modulo.getSubgrupos().getNombre() + "-");
        item.put("src", modulo.getSubgrupos().getNombre());
        item.put("id", modulo.getIdmodulo());
        item.put("icono", modulo.getGrupomodulo().getIcono());
        item.put("nombre", modulo.getGrupomodulo().getNombre());
        List<Modulo> modulosvalidos = new ArrayList<Modulo>();
        List<Modulo> submodulosvalidos = new ArrayList<Modulo>();
        submodulosvalidos.add(modulo);
        modulo.getSubgrupos().setModulos(submodulosvalidos);
        modulosvalidos.add(modulo);
        item.put("modulos", modulosvalidos);
        item.put("tipo","subgrupo");
        item.put("clase","fa-angle-left pull-right");
    }


    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

}
