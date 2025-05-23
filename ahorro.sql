DROP DATABASE IF EXISTS ahorro;
CREATE DATABASE ahorro DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE ahorro;

-- Tabla de usuarios
CREATE TABLE usuarios (
  idUsuario INT AUTO_INCREMENT PRIMARY KEY,
  nombreCompleto VARCHAR(45),
  password VARCHAR(45),
  correo VARCHAR(45),
  rol VARCHAR(45)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla de categorías (sin usuarioId, categorías generales)
CREATE TABLE categorias (
  idCategoria INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(45)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla de tipos de transacción
CREATE TABLE tipos_transaccion (
  idTipo INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  icono VARCHAR(100) NOT NULL,
  tipoCategoriaId INT NOT NULL,
  usuarioId INT NOT NULL,
  FOREIGN KEY (tipoCategoriaId) REFERENCES categorias(idCategoria),
  FOREIGN KEY (usuarioId) REFERENCES usuarios(idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla de transacciones
CREATE TABLE transacciones (
  idTransaccion INT AUTO_INCREMENT PRIMARY KEY,
  fecha DATE,
  categoriaId INT,
  usuarioId INT,
  cantidad DECIMAL(10,2),
  tipo VARCHAR(255),
  FOREIGN KEY (categoriaId) REFERENCES categorias(idCategoria) ON DELETE CASCADE,
  FOREIGN KEY (usuarioId) REFERENCES usuarios(idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla de recordatorios
CREATE TABLE recordatorios (
  idRecordatorio INT NOT NULL AUTO_INCREMENT,
  cantidad DOUBLE DEFAULT NULL,
  concepto VARCHAR(255) DEFAULT NULL,
  fecha DATE DEFAULT NULL,
  tipo VARCHAR(255) DEFAULT NULL,
  usuarioId INT DEFAULT NULL,
  PRIMARY KEY (idRecordatorio),
  FOREIGN KEY (usuarioId) REFERENCES usuarios(idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insertar usuarios
INSERT INTO usuarios (nombreCompleto, password, correo, rol) VALUES
('Gonzalo', '1234', 'Gonzlito@gmail.com', 'ADMINISTRADOR'),
('Hugo', '5678', 'Hugito@gmail.com', 'ADMINISTRADOR'),
('Pablo', '2468', 'Pablito@gmail.com', 'ADMINISTRADOR'),
('Juan Pérez', '123456', 'juan@example.com', 'USUARIO'),
('Pablo Castro', '1357', 'pablete@example.com', 'ADMINISTRADOR');

-- Insertar categorías
INSERT INTO categorias (idCategoria, nombre) VALUES
(1, 'Gasto'),
(2, 'Ingreso');

-- Insertar transacciones (ejemplo con usuarios y categorías válidas)
INSERT INTO transacciones (idTransaccion, fecha, categoriaId, usuarioId, cantidad, tipo) VALUES
(1, '2025-04-10', 2, 2, 1200, 'Trabajo');

-- Consultas para comprobar
SELECT * FROM usuarios;
SELECT * FROM categorias;
SELECT * FROM transacciones;
SELECT * FROM tipos_transaccion;
SELECT * FROM recordatorios;