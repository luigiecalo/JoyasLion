/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import com.DaonInterface.PermisosDao;
import com.Entidades.Permisos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author LuisGuillermo
 */
public class PermisosDaoimplement extends ImplDao<Permisos, Long> implements PermisosDao, Serializable {
    EntityManager em=getEmf().createEntityManager();
//    public List<Permisos> Listar() {
//        return em.createNamedQuery("Permisos.findAll").getResultList();
//    }

}
