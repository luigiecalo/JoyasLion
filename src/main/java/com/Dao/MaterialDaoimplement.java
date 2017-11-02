/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.MaterialDao;
import com.Entidades.Material;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class MaterialDaoimplement extends ImplDao<Material, Long> implements MaterialDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

   

    public List<Material> buscarNombre(String nombre) {
        Query query = em.createNamedQuery(Material.BUSCAR_NOMBRE);
        query.setParameter("codigo", '%' + nombre + '%');
        List<Material> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
  


}
