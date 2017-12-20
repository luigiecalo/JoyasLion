/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import Utilidades.crearcarpeta;
import com.Dao.MiembroDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.UsuarioDaoimplement;
import com.Entidades.Miembro;
import com.Entidades.Modulo;
import com.Entidades.Rol;
import com.Entidades.Usuario;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author luigi
 */
@ManagedBean
@ViewScoped
public final class ControlUsuariosClientes implements Serializable {

    //UTILIDADES
    private Long idmod;
    private Long rolselect = 0l;
    private Utilidades util = new Utilidades();
    private Usuario usuario = new Usuario();
    private Miembro miembro = new Miembro();
    private String identificacion = " ";
    private String apellido1 = " ";
    private String apellido2 = " ";
    private String nombre1 = " ";
    private String nombre2 = " ";
    private crearcarpeta ruta = new crearcarpeta();
    private String imagen = "default";
    private String carpeta = "temp/";
    private String imgTemp = "";
    File directorioTemp = new File(ruta.Ruta() + "/temp");

    private Rol rol = new Rol();
//    private Rol rolselect= new Rol();
    private Miembro miembroSelecionado = new Miembro();
    private List<Rol> roles = new ArrayList<Rol>();
    private List<Rol> rolesSelect = new ArrayList<Rol>();
    private Map<String, Long> rolesLista;
    private boolean Registrar = false;
    private boolean estadoMiembro = true;
    private boolean imagenedit = false;
    public String data = "1";
    private String estado = "R";
    private UsuarioDaoimplement UsuDao = new UsuarioDaoimplement();
    private MiembroDaoimplement MiemDao = new MiembroDaoimplement();
    private RolDaoimplement RolDao = new RolDaoimplement();
    RequestContext Requescontext = RequestContext.getCurrentInstance();

    //Session
    @ManagedProperty(value = "#{controlSeccion}")
    private ControlSeccion controlSeccion;

    /**
     * Creates a new instance of controlplantillas
     */
    public ControlUsuariosClientes() {
        cargarRoles();

    }

//metodos
    public boolean bootonregistro(Long idrol, Modulo mod) {
        return util.permisos(idrol, mod, "E");
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
        carpeta = "select00001";
        imgTemp = "U" + controlSeccion.getMiembro().getDocumento();
        this.imagen = util.cargarimagenTemp(event.getFile(), imgTemp);
        this.imagenedit = true;
        editarcarpeta();
    }

    public void enviarselecionado(Miembro m) {
        estado = "A";
        Registrar = true;
        RequestContext.getCurrentInstance().reset("form:panel");
        miembroSelecionado = m;
        imagen = util.getExiteimagen("imagenes/usuarios", m.getImagen());
        editarcarpeta();
        rolesSelect = m.getUsuario().getRoles();
    }
//esto Para selecionar y retornar un miembroslecionado

    public void selectCarFromDialog(Miembro car) {
        RequestContext.getCurrentInstance().closeDialog(car);
    }

    public void limpiar() {

        Miembro m = new Miembro();
        apellido1 = "";
        apellido2 = "";
        nombre1 = "";
        nombre2 = "";
        miembroSelecionado = m;
        editarcarpeta();
        rolesSelect.clear();
        Eliminartemp();
        imagenedit = false;
        rol= new Rol();

//        ControlUsuarios cu= new ControlUsuarios();
    }

    public void save() {
        rol = RolDao.consultarC(Rol.class, 4l);
        estadoMiembro = true;
        if (identificacion.isEmpty() || apellido1.isEmpty() || nombre1.equals(" ") || identificacion.equals(" ") || apellido1.equals(" ") || nombre1.equals(" ")) {
            util.crearmensajes("ALERTA", "Primer Mensage", "Llene todos Los campos Obligatorios");
        } else {
            miembroSelecionado = new Miembro();
            miembroSelecionado.setDocumento(identificacion);
            miembroSelecionado.setApellido1(apellido1);
            miembroSelecionado.setApellido2(apellido2);
            miembroSelecionado.setNombre1(nombre1);
            miembroSelecionado.setNombre2(nombre2);
            miembroSelecionado.setEstado(estadoMiembro ? "ACTIVO" : "INACTIVO");
            usuario.setLogin(miembroSelecionado.getNombre1());
            rolesSelect.add(rol);
            if (estado.equals("R")) {
                saveUsuario(miembroSelecionado);
                MiemDao.crear(miembroSelecionado);
                if (imagen != "default") {
                    guardarImagen2(miembroSelecionado);
                }

            } else {
                saveUsuario(miembroSelecionado);
                MiemDao.modificar(miembroSelecionado);
                if (imagen != "default") {
                    guardarImagen2(miembroSelecionado);
                }
            }

            controlSeccion.iniciar(miembroSelecionado.getNombre1(), miembroSelecionado.getDocumento());
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
        miem.setImagen(imagen.equals("default") ? imagen : miem.getDocumento());
        miem.setUsuario(usuario);

    }

    private void guardarImagen(Miembro miembro) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            File Origen = new File(ruta.Ruta() + "");
            if (estado.equals("R")) {
                Origen = new File(ruta.Ruta() + "/temp", imagen + ".png");
            } else if (imagenedit) {
                Origen = new File(ruta.Ruta() + "/temp", imagen + ".png");
            } else {
                Origen = new File(ruta.Ruta() + "/imagenes/usuarios", imagen + ".png");
            }
            BufferedImage imBuff = ImageIO.read(Origen);
            File Destino = new File(ruta.Ruta() + "/imagenes/usuarios", "" + miembro.getDocumento() + ".png");
            ImageIO.write(imBuff, "png", Destino);
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.toString()));
        }
    }

    private void guardarImagen2(Miembro miembro) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            util.guardarImagen("usuarios", estado, imagen, imagenedit, miembro.getDocumento());
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.toString()));
        }
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
    //SET SECCION
    public void setControlSeccion(ControlSeccion controlSeccion) {
        this.controlSeccion = controlSeccion;
    }

    //GET AND SET
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isRegistrar() {
        return Registrar;
    }

    public void setRegistrar(boolean Registrar) {
        this.Registrar = Registrar;
    }

    public boolean isImagenedit() {
        return imagenedit;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    private void Eliminartemp() {
        ruta.EliminarArchivosTemp(directorioTemp, imgTemp);
    }

    public void editarcarpeta() {
        if (estado.equals("R") || carpeta.equals("select00001")) {
            carpeta = "temp/";
        } else {
            carpeta = "imagenes/usuarios/";
        }
    }

    public String getCarpeta() {

        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

}
