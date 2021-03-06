/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import Utilidades.crearcarpeta;
import com.Dao.CirconDaoimplement;
import com.Dao.ModeloCirconDaoimplement;
import com.Dao.ModeloDaoimplement;
import com.Dao.ModeloImagenDaoimplement;
import com.Dao.PiedraCentralDaoimplement;
import com.Dao.TipoDaoimplement;
import com.Entidades.Circon;
import com.Entidades.Modelo;
import com.Entidades.ModeloCircon;
import com.Entidades.ModeloImagen;
import com.Entidades.ModeloPiedraCentral;
import com.Entidades.Modulo;
import com.Entidades.PiedraCentral;
import com.Entidades.Tipo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author luigi
 */
@ManagedBean
@ViewScoped
public final class ControlModelos implements Serializable {

    //UTILIDADES
    private Long idmod;
    private Utilidades util = new Utilidades();
    private String estado = "R";
    private String valueFilter = "";
    private String estadofilter = "";
    private boolean Registrar = false;
    private boolean estadoModelo = true;
    private boolean zircon = false;
    private boolean piedra = false;
    private boolean cantidad = true;
    private boolean cantidadPiedra = true;
    private String focus = "panel";
    private Double pesoCirones = 0.0;
    private String imagen = "default2";
    private String imgTemp = "";
    private List<Map> modelogaleria = new ArrayList<Map>();
    private String carpeta = "temp/";
    private boolean imagenedit = false;
    private boolean imageneditgaleria = false;
    private crearcarpeta ruta = new crearcarpeta();
    File directorioTemp = new File(ruta.Ruta() + "/temp");

    private Map<String, Long> piedrasCentralesLista;
    private Map<String, Long> circonesLista;
    private Map<String, Long> tiposModeloLista;
    private Long piedrasCentralSelect = 0l;
    private Long circoneSelect = 0l;
    private Long tiposModelolSelect = 0l;
    private Long tiposModelolSelectfilter = 0l;
    private int valorCantidad = 0;
    private int valorCantidadPiedra = 0;

    //OBJECTOS
    private Modelo modeloSelecionado = new Modelo();
    private Circon circon = new Circon();
    private Tipo tipo = new Tipo();
    private PiedraCentral piedracentral = new PiedraCentral();
    private ModeloCircon modelocircon = new ModeloCircon();
    //LISTAS
    private List<Modelo> modelos = new ArrayList<Modelo>();
    private List<Circon> circones = new ArrayList<Circon>();
    private List<Circon> circonesSelect = new ArrayList<Circon>();
    private List<ModeloCircon> modelocircones = new ArrayList<ModeloCircon>();
    private List<ModeloCircon> modelocirconSelect = new ArrayList<ModeloCircon>();
    private List<Tipo> tiposModelo = new ArrayList<Tipo>();
    private List<PiedraCentral> piedracentrales = new ArrayList<PiedraCentral>();
    private List<ModeloPiedraCentral> modeloPiedracentralesSelect = new ArrayList<ModeloPiedraCentral>();
    private List<ModeloPiedraCentral> modelopiedracentrales = new ArrayList<ModeloPiedraCentral>();
    //DAOS

    private ModeloDaoimplement ModeloDao = new ModeloDaoimplement();
    private CirconDaoimplement CirconDao = new CirconDaoimplement();
    private TipoDaoimplement TipoDao = new TipoDaoimplement();
    private PiedraCentralDaoimplement PiedraCentralDao = new PiedraCentralDaoimplement();
    private ModeloCirconDaoimplement ModeloCirconDao = new ModeloCirconDaoimplement();
    private ModeloImagenDaoimplement mdimpl = new ModeloImagenDaoimplement();
//    RequestContext Requescontext = RequestContext.getCurrentInstance();

    //Session
    @ManagedProperty(value = "#{controlSeccion}")
    private ControlSeccion controlSeccion;

    /**
     * Creates a new instance of controlplantillas
     */
    public ControlModelos() {
        cargarListas();
    }
////METODOS
    //validar los permisos De Los botones

    public boolean butompermisos(Long idrol, Modulo mod, String permiso) {
        return util.permisos(idrol, mod, permiso);
    }

    public String format(Double valor, String formato) {
        return util.formatoDecimal(valor, formato);
    }

    //cambia de vista a la vista De consulta
    public void registroModulo() {
        Registrar = true;
        limpiar();
    }

    //Limpia la vista
    public void limpiar() {
        estado = "R";
        Modelo modelo = new Modelo();
        modeloSelecionado = modelo;
        RequestContext.getCurrentInstance().reset("form:panel");
        tiposModelolSelect = 0l;
        piedrasCentralSelect = 0l;
        circoneSelect = 0l;
        modeloPiedracentralesSelect.clear();
        modelocirconSelect.clear();
        modelopiedracentrales.clear();
        modelocircones.clear();
        listarModelos();
        Eliminartemp();
        editarcarpeta();
        imagenedit = false;
        imagen = "default2";
        modelogaleria.clear();
    }

    public void deletemodelogaleria(Map gal) {
        modelogaleria.remove(gal);
        ruta.EliminarArchivosTemp(directorioTemp, (String) gal.get("nombre"));
    }

    //Lista todos Los Modelos de La vista De Consulta
    public void listarModelos() {
        if (valueFilter.equals("") && tiposModelolSelectfilter.equals(0l) && estadofilter.equals("")) {
            modelos = ModeloDao.consultarTodo(Modelo.class);
        } else if (tiposModelolSelectfilter.equals(0l)) {
            modelos = ModeloDao.buscarModeloEstadoILKE(valueFilter, estadofilter);
        } else {
            modelos = ModeloDao.buscarModeloEstadoTipoILKE(valueFilter, TipoDao.consultar(Tipo.class, tiposModelolSelectfilter), estadofilter);
        }

    }

    public void selecionar() {
        estado = "A";
        Registrar = true;
        RequestContext.getCurrentInstance().reset("form:panel");
    }

    private void cargarListas() {
        cargalistaTipoModelos();
        cargaListaCircones();
        cargaListaPiedrasC();
        selecoionarcircon();
        selecoionarpiedra();
    }

    private void cargalistaTipoModelos() {
        tiposModeloLista = new HashMap<String, Long>();
        for (Tipo tipo : getTiposModelo()) {
            tiposModeloLista.put(tipo.getNombre(), tipo.getId());
        }
    }

    private void cargaListaPiedrasC() {
        piedrasCentralesLista = new HashMap<String, Long>();
        for (PiedraCentral piedra : getPiedracentrales()) {
            piedrasCentralesLista.put(piedra.getNombre(), piedra.getId());
        }
    }

    private void cargaListaCircones() {
        circonesLista = new HashMap<String, Long>();
        for (Circon circon : CirconDao.consultarTodo(Circon.class)) {
            circonesLista.put(circon.getTamano(), circon.getId());
        }
    }

    //Envia Los datos De la consulta Al registro
    public void enviarselecionado(Modelo m) {
        estado = "A";
        Registrar = true;
        RequestContext.getCurrentInstance().reset("form:panel");
        modeloSelecionado = m;
        tiposModelolSelect = m.getTipo_modelo().getId();
        modelocirconSelect = m.getModelo_circon();
        imagen = util.getExiteimagen("imagenes/modelos", m.getImagen());
        modelogaleria.clear();
        for (ModeloImagen img : m.getModulo_imagenes()) {
            Map imagenmap = new HashMap<String, String>();
            imagenmap.put("nombre", img.getNombre());
            imagenmap.put("temp", false);
            modelogaleria.add(imagenmap);
        }
        editarcarpeta();
        modeloPiedracentralesSelect = m.getPiedra_centrales();
    }

    //pasa Del modulo De registro la modulo De consulta
    public void consultaModulo() {
        Registrar = false;
    }

    public void selecoionarcircon() {
        if (circoneSelect <= 0) {
            cantidad = true;
            focus = "panel";
        } else {
            cantidad = false;
            focus = "valorCantidad";
        }
    }

    public void selecoionarpiedra() {
        if (piedrasCentralSelect <= 0) {
            cantidadPiedra = true;
            focus = "panel";
        } else {
            cantidadPiedra = false;
            focus = "valorCantidadPiedra";
        }
    }

    public void eliminaCircon(ModeloCircon circon) {
//        RequestContext context = RequestContext.getCurrentInstance();
        modelocirconSelect.remove(circon);
        this.pesoCirones = getPesoCirones();
//        context.update("pesocircones");
    }

    public void agregarCircon() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (circoneSelect == 0L) {
            util.crearmensajes("ALERTA", "CUIDADO!!", "Selecione Primero una Piedra central");
        } else {
            circon = CirconDao.consultarC(Circon.class, circoneSelect);
            if (circon != null) {
                ModeloCircon modeloCircon = new ModeloCircon();
                modeloCircon.setCircon(circon);
                modeloCircon.setCantidad(valorCantidad);
                if (modelocirconSelect.size() <= 0) {
                    modelocirconSelect.add(modeloCircon);
                } else {
                    int i = 0;
                    int index = 0;
                    ModeloCircon Modcirconencontrado = new ModeloCircon();
                    boolean encontro = false;
                    for (ModeloCircon Mc : modelocirconSelect) {
                        if (Mc.getCircon().equals(circon)) {
                            encontro = true;
                            Modcirconencontrado = Mc;
                            index = i;
                        }
                        i++;
                    }
                    if (encontro) {
                        modeloCircon.setCantidad(modeloCircon.getCantidad() + Modcirconencontrado.getCantidad());
                        modelocirconSelect.set(index, modeloCircon);
                    } else {
                        modelocirconSelect.add(modeloCircon);
                    }
                }
                valorCantidad = 0;
                circoneSelect = 0l;
                cantidad = true;

                focus = "panel";
//                context.update(":form:circonselect :form:listaCirconselct :form:msg :form:growlMsg :form:valorCantidad :form:pesocircones");
            } else {
                util.crearmensajes("ALERTA", "CUIDADO 2!!", "Selecione Primero una Piedra central");
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        carpeta = "select00001";
        imgTemp = "MO" + controlSeccion.getMiembro().getDocumento();
        this.imagen = util.cargarimagenTemp(event.getFile(), imgTemp);
        this.imagenedit = true;
        editarcarpeta();
    }

    public void handleFileUploadLista(FileUploadEvent event) {
//        carpeta = "select00001";
        Map imagenmap = new HashMap<String, String>();
        String imgTemp = "";
        if (modelogaleria.isEmpty()) {
            imgTemp = "MO" + controlSeccion.getMiembro().getDocumento() + "-" + modelogaleria.size();
        } else {
            int mayor = 0;
            for (Map dato : modelogaleria) {
                String nombre = (String) dato.get("nombre");
                boolean temp = (Boolean) dato.get("temp");
                StringTokenizer format = new StringTokenizer(nombre, "-");
                String num1 = format.nextToken();
                String num2 = format.nextToken();
                int numero = Integer.parseInt(num2);
                if (numero >= mayor) {
                    mayor = numero + 1;
                }
            }
            imgTemp = "MO" + controlSeccion.getMiembro().getDocumento() + "-" + mayor + "";
        }
        util.cargarimagenTemp(event.getFile(), imgTemp);
        imagenmap.put("nombre", imgTemp);
        imagenmap.put("temp", true);
        modelogaleria.add(imagenmap);
//        this.imagenedit = true;
//        editarcarpeta();
    }

    public void eliminaPiedra(ModeloPiedraCentral piedra) {
        modeloPiedracentralesSelect.remove(piedra);
    }

    public void agregarPiedra() {
        if (piedrasCentralSelect == 0L) {
            util.crearmensajes("ALERTA", "CUIDADO!!", "Selecione Primero una Piedra central");
        } else {

            piedracentral = PiedraCentralDao.consultarC(PiedraCentral.class, piedrasCentralSelect);
            if (piedracentral != null) {
                ModeloPiedraCentral pcentreal = new ModeloPiedraCentral();
                pcentreal.setPiedra(piedracentral);
                pcentreal.setCantidad(valorCantidadPiedra);
                if (modeloPiedracentralesSelect.size() <= 0) {
                    modeloPiedracentralesSelect.add(pcentreal);
                } else {
                    int i = 0;
                    int index = 0;
                    ModeloPiedraCentral ModPiedraencontrado = new ModeloPiedraCentral();
                    boolean encontro = false;
                    for (ModeloPiedraCentral Mp : modeloPiedracentralesSelect) {
                        if (Mp.getPiedra().equals(piedracentral)) {
                            encontro = true;
                            ModPiedraencontrado = Mp;
                            index = i;
                        }
                        i++;
                    }
                    if (encontro) {
                        pcentreal.setCantidad(pcentreal.getCantidad() + ModPiedraencontrado.getCantidad());
                        modeloPiedracentralesSelect.set(index, pcentreal);
                    } else {
                        modeloPiedracentralesSelect.add(pcentreal);
                    }
                }
                piedrasCentralSelect = 0l;
                valorCantidadPiedra = 0;
                cantidadPiedra = true;

                focus = "panel";
            } else {
                util.crearmensajes("ALERTA", "CUIDADO 2!!", "Selecione Primero una Piedra central");
            }
        }
    }

    public void guardarModelo() {
        modeloSelecionado.setEstado(estadoModelo ? "ACTIVO" : "INACTIVO");

        modeloSelecionado.setTipo_modelo(TipoDao.consultar(Tipo.class, tiposModelolSelect));
        modeloSelecionado.setPeso_circones(pesoCirones);

        if (estado.equals("R")) {
            modeloSelecionado.setCodigo("M" + String.format("%03d", ModeloDao.Ultima()));
            modeloSelecionado.setImagen(imagen.equals("default2") ? imagen : modeloSelecionado.getCodigo());
            ModeloDao.crear(modeloSelecionado);

            if (modelocirconSelect.size() > 0 || modeloPiedracentralesSelect.size() > 0 || modelogaleria.size() > 0) {
                modeloSelecionado = ModeloDao.buscarModeloEstado(modeloSelecionado.getCodigo(), "ACTIVO");
                modificar(modeloSelecionado);
            }
            if (imagen != "default2") {
                guardarImagen(modeloSelecionado);
            }

            limpiar();
            util.crearmensajes("INFO", "EXITOSO", "Modelo Guadado Satifactoriamente");
        } else {
            modeloSelecionado.setImagen(imagen.equals("default2") ? imagen : modeloSelecionado.getCodigo());
            modificar(modeloSelecionado);

            if (imagen != "default2") {
                guardarImagen(modeloSelecionado);
            }
            limpiar();
            util.crearmensajes("INFO", "EXITOSO", "Modelo Modificado Satifactoriamente");

        }

    }

    private void guardarImagen(Modelo modelo) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            util.guardarImagen("modelos", estado, imagen, imagenedit, modelo.getCodigo());
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.toString()));
        }
    }

    private void guardarImagenGaleria(String img) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            util.guardarImagen("modelos", estado, imagen, imagenedit, img);
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.toString()));
        }
    }

    private void guardarImagenGaleria(String imagen, String img, boolean registrar) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            util.guardarImagen("modelos", estado, imagen, registrar, img);
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.toString()));
        }
    }

    private void modificar(Modelo modeloSelecionado) {
        List<ModeloImagen> listMi = new ArrayList<ModeloImagen>();
//        if (!modelogaleria.isEmpty()) {

        for (Map map : modelogaleria) {
            ModeloImagen mi = new ModeloImagen();
            String nombre = (String) map.get("nombre");
            boolean temp = (Boolean) map.get("temp");
            StringTokenizer toke = new StringTokenizer(nombre, "-");
            String nameTemp = toke.nextToken();
            String numTemp = toke.nextToken();
            mi.setModelo(modeloSelecionado);
            mi.setNombre(modeloSelecionado.getCodigo() + "-" + numTemp);
            if (temp) {
                guardarImagenGaleria(nombre, mi.getNombre(), temp);
            }
            listMi.add(mi);
        }
//        }

        for (ModeloCircon modeloCircon : modelocirconSelect) {
            ModeloCircon nuevomodeloCircon = new ModeloCircon(modeloSelecionado, modeloCircon.getCircon(), modeloCircon.getCantidad());
            modelocircones.add(nuevomodeloCircon);
        }
        for (ModeloPiedraCentral modeloPiedra : modeloPiedracentralesSelect) {
            ModeloPiedraCentral nuevomodeloPiedra = new ModeloPiedraCentral(modeloSelecionado, modeloPiedra.getPiedra(), modeloPiedra.getCantidad());
            modelopiedracentrales.add(nuevomodeloPiedra);
        }
        modeloSelecionado.setPiedra_centrales(modelopiedracentrales);
        modeloSelecionado.setModelo_circon(modelocircones);
        modeloSelecionado.setModulo_imagenes(listMi);
        ModeloDao.modificar(modeloSelecionado);
    }

    public boolean verBtnRegistro() {
        if (estado.equals("R")) {
            return true;
        } else {
            return false;
        }
    }

    //SET SECCION
    public void setControlSeccion(ControlSeccion controlSeccion) {
        this.controlSeccion = controlSeccion;
    }
////GET AND SET

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isRegistrar() {
        return Registrar;
    }

    public void setRegistrar(boolean Registrar) {
        this.Registrar = Registrar;
    }

    public boolean isEstadoModelo() {
        return estadoModelo;
    }

    public void setEstadoModelo(boolean estadoModelo) {
        this.estadoModelo = estadoModelo;
    }

    public Map<String, Long> getPiedrasCentralesLista() {
        return piedrasCentralesLista;
    }

    public void setPiedrasCentralesLista(Map<String, Long> piedrasCentralesLista) {
        this.piedrasCentralesLista = piedrasCentralesLista;
    }

    public Map<String, Long> getCirconesLista() {
        return circonesLista;
    }

    public void setCirconesLista(Map<String, Long> circonesLista) {
        this.circonesLista = circonesLista;
    }

    public Map<String, Long> getTiposModeloLista() {
        return tiposModeloLista;
    }

    public void setTiposModeloLista(Map<String, Long> tiposModeloLista) {
        this.tiposModeloLista = tiposModeloLista;
    }

    public Circon getCircon() {
        return circon;
    }

    public void setCircon(Circon circon) {
        this.circon = circon;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public PiedraCentral getPiedracentral() {
        return piedracentral;
    }

    public void setPiedracentral(PiedraCentral piedracentral) {
        this.piedracentral = piedracentral;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public List<Circon> getCircones() {
        circones = CirconDao.consultarTodo(Circon.class);
        return circones;
    }

    public void setCircones(List<Circon> circones) {
        this.circones = circones;
    }

    public List<Circon> getCirconesSelect() {
        return circonesSelect;
    }

    public void setCirconesSelect(List<Circon> circonesSelect) {
        this.circonesSelect = circonesSelect;
    }

    public List<Tipo> getTiposModelo() {
        tiposModelo = TipoDao.ListarDescripcion("Tipo_piezas");
        return tiposModelo;
    }

    public void setTiposModelo(List<Tipo> tiposModelo) {
        this.tiposModelo = tiposModelo;
    }

    public List<ModeloPiedraCentral> getModeloPiedracentralesSelect() {
        return modeloPiedracentralesSelect;
    }

    public void setModeloPiedracentralesSelect(List<ModeloPiedraCentral> modeloPiedracentralesSelect) {
        this.modeloPiedracentralesSelect = modeloPiedracentralesSelect;
    }

    public Modelo getModeloSelecionado() {
        return modeloSelecionado;
    }

    public void setModeloSelecionado(Modelo modeloSelecionado) {
        this.modeloSelecionado = modeloSelecionado;
    }

    public Long getPiedrasCentralSelect() {
        return piedrasCentralSelect;
    }

    public void setPiedrasCentralSelect(Long piedrasCentralSelect) {
        this.piedrasCentralSelect = piedrasCentralSelect;
    }

    public Long getCirconeSelect() {
        return circoneSelect;
    }

    public void setCirconeSelect(Long circoneSelect) {
        this.circoneSelect = circoneSelect;
    }

    public Long getTiposModelolSelect() {
        return tiposModelolSelect;
    }

    public void setTiposModelolSelect(Long tiposModelolSelect) {
        this.tiposModelolSelect = tiposModelolSelect;
    }

    public Long getTiposModelolSelectfilter() {
        return tiposModelolSelectfilter;
    }

    public void setTiposModelolSelectfilter(Long tiposModelolSelectfilter) {
        this.tiposModelolSelectfilter = tiposModelolSelectfilter;
    }

    public ModeloCircon getModelocircon() {
        return modelocircon;
    }

    public void setModelocircon(ModeloCircon modelocircon) {
        this.modelocircon = modelocircon;
    }

    public List<ModeloCircon> getModelocircones() {
        return modelocircones;
    }

    public void setModelocircones(List<ModeloCircon> modelocircones) {
        this.modelocircones = modelocircones;
    }

    public List<ModeloCircon> getModelocirconSelect() {
        return modelocirconSelect;
    }

    public void setModelocirconSelect(List<ModeloCircon> modelocirconSelect) {
        this.modelocirconSelect = modelocirconSelect;
    }

    public List<PiedraCentral> getPiedracentrales() {
        piedracentrales = PiedraCentralDao.consultarTodo(PiedraCentral.class);
        return piedracentrales;
    }

    public void setPiedracentrales(List<PiedraCentral> piedracentrales) {
        this.piedracentrales = piedracentrales;
    }

    public boolean isCantidad() {
        return cantidad;
    }

    public void setCantidad(boolean cantidad) {
        this.cantidad = cantidad;
    }

    public int getValorCantidad() {
        return valorCantidad;
    }

    public void setValorCantidad(int valorCantidad) {
        this.valorCantidad = valorCantidad;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public Double getPesoCirones() {
        double peso = 0;
        for (ModeloCircon Mcircon : modelocirconSelect) {
            Double sum = Mcircon.getCircon().getMuestra() * Mcircon.getCantidad();
            peso = sum + peso;
        }
        pesoCirones = peso;
        return pesoCirones;
    }

    public void setPesoCirones(Double pesoCirones) {
        this.pesoCirones = pesoCirones;
    }

    public boolean isCantidadPiedra() {
        return cantidadPiedra;
    }

    public void setCantidadPiedra(boolean cantidadPiedra) {
        this.cantidadPiedra = cantidadPiedra;
    }

    public int getValorCantidadPiedra() {
        return valorCantidadPiedra;
    }

    public void setValorCantidadPiedra(int valorCantidadPiedra) {
        this.valorCantidadPiedra = valorCantidadPiedra;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isImagenedit() {
        return imagenedit;
    }

    public void setImagenedit(boolean imagenedit) {
        this.imagenedit = imagenedit;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    private void Eliminartemp() {
        ruta.EliminarArchivosTemp(directorioTemp, imgTemp);
        ruta.EliminarArchivosTempLista(directorioTemp, modelogaleria);
    }

    public List<Map> getModelogaleria() {
        return modelogaleria;
    }

    public boolean isZircon() {
        return zircon;
    }

    public void setZircon(boolean zircon) {
        this.zircon = zircon;
    }

    public boolean isPiedra() {
        return piedra;
    }

    public void setPiedra(boolean piedra) {
        this.piedra = piedra;
    }

    public void setModelogaleria(List<Map> modelogaleria) {
        this.modelogaleria = modelogaleria;
    }

    public void editarcarpeta() {
        if (estado.equals("R") || carpeta.equals("select00001")) {
            carpeta = "temp/";
        } else {
            carpeta = "imagenes/modelos/";
        }
    }

    public String editarcarpeta2(boolean temp) {
        String estado = "";
        if (estado.equals("R") || carpeta.equals("select00001")) {
            carpeta = "temp/";
        } else {
            carpeta = "imagenes/modelos/";
        }
        return carpeta;
    }

    public String getValueFilter() {
        return valueFilter;
    }

    public void setValueFilter(String valueFilter) {
        this.valueFilter = valueFilter;
    }

    public String getEstadofilter() {
        return estadofilter;
    }

    public void setEstadofilter(String estadofilter) {
        this.estadofilter = estadofilter;
    }

}
