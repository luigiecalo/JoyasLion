/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Entidades.Modulo;
import com.Entidades.RolModuloPermiso;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author usuario
 */
public class Utilidades implements Serializable {

    public boolean permisos(Long idRol, Modulo modulo,String permiso) {
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
        }else{
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,mensagePrincipal, MensageSegundario));
        }
        requestContext.getCurrentInstance().execute("$('.modalPseudoClass').modal('hide');");
    }

    public long toLong(Number number) {
        return number.longValue();
    }
}
