CREATE DATABASE `ahorro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use ahorro;
CREATE TABLE `categorias` (
  `idCategoria` int NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `usuarioId` int DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE recordatorios (
    idRecordatorio INT AUTO_INCREMENT PRIMARY KEY,
    concepto VARCHAR(255) NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(idUsuario)
);

SELECT * FROM usuarios;
SELECT * FROM transacciones;
SELECT * FROM categorias;
SELECT * FROM recordatorios;

SET SQL_SAFE_UPDATES = 0; 

UPDATE usuarios SET rol = 'USUARIO' WHERE rol = 'usuario';
UPDATE usuarios SET rol = 'ADMINISTRADOR' WHERE rol = 'administrador';


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
ALTER TABLE transacciones MODIFY idTransaccion INT AUTO_INCREMENT;
ALTER TABLE categorias MODIFY idCategoria INT AUTO_INCREMENT;

ALTER TABLE transacciones 
ADD CONSTRAINT FKtr2kyu22nmb2wax19i9girxsh 
FOREIGN KEY (categoriaId) REFERENCES categorias(idCategoria) 
ON DELETE CASCADE;

ALTER TABLE transacciones DROP FOREIGN KEY FKtr2kyu22nmb2wax19i9girxsh;


INSERT INTO usuarios (nombreCompleto, password, correo, rol) 
VALUES ('Juan Pérez', '123456', 'juan@example.com', 'usuario');

INSERT INTO categorias (nombre, tipo, usuarioId) 
VALUES ('Gasto', 'Hacienda', '2');

INSERT INTO transacciones (fecha, categoriaId, usuarioId, cantidad) 
VALUES ('2025-03-14', '1', '2', '20');

ALTER TABLE categorias AUTO_INCREMENT = 1;
ALTER TABLE transacciones AUTO_INCREMENT = 1;

DELETE FROM categorias WHERE idCategoria = 2;
DELETE FROM transacciones WHERE idTransaccion = 4;

SHOW DATABASES;