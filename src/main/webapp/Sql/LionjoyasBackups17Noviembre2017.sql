/*
 Navicat Premium Data Transfer

 Source Server         : MySQLProject
 Source Server Type    : MySQL
 Source Server Version : 50630
 Source Host           : localhost
 Source Database       : LionJoyas

 Target Server Type    : MySQL
 Target Server Version : 50100
 File Encoding         : utf-8

 Date: 11/17/2017 17:57:51 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `SEQUENCE`
-- ----------------------------
DROP TABLE IF EXISTS `SEQUENCE`;
CREATE TABLE `SEQUENCE` (
	`SEQ_NAME` varchar(50) NOT NULL,
	`SEQ_COUNT` decimal(38,0) DEFAULT NULL,
	PRIMARY KEY (`SEQ_NAME`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `SEQUENCE`
-- ----------------------------
BEGIN;
INSERT INTO `SEQUENCE` VALUES ('SEQ_GEN', '850');
COMMIT;

-- ----------------------------
--  Table structure for `circon`
-- ----------------------------
DROP TABLE IF EXISTS `circon`;
CREATE TABLE `circon` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`codigo` varchar(255) DEFAULT NULL,
	`muestra` double DEFAULT NULL,
	`precio` double DEFAULT NULL,
	`tamano` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=18 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `circon`
-- ----------------------------
BEGIN;
INSERT INTO `circon` VALUES ('1', '002', '0.023', null, '0.1mm'), ('2', '001', '0.009', null, '0.2mm'), ('14', '003', '0.03', null, '0.3mm'), ('16', '003', '0.03', null, '0.4mm'), ('17', '003', '0.03', null, '0.5mm');
COMMIT;

-- ----------------------------
--  Table structure for `grupo`
-- ----------------------------
DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
	`idgrupo` bigint(20) NOT NULL AUTO_INCREMENT,
	`icono` varchar(255) DEFAULT NULL,
	`nombre` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`idgrupo`)
) ENGINE=`InnoDB` AUTO_INCREMENT=7 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `grupo`
-- ----------------------------
BEGIN;
INSERT INTO `grupo` VALUES ('1', 'fa-gears ', 'CONFIGURACION'), ('4', 'fa-gears', 'GRUPO'), ('5', 'fa-shopping-cart', 'ORDENES'), ('6', 'fa-newspaper-o', 'ADMINISTRADOR');
COMMIT;

-- ----------------------------
--  Table structure for `lote`
-- ----------------------------
DROP TABLE IF EXISTS `lote`;
CREATE TABLE `lote` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`cantidad_modelos` int(11) DEFAULT NULL,
	`codigo` varchar(255) DEFAULT NULL,
	`estado` varchar(255) DEFAULT NULL,
	`fecha` bigint(20) DEFAULT NULL,
	`valor` double DEFAULT NULL,
	`encargado` bigint(20) DEFAULT NULL,
	`usuario` bigint(20) DEFAULT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_lote_encargado` FOREIGN KEY (`encargado`) REFERENCES `usuario` (`idusuario`),
	CONSTRAINT `FK_lote_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`idusuario`),
	INDEX `FK_lote_encargado` (`encargado`) ,
	INDEX `FK_lote_usuario` (`usuario`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=1 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Table structure for `lote_modelo_orden`
-- ----------------------------
DROP TABLE IF EXISTS `lote_modelo_orden`;
CREATE TABLE `lote_modelo_orden` (
	`cantidad` int(11) DEFAULT NULL,
	`peso_material` double DEFAULT NULL,
	`total` double DEFAULT NULL,
	`idmodelo` bigint(20) NOT NULL,
	`idorden` bigint(20) NOT NULL,
	`idmaterial` bigint(20) NOT NULL,
	`idlote` bigint(20) NOT NULL,
	PRIMARY KEY (`idmodelo`, `idorden`, `idmaterial`, `idlote`),
	CONSTRAINT `FK_lote_modelo_orden_idlote` FOREIGN KEY (`idlote`) REFERENCES `lote` (`id`),
	CONSTRAINT `FK_lote_modelo_orden_idmaterial` FOREIGN KEY (`idmaterial`) REFERENCES `material` (`id`),
	CONSTRAINT `FK_lote_modelo_orden_idmodelo` FOREIGN KEY (`idmodelo`) REFERENCES `modelo` (`id`),
	CONSTRAINT `FK_lote_modelo_orden_idorden` FOREIGN KEY (`idorden`) REFERENCES `orden` (`id`),
	INDEX `FK_lote_modelo_orden_idmaterial` (`idmaterial`) ,
	INDEX `FK_lote_modelo_orden_idlote` (`idlote`) ,
	INDEX `FK_lote_modelo_orden_idorden` (`idorden`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Table structure for `material`
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`descripcion` varchar(255) DEFAULT NULL,
	`nombre` varchar(255) DEFAULT NULL,
	`valor` double DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=4 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `material`
-- ----------------------------
BEGIN;
INSERT INTO `material` VALUES ('1', 'gm', 'ORO', '1200'), ('2', 'gm', 'PLATA', '1000'), ('3', 'gm', 'BRONCE', '500');
COMMIT;

-- ----------------------------
--  Table structure for `miembro`
-- ----------------------------
DROP TABLE IF EXISTS `miembro`;
CREATE TABLE `miembro` (
	`idmiembro` bigint(20) NOT NULL AUTO_INCREMENT,
	`apellido1` varchar(255) DEFAULT NULL,
	`apellido2` varchar(255) DEFAULT NULL,
	`documento` varchar(255) DEFAULT NULL,
	`estado` varchar(255) DEFAULT NULL,
	`nombre1` varchar(255) DEFAULT NULL,
	`nombre2` varchar(255) DEFAULT NULL,
	`idusuarios` bigint(20) DEFAULT NULL,
	`imagen` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`idmiembro`),
	CONSTRAINT `FK_miembro_idusuarios` FOREIGN KEY (`idusuarios`) REFERENCES `usuario` (`idusuario`),
	INDEX `FK_miembro_idusuarios` (`idusuarios`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=32 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `miembro`
-- ----------------------------
BEGIN;
INSERT INTO `miembro` VALUES ('1', 'SISTEMA', 'ADMIN', '123456', 'ACTIVO', 'LUIGIE', 'CASTRO', '1', '123456'), ('13', '123', '123', '4321123', 'ACTIVO', '123', '123', '5', '4321123'), ('14', 'Castro', 'Lora', '1143354429', 'ACTIVO', 'Luis', 'Guillermo', '6', '1143354429'), ('15', '12312312', '', '123123123', 'INACTIVO', '123123123', '', '7', null), ('16', 'cliente', 'cliente', '123123', 'ACTIVO', 'cliente', 'cliente', '8', 'default'), ('17', '123', '', '123', 'INACTIVO', '323', '', '9', null), ('18', 'CAstro', 'MArtinez', '1143354421', 'ACTIVO', 'Lopez ', 'Julio', '10', 'Captura de pantalla 2016-12-01 10.14.46.png'), ('19', '1234', '1234', '1234', 'ACTIVO', '1234', '1234', '11', 'default2'), ('20', '4321', '4321', '4321', 'ACTIVO', '4321', '4321', '12', '4321'), ('21', '1111', '1111', '1111', 'ACTIVO', '1111', '1111', '13', 'default2'), ('22', 'asd', 'asd', '123', 'ACTIVO', 'asd', 'asd', '14', '1483741834_home_back.png4'), ('23', '111', '111', '111', 'ACTIVO', '111', '111', '15', '111'), ('24', '9999', '999', '9999', 'ACTIVO', '999', '999', '16', '9999'), ('25', '777777', '777777', '777777', 'ACTIVO', '77777', '777777', '17', '777777'), ('26', '3333', '333', '3333', 'ACTIVO', '333', '333', '18', 'default'), ('27', '23', '23', '23', 'ACTIVO', '23', '23', '19', '23'), ('28', '444', '444', '444', 'ACTIVO', '444', '444', '20', '444'), ('29', '43', '43', '34', 'ACTIVO', '43', '43', '22', 'default'), ('30', '123123', '123123', '123123', 'ACTIVO', '123123', '123123', '23', 'default'), ('31', '5345', '543', '15432', 'ACTIVO', '543', '543', '24', '15432');
COMMIT;

-- ----------------------------
--  Table structure for `modelo`
-- ----------------------------
DROP TABLE IF EXISTS `modelo`;
CREATE TABLE `modelo` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`codigo` varchar(255) DEFAULT NULL,
	`estado` varchar(255) DEFAULT NULL,
	`imagen` varchar(255) DEFAULT NULL,
	`peso_circones` double DEFAULT NULL,
	`peso_modelo` double DEFAULT NULL,
	`tipo_modelo` bigint(20) DEFAULT NULL,
	`piedra` tinyint(1) DEFAULT 1,
	`zircon` tinyint(1) DEFAULT 1,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_modelo_tipo_modelo` FOREIGN KEY (`tipo_modelo`) REFERENCES `tipo` (`id`),
	INDEX `FK_modelo_tipo_modelo` (`tipo_modelo`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=68 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `modelo`
-- ----------------------------
BEGIN;
INSERT INTO `modelo` VALUES ('53', 'M001', 'INACTIVO', 'default2', '0', '234', '6', '1', '0'), ('54', 'M054', 'ACTIVO', 'M054', '0.0184', '600', '5', '1', '1'), ('55', 'M055', 'ACTIVO', 'M055', '0.054000000000000006', '900', '5', '1', '1'), ('56', 'M056', 'ACTIVO', 'M056', '0', '6000', '5', '1', '1'), ('57', 'M057', 'ACTIVO', 'M057', '0', '30', '4', '1', '1'), ('58', 'M058', 'ACTIVO', 'M058', '0.018', '400', '5', '1', '1'), ('59', 'M059', 'ACTIVO', 'M059', '0.0092', '23', '5', '1', '1'), ('60', 'M060', 'ACTIVO', 'M060', '0.0184', '600', '5', '1', '1'), ('61', 'M061', 'ACTIVO', 'M061', '0', '800', '6', '1', '1'), ('62', 'M062', 'ACTIVO', 'default2', '0', '100', '4', '0', '0'), ('63', 'M063', 'ACTIVO', 'default2', '0.024', '1000', '6', '1', '1'), ('64', 'M064', 'ACTIVO', 'M064', '0', '900', '4', '1', '1'), ('65', 'M065', 'ACTIVO', 'M065', '0', '900', '4', '1', '0'), ('66', 'M066', 'ACTIVO', 'default2', '0.006', '32323', '4', '0', '1'), ('67', 'M067', 'ACTIVO', 'M067', '0.03', '400', '6', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `modelo_cricon`
-- ----------------------------
DROP TABLE IF EXISTS `modelo_cricon`;
CREATE TABLE `modelo_cricon` (
	`cantidad` int(11) DEFAULT NULL,
	`idmodelo` bigint(20) NOT NULL,
	`idcircon` bigint(20) NOT NULL,
	PRIMARY KEY (`idmodelo`, `idcircon`),
	CONSTRAINT `FK_modelo_cricon_idcircon` FOREIGN KEY (`idcircon`) REFERENCES `circon` (`id`),
	CONSTRAINT `FK_modelo_cricon_idmodelo` FOREIGN KEY (`idmodelo`) REFERENCES `modelo` (`id`),
	INDEX `FK_modelo_cricon_idcircon` (`idcircon`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `modelo_cricon`
-- ----------------------------
BEGIN;
INSERT INTO `modelo_cricon` VALUES ('5', '53', '1'), ('2', '53', '14'), ('2', '53', '17'), ('4', '54', '1'), ('9', '55', '16'), ('3', '58', '17'), ('2', '59', '1'), ('4', '60', '1'), ('4', '63', '16'), ('1', '66', '16'), ('3', '67', '16'), ('2', '67', '17');
COMMIT;

-- ----------------------------
--  Table structure for `modelo_imagen`
-- ----------------------------
DROP TABLE IF EXISTS `modelo_imagen`;
CREATE TABLE `modelo_imagen` (
	`id` bigint(20) NOT NULL,
	`nombre` varchar(255) DEFAULT NULL,
	`idmodelo` bigint(20) DEFAULT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_modelo_imagen_idmodelo` FOREIGN KEY (`idmodelo`) REFERENCES `modelo` (`id`),
	INDEX `FK_modelo_imagen_idmodelo` (`idmodelo`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `modelo_imagen`
-- ----------------------------
BEGIN;
INSERT INTO `modelo_imagen` VALUES ('637', 'M060-2', '60'), ('638', 'M061-2', '61'), ('639', 'M061-3', '61'), ('640', 'M061-4', '61'), ('701', 'M055-5', '55'), ('702', 'M055-6', '55'), ('703', 'M057-4', '57'), ('704', 'M057-5', '57'), ('705', 'M058-0', '58'), ('706', 'M058-1', '58'), ('707', 'M058-2', '58'), ('708', 'M058-3', '58'), ('709', 'M059-3', '59'), ('710', 'M054-0', '54'), ('711', 'M056-0', '56'), ('712', 'M056-1', '56'), ('754', 'M065-0', '65'), ('755', 'M065-1', '65'), ('756', 'M065-2', '65'), ('801', 'M062-0', '62'), ('802', 'M062-1', '62');
COMMIT;

-- ----------------------------
--  Table structure for `modelo_piedraCentral`
-- ----------------------------
DROP TABLE IF EXISTS `modelo_piedraCentral`;
CREATE TABLE `modelo_piedraCentral` (
	`cantidad` int(11) DEFAULT NULL,
	`idpiedra` bigint(20) NOT NULL,
	`idmodelo` bigint(20) NOT NULL,
	PRIMARY KEY (`idpiedra`, `idmodelo`),
	CONSTRAINT `FK_modelo_piedraCentral_idmodelo` FOREIGN KEY (`idmodelo`) REFERENCES `modelo` (`id`),
	CONSTRAINT `FK_modelo_piedraCentral_idpiedra` FOREIGN KEY (`idpiedra`) REFERENCES `piedra_central` (`id`),
	INDEX `FK_modelo_piedraCentral_idmodelo` (`idmodelo`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `modelo_piedraCentral`
-- ----------------------------
BEGIN;
INSERT INTO `modelo_piedraCentral` VALUES ('3', '1', '58'), ('1', '2', '63'), ('2', '3', '53'), ('5', '6', '54');
COMMIT;

-- ----------------------------
--  Table structure for `modulo`
-- ----------------------------
DROP TABLE IF EXISTS `modulo`;
CREATE TABLE `modulo` (
	`idmodulo` bigint(20) NOT NULL AUTO_INCREMENT,
	`icono` varchar(255) DEFAULT NULL,
	`nombre` varchar(255) DEFAULT NULL,
	`posicion` int(11) DEFAULT NULL,
	`src` varchar(255) DEFAULT NULL,
	`idgrupo` bigint(20) DEFAULT NULL,
	`idsubgrupos` bigint(20) DEFAULT NULL,
	PRIMARY KEY (`idmodulo`),
	CONSTRAINT `FK_modulo_idgrupo` FOREIGN KEY (`idgrupo`) REFERENCES `grupo` (`idgrupo`),
	CONSTRAINT `FK_modulo_idsubgrupos` FOREIGN KEY (`idsubgrupos`) REFERENCES `subgrupo` (`idsubgrupo`),
	INDEX `FK_modulo_idgrupo` (`idgrupo`) ,
	INDEX `FK_modulo_idsubgrupos` (`idsubgrupos`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=23 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `modulo`
-- ----------------------------
BEGIN;
INSERT INTO `modulo` VALUES ('1', 'fa-user', 'Usuario', '7', 'usuario.xhtml', '1', null), ('3', 'fa-yelp', 'Lista Productos', '3', 'principal.xhtml', null, null), ('4', 'fa-paint-brush', 'temas', '4', 'temas.xhtml', '6', null), ('5', 'fa-circle-o-notch', 'Modelo', '5', 'modelo.xhtml', '1', null), ('7', 'fa-circle-o-notch', 'modulos', '1', 'modulo.xhtml', '6', null), ('19', 'fa-cart-plus', 'Mis Ordenes', '0', 'miorden.xhtml', '5', null), ('20', 'fa-group', 'Roles', '0', 'roles.xhtml', '6', null), ('21', 'fa-shopping-cart', 'Consultar Ordenes', '0', 'ordenes.xhtml', '5', null), ('22', 'fa-newspaper-o', 'LOTES', '0', 'lotes.xhtml', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `orden`
-- ----------------------------
DROP TABLE IF EXISTS `orden`;
CREATE TABLE `orden` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`codigo` varchar(255) DEFAULT NULL,
	`estado` varchar(255) DEFAULT NULL,
	`fecha` bigint(20) DEFAULT NULL,
	`valor_total` double DEFAULT NULL,
	`cliente` bigint(20) DEFAULT NULL,
	`usuario` bigint(20) DEFAULT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_orden_cliente` FOREIGN KEY (`cliente`) REFERENCES `usuario` (`idusuario`),
	CONSTRAINT `FK_orden_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`idusuario`),
	INDEX `FK_orden_usuario` (`usuario`) ,
	INDEX `FK_orden_cliente` (`cliente`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=5 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `orden`
-- ----------------------------
BEGIN;
INSERT INTO `orden` VALUES ('1', '0211171', 'EN ESPERA', '1509657298536', '163800000', '1', '1'), ('2', '0211172', 'EN ESPERA', '1509660245365', '834000000', '1', '1'), ('3', '0211173', 'EN ESPERA', '1509660279503', '756000000', '1', '1'), ('4', '0211174', 'EN ESPERA', '1509660314157', '90000000', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `orden_modelo`
-- ----------------------------
DROP TABLE IF EXISTS `orden_modelo`;
CREATE TABLE `orden_modelo` (
	`cantidad` int(11) DEFAULT NULL,
	`descuento` double DEFAULT NULL,
	`estado` varchar(255) DEFAULT NULL,
	`peso_material` double DEFAULT NULL,
	`total` double DEFAULT NULL,
	`valor` double DEFAULT NULL,
	`idmodelo` bigint(20) NOT NULL,
	`idorden` bigint(20) NOT NULL,
	`idmaterial` bigint(20) NOT NULL,
	PRIMARY KEY (`idmodelo`, `idorden`, `idmaterial`),
	CONSTRAINT `FK_orden_modelo_idmaterial` FOREIGN KEY (`idmaterial`) REFERENCES `material` (`id`),
	CONSTRAINT `FK_orden_modelo_idmodelo` FOREIGN KEY (`idmodelo`) REFERENCES `modelo` (`id`),
	CONSTRAINT `FK_orden_modelo_idorden` FOREIGN KEY (`idorden`) REFERENCES `orden` (`id`),
	INDEX `FK_orden_modelo_idorden` (`idorden`) ,
	INDEX `FK_orden_modelo_idmaterial` (`idmaterial`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `orden_modelo`
-- ----------------------------
BEGIN;
INSERT INTO `orden_modelo` VALUES ('5', '0', 'PENDIENTE', '842400', '140400000', '28080000', '53', '1', '1'), ('1', '0', 'PENDIENTE', '234000', '23400000', '23400000', '53', '1', '2'), ('10', '0', 'PENDIENTE', '2340000', '234000000', '23400000', '53', '2', '2'), ('3', '0', 'PENDIENTE', '900000', '90000000', '30000000', '54', '4', '3'), ('3', '0', 'PENDIENTE', '3240000', '324000000', '108000000', '55', '3', '1'), ('2', '0', 'PENDIENTE', '6000000', '600000000', '300000000', '56', '2', '3'), ('4', '0', 'PENDIENTE', '4320000', '432000000', '108000000', '64', '3', '1');
COMMIT;

-- ----------------------------
--  Table structure for `permisos`
-- ----------------------------
DROP TABLE IF EXISTS `permisos`;
CREATE TABLE `permisos` (
	`idpermisos` bigint(20) NOT NULL AUTO_INCREMENT,
	`descripcion_permiso` varchar(255) DEFAULT NULL,
	`nombre_permiso` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`idpermisos`)
) ENGINE=`InnoDB` AUTO_INCREMENT=6 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `permisos`
-- ----------------------------
BEGIN;
INSERT INTO `permisos` VALUES ('1', 'TOTAL', 'T'), ('2', 'LECTURA', 'L'), ('3', 'ESCRITURA', 'E'), ('4', 'MODIFICACION', 'M'), ('5', 'IMPRIMIR', 'I');
COMMIT;

-- ----------------------------
--  Table structure for `piedra_central`
-- ----------------------------
DROP TABLE IF EXISTS `piedra_central`;
CREATE TABLE `piedra_central` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`codigo` varchar(255) DEFAULT NULL,
	`descripcion` varchar(255) DEFAULT NULL,
	`nombre` varchar(255) DEFAULT NULL,
	`peso` double DEFAULT NULL,
	`forma` bigint(20) DEFAULT NULL,
	`tipo` bigint(20) DEFAULT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_piedra_central_forma` FOREIGN KEY (`forma`) REFERENCES `tipo` (`id`),
	CONSTRAINT `FK_piedra_central_tipo` FOREIGN KEY (`tipo`) REFERENCES `tipo` (`id`),
	INDEX `FK_piedra_central_forma` (`forma`) ,
	INDEX `FK_piedra_central_tipo` (`tipo`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=7 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `piedra_central`
-- ----------------------------
BEGIN;
INSERT INTO `piedra_central` VALUES ('1', 'PC002', null, 'ESMERALDA GRANDE 1', '5000.19', '14', '7'), ('2', 'PC002', null, 'ESMERALDA GRANDE 2', '5000.19', '16', '13'), ('3', 'PC003', null, 'ESMERALDA GRANDE 3', '5000.19', '13', '12'), ('4', 'PC003', null, 'ESMERALDA GRANDE 4', '5000.19', '15', '9'), ('5', 'PC003', null, 'ESMERALDA GRANDE 5', '5000.19', '12', '8'), ('6', 'PC003', null, 'ESMERALDA GRANDE 6', '5000.19', '18', '8');
COMMIT;

-- ----------------------------
--  Table structure for `rol`
-- ----------------------------
DROP TABLE IF EXISTS `rol`;
CREATE TABLE `rol` (
	`idrol` bigint(20) NOT NULL AUTO_INCREMENT,
	`activo` tinyint(1) DEFAULT 0,
	`nombre` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`idrol`)
) ENGINE=`InnoDB` AUTO_INCREMENT=5 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `rol`
-- ----------------------------
BEGIN;
INSERT INTO `rol` VALUES ('1', '1', 'ADMINISTRADOR'), ('2', '1', 'ADMIN'), ('3', '1', 'CASTEADOR'), ('4', '1', 'CLIENTE');
COMMIT;

-- ----------------------------
--  Table structure for `rol_modulo_permiso`
-- ----------------------------
DROP TABLE IF EXISTS `rol_modulo_permiso`;
CREATE TABLE `rol_modulo_permiso` (
	`idrol` bigint(20) NOT NULL,
	`idmodulo` bigint(20) NOT NULL,
	`idpermiso` bigint(20) NOT NULL,
	PRIMARY KEY (`idrol`, `idmodulo`, `idpermiso`),
	CONSTRAINT `FK_rol_modulo_permiso_idmodulo` FOREIGN KEY (`idmodulo`) REFERENCES `modulo` (`idmodulo`),
	CONSTRAINT `FK_rol_modulo_permiso_idpermiso` FOREIGN KEY (`idpermiso`) REFERENCES `permisos` (`idpermisos`),
	CONSTRAINT `FK_rol_modulo_permiso_idrol` FOREIGN KEY (`idrol`) REFERENCES `rol` (`idrol`),
	INDEX `FK_rol_modulo_permiso_idmodulo` (`idmodulo`) ,
	INDEX `FK_rol_modulo_permiso_idpermiso` (`idpermiso`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `rol_modulo_permiso`
-- ----------------------------
BEGIN;
INSERT INTO `rol_modulo_permiso` VALUES ('1', '1', '1'), ('2', '1', '1'), ('1', '3', '1'), ('2', '3', '1'), ('4', '3', '1'), ('1', '4', '1'), ('1', '5', '1'), ('2', '5', '1'), ('1', '7', '1'), ('1', '19', '1'), ('2', '19', '1'), ('4', '19', '1'), ('1', '20', '1'), ('1', '21', '1'), ('2', '21', '1'), ('1', '22', '1'), ('3', '22', '4'), ('3', '22', '5');
COMMIT;

-- ----------------------------
--  Table structure for `subgrupo`
-- ----------------------------
DROP TABLE IF EXISTS `subgrupo`;
CREATE TABLE `subgrupo` (
	`idsubgrupo` bigint(20) NOT NULL AUTO_INCREMENT,
	`icono` varchar(255) DEFAULT NULL,
	`nombre` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`idsubgrupo`)
) ENGINE=`InnoDB` AUTO_INCREMENT=3 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `subgrupo`
-- ----------------------------
BEGIN;
INSERT INTO `subgrupo` VALUES ('1', 'fa-newspaper-o', 'ADMINISTRATIVAS'), ('2', 'fa-newspaper-o', 'TEMAS');
COMMIT;

-- ----------------------------
--  Table structure for `tipo`
-- ----------------------------
DROP TABLE IF EXISTS `tipo`;
CREATE TABLE `tipo` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`codigo` varchar(255) DEFAULT NULL,
	`descripcion` varchar(255) NOT NULL DEFAULT '',
	`nombre` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `SEGUN` (`descripcion`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=35 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `tipo`
-- ----------------------------
BEGIN;
INSERT INTO `tipo` VALUES ('2', '02', 'estado_cliente', 'INACTIVO'), ('3', '03', 'estado_cliente', 'PROCESOS'), ('4', '01', 'Tipo_piezas', 'ARETES'), ('5', '02', 'Tipo_piezas', 'ANILLOS'), ('6', '03', 'Tipo_piezas', 'DIJES'), ('7', '01', 'tipo_piedra_central', 'DIAMANTE'), ('8', '02', 'tipo_piedra_central', 'RUBI'), ('9', '03', 'tipo_piedra_central', 'ZAFIRO'), ('10', '04', 'tipo_piedra_central', 'HELIODORO'), ('11', '05', 'tipo_piedra_central', 'MORGANITA'), ('12', '06', 'tipo_piedra_central', 'BESVITO'), ('13', '07', 'tipo_piedra_central', 'AMATISTO'), ('14', '01', 'forma_pieza', 'FORMA1'), ('15', '02', 'forma_pieza', 'FORMA2'), ('16', '03', 'forma_pieza', 'FORMA3'), ('17', '04', 'forma_pieza', 'FORMA4'), ('18', '05', 'forma_pieza', 'FORMA5'), ('19', '06', 'forma_pieza', 'FORMA6'), ('20', '07', 'forma_pieza', 'FORMA7'), ('21', '01', 'estado_ordentes_lotes', 'ESPERA'), ('22', '02', 'estado_ordentes_lotes', 'PROCESO'), ('23', '03', 'estado_ordentes_lotes', 'FINALIZADO'), ('24', '01', 'paginas', 'configuracion.xhtml'), ('25', '02', 'paginas', 'principal.xhtml'), ('26', '03', 'paginas', 'modelo.xhtml'), ('27', '04', 'paginas', 'modulo.xhtml'), ('28', '05', 'paginas', 'ordenes.xhtml'), ('29', '06', 'paginas', 'principal.xhtml'), ('30', '07', 'paginas', 'roles.xhtml'), ('31', '08', 'paginas', 'temas.xhtml'), ('32', '09', 'paginas', 'usuario.xhtml'), ('33', '10', 'paginas', 'miorden.xhtml'), ('34', '11', 'paginas', 'lotes.xhtml');
COMMIT;

-- ----------------------------
--  Table structure for `usr_rol`
-- ----------------------------
DROP TABLE IF EXISTS `usr_rol`;
CREATE TABLE `usr_rol` (
	`usr_id` bigint(20) NOT NULL,
	`rol_id` bigint(20) NOT NULL,
	PRIMARY KEY (`usr_id`, `rol_id`),
	CONSTRAINT `FK_usr_rol_rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`idrol`),
	CONSTRAINT `FK_usr_rol_usr_id` FOREIGN KEY (`usr_id`) REFERENCES `usuario` (`idusuario`),
	INDEX `FK_usr_rol_rol_id` (`rol_id`) 
) ENGINE=`InnoDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `usr_rol`
-- ----------------------------
BEGIN;
INSERT INTO `usr_rol` VALUES ('1', '1'), ('6', '1'), ('18', '1'), ('1', '2'), ('7', '2'), ('11', '2'), ('20', '2'), ('1', '3'), ('6', '3'), ('9', '3'), ('22', '3'), ('1', '4'), ('5', '4'), ('8', '4'), ('10', '4'), ('12', '4'), ('13', '4'), ('14', '4'), ('15', '4'), ('16', '4'), ('17', '4'), ('19', '4'), ('23', '4'), ('24', '4');
COMMIT;

-- ----------------------------
--  Table structure for `usuario`
-- ----------------------------
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
	`idusuario` bigint(20) NOT NULL AUTO_INCREMENT,
	`estado` int(11) DEFAULT NULL,
	`login` varchar(150) NOT NULL,
	`password` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`idusuario`),
	UNIQUE `login` (`login`) 
) ENGINE=`InnoDB` AUTO_INCREMENT=25 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `usuario`
-- ----------------------------
BEGIN;
INSERT INTO `usuario` VALUES ('1', '1', 'ADMIN', 'ADMIN'), ('5', '1', '123', '123'), ('6', '1', 'Luis', '1143354429'), ('7', '0', '123123123', '123123123'), ('8', '1', 'wewe', '123123'), ('9', '0', '323', '123'), ('10', '1', 'Lopez ', '1143354421'), ('11', '1', '1234', '1234'), ('12', '1', '4321', '4321'), ('13', '1', '1111', '1111'), ('14', '1', 'asd', '123'), ('15', '1', '111', '111'), ('16', '1', '999', '9999'), ('17', '1', '77777', '777777'), ('18', '1', '333', '3333'), ('19', '1', '23', '23'), ('20', '1', '444', '444'), ('22', '1', '43', '34'), ('23', '1', '123123', '123123'), ('24', '1', '543', '15432');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
