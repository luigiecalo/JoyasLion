/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.ModeloImagenDao;
import com.Entidades.Modelo;
import com.Entidades.ModeloImagen;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class ModeloImagenDaoimplement extends ImplDao<ModeloImagen, Long> implements ModeloImagenDao, Serializable {
EntityManager em = getEmf().createEntityManager();
     
       public int Ultima() {
        int result = 0;
        Query query = em.createNamedQuery(ModeloImagen.ULTIMO);
        try {
            result = Integer.parseInt(query.getSingleResult().toString());
        } catch (NullPointerException ex) {
            result = 1;
        }
        return result;
    }

}
