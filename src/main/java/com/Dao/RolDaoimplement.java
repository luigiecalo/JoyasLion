/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.RolDao;
import com.Entidades.Rol;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class RolDaoimplement extends ImplDao<Rol, Long> implements RolDao, Serializable {
  EntityManager em = getEmf().createEntityManager();

    public List<Rol> Listar() {
        return em.createNamedQuery("Rol.findAll").getResultList();
    }
    
     public Rol BuscarRolId(Long idrol) {
        Query query = em.createNamedQuery(Rol.BUSCAR_ID);
        query.setParameter("idrol", idrol);
        query.setMaxResults(1);
        List<Rol> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
