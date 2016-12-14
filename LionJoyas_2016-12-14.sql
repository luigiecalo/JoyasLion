# ************************************************************
# Sequel Pro SQL dump
# Versión 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.30)
# Base de datos: LionJoyas
# Tiempo de Generación: 2016-12-14 23:01:16 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Volcado de tabla circon
# ------------------------------------------------------------

DROP TABLE IF EXISTS `circon`;

CREATE TABLE `circon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `muestra` double DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `referencia` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `circon` WRITE;
/*!40000 ALTER TABLE `circon` DISABLE KEYS */;

INSERT INTO `circon` (`id`, `codigo`, `muestra`, `precio`, `referencia`)
VALUES
	(1,'002',0.023,NULL,'Referencia'),
	(2,'001',0.009,NULL,'Referencia'),
	(14,'003',0.03,NULL,'Referencia'),
	(16,'003',0.03,NULL,'Referencia'),
	(17,'003',0.03,NULL,'Referencia');

/*!40000 ALTER TABLE `circon` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla grupo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `grupo`;

CREATE TABLE `grupo` (
  `idgrupo` bigint(20) NOT NULL AUTO_INCREMENT,
  `icono` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;

INSERT INTO `grupo` (`idgrupo`, `icono`, `nombre`)
VALUES
	(1,'fa-gears ','CONFIGURACION'),
	(2,'fa-newspaper-o','GRUPO 2');

/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla miembro
# ------------------------------------------------------------

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
  PRIMARY KEY (`idmiembro`),
  KEY `FK_miembro_idusuarios` (`idusuarios`),
  CONSTRAINT `FK_miembro_idusuarios` FOREIGN KEY (`idusuarios`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `miembro` WRITE;
/*!40000 ALTER TABLE `miembro` DISABLE KEYS */;

INSERT INTO `miembro` (`idmiembro`, `apellido1`, `apellido2`, `documento`, `estado`, `nombre1`, `nombre2`, `idusuarios`)
VALUES
	(1,'SISTEMA','ADMIN','123456','ACTIVO','LUIGIE','CASTRO',1),
	(13,'123','123','123','ACTIVO','123','123',5),
	(14,'Castro','Lora','1143354429','ACTIVO','Luis','Guillermo',6);

/*!40000 ALTER TABLE `miembro` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla modelo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `modelo`;

CREATE TABLE `modelo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `peso_circones` double DEFAULT NULL,
  `peso_modelo` double DEFAULT NULL,
  `tipo_modelo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_modelo_tipo_modelo` (`tipo_modelo`),
  CONSTRAINT `FK_modelo_tipo_modelo` FOREIGN KEY (`tipo_modelo`) REFERENCES `tipo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `modelo` WRITE;
/*!40000 ALTER TABLE `modelo` DISABLE KEYS */;

INSERT INTO `modelo` (`id`, `codigo`, `estado`, `imagen`, `peso_circones`, `peso_modelo`, `tipo_modelo`)
VALUES
	(1,'M006','ACTIVO','dsd/imag123.png',23400,332.8,1);

/*!40000 ALTER TABLE `modelo` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla modelo_cricon
# ------------------------------------------------------------

DROP TABLE IF EXISTS `modelo_cricon`;

CREATE TABLE `modelo_cricon` (
  `cantidad` int(11) DEFAULT NULL,
  `idmodelo` bigint(20) NOT NULL,
  `idcircon` bigint(20) NOT NULL,
  PRIMARY KEY (`idmodelo`,`idcircon`),
  KEY `FK_modelo_cricon_idcircon` (`idcircon`),
  CONSTRAINT `FK_modelo_cricon_idcircon` FOREIGN KEY (`idcircon`) REFERENCES `circon` (`id`),
  CONSTRAINT `FK_modelo_cricon_idmodelo` FOREIGN KEY (`idmodelo`) REFERENCES `modelo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `modelo_cricon` WRITE;
/*!40000 ALTER TABLE `modelo_cricon` DISABLE KEYS */;

INSERT INTO `modelo_cricon` (`cantidad`, `idmodelo`, `idcircon`)
VALUES
	(6,1,1),
	(7,1,2),
	(8,1,14),
	(9,1,16),
	(10,1,17);

/*!40000 ALTER TABLE `modelo_cricon` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla modelo_piedraCentral
# ------------------------------------------------------------

DROP TABLE IF EXISTS `modelo_piedraCentral`;

CREATE TABLE `modelo_piedraCentral` (
  `id_modelo` bigint(20) NOT NULL,
  `id_piedra` bigint(20) NOT NULL,
  PRIMARY KEY (`id_modelo`,`id_piedra`),
  KEY `FK_modelo_piedraCentral_id_piedra` (`id_piedra`),
  CONSTRAINT `FK_modelo_piedraCentral_id_modelo` FOREIGN KEY (`id_modelo`) REFERENCES `modelo` (`id`),
  CONSTRAINT `FK_modelo_piedraCentral_id_piedra` FOREIGN KEY (`id_piedra`) REFERENCES `piedra_central` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `modelo_piedraCentral` WRITE;
/*!40000 ALTER TABLE `modelo_piedraCentral` DISABLE KEYS */;

INSERT INTO `modelo_piedraCentral` (`id_modelo`, `id_piedra`)
VALUES
	(1,2);

/*!40000 ALTER TABLE `modelo_piedraCentral` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla modulo
# ------------------------------------------------------------

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
  KEY `FK_modulo_idgrupo` (`idgrupo`),
  KEY `FK_modulo_idsubgrupos` (`idsubgrupos`),
  CONSTRAINT `FK_modulo_idgrupo` FOREIGN KEY (`idgrupo`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `FK_modulo_idsubgrupos` FOREIGN KEY (`idsubgrupos`) REFERENCES `subgrupo` (`idsubgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `modulo` WRITE;
/*!40000 ALTER TABLE `modulo` DISABLE KEYS */;

INSERT INTO `modulo` (`idmodulo`, `icono`, `nombre`, `posicion`, `src`, `idgrupo`, `idsubgrupos`)
VALUES
	(1,'fa-user','Usuario',0,'usuario.xhtml',1,NULL),
	(2,'fa-newspaper-o','Configuracion',0,'configuracion.xhtml',NULL,NULL),
	(3,'fa-home','INICIO',0,'principal.xhtml',2,NULL),
	(4,'fa-paint-brush','temas',0,'temas.xhtml',2,2),
	(5,'fa-circle-o-notch','Modelo',1,'modelo.xhtml',1,NULL),
	(6,'fa-users','Roles',2,'roles.xhtml',1,NULL),
	(7,'fa-circle-o-notch','modulos',3,'modulo.xhtml',1,NULL);

/*!40000 ALTER TABLE `modulo` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla permisos
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permisos`;

CREATE TABLE `permisos` (
  `idpermisos` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion_permiso` varchar(255) DEFAULT NULL,
  `nombre_permiso` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idpermisos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;

INSERT INTO `permisos` (`idpermisos`, `descripcion_permiso`, `nombre_permiso`)
VALUES
	(1,'TOTAL','T'),
	(2,'LECTURA','L'),
	(3,'ESCRITURA','E'),
	(4,'MODIFICACION','M'),
	(5,'IMPRIMIR','I');

/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla piedra_central
# ------------------------------------------------------------

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
  KEY `FK_piedra_central_forma` (`forma`),
  KEY `FK_piedra_central_tipo` (`tipo`),
  CONSTRAINT `FK_piedra_central_forma` FOREIGN KEY (`forma`) REFERENCES `tipo` (`id`),
  CONSTRAINT `FK_piedra_central_tipo` FOREIGN KEY (`tipo`) REFERENCES `tipo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `piedra_central` WRITE;
/*!40000 ALTER TABLE `piedra_central` DISABLE KEYS */;

INSERT INTO `piedra_central` (`id`, `codigo`, `descripcion`, `nombre`, `peso`, `forma`, `tipo`)
VALUES
	(1,'PC002',NULL,'ESMERALDA GRANDE',5000.19,2,4),
	(2,'PC002',NULL,'ESMERALDA GRANDE',5000.19,2,4),
	(3,'PC003',NULL,'ESMERALDA GRANDE',5000.19,2,4),
	(4,'PC003',NULL,'ESMERALDA GRANDE',5000.19,2,4),
	(5,'PC003',NULL,'ESMERALDA GRANDE',5000.19,2,4),
	(6,'PC003',NULL,'ESMERALDA GRANDE',5000.19,2,4);

/*!40000 ALTER TABLE `piedra_central` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla rol
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rol`;

CREATE TABLE `rol` (
  `idrol` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` tinyint(1) DEFAULT '0',
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;

INSERT INTO `rol` (`idrol`, `activo`, `nombre`)
VALUES
	(1,1,'ADMINISTRADOR'),
	(2,1,'ADMIN'),
	(3,1,'CASTEADOR');

/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla rol_modulo_permiso
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rol_modulo_permiso`;

CREATE TABLE `rol_modulo_permiso` (
  `idrol` bigint(20) NOT NULL,
  `idmodulo` bigint(20) NOT NULL,
  `idpermiso` bigint(20) NOT NULL,
  PRIMARY KEY (`idrol`,`idmodulo`,`idpermiso`),
  KEY `FK_rol_modulo_permiso_idmodulo` (`idmodulo`),
  KEY `FK_rol_modulo_permiso_idpermiso` (`idpermiso`),
  CONSTRAINT `FK_rol_modulo_permiso_idmodulo` FOREIGN KEY (`idmodulo`) REFERENCES `modulo` (`idmodulo`),
  CONSTRAINT `FK_rol_modulo_permiso_idpermiso` FOREIGN KEY (`idpermiso`) REFERENCES `permisos` (`idpermisos`),
  CONSTRAINT `FK_rol_modulo_permiso_idrol` FOREIGN KEY (`idrol`) REFERENCES `rol` (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `rol_modulo_permiso` WRITE;
/*!40000 ALTER TABLE `rol_modulo_permiso` DISABLE KEYS */;

INSERT INTO `rol_modulo_permiso` (`idrol`, `idmodulo`, `idpermiso`)
VALUES
	(1,1,1),
	(1,2,1),
	(2,2,2),
	(1,3,1),
	(1,4,1),
	(1,5,1),
	(1,6,1),
	(1,7,1);

/*!40000 ALTER TABLE `rol_modulo_permiso` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla subgrupo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `subgrupo`;

CREATE TABLE `subgrupo` (
  `idsubgrupo` bigint(20) NOT NULL AUTO_INCREMENT,
  `icono` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idsubgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `subgrupo` WRITE;
/*!40000 ALTER TABLE `subgrupo` DISABLE KEYS */;

INSERT INTO `subgrupo` (`idsubgrupo`, `icono`, `nombre`)
VALUES
	(1,'fa-newspaper-o','SUB GRUPO'),
	(2,'fa-newspaper-o','TEMAS');

/*!40000 ALTER TABLE `subgrupo` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla tipo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tipo`;

CREATE TABLE `tipo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `SEGUN` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `tipo` WRITE;
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;

INSERT INTO `tipo` (`id`, `codigo`, `descripcion`, `nombre`)
VALUES
	(1,'01','estado_cliente','ACTIVO'),
	(2,'02','estado_cliente','INACTIVO'),
	(3,'03','estado_cliente','PROCESOS'),
	(4,'01','Tipo_piezas','ARETES');

/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla usr_rol
# ------------------------------------------------------------

DROP TABLE IF EXISTS `usr_rol`;

CREATE TABLE `usr_rol` (
  `usr_id` bigint(20) NOT NULL,
  `rol_id` bigint(20) NOT NULL,
  PRIMARY KEY (`usr_id`,`rol_id`),
  KEY `FK_usr_rol_rol_id` (`rol_id`),
  CONSTRAINT `FK_usr_rol_rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`idrol`),
  CONSTRAINT `FK_usr_rol_usr_id` FOREIGN KEY (`usr_id`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `usr_rol` WRITE;
/*!40000 ALTER TABLE `usr_rol` DISABLE KEYS */;

INSERT INTO `usr_rol` (`usr_id`, `rol_id`)
VALUES
	(1,1),
	(6,1),
	(1,2),
	(5,2),
	(5,3);

/*!40000 ALTER TABLE `usr_rol` ENABLE KEYS */;
UNLOCK TABLES;


# Volcado de tabla usuario
# ------------------------------------------------------------

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `idusuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` int(11) DEFAULT NULL,
  `login` varchar(150) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;

INSERT INTO `usuario` (`idusuario`, `estado`, `login`, `password`)
VALUES
	(1,1,'ADMIN','ADMIN'),
	(5,1,'123','123'),
	(6,1,'Luis','1143354429');

/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
