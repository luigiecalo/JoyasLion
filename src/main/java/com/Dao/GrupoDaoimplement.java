/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import static com.Dao.ImplDao.getEmf;
import com.DaonInterface.GrupoDao;
import com.DaonInterface.RolDao;
import com.Entidades.Grupo;
import com.Entidades.Rol;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author LuisGuillermo
 */
public class GrupoDaoimplement extends ImplDao<Grupo, Long> implements GrupoDao, Serializable {

    

}
