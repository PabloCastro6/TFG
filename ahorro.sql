CREATE DATABASE `ahorro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use ahorro;
CREATE TABLE `categorias` (
  `idCategoria` int NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `usuarioId` int DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


SELECT * FROM usuarios;

CREATE TABLE `transacciones` (
  `idTransaccion` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `categoriaId` int DEFAULT NULL,
  `usuarioId` int DEFAULT NULL,
  `cantidad` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`idTransaccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE transacciones MODIFY idTransaccion INT auto_increment;

CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL,
  `nombreCompleto` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `rol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE usuarios MODIFY idUsuario INT AUTO_INCREMENT;

INSERT INTO usuarios (nombreCompleto, password, correo, rol) 
VALUES ('Juan Pérez', '123456', 'juan@example.com', 'usuario');

SHOW DATABASES;