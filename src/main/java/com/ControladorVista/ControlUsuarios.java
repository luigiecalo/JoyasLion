/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import static com.ControladorVista.ControlSeccion.toLong;
import com.Dao.MiembroDaoimplement;
import com.Dao.ModuloDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.UsuarioDaoimplement;
import com.Entidades.Miembro;
import com.Entidades.Modulo;
import com.Entidades.Rol;
import com.Entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author luigi
 */
@ManagedBean
@ViewScoped
public final class ControlUsuarios implements Serializable {

    private Long idmod;
    private Long rolselect = 0l;
    private Utilidades util = new Utilidades();
    private Usuario usuario = new Usuario();
    private Miembro miembro = new Miembro();
    private Rol rol = new Rol();
//    private Rol rolselect= new Rol();
    private Miembro miembroSelecionado = new Miembro();
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Rol> rolesSelect = new ArrayList<Rol>();
    private List<Usuario> usuarioLista = new ArrayList<Usuario>();
    private List<Miembro> miembros = new ArrayList<Miembro>();
    private Map<String, Long> rolesLista;
    private boolean Registrar = false;
    private boolean estadoMiembro = true;
    public String data = "1";
    private String apellido1 = "";
    private String estado = "R";
    private UsuarioDaoimplement UsuDao = new UsuarioDaoimplement();
    private MiembroDaoimplement MiemDao = new MiembroDaoimplement();
    private RolDaoimplement RolDao = new RolDaoimplement();
    RequestContext Requescontext = RequestContext.getCurrentInstance();

    /**
     * Creates a new instance of controlplantillas
     */
    public ControlUsuarios() {
        cargarRoles();

    }

//metodos
    public void listarusuarios() {
        usuarioLista = UsuDao.Listar();
    }

    public void listarMiembros() {
        miembros = MiemDao.consultarTodo(Miembro.class);
    }

    public boolean bootonregistro(Long idrol, Modulo mod) {
        return util.permisos(idrol, mod,"E");
    }

    public boolean verBtnRegistro() {
        if (estado.equals("R")) {
            return true;
        } else {
            return false;
        }
    }

    public void registroModulo() {
        Registrar = true;
        limpiar();
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void enviarselecionado(Miembro m) {
        estado = "A";
        Registrar = true;
        RequestContext.getCurrentInstance().reset("form:panel");
        miembroSelecionado = m;
        rolesSelect=m.getUsuario().getRoles();
    }

    public void limpiar() {
        estado = "R";
        Miembro m = new Miembro();
        m.setApellido1("");
        m.setApellido2("");
        m.setNombre1("");
        m.setNombre2("");
        RequestContext.getCurrentInstance().reset("form:panel");
        miembroSelecionado = m;
        rolesSelect.clear();
        listarMiembros();
        
//        ControlUsuarios cu= new ControlUsuarios();

    }

    public void save() {

        if (rolesSelect.size() <= 0) {
            util.crearmensajes("ALERTA", "Primer Mensage", "SELECIONE AL MENENOS UN ROL");
        } else {
            miembroSelecionado.setEstado(estadoMiembro ? "ACTIVO" : "INACTIVO");
            usuario.setLogin(miembroSelecionado.getNombre1());
            if (estado.equals("R")) {
                saveUsuario(miembroSelecionado);
                MiemDao.crear(miembroSelecionado);

            } else {
                saveUsuario(miembroSelecionado);
                MiemDao.modificar(miembroSelecionado);

            }

            limpiar();
            util.crearmensajes("INFO", "Primer Mensage", "REGISTRO EXITOSO");
        }

    }

    private void saveUsuario(Miembro miem) {
        Usuario usuario = new Usuario();
        if (estado.equals("R")) {
            usuario.setLogin(miem.getNombre1());
            usuario.setPassword(miem.getDocumento());
        } else if (miem.getUsuario() != null) {
            usuario = miem.getUsuario();
        }
        usuario.setEstado(estadoMiembro ? 1 : 0);
        usuario.setRoles(rolesSelect);
        miem.setUsuario(usuario);

    }

    public void eliminaRol(Rol rol) {
        rolesSelect.remove(rol);
    }

    public void agregaRol() {
        rol = RolDao.consultarC(Rol.class, rolselect);
        if (rol != null) {
            if (rolesSelect.size() <= 0) {
                rolesSelect.add(rol);
            } else {
                boolean encontro = false;
                for (Rol role : rolesSelect) {
                    if (role.equals(rol)) {
                        encontro = true;
                    }
                }
                if (encontro) {
                    util.crearmensajes("INFO", "ADVERTENCIA", "ROL YA SELECIONADO");
                } else {
                    rolesSelect.add(rol);
                }
            }

        } else {
             util.crearmensajes("INFO", "ADVERTENCIA", "SELECIONE ALMENOS UN ROL");
        }
        rolselect=0l;
    }

    public void consultaModulo() {
        Registrar = false;
    }

//    public String moduloRegistro() {
//        if (Registrar) {
//            return "visible";
//        } else {
//            return "hidden";
//        }
//    }
//
//    public String moduloreConsulta() {
//        if (Registrar) {
//            return "hidden";
//        } else {
//            return "visible";
//        }
//    }
    //GET AND SET
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarioLista() {
        return usuarioLista;
    }

    public void setUsuarioLista(List<Usuario> usuarioLista) {
        this.usuarioLista = usuarioLista;
    }

    public boolean isRegistrar() {
        return Registrar;
    }

    public void setRegistrar(boolean Registrar) {
        this.Registrar = Registrar;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Miembro getMiembroSelecionado() {
        return miembroSelecionado;
    }

    public void setMiembroSelecionado(Miembro miembroSelecionado) {
        this.miembroSelecionado = miembroSelecionado;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public boolean isEstadoMiembro() {
        return estadoMiembro;
    }

    public void setEstadoMiembro(boolean estadoMiembro) {
        this.estadoMiembro = estadoMiembro;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRoles() {
        roles = RolDao.Listar();
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Rol> getRolesSelect() {
        return rolesSelect;
    }

    public void setRolesSelect(List<Rol> rolesSelect) {
        this.rolesSelect = rolesSelect;
    }

    public void cargarRoles() {
        rolesLista = new HashMap<String, Long>();
        for (Rol rol : getRoles()) {
            rolesLista.put(rol.getNombre(), rol.getIdrol());
        }
    }

    public Map<String, Long> getRolesLista() {

        return rolesLista;
    }

    public Long getRolselect() {
        return rolselect;
    }

    public void setRolselect(Long rolselect) {
        this.rolselect = rolselect;
    }

}
