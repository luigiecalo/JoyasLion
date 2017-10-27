/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.LoteDao;
import com.DaonInterface.OrdenDao;
import com.Entidades.Lote;
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
public class LoteDaoimplement extends ImplDao<Lote, Long> implements LoteDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

    public List<Lote> ListarPorUsuario() {
        return em.createNamedQuery(Lote.LISTAR).getResultList();
    }

    public List<Lote> buscarLoteUsuario(Usuario cliente) {
        Query query = em.createNamedQuery(Lote.USUARIO_LISTA);
        query.setParameter("usuario", cliente);
        List<Lote> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
     public List<Lote> buscarLoteEncargado(Usuario emcargado) {
        Query query = em.createNamedQuery(Lote.ENCARGADO_LISTA);
        query.setParameter("encargado", emcargado);
        List<Lote> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<Lote> buscarLoteEstado(String estado) {
        Query query = em.createNamedQuery(Lote.BUSCAR_ESTADO);
        query.setParameter("estado", estado);
        List<Lote> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Lote buscarLoteCodigoEstado(String codigo, String estado) {
        Query query = em.createNamedQuery(Lote.BUSCAR_CODIGO_ESTADO);
        query.setParameter("codigo", codigo);
        query.setParameter("estado", estado);
        query.setMaxResults(1);
        List<Lote> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public int Ultima(String cod) {
        int result = 0;
        Query query = em.createNamedQuery(Lote.ULTIMO);
        query.setParameter("codigo", '%' + cod + '%');
        try {
            result = Integer.parseInt(query.getSingleResult().toString());
        } catch (NullPointerException ex) {
            result = 1;
        }
        return result;
    }

}
