/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.ModeloDao;
import com.Entidades.Modelo;
import com.Entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class ModeloDaoimplement extends ImplDao<Modelo, Long> implements ModeloDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

    public List<Modelo> Listar() {
        return em.createNamedQuery(Modelo.LISTAR).getResultList();
    }
    
     public Modelo buscarModeloEstado(String codigo,String estado) {
         Query query = em.createNamedQuery(Modelo.BUSCAR_CODIGO_ESTADO);
        query.setParameter("codigo",codigo);
        query.setParameter("estado",estado);
        query.setMaxResults(1);
        List<Modelo> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
