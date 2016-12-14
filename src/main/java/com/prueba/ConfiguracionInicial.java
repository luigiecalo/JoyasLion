/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba;

import com.Dao.GrupoDaoimplement;
import com.Dao.MiembroDaoimplement;
import com.Dao.ModuloDaoimplement;
import com.Dao.PermisosDaoimplement;
import com.Dao.RolDaoimplement;
import com.Dao.RolModuloPermisoDaoimplement;
import com.Dao.UsuarioDaoimplement;
import com.Entidades.Grupo;
import com.Entidades.Miembro;
import com.Entidades.Modulo;
import com.Entidades.Permisos;
import com.Entidades.Rol;
import com.Entidades.RolModuloPermiso;
import com.Entidades.RolModuloPermisoPK;
import com.Entidades.SubGrupo;
import com.Entidades.Usuario;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
//import java.util.function.UnaryOperator;

/**
 *
 * @author usuario
 */
public class ConfiguracionInicial implements Serializable {

    public static void main(String[] args) {
        System.out.println("Configurando parametros inicials");
        ConfiguracionInicial con = new ConfiguracionInicial();
        // ----<Controladore JPADAO Controller>-----
        PermisosDaoimplement PDao = new PermisosDaoimplement();
        MiembroDaoimplement MDao = new MiembroDaoimplement();
        RolDaoimplement RDao = new RolDaoimplement();
        UsuarioDaoimplement UDao = new UsuarioDaoimplement();
        ModuloDaoimplement MoDao = new ModuloDaoimplement();
        RolModuloPermisoDaoimplement RMPDao = new RolModuloPermisoDaoimplement();
        GrupoDaoimplement GRPDao = new GrupoDaoimplement();

        //OBJETOS
        Permisos permiso = new Permisos();
        Miembro miembro = new Miembro();
        Modulo modulo = new Modulo();
        RolModuloPermiso rmp = new RolModuloPermiso();
        Rol rol = new Rol();
        Usuario usuario = new Usuario();
        Grupo grupo = new Grupo();
        //Lita de OBJETOS
        List<Permisos> permisoLista = new ArrayList<Permisos>();
        List<Miembro> miembroLista = new ArrayList<Miembro>();
        List<Rol> rolesLista = new ArrayList<Rol>();
        List<Usuario> usuarioLista = new ArrayList<Usuario>();
        List<Grupo> grupos = new ArrayList<Grupo>();
        List<Modulo> modulos = new ArrayList<Modulo>();
        List<RolModuloPermiso> rolmodulospermisos = new ArrayList<RolModuloPermiso>();
        Long id;

//
////                ///CREAR PERMISOS
        System.out.println("---CREANDO PERMISOS----");
//        //1
        id = Long.parseLong("1");
        permiso.setIdpermisos(id);
        permiso.setNombrePermiso("T");
        permiso.setDescripcionPermiso("TOTAL");
        PDao.crear(permiso);
//        
//        //2
        id = Long.parseLong("2");
        permiso.setIdpermisos(id);
        permiso.setNombrePermiso("L");
        permiso.setDescripcionPermiso("LECTURA");
        PDao.crear(permiso);
//
//        //3
        id = Long.parseLong("3");
        permiso.setIdpermisos(id);
        permiso.setNombrePermiso("E");
        permiso.setDescripcionPermiso("ESCRITURA");
        PDao.crear(permiso);
//        //4
        id = Long.parseLong("4");
        permiso.setIdpermisos(id);
        permiso.setNombrePermiso("M");
        permiso.setDescripcionPermiso("MODIFICACION");
        PDao.crear(permiso);
//        //5
        id = Long.parseLong("5");
        permiso.setIdpermisos(id);
        permiso.setNombrePermiso("I");
        permiso.setDescripcionPermiso("IMPRIMIR");
        PDao.crear(permiso);
        System.out.println("REGISTRO DE PERMISOS CREADOS CON EXITO");
//
//        //CREAR ROL
        System.out.println("---CREANDO ROLE POR DEFECTO----");
        id = Long.parseLong("1");
        rol.setIdrol(id);
        rol.setNombre("ADMINISTRADOR");
        rol.setActivo(Boolean.TRUE);
        RDao.crear(rol);//CREAR
        rolesLista.add(rol);
        System.out.println("ROL REGISTRADO CON EXITO ");
//////        
//        //CREAR GRUPOS
         grupo.setIcono("fa-newspaper-o");
        grupo.setNombre("Configuracion");
//        
//        ///CREAR MODULOS
        System.out.println("---CREANDO MODULO USUARIO POR DEFECTO----");
        id = Long.parseLong("1");
        modulo.setIdmodulo(id);
        modulo.setNombre("Usuario");
        modulo.setSrc("usuario.xhtm");
        modulo.setIcono("fa-user");
        MoDao.crear(modulo);
        System.out.println("MODULO CREADO CON EXITO ");
        //
        System.out.println("---CREANDO MODULO CONFIGURACION POR DEFECTO----");
        id = Long.parseLong("2");
        modulo.setIdmodulo(id);
        modulo.setNombre("Configuracion");
        modulo.setSrc("configuracion.xhtm");
        modulo.setIcono("fa-newspaper-o");
        modulo.setGrupomodulo(grupo);
        MoDao.crear(modulo);
        System.out.println("MODULO CREADO CON EXITO ");
//
//        //REGISTRAR RELACION ROL MODULO PERMISOS 
        System.out.println("---ESTABLECIENDO RELACION ROL MODULO PERMISOS----");
        id = Long.parseLong("1");
        permisoLista.clear();
        permiso=PDao.consultarC(Permisos.class, id);
        permisoLista.add(permiso);
        RMPDao.registrarRolModuloPermisos(rol, modulo, permisoLista);
        id = Long.parseLong("1");
        modulo=MoDao.consultar(Modulo.class, id);
        RMPDao.registrarRolModuloPermisos(rol, modulo, permisoLista);
        System.out.println("RELACION ROL MODULO PERMISOS EXITOSA");
//
////        
//        //CRAER MIEMBRO CON USUARIO
        System.out.println("---CREANDO MIEMBRO CON USUARIO POR DEFECTO----");
        id = Long.parseLong("1");
        miembro.setIdmiembro(id);
        miembro.setNombre1("SUPERADMINISTRADOR");
        miembro.setApellido1("SISTEMA");
        miembro.setEstado("ACTIVO");
        miembro.setDocumento("123456");
        usuario.setId(id);
        usuario.setRoles(rolesLista);
        usuario.setLogin("ADMIN");
        usuario.setPassword("ADMIN");
        usuario.setEstado(1);
        miembro.setUsuario(usuario);
        MDao.crear(miembro);
        System.out.println("MIEMBRO CON USUARIO CREADOS EXITOSAMENTE");
////        
//////        
////        LISTAR USUARIOS
        miembroLista = MDao.consultarTodo(Miembro.class);
        for (Miembro miebro : miembroLista) {
            System.out.println("SE REGISTRO EL MIEMBRO:"+miebro.getNombre1()+"-"+miebro.getApellido1());
            System.out.println("CON USUARIO:"+miebro.getUsuario().getLogin());
            System.out.println("CON "+miebro.getUsuario().getRoles().size()+" ROLES");
            System.out.println("Y PERMISOS EN EL MODULO "+miebro.getUsuario().getRoles().get(0).getRolModuloPermisoList().get(0).getModulo().getNombre()+" ");
        }
//Listar Modulos
//        int index = 0;
//        modulos = MoDao.consultarTodo(Modulo.class);
//        List<Map> menu = new LinkedList<Map>();
//        for (Modulo m : modulos) {
//            Modulo modulo = MoDao.consultar(Modulo.class, m.getIdmodulo());
//            Map item = new HashMap<String, String>();
//            item.put("src", modulo.getSrc());
//            if (modulo.getGrupomodulo() == null) {
//                con.addItem(item, modulo, menu);
//
//                menu.add(item);
//            } else {
//                item.put("nombre", modulo.getGrupomodulo().getNombre());
//                item.put("icono", modulo.getGrupomodulo().getIcono());
//                if (menu.isEmpty()) {
//                    con.addItemGrup(item, modulo);
//                    menu.add(item);
//                } else {
//                    boolean encontro = false;
//                    Map itemencontrado = null;
//                    for (int i = 0; i < menu.size(); i++) {
//                        Map itemMenu = menu.get(i);
//                        if (itemMenu.get("nombre").equals(item.get("nombre"))) {
//                            encontro = true;
//                            itemencontrado = itemMenu;
//                            break;
//                        }
//                    }
//                    if (!encontro) {
//                        con.addItemGrup(item, modulo);
//                        menu.add(item);
//                    } else {
//                        con.addItemGrup(item, modulo);
////                        if (item.get("submodulos") == null) {
//                        System.out.println("Item Encontrado" + itemencontrado);
//                        System.out.println("Item -         " + item);
//                        List<Modulo> modulosEncontraos = (List<Modulo>) itemencontrado.get("modulos");
//                        List<Modulo> modulositem = (List<Modulo>) item.get("modulos");
//                        boolean relacion = false;
//                        for (Modulo moduloencontrado : modulosEncontraos) {
//                            System.out.println("---" + moduloencontrado.getSubgrupos());
//                            if (moduloencontrado.getSubgrupos() != null) {
//                                for (Modulo moduloitem : modulositem) {
//                                    System.out.println(moduloitem.getSubgrupos());
//                                    if (moduloitem.getSubgrupos() != null) {
//                                        if (moduloitem.getSubgrupos().equals(moduloencontrado.getSubgrupos())) {
//                                            Modulo moduloRelacion = moduloencontrado;
//                                            System.err.println("Ese encontro a:" + moduloRelacion);
//                                            moduloRelacion.getSubgrupos().getModulos().add(modulo);
//                                            System.err.println("se modificco A:" + moduloRelacion);
//                                            relacion = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        if (!relacion) {
//                            modulosEncontraos.addAll(modulositem);
//                        }
//                        System.out.println("COMBINADO :" + itemencontrado);
//                    }
//                }
//            }
//        }
//
//        System.out.println("---MENU PRINCIPAL--");
////        for (Map menu1 : menu) {
////            System.out.println("*" + menu1.get("nombre") + "*");
////            if (menu1.get("modulos") != null) {
////                for (Modulo modulo : (List<Modulo>) menu1.get("modulos")) {
////                    System.out.println("-" + modulo.getNombre() + "-");
////                    if(modulo.getSubgrupos()!=null){
////                    System.out.println(modulo.getSubgrupos().getNombre());
////                    System.out.println(modulo.getNombre());
////                    
////                    }
////                }
////            }
////        }
//        for (Map menu1 : menu) {
//            System.out.println("*" + menu1.get("nombre") + "*");
//            if (menu1.get("modulos") != null) {
//                for (Modulo modulo : (List<Modulo>) menu1.get("modulos")) {
//                    if (modulo.getSubgrupos() == null) {
//                        System.out.println("-" + modulo.getNombre() + "-");
//                    } else {
//                        System.out.println("-" + modulo.getSubgrupos().getNombre());
//                        for (Modulo modulosub : modulo.getSubgrupos().getModulos()) {
//                            System.out.println(" -" + modulosub.getNombre());
//                        }
//                    }
//////
//                }
//            }
//
//        }

    }

    public void addItem(Map item, Modulo modulo, List<Map> menu) {

        if (modulo.getSubgrupos() == null) {

            System.out.println("*" + modulo.getNombre() + "*");
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
            System.out.println("*" + modulo.getGrupomodulo().getNombre() + "*");
            item.put("id", modulo.getIdmodulo());
            item.put("icono", modulo.getGrupomodulo().getIcono());
            item.put("nombre", modulo.getGrupomodulo().getNombre());
            item.put("grupo", modulo.getGrupomodulo().getNombre());
            List<Modulo> modulosvalidos = new ArrayList<Modulo>();

            System.out.println("-" + modulo.getNombre() + "-");
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
        System.out.println("-" + modulo.getSubgrupos().getNombre() + "-");
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
