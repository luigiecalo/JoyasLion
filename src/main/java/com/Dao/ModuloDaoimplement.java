/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.ModuloDao;
import com.Entidades.Modulo;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class ModuloDaoimplement extends ImplDao<Modulo, Long> implements ModuloDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

    public void modificar1(Modulo mod) {
        Query query = em.createQuery("UPDATE modulo m SET m.src='zzzz',m.nombre='fff', m.icono='12312312' WHERE m.idmodulo= 13");
//        query.setParameter(1, mod.getSrc());
//        query.setParameter(2, mod.getNombre());
//        query.setParameter(3, mod.getIcono());
//        query.setParameter(4, mod.getGrupomodulo());
//        query.setParameter(5, mod.getSubgrupos());
//        query.setParameter(6, mod.getIdmodulo());
        query.executeUpdate();

    }
}
