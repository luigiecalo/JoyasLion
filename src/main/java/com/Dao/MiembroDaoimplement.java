/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.MiembrosDao;
import com.Entidades.Miembro;
import com.Entidades.Rol;
import com.Entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author LuisGuillermo
 */
public class MiembroDaoimplement extends ImplDao<Miembro, Long> implements MiembrosDao, Serializable {

    EntityManager em = getEmf().createEntityManager();

    public Miembro BuscarMiembroUsuario(Usuario usu) {
        Query query = em.createNamedQuery(Miembro.BUSCAR_USUARIO);
        query.setParameter("usuario", usu);
        query.setMaxResults(1);
        List<Miembro> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Miembro BuscarMiembroIdMiebro(Long idmiembro) {
        Query query = em.createNamedQuery(Miembro.BUSCAR_IDMIEMBRO);
        query.setParameter("idmiembro", idmiembro);
        query.setMaxResults(1);
        List<Miembro> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Miembro> BuscarMiembroUsuarioLike(String valor) {
        Query query = em.createNamedQuery(Miembro.BUSCAR_LIKE);
        query.setParameter("valor", '%' + valor + '%');
        List<Miembro> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<Miembro> BuscarMiembroRol(Long idrol) {
        Query query = em.createNamedQuery(Miembro.BUSCAR_ROL);
        query.setParameter("idrol", idrol);
        List<Miembro> list = (List<Miembro>) query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<Miembro> BuscarMiembroRolLike(Long idrol, String valor) {
        Query query = em.createNamedQuery(Miembro.BUSCAR_ROL_LIKE);
        query.setParameter("idrol", idrol);
        query.setParameter("valor", '%' + valor + '%');
        List<Miembro> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

}
