/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba;

import Utilidades.Utilidades;
import com.Dao.CirconDaoimplement;
import com.Dao.GrupoDaoimplement;
import com.Dao.MiembroDaoimplement;
import com.Dao.ModeloDaoimplement;
import com.Dao.ModeloImagenDaoimplement;
import com.Dao.ModuloDaoimplement;
import com.Dao.PermisosDaoimplement;
import com.Dao.PiedraCentralDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.RolModuloPermisoDaoimplement;
import com.Dao.TipoDaoimplement;
import com.Dao.UsuarioDaoimplement;
import com.Entidades.Circon;
import com.Entidades.Grupo;
import com.Entidades.Miembro;
import com.Entidades.Modelo;
import com.Entidades.ModeloCircon;
import com.Entidades.ModeloImagen;
import com.Entidades.ModeloPiedraCentral;
import com.Entidades.Modulo;
import com.Entidades.Permisos;
import com.Entidades.PiedraCentral;
import com.Entidades.Rol;
import com.Entidades.RolModuloPermiso;
import com.Entidades.RolModuloPermisoPK;
import com.Entidades.Usuario;
import com.Entidades.Tipo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class prueba implements Serializable {

    public static void main(String[] args) {
        System.out.println("Hola");
        // ----<Controladore JPADAO Controller>-----
        PermisosDaoimplement PDao = new PermisosDaoimplement();
        MiembroDaoimplement MDao = new MiembroDaoimplement();
        RolDaoimplement RDao = new RolDaoimplement();
        UsuarioDaoimplement UDao = new UsuarioDaoimplement();
        ModuloDaoimplement MoDao = new ModuloDaoimplement();
        RolModuloPermisoDaoimplement RMPDao = new RolModuloPermisoDaoimplement();
        GrupoDaoimplement GRPDao = new GrupoDaoimplement();
        TipoDaoimplement TDao = new TipoDaoimplement();
        CirconDaoimplement CirDao = new CirconDaoimplement();
        ModeloDaoimplement MdDao = new ModeloDaoimplement();
        PiedraCentralDaoimplement PCDao = new PiedraCentralDaoimplement();
        ModeloImagenDaoimplement mdimpl= new ModeloImagenDaoimplement();

        //OBJETOS
//        Permisos permiso = new Permisos();
//        Miembro miembro = new Miembro();
//        Modulo modulo = new Modulo();
//        RolModuloPermiso rmp = new RolModuloPermiso();
//        Rol rol = new Rol();
//        Usuario usuario = new Usuario();
//        Grupo grupo = new Grupo();
        Tipo tipo = new Tipo();
        Circon circon = new Circon();
        Modelo modelo = new Modelo();
        PiedraCentral PC = new PiedraCentral();
        Utilidades uti = new Utilidades();
        //Lita de OBJETOS
//        List<Permisos> permisoLista = new ArrayList<Permisos>();
//        List<Miembro> miembroLista = new ArrayList<Miembro>();
//        List<Rol> rolesLista = new ArrayList<Rol>();
        List<Usuario> usuarioLista = new ArrayList<Usuario>();
//        List<Grupo> grupos = new ArrayList<Grupo>();
        List<Circon> circones = new ArrayList<Circon>();
        List<Modelo> modelos = new ArrayList<Modelo>();
        List<PiedraCentral> piedras = new ArrayList<PiedraCentral>();
        usuarioLista= UDao.Listar();
        for (Usuario usu : usuarioLista) {
            System.out.println(usu.toString());
            for (Rol rol : usu.getRoles()) {
                System.out.println("ROLES");
                System.out.println(rol.getNombre());
            }
            
        }
//        List<RolModuloPermiso> rolmodulospermisos = new ArrayList<RolModuloPermiso>();
//        Long id;
//        usuario = UDao.login("ADMIN", "ADMIN");
//        System.out.println("USUARIO:" + usuario.getLogin());
//        miembro = MDao.BuscarMiembroUsuario(usuario);
//        System.out.println("Mienbro:" + miembro.getNombre1());
//        List<Modulo> modulos = RMPDao.buscarModulos(usuario.getRoles().get(0).getIdrol());
//        for (Modulo modulo1 : modulos) {
//            System.out.println("Los modulos son:" + modulo1.getNombre() + " Con direcion: " + modulo1.getSrc());
//        }
//        prueba con = new prueba();
//        con.ListarModulos();
//        tipo.setCodigo("01");
//        tipo.setDescripcion("Tipo_piezas");
//        tipo.setNombre("ANILLOS");
//        TDao.crear(tipo);

//        circon.setCodigo("003");
//        circon.setMuestra(0.030);
//        circon.setReferencia("Referencia");
//        CirDao.crear(circon);
//        System.out.println("PIEDRAS CENTRALES*****");
//        PC.setCodigo("PC003");
//        PC.setNombre("ESMERALDA GRANDE");
//        tipo = TDao.consultarC(Tipo.class, 2L);
//        PC.setForma(tipo);
//        tipo = TDao.consultarC(Tipo.class, 4L);
//        PC.setTipo(tipo);
//        PC.setPeso(5000.19);
//        List<ModeloPiedraCentral> modelopiedras = new ArrayList<ModeloPiedraCentral>();
//
////        PCDao.crear(PC);
//        piedras = PCDao.consultarTodo(PiedraCentral.class);
//        circones = CirDao.consultarTodo(Circon.class);
//
//        int ultimo = MdDao.Ultima();
//        modelo.setCodigo("M006");
//        modelo.setEstado("ACTIVO");
//
//        modelo.setImagen("dsd/imag123.png");
//        modelo.setPeso_circones(23400.0);
//        modelo.setPeso_modelo(332.8);
//        tipo = TDao.consultarC(Tipo.class, 1L);
//        modelo.setTipo_modelo(tipo);
//        MdDao.crear(modelo);
//        int cant = 14;
//        modelo = MdDao.buscarModeloEstado(modelo.getCodigo(), "ACTIVO");
//        List<ModeloCircon> modelocircones = new ArrayList<ModeloCircon>();
//        System.out.println("______CIRCONESSS__________");
//        for (Circon circone : circones) {
//            System.out.println(circone.getCodigo());
//            ModeloCircon modeloCircon = new ModeloCircon(modelo, circone, cant);
//            modeloCircon.setCantidad(cant);
//            modelocircones.add(modeloCircon);
//            cant++;
//        }
//        System.out.println("______PIEDRAS__________");
//        for (PiedraCentral piedrasc : piedras) {
//            System.out.println(piedrasc.getCodigo());
//            ModeloPiedraCentral mp = new ModeloPiedraCentral();
//
//            mp.setPiedra(piedrasc);
//            mp.setModelo(modelo);
//            if (mp.getId() == null) {
//                modelopiedras.add(mp);
//            }
//        }
//
//        modelo.setPiedra_centrales(modelopiedras);
//        modelo.setModelo_circon(modelocircones);
//        MdDao.modificar(modelo);
        modelos = MdDao.consultarTodo(Modelo.class);

//        System.out.println("______MODELOS__("+uti.formatoDecimal(3.99865424, "0.000")+"________");
//        for (Modelo mod : modelos) {
//            System.out.println(mod.getCodigo() + "-" + mod.getTipo_modelo().getNombre());
//            System.out.println("*CIRCONES(" + mod.getModelo_circon().size() + ")_________");
//            for (ModeloCircon mc1 : mod.getModelo_circon()) {
//                System.out.println(mc1.getCircon().getCodigo() + "--" + mc1.getCircon().getTamano());
//            }
//            System.out.println("*PIEDRAS(" + mod.getPiedra_centrales().size() + ")__________");
//            for (ModeloPiedraCentral mp1 : mod.getPiedra_centrales()) {
//                System.out.println(mp1.getPiedra().getCodigo() + "--" + mp1.getPiedra().getNombre());
//            }
//        }
//        String number = ultimo + "";
//        if (number.length() == 1) {
//            number.format("00{0}", 2);
//        }
      
//        System.out.println("REGISTRO EXITOSO " + String.format("%03d", ultimo));
//        ultimo = 190;
//        System.out.println("REGISTRO EXITOSO " + String.format("%03d", ultimo));

    }

    public void ListarModulos() {
        List<Modulo> modulos = new ArrayList<Modulo>();
        ModuloDaoimplement MoDao = new ModuloDaoimplement();
        prueba con = new prueba();
        int index = 0;
        modulos = MoDao.consultarTodo(Modulo.class);
        List<Map> menu = new LinkedList<Map>();
        for (Modulo m : modulos) {
            Modulo modulo = MoDao.consultar(Modulo.class, m.getIdmodulo());
            Map item = new HashMap<String, String>();
            item.put("src", modulo.getSrc());
            if (modulo.getGrupomodulo() == null) {
                con.addItem(item, modulo, menu);

                menu.add(item);
            } else {
                item.put("nombre", modulo.getGrupomodulo().getNombre());
                item.put("icono", modulo.getGrupomodulo().getIcono());
                if (menu.isEmpty()) {
                    con.addItemGrup(item, modulo);
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
                        con.addItemGrup(item, modulo);
                        menu.add(item);
                    } else {
                        con.addItemGrup(item, modulo);
//                        if (item.get("submodulos") == null) {
                        System.out.println("Item Encontrado" + itemencontrado);
                        System.out.println("Item -         " + item);
                        List<Modulo> modulosEncontraos = (List<Modulo>) itemencontrado.get("modulos");
                        List<Modulo> modulositem = (List<Modulo>) item.get("modulos");
                        boolean relacion = false;
                        for (Modulo moduloencontrado : modulosEncontraos) {
                            System.out.println("---" + moduloencontrado.getSubgrupos());
                            if (moduloencontrado.getSubgrupos() != null) {
                                for (Modulo moduloitem : modulositem) {
                                    System.out.println(moduloitem.getSubgrupos());
                                    if (moduloitem.getSubgrupos() != null) {
                                        if (moduloitem.getSubgrupos().equals(moduloencontrado.getSubgrupos())) {
                                            Modulo moduloRelacion = moduloencontrado;
                                            System.err.println("Ese encontro a:" + moduloRelacion);
                                            moduloRelacion.getSubgrupos().getModulos().add(modulo);
                                            System.err.println("se modificco A:" + moduloRelacion);
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
                        System.out.println("COMBINADO :" + itemencontrado);
                    }
                }
            }
        }

        System.out.println("---MENU PRINCIPAL--");
//        for (Map menu1 : menu) {
//            System.out.println("*" + menu1.get("nombre") + "*");
//            if (menu1.get("modulos") != null) {
//                for (Modulo modulo : (List<Modulo>) menu1.get("modulos")) {
//                    System.out.println("-" + modulo.getNombre() + "-");
//                    if(modulo.getSubgrupos()!=null){
//                    System.out.println(modulo.getSubgrupos().getNombre());
//                    System.out.println(modulo.getNombre());
//                    
//                    }
//                }
//            }
//        }
        for (Map menu1 : menu) {
            System.out.println("*" + menu1.get("nombre") + "*");
            if (menu1.get("modulos") != null) {
                for (Modulo modulo : (List<Modulo>) menu1.get("modulos")) {
                    if (modulo.getSubgrupos() == null) {
                        System.out.println("-" + modulo.getNombre() + "-");
                    } else {
                        System.out.println("-" + modulo.getSubgrupos().getNombre());
                        for (Modulo modulosub : modulo.getSubgrupos().getModulos()) {
                            System.out.println(" -" + modulosub.getNombre());
                        }
                    }
////
                }
            }

        }

    }

    public void provarrmp() {
        PermisosDaoimplement PDao = new PermisosDaoimplement();
        Permisos permiso = new Permisos();
        List<Permisos> permisoLista = new ArrayList<Permisos>();
        RolModuloPermisoDaoimplement RMPDao = new RolModuloPermisoDaoimplement();
        ModuloDaoimplement MoDao = new ModuloDaoimplement();
        System.out.println("---ESTABLECIENDO RELACION ROL MODULO PERMISOS----");
        Long idmdulo = Long.parseLong("1");
        Long idrol = Long.parseLong("3");
//        permisoLista = PDao.consultarTodo(Permisos.class);
        permisoLista.clear();
        Modulo modulo = MoDao.consultar(Modulo.class, idmdulo);
        Rol rol = MoDao.consultar(Rol.class, idrol);
        RMPDao.registrarRolModuloPermisos(rol, modulo, permisoLista);
        System.out.println("RELACION ROL MODULO PERMISOS EXITOSA");
    }

    public void addItem(Map item, Modulo modulo, List<Map> menu) {

        if (modulo.getSubgrupos() == null) {

//            System.out.println("*" + modulo.getNombre() + "*");
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
//        System.out.println("-" + modulo.getSubgrupos().getNombre() + "-");
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
}
