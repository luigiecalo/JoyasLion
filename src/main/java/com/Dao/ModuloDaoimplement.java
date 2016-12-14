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

/**
 *
 * @author LuisGuillermo
 */
public class ModuloDaoimplement extends ImplDao<Modulo, Long> implements ModuloDao, Serializable {
 EntityManager em = getEmf().createEntityManager();

}
