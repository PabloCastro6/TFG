-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: ahorro
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `idCategoria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Gasto'),(2,'Ingreso');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recordatorios`
--

DROP TABLE IF EXISTS `recordatorios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recordatorios` (
  `idRecordatorio` int NOT NULL AUTO_INCREMENT,
  `cantidad` double DEFAULT NULL,
  `concepto` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `usuarioId` int DEFAULT NULL,
  PRIMARY KEY (`idRecordatorio`),
  KEY `FKog9qt1oevomrxduf8yhp6tsv1` (`usuarioId`),
  CONSTRAINT `FKog9qt1oevomrxduf8yhp6tsv1` FOREIGN KEY (`usuarioId`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recordatorios`
--

LOCK TABLES `recordatorios` WRITE;
/*!40000 ALTER TABLE `recordatorios` DISABLE KEYS */;
/*!40000 ALTER TABLE `recordatorios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_transaccion`
--

DROP TABLE IF EXISTS `tipos_transaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_transaccion` (
  `idTipo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `icono` varchar(255) DEFAULT NULL,
  `tipoCategoriaId` int NOT NULL,
  `usuarioId` int NOT NULL,
  PRIMARY KEY (`idTipo`),
  KEY `tipoCategoriaId` (`tipoCategoriaId`),
  KEY `usuarioId` (`usuarioId`),
  CONSTRAINT `tipos_transaccion_ibfk_1` FOREIGN KEY (`tipoCategoriaId`) REFERENCES `categorias` (`idCategoria`),
  CONSTRAINT `tipos_transaccion_ibfk_2` FOREIGN KEY (`usuarioId`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_transaccion`
--

LOCK TABLES `tipos_transaccion` WRITE;
/*!40000 ALTER TABLE `tipos_transaccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipos_transaccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transacciones`
--

DROP TABLE IF EXISTS `transacciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transacciones` (
  `idTransaccion` int NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `categoriaId` int DEFAULT NULL,
  `usuarioId` int DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTransaccion`),
  KEY `FKtr2kyu22nmb2wax19i9girxsh` (`categoriaId`),
  KEY `FKjx708ff64icaev35hfjxn4dvl` (`usuarioId`),
  CONSTRAINT `FKjx708ff64icaev35hfjxn4dvl` FOREIGN KEY (`usuarioId`) REFERENCES `usuarios` (`idUsuario`),
  CONSTRAINT `FKtr2kyu22nmb2wax19i9girxsh` FOREIGN KEY (`categoriaId`) REFERENCES `categorias` (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transacciones`
--

LOCK TABLES `transacciones` WRITE;
/*!40000 ALTER TABLE `transacciones` DISABLE KEYS */;
INSERT INTO `transacciones` VALUES (106,'2025-04-10',2,32,1200,'Trabajo'),(107,'2025-04-12',1,32,50,'Regalos'),(108,'2025-04-29',2,32,500,'Alquileres Casas'),(111,'2025-04-11',2,29,1200,'Trabajo'),(113,'2025-04-10',1,29,50,'Regalos');
/*!40000 ALTER TABLE `transacciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombreCompleto` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (29,'Gonzalo','1234','Gonzlito@gmail.com','ADMINISTRADOR'),(30,'Hugo','5678','Hugito@gmail.com','ADMINISTRADOR'),(31,'Pablo','2468','Pablito@gmail.com','ADMINISTRADOR'),(32,'Juan','1234','juanito02@gmail.com','USUARIO'),(33,'Laura','4532','laurita@gmail.com','USUARIO'),(34,'Juanito Valderrama','1234','Juancho@gmail.com','USUARIO'),(35,'Laura','12334','laurita3@gmail.com','USUARIO'),(36,'Sergio','2354','sergio20@gmail.com','USUARIO'),(37,'Juanito Juan','23654','JuanBartolome@gmail.com','USUARIO'),(38,'hugo','1234','hugo@gmail.com','USUARIO');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:14:41
