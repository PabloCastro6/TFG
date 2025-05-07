
DROP DATABASE IF EXISTS ahorro;
CREATE DATABASE ahorro DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE ahorro;

CREATE TABLE usuarios (
  idUsuario INT AUTO_INCREMENT PRIMARY KEY,
  nombreCompleto VARCHAR(45),
  password VARCHAR(45),
  correo VARCHAR(45),
  rol VARCHAR(45)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE categorias (
  idCategoria INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(45),
  tipo VARCHAR(45),
  usuarioId INT,
  FOREIGN KEY (usuarioId) REFERENCES usuarios(idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tipos_transaccion (
  idTipo INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  icono VARCHAR(100) NOT NULL,
  tipoCategoriaId INT NOT NULL,
  usuarioId INT NOT NULL,
  FOREIGN KEY (tipoCategoriaId) REFERENCES categorias(idCategoria),
  FOREIGN KEY (usuarioId) REFERENCES usuarios(idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE transacciones (
  idTransaccion INT AUTO_INCREMENT PRIMARY KEY,
  fecha DATE,
  categoriaId INT,
  usuarioId INT,
  cantidad DECIMAL(10,2),
  FOREIGN KEY (categoriaId) REFERENCES categorias(idCategoria) ON DELETE CASCADE,
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
INSERT INTO categorias (nombre, tipo, usuarioId) VALUES
('Gasto', 'Hacienda', 2);

-- Insertar transacciones
INSERT INTO transacciones (fecha, categoriaId, usuarioId, cantidad) VALUES
('2025-03-14', 1, 2, 20.00);

-- Consultas
SELECT * FROM usuarios;
SELECT * FROM transacciones;
SELECT * FROM categorias;
