/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.TipoDao;
import com.Entidades.Tipo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class TipoDaoimplement extends ImplDao<Tipo, Long> implements TipoDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

    public List<Tipo> ListarDescripcion(String tipo) {
        Query query = em.createNamedQuery(Tipo.LISTAR_DESCRIPCION);
        query.setParameter("descripcion", tipo);
        List<Tipo> result = query.getResultList();
        return result;
    }

}
