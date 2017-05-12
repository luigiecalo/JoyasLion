/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.OrdenDao;
import com.Entidades.Modelo;
import com.Entidades.Orden;
import com.Entidades.OrdenModelo;
import com.Entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class OrdenDaoimplement extends ImplDao<Orden, Long> implements OrdenDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

    public List<Orden> ListarPorUsuario() {
        return em.createNamedQuery(Orden.LISTAR).getResultList();
    }

    public List<Orden> buscarOrdenUsuario(Usuario cliente) {
        Query query = em.createNamedQuery(Orden.USUARIO_LISTA);
        query.setParameter("cliente", cliente);
        List<Orden> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<Orden> buscarOrdenEstado(String estado) {
        Query query = em.createNamedQuery(Orden.BUSCAR_ESTADO);
        query.setParameter("estado", estado);
        List<Orden> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Orden buscarOrdenCodigoEstado(String codigo, String estado) {
        Query query = em.createNamedQuery(Orden.BUSCAR_CODIGO_ESTADO);
        query.setParameter("codigo", codigo);
        query.setParameter("estado", estado);
        query.setMaxResults(1);
        List<Orden> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public int Ultima(String cod) {
        int result = 0;
        Query query = em.createNamedQuery(Orden.ULTIMO);
        query.setParameter("codigo", '%' + cod + '%');
        try {
            result = Integer.parseInt(query.getSingleResult().toString());
        } catch (NullPointerException ex) {
            result = 1;
        }
        return result;
    }

}
