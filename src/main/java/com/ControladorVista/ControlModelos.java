/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.CirconDaoimplement;
import com.Dao.ModeloDaoimplement;
import com.Dao.PiedraCentralDaoimplement;
import com.Dao.TipoDaoimplement;
import com.Entidades.Circon;
import com.Entidades.Modelo;
import com.Entidades.ModeloCircon;
import com.Entidades.Modulo;
import com.Entidades.PiedraCentral;
import com.Entidades.Tipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

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
    private boolean Registrar = false;
    private boolean estadoModelo = true;
    private boolean cantidad = true;
    private String focus = "panel";
    private Double pesoCirones = 0.0;

    private Map<String, Long> piedrasCentralesLista;
    private Map<String, Long> circonesLista;
    private Map<String, Long> tiposModeloLista;
    private Long piedrasCentralSelect = 0l;
    private Long circoneSelect = 0l;
    private Long tiposModelolSelect = 0l;
    private int valorCantidad = 0;

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
    private List<PiedraCentral> piedracentralesSelect = new ArrayList<PiedraCentral>();
    //DAOS

    private ModeloDaoimplement ModeloDao = new ModeloDaoimplement();
    private CirconDaoimplement CirconDao = new CirconDaoimplement();
    private TipoDaoimplement TipoDao = new TipoDaoimplement();
    private PiedraCentralDaoimplement PiedraCentralDao = new PiedraCentralDaoimplement();
//    RequestContext Requescontext = RequestContext.getCurrentInstance();

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
        piedracentralesSelect.clear();
        modelocirconSelect.clear();
        listarModelos();
    }

    //Lista todos Los Modelos de La vista De Consulta
    public void listarModelos() {
        modelos = ModeloDao.consultarTodo(Modelo.class);
    }

    private void cargarListas() {
        cargalistaTipoModelos();
        cargaListaCircones();
        cargaListaPiedrasC();
        selecoionarcircon();
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
//        piedracentralesSelect = m.getPiedra_centrales();
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

    public void eliminarcircon(ModeloCircon mcircon) {
        modelocirconSelect.remove(mcircon);
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

    public void guardarModelo() {
        modeloSelecionado.setEstado(estadoModelo ? "ACTIVO" : "INACTIVO");
        modeloSelecionado.setImagen("dsd/imag123.png");
        modeloSelecionado.setTipo_modelo(TipoDao.consultar(Tipo.class, tiposModelolSelect));
        modeloSelecionado.setPeso_circones(pesoCirones);
//        modeloSelecionado.setPiedra_centrales(piedracentralesSelect);
        if (estado.equals("R")) {
            modeloSelecionado.setCodigo("M" + String.format("%03d", ModeloDao.Ultima()));
            ModeloDao.crear(modeloSelecionado);

            if (modelocirconSelect.size() > 0) {
                modeloSelecionado = ModeloDao.buscarModeloEstado(modeloSelecionado.getCodigo(), "ACTIVO");
                modificar(modeloSelecionado);
            }
            limpiar();
            util.crearmensajes("INFO", "EXITOSO", "Modelo Guadado Satifactoriamente");
        } else {
            modificar(modeloSelecionado);
            limpiar();
            util.crearmensajes("INFO", "EXITOSO", "Modelo Modificado Satifactoriamente");

        }

    }

    public void eliminaPiedra(PiedraCentral piedra) {
        piedracentralesSelect.remove(piedra);
    }

    public void agregarPiedra() {
        if (piedrasCentralSelect == 0L) {
            util.crearmensajes("ALERTA", "CUIDADO!!", "Selecione Primero una Piedra central");
        } else {

            piedracentral = PiedraCentralDao.consultarC(PiedraCentral.class, piedrasCentralSelect);
            if (piedracentral != null) {
                piedracentralesSelect.add(piedracentral);
                piedrasCentralSelect = 0L;
            } else {
                util.crearmensajes("ALERTA", "CUIDADO 2!!", "Selecione Primero una Piedra central");
            }
        }
    }

    public void eliminaCircon(Circon circon) {
        circonesSelect.remove(circon);
    }

    public boolean verBtnRegistro() {
        if (estado.equals("R")) {
            return true;
        } else {
            return false;
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
        pesoCirones = peso* 0.2;
        return pesoCirones;
    }

    public void setPesoCirones(Double pesoCirones) {
        this.pesoCirones = pesoCirones;
    }

    private void modificar(Modelo modeloSelecionado) {
        for (ModeloCircon modeloCircon : modelocirconSelect) {
            ModeloCircon nuevomodeloCircon = new ModeloCircon(modeloSelecionado, modeloCircon.getCircon(), modeloCircon.getCantidad());
            modelocircones.add(nuevomodeloCircon);
        }
        modeloSelecionado.setModelo_circon(modelocircones);
        ModeloDao.modificar(modeloSelecionado);
        modelocircones.clear();
    }

}
