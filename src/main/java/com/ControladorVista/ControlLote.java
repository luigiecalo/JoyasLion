/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ControladorVista;

import Utilidades.Utilidades;
import com.Dao.MiembroDaoimplement;
import com.Dao.OrdenDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.UsuarioDaoimplement;
import com.Entidades.Lote;
import com.Entidades.LoteModeloOrden;
import com.Entidades.Miembro;
import com.Entidades.Orden;
import com.Entidades.OrdenModelo;
import com.Entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ControlLote {

    //UTILIDADES
    private String message = "Hello World nuevo!";
    private int cant = 0;
    private String estado = "R";
    private Utilidades util = new Utilidades();
    private boolean Registrar = false;
    private boolean responsable = false;
    private Date date = new Date();
    // ENTIDADES
    private Orden orden = new Orden();
    private Usuario clienteSelect = new Usuario();
    private OrdenModelo oredeModelo;
    private Lote lote = new Lote();
    private LoteModeloOrden loteModeloOrden = new LoteModeloOrden();
    private Miembro miembro = new Miembro();

    //LISTAS
    private List<Orden> ordenes = new ArrayList<Orden>();
    private List<OrdenModelo> ordenesMoldelos = new ArrayList<OrdenModelo>();
    private List<Lote> lotes = new ArrayList<Lote>();
    private List<LoteModeloOrden> lotesModelosOrdenes = new ArrayList<LoteModeloOrden>();
    private List<Object> Litas = new ArrayList<Object>();
    private List<Map> lotesModelosOrdenesmap = new ArrayList<Map>();
    private List<Usuario> usuariosRol = new ArrayList<Usuario>();
    private List<Miembro> miembros = new ArrayList<Miembro>();

    //DAO
    private OrdenDaoimplement ODAO = new OrdenDaoimplement();
    private RolDaoimplement RolDAO = new RolDaoimplement();
    private UsuarioDaoimplement UsuarioDAO = new UsuarioDaoimplement();
    private MiembroDaoimplement MiembroDAO = new MiembroDaoimplement();
    //Session
    @ManagedProperty(value = "#{controlSeccion}")
    private ControlSeccion controlSeccion;

    public ControlLote() {

    }
/// METODOS
    //Cisualiza registro y consultas

    public void registroModulo() {
        Registrar = true;
        limpiar();
    }

     public boolean verBtnRegistro() {
        if (estado.equals("R")) {
            return true;
        } else {
            return false;
        }
    }
    public List<Miembro> completeUsuario(String query) {
        List<Miembro> allThemes = MiembroDAO.BuscarMiembroRol(3l);
        List<Miembro> filteredThemes = MiembroDAO.BuscarMiembroRolLike(3l, query);
        return filteredThemes;
    }

    public void selecionarResponsable() {
        if (miembro != null) {
            this.responsable = true;
        } else {
            this.responsable = false;
        }

    }

    public void consultaModulo() {
        Registrar = false;
    }

    //Se Genweara Lel Registro De La orden En base De Datos
    public void limpiar() {
        clienteSelect = new Usuario();
        orden = new Orden();
        lote = new Lote();
        lotesModelosOrdenes.clear();
        lotesModelosOrdenesmap.clear();
        ordenesMoldelos.clear();
        ordenes.clear();

        buscarOredenesEsra();
    }

    public void buscarOredenesEsra() {
        if (lotesModelosOrdenes.isEmpty()) {
            this.ordenes = ODAO.buscarOrdenEstado("EN ESPERA");
        } else {
            this.ordenes = ordenes;
        }

    }

    public void agregarOreden(Orden orden) {
        List<OrdenModelo> modelosordenes = orden.getOrdenesModelo();
        List<OrdenModelo> listaremover = new ArrayList<OrdenModelo>();
        for (OrdenModelo modelosorden : modelosordenes) {
            LoteModeloOrden lmo = new LoteModeloOrden();
            lmo.setCantidad(modelosorden.getCantidad());
            lmo.setMaterial(modelosorden.getMaterial());
            lmo.setOrden(modelosorden.getOrden());
            lmo.setModelo(modelosorden.getModelo());
            this.lotesModelosOrdenes.add(lmo);
            listaremover.add(modelosorden);
            agregarlistaMap(lmo);
        }
        for (OrdenModelo ordenModelo : listaremover) {
            orden.getOrdenesModelo().remove(ordenModelo);
        }
//        this.ordenes.remove(orden);

    }

    public void agregarOredenModelo(OrdenModelo ordenmodu) {
        LoteModeloOrden lmo = new LoteModeloOrden();
        lmo.setCantidad(ordenmodu.getCantidad());
        lmo.setMaterial(ordenmodu.getMaterial());
        lmo.setOrden(ordenmodu.getOrden());
        lmo.setModelo(ordenmodu.getModelo());
        this.lotesModelosOrdenes.add(lmo);
        ordenes.forEach((orden) -> {
            orden.getOrdenesModelo().remove(ordenmodu);
        });
        agregarlistaMap(lmo);

    }

    public void cancelarTodo() {
        for (Map mapa : lotesModelosOrdenesmap) {
            cancelarOredenModelo(mapa);
        }
    }

    public void cancelarOredenModelo(Map mapa) {
        List<LoteModeloOrden> listaremover = new ArrayList<LoteModeloOrden>();
        for (LoteModeloOrden loteModeloOrden : getLotesModelosOrdenes()) {
            String modelo = (String) mapa.get("modelo");
            String material = (String) mapa.get("material");
            String modelo2 = loteModeloOrden.getModelo().getCodigo();
            String material2 = loteModeloOrden.getMaterial().getNombre();
            if (modelo2.equals(modelo) && material2.equals(material)) {
                ordenes.forEach((orden) -> {
                    OrdenModelo ordenModelo = new OrdenModelo();
                    ordenModelo.setCantidad(loteModeloOrden.getCantidad());
                    ordenModelo.setMaterial(loteModeloOrden.getMaterial());
                    ordenModelo.setModelo(loteModeloOrden.getModelo());
                    ordenModelo.setOrden(loteModeloOrden.getOrden());
                    ordenModelo.setOrdenModeloPK(loteModeloOrden.getModelo().getId(), loteModeloOrden.getOrden().getId(), loteModeloOrden.getMaterial().getId());
                    if (orden.getCodigo().equals((String) loteModeloOrden.getOrden().getCodigo())) {
                        orden.getOrdenesModelo().add(ordenModelo);
                    }
                });
                listaremover.add(loteModeloOrden);
            }
        }

        for (LoteModeloOrden loteModeloOrden1 : listaremover) {
            lotesModelosOrdenes.remove(loteModeloOrden1);
        }

        eliminarListaMap(mapa);

    }

    public void eliminarListaMap(Map mapa) {
        this.lotesModelosOrdenesmap.remove(mapa);
    }

    public void agregarlistaMap(LoteModeloOrden loteModelOrden) {
//        for (LoteModeloOrden loteModelOrden : lotesModelosOrdenes) {
        Map map = new HashMap();
        map.put("modelo", loteModelOrden.getModelo().getCodigo());
        map.put("material", loteModelOrden.getMaterial().getNombre());
        if (lotesModelosOrdenesmap.isEmpty()) {
            map.put("cantidad", loteModelOrden.getCantidad());
            this.lotesModelosOrdenesmap.add(map);
        } else {
            boolean encontro = false;
            Map encontroMap = new HashMap();
            for (Map mapa : getLotesModelosOrdenesMap()) {
                String modelo = (String) mapa.get("modelo");
                String material = (String) mapa.get("material");
                String modelo2 = loteModelOrden.getModelo().getCodigo();
                String material2 = loteModelOrden.getMaterial().getNombre();
                if (modelo2.equals(modelo) && material2.equals(material)) {
                    encontro = true;
                    encontroMap = mapa;
                }
            }
            if (encontro) {
                this.lotesModelosOrdenesmap.remove(encontroMap);
                map.put("cantidad", loteModelOrden.getCantidad() + ((int) encontroMap.get("cantidad")));
                this.lotesModelosOrdenesmap.add(map);
            } else {
                map.put("cantidad", loteModelOrden.getCantidad());
                this.lotesModelosOrdenesmap.add(map);
            }

        }

    }

///GET Y SET
    public Orden getOrden() {
        return orden;
    }

    public Double getTotalOrden() {
        Double total = 0.0;
        for (OrdenModelo ordenesMoldelo : ordenesMoldelos) {
            total = total + ordenesMoldelo.getTotal();
        }
        return total;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public OrdenModelo getOredeModelo() {
        return oredeModelo;
    }

    public void setOredeModelo(OrdenModelo oredeModelo) {
        this.oredeModelo = oredeModelo;
    }

    public List<OrdenModelo> getOrdenesMoldelos() {
        return ordenesMoldelos;
    }

    public void setOrdenesMoldelos(List<OrdenModelo> ordenesMoldelos) {
        this.ordenesMoldelos = ordenesMoldelos;
    }

    public List<LoteModeloOrden> getLotesModelosOrdenes() {
        return lotesModelosOrdenes;
    }

    public List<Map> getLotesModelosOrdenesMap() {
        return lotesModelosOrdenesmap;
    }

    public Integer getPiezasTotales() {
        Integer pieszas = 0;
        for (Map map : lotesModelosOrdenesmap) {
            pieszas = pieszas + (Integer) map.get("cantidad");
        }
        return pieszas;
    }

    public void setLotesModelosOrdenes(List<LoteModeloOrden> lotesModelosOrdenes) {
        this.lotesModelosOrdenes = lotesModelosOrdenes;
    }

    public List<Orden> getOrdenes() {

        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    //SET SECCION
    public void setControlSeccion(ControlSeccion controlSeccion) {
        this.controlSeccion = controlSeccion;
    }

    public Usuario getClienteSelect() {
        return clienteSelect;
    }

    public void setClienteSelect(Usuario clienteSelect) {
        this.clienteSelect = clienteSelect;
    }

    public boolean isRegistrar() {
        return Registrar;
    }

    public void setRegistrar(boolean Registrar) {
        this.Registrar = Registrar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public List<Usuario> getUsuariosRol() {
        return usuariosRol;
    }

    public void setUsuariosRol(List<Usuario> usuariosRol) {
        this.usuariosRol = usuariosRol;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public boolean isResponsable() {
        return responsable;
    }

    public void setResponsable(boolean responsable) {
        this.responsable = responsable;
    }

}
