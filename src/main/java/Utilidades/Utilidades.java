/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Dao.ModuloDaoimplement;
import com.Entidades.Modulo;
import com.Entidades.RolModuloPermiso;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.imageio.ImageIO;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author usuario
 */
public class Utilidades {

    private String focus = "null";
    private crearcarpeta ruta = new crearcarpeta();

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
        ModuloDaoimplement MoDao = new ModuloDaoimplement();
        int index = 0;
        List<Map> menu = new LinkedList<Map>();
        for (Modulo m : modulos) {
            Modulo modulo = MoDao.consultar(Modulo.class, m.getIdmodulo());
            Map item = new HashMap<String, String>();
            item.put("src", modulo.getSrc());
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

    public void logMenu(List<Map> menu) {
        System.out.println("---MENU PRINCIPAL--");

        for (Map menu1 : menu) {
            System.out.println("*" + menu1.get("nombre") + "*");
            if (menu1.get("modulos") != null) {
                for (Modulo modulo : (List<Modulo>) menu1.get("modulos")) {
                    if (modulo.getSubgrupos() == null) {
                        System.out.println("-" + modulo.getNombre() + "-");
                    } else {
                        System.out.println("v" + modulo.getSubgrupos().getNombre());
                        for (Modulo modulosub : modulo.getSubgrupos().getModulos()) {
                            System.out.println(" -" + modulosub.getNombre());
                        }
                    }
////
                }
            }

        }
    }

    public void addItem(Map item, Modulo modulo, List<Map> menu) {

        if (modulo.getSubgrupos() == null) {
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getIcono());
            item.put("nombre", modulo.getNombre());
            item.put("grupo", null);
            item.put("modulos", null);
            item.put("submodulos", null);
            item.put("update", "@form");
            item.put("subupdate", null);
            item.put("subupdate2", null);
        }
    }

    public void addItemGrup(Map item, Modulo modulo) {
        if (modulo.getSubgrupos() == null) {
//            System.out.println("*" + modulo.getGrupomodulo().getNombre() + "*");
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getGrupomodulo().getIcono());
            item.put("nombre", modulo.getGrupomodulo().getNombre());
            item.put("grupo", modulo.getGrupomodulo().getNombre());
            List<Modulo> modulosvalidos = new ArrayList<Modulo>();

//            System.out.println("-" + modulo.getNombre() + "-");
            modulosvalidos.add(modulo);

            item.put("modulos", modulosvalidos);
            item.put("submodulos", null);
            item.put("update", null);
            item.put("subupdate", "@form");
            item.put("subupdate2", null);
        } else {
            addItemSubGrup(item, modulo);
        }
    }

    public void addItemSubGrup(Map item, Modulo modulo) {
//            System.out.println("-" + modulo.getSubgrupos().getNombre() + "-");
        item.put("id", modulo.getIdmodulo());
        item.put("icono", modulo.getGrupomodulo().getIcono());
        item.put("nombre", modulo.getGrupomodulo().getNombre());
        item.put("grupo", modulo.getSubgrupos().getNombre());
        List<Modulo> modulosvalidos = new ArrayList<Modulo>();
        List<Modulo> submodulosvalidos = new ArrayList<Modulo>();
        submodulosvalidos.add(modulo);
        modulo.getSubgrupos().setModulos(submodulosvalidos);
        modulosvalidos.add(modulo);
        item.put("modulos", modulosvalidos);
        item.put("update", null);
        item.put("subupdate", null);
        item.put("subupdate2", "@form");
    }

    public String cargarimagenTemp(UploadedFile file,String img) {
//        String img = "";
        try {
            FacesMessage message = new FacesMessage("Se Cargo", file.getFileName() + " Exitosamente");
            BufferedImage imBuff = ImageIO.read(file.getInputstream());

            File directorio = null;
            try {

                directorio = new File(ruta.Ruta() + "/temp");
                directorio.mkdir();
                String carpeta = "select00001";
            } catch (Exception ex) {
                ex.printStackTrace();
            }
//            img = file.getFileName() + ruta.totalArchivos(directorio);
            File file2 = new File(directorio.getPath(), img + ".png");
            ImageIO.write(imBuff, "png", file2);
            crearmensajes("INFO", "Primer Mensage", "SE CARGO IMAGEN CORRECTAMENTE");

        } catch (IOException ex) {
        }
        return img;
    }

    public void guardarImagen(String nombrecarpeta, String estado, String imagen, boolean editarimg, String id) throws IOException {
        File Origen = new File(ruta.Ruta() + "");
        if (estado.equals("R")) {
            Origen = new File(ruta.Ruta() + "/temp", imagen + ".png");
        } else if (editarimg) {
            Origen = new File(ruta.Ruta() + "/temp", imagen + ".png");
        } else {
            Origen = new File(ruta.Ruta() + "/imagenes/" + nombrecarpeta, imagen + ".png");
        }
        BufferedImage imBuff = ImageIO.read(Origen);
        File Destino = new File(ruta.Ruta() + "/imagenes/" + nombrecarpeta, "" + id + ".png");
        ImageIO.write(imBuff, "png", Destino);
    }

    public String getExiteimagen(String carpeta, String imagen) {
        File Origen = new File(ruta.Ruta() + "/" + carpeta, imagen + ".png");
        if (!Origen.exists()) {
            if (carpeta.equals("imagenes/usuarios")) {
                imagen = "default";
            } else if (carpeta.equals("imagenes/modelos")) {
                imagen = "default2";
            }
        }
        return imagen;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

}
