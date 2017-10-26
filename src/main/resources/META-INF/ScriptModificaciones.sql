/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  luigie
 * Created: 26-oct-2017
 */
-- 1): adiciona la columna estado en ordenModelo
ALTER TABLE `LionJoyas`.`orden_modelo` ADD COLUMN `estado` varchar(50) DEFAULT NULL AFTER `idorden`;