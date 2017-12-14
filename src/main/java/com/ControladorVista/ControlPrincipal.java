/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.CirconDaoimplement;
import com.Dao.MaterialDaoimplement;
import com.Dao.ModeloDaoimplement;
import com.Dao.PiedraCentralDaoimplement;
import com.Dao.TipoDaoimplement;
import com.Entidades.Circon;
import com.Entidades.Material;
import com.Entidades.Modelo;
import com.Entidades.ModeloCircon;
import com.Entidades.Modulo;
import com.Entidades.PiedraCentral;
import com.Entidades.Tipo;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named(value = "controlPrincipal")
@ManagedBean
@ViewScoped
public final class ControlPrincipal implements Serializable {

    //UTILIDADES
    private Long idmod;
    private Utilidades util = new Utilidades();
    private String estado = "R";
    private boolean Registrar = false;
    private boolean estadoModelo = true;
    private String focus = "panel";
    private int cantidad = 1;
    private Long materialSelect = 0l;
    private Material material = new Material();
    private double valor = 0.0;
    private String valortexto = "$0";
    private List<String> images;
    private List<String> imagesid;
    @ManagedProperty(value = "#{controlOrden}")
    private ControlOrden controlOrden;
    //OBJECTOS
    private Modelo modeloSelecionado = new Modelo();
    private Circon circon = new Circon();
    private Tipo tipo = new Tipo();
    private PiedraCentral piedracentral = new PiedraCentral();
    private ModeloCircon modelocircon = new ModeloCircon();
    //LISTAS
    private List<Modelo> modelos = new ArrayList<Modelo>();
    private List<Circon> circones = new ArrayList<Circon>();
    private List<Material> materiales = new ArrayList<Material>();
    private List<Circon> circonesSelect = new ArrayList<Circon>();
    private List<ModeloCircon> modelocircones = new ArrayList<ModeloCircon>();
    private List<ModeloCircon> modelocirconSelect = new ArrayList<ModeloCircon>();
    private List<Tipo> tiposModelo = new ArrayList<Tipo>();
    private List<PiedraCentral> piedracentrales = new ArrayList<PiedraCentral>();
    private List<PiedraCentral> piedracentralesSelect = new ArrayList<PiedraCentral>();
    private Map<String, Long> MaterialLista;
    //DAOS
    private ModeloDaoimplement ModeloDao = new ModeloDaoimplement();
    private MaterialDaoimplement MaterialDao = new MaterialDaoimplement();
    private CirconDaoimplement CirconDao = new CirconDaoimplement();
    private TipoDaoimplement TipoDao = new TipoDaoimplement();
    private PiedraCentralDaoimplement PiedraCentralDao = new PiedraCentralDaoimplement();

    private String message;

    public ControlPrincipal() {
        listarModelos();
        listarMateriales();
        cargarimagenes();
    }
    ////METODOS
    //validar los permisos De Los botones

    public boolean butompermisos(Long idrol, Modulo mod, String permiso) {
        return util.permisos(idrol, mod, permiso);
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
        piedracentralesSelect.clear();
        modelocirconSelect.clear();
        listarModelos();
    }

    //Lista todos Los Modelos de La vista De Consulta
    public void listarModelos() {
        modelos = ModeloDao.consultarTodo(Modelo.class);
    }

    public void listarMateriales() {
        materiales = MaterialDao.consultarTodo(Material.class);
    }

    //pasa Del modulo De registro la modulo De consulta
    public void consultaModulo() {
        Registrar = false;
    }
    // seleciona el modelo a agregar a La orden

    public void selecoionarmodelo(Modelo modelo) {
        RequestContext context = RequestContext.getCurrentInstance();
        modeloSelecionado = modelo;
        context.getCurrentInstance().execute("$(#amodal).modal(');");
    }

//    suma La Cantidad DE modelos 
    public void sumar() {
        cantidad = cantidad + 1;
        calcular();
    }
//resta la Cantidad  De  modelos 

    public void restar() {
        if (cantidad > 1) {
            cantidad = cantidad - 1;
        }
        calcular();

    }
//    añade al carro De compras

    public void anadirOrden() {
        if (validarorden()) {
            calcular();
            controlOrden.agregarordenmodelo(modeloSelecionado, cantidad, material, valor);
//            controlOrdenes.setCantidad(203);
//            ControlOrdenes bean1 = context.getApplication().evaluateExpressionGet(context, "#{controlOrdenes}", ControlOrdenes.class);
//            bean.setCantidad(bean.getCantidad() + 1);
            util.crearmensajes("INFO", "MENSAGE", "REgistro Exitoso");
            util.modal("mdModelo", "hide");
            limpiarOrden();
        }
    }

    public void calcular() {
        Double peso_modelo = 0.0;
        if (validarorden()) {
            Material materi = MaterialDao.consultarC(Material.class, materialSelect);
            double pesopatron = modeloSelecionado.getPeso_modelo() * materi.getPatron();
            double pesoZircones = pesopatron + modeloSelecionado.getPeso_circones();
            double subvalor = pesoZircones * materi.getValor();
            valor = subvalor * cantidad;
        }

    }

    public boolean validarorden() {
        boolean valido = false;
        if (cantidad == 0) {
            util.crearmensajes("ALERTA", "INGRESE CANTIDAD", "ALERTA");
        } else if (materialSelect == 0l) {
            util.crearmensajes("ALERTA", "INGRESE MATERIAL", "Selecione Primero Un Material");
        } else {
            valido = true;
        }
        return valido;
    }

    public void cargarimagenes() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 8; i++) {
            images.add(i + ".jpg");
        }
        imagesid = new ArrayList<String>();
        for (int i = 1; i <= 8; i++) {
            imagesid.add(i + "");
        }
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

    public List<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<Material> materialesSelect) {
        this.materiales = materialesSelect;
    }

    public Long getMaterialSelect() {
        return materialSelect;
    }

    public void setMaterialSelect(Long materialSelect) {
        this.materialSelect = materialSelect;
    }

    public List<Tipo> getTiposModelo() {
        tiposModelo = TipoDao.ListarDescripcion("Tipo_piezas");
        return tiposModelo;
    }

    public void setTiposModelo(List<Tipo> tiposModelo) {
        this.tiposModelo = tiposModelo;
    }

    public List<PiedraCentral> getPiedracentralesSelect() {
        return piedracentralesSelect;
    }

    public void setPiedracentralesSelect(List<PiedraCentral> piedracentralesSelect) {
        this.piedracentralesSelect = piedracentralesSelect;
    }

    public Modelo getModeloSelecionado() {
        return modeloSelecionado;
    }

    public void setModeloSelecionado(Modelo modeloSelecionado) {
        this.modeloSelecionado = modeloSelecionado;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public Map<String, Long> getMaterialLista() {
        MaterialLista = new HashMap<String, Long>();
        for (Material material : getMateriales()) {
            MaterialLista.put(material.getNombre(), material.getId());
        }
        return MaterialLista;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getImagesid() {

        return imagesid;
    }

    public void cambiartext() {
        controlOrden.setMessage("este Es un nuevo texto");
        controlOrden.setCant(controlOrden.getCant() + 1);
    }

    public String getMessage() {
        if (controlOrden != null) {
            message = controlOrden.getMessage();
        }
        return message;
    }

    public void setControlOrden(ControlOrden message) {
        this.controlOrden = message;
    }

    public void limpiarOrden() {
        cantidad = 1;
        material = null;
        materialSelect=0l;
    }

    public String getValortexto() {
        DecimalFormat formateador = new DecimalFormat("###,###.##");
//Este daria a la salida 1,000
        valortexto = formateador.format(valor);
        return valortexto;
    }

    public void setValortexto(String valortexto) {
        this.valortexto = valortexto;
    }

}
