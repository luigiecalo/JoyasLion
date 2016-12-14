/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.UsuarioDao;
import com.Entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class UsuarioDaoimplement extends ImplDao<Usuario, Long> implements UsuarioDao, Serializable {

 EntityManager em = getEmf().createEntityManager();

    public List<Usuario> Listar() {
        return em.createNamedQuery(Usuario.LISTAR).getResultList();
    }
    public Usuario login(String usu,String pass) {
         Query query = em.createNamedQuery(Usuario.BUSCAR_USUARIO);
        query.setParameter("usu",usu);
        query.setParameter("pass",pass);
        query.setMaxResults(1);
        List<Usuario> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public void modificar1(Usuario usuario) {
        em.merge(usuario);
    }
    
    public Usuario buscarId1(Long id) {
        return em.find(Usuario.class, id);
    }
    
    public Usuario buscarxid1( Long id) {
        Query query = em.createNamedQuery(Usuario.BUSCAR_POR_ID);
        query.setParameter("idusuario",id);
        query.setMaxResults(1);
        List<Usuario> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public void eliminar1(Long id) {
        Usuario usu = buscarId1(id);
        em.remove(usu);
    }
}
