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
public final class ControlUsuarios implements Serializable {

    //UTILIDADES
    private Long idmod;
    private Long rolselect = 0l;
    private Utilidades util = new Utilidades();
    private Usuario usuario = new Usuario();
    private Miembro miembro = new Miembro();
    private crearcarpeta ruta = new crearcarpeta();
    private String imagen = "default";
    private String carpeta = "temp/";
    private List<String>listimgTemp= new ArrayList<String>();
    File directorioTemp = new File(ruta.Ruta() + "/temp");

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
    private boolean imagenedit = false;
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
        try {
            FacesMessage message = new FacesMessage("Se Cargo", event.getFile().getFileName() + " Exitosamente");
            BufferedImage imBuff = ImageIO.read(event.getFile().getInputstream());

            File directorio = null;
            try {

                directorio = new File(ruta.Ruta() + "/temp");
                directorio.mkdir();
                carpeta = "select00001";
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String img = event.getFile().getFileName() + ruta.totalArchivos(directorio);
            File file = new File(directorio.getPath(), img + ".png");
            ImageIO.write(imBuff, "png", file);
            this.imagenedit = true;
            this.imagen = img;
            editarcarpeta();
            util.crearmensajes("INFO", "Primer Mensage", "SE CARGO IMAGEN CORRECTAMENTE");
        } catch (IOException ex) {
        }
    }

    public void handleFileUpload2(FileUploadEvent event) {
        carpeta = "select00001";
        this.imagen = util.cargarimagenTemp(event.getFile());
        listimgTemp.add(event.getFile().toString());
        this.imagenedit = true;
        editarcarpeta();
    }

    public void enviarselecionado(Miembro m) {
        estado = "A";
        Registrar = true;
        RequestContext.getCurrentInstance().reset("form:panel");
        miembroSelecionado = m;
        imagen = util.getExiteimagen("imagenes/usuarios",m.getImagen());
        editarcarpeta();
        rolesSelect = m.getUsuario().getRoles();
    }

    public void limpiar() {
        estado = "R";
        Miembro m = new Miembro();
        m.setApellido1("");
        m.setApellido2("");
        m.setNombre1("");
        m.setNombre2("");
        RequestContext.getCurrentInstance().reset("form:panel");
        imagen = "default";
        miembroSelecionado = m;
        editarcarpeta();
        rolesSelect.clear();
        listarMiembros();
        Eliminartemp();
        imagenedit = false;

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
        miem.setImagen(imagen.equals("default") ? imagen : miem.getDocumento());
        miem.setUsuario(usuario);

    }

    public void eliminaRol(Rol rol) {
        rolesSelect.remove(rol);
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
        rolselect = 0l;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    private void Eliminartemp() {
        ruta.EliminarArchivosTemp(directorioTemp, listimgTemp);
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

}
