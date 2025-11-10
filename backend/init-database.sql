DROP DATABASE IF EXISTS loteriadb;
CREATE DATABASE loteriadb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE loteriadb;

-- Eliminar tablas si existen (en orden correcto por las FK)
DROP TABLE IF EXISTS billete;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS sorteo;

-- Crear tabla sorteo
CREATE TABLE sorteo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL
);

-- Crear tabla cliente
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE
);

-- Crear tabla billete
CREATE TABLE billete (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(255) NOT NULL,
    precio DECIMAL(19,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    sorteo_id BIGINT,
    cliente_id BIGINT,
    CONSTRAINT fk_billete_sorteo FOREIGN KEY (sorteo_id) REFERENCES sorteo(id),
    CONSTRAINT fk_billete_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    CONSTRAINT uk_billete_numero_sorteo UNIQUE (numero, sorteo_id)
);

-- ========================================
-- DATOS DE PRUEBA
-- ========================================

-- Insertar sorteos
INSERT INTO sorteo (nombre, fecha) VALUES 
('Sorteo de Navidad 2024', '2024-12-24'),
('Sorteo de Año Nuevo 2025', '2025-01-01'),
('Sorteo del Niño 2025', '2025-01-06');

-- Insertar clientes
INSERT INTO cliente (nombre, correo) VALUES 
('Juan Pérez', 'juan.perez@email.com'),
('María García', 'maria.garcia@email.com'),
('Carlos López', 'carlos.lopez@email.com'),
('Ana Martínez', 'ana.martinez@email.com'),
('Pedro Sánchez', 'pedro.sanchez@email.com');

-- Insertar billetes para Sorteo de Navidad (ID 1)
INSERT INTO billete (numero, precio, estado, sorteo_id, cliente_id) VALUES 
('00001', 20.00, 'VENDIDO', 1, 1),
('00002', 20.00, 'VENDIDO', 1, 1),
('00003', 20.00, 'VENDIDO', 1, 2),
('00004', 20.00, 'DISPONIBLE', 1, NULL),
('00005', 20.00, 'DISPONIBLE', 1, NULL),
('00123', 20.00, 'VENDIDO', 1, 3),
('00456', 20.00, 'DISPONIBLE', 1, NULL),
('00789', 20.00, 'VENDIDO', 1, 4),
('01234', 20.00, 'DISPONIBLE', 1, NULL),
('05678', 20.00, 'DISPONIBLE', 1, NULL);

-- Insertar billetes para Sorteo de Año Nuevo (ID 2)
INSERT INTO billete (numero, precio, estado, sorteo_id, cliente_id) VALUES 
('10001', 25.00, 'VENDIDO', 2, 2),
('10002', 25.00, 'DISPONIBLE', 2, NULL),
('10003', 25.00, 'VENDIDO', 2, 3),
('10004', 25.00, 'DISPONIBLE', 2, NULL),
('10005', 25.00, 'VENDIDO', 2, 5),
('20001', 25.00, 'DISPONIBLE', 2, NULL),
('20002', 25.00, 'DISPONIBLE', 2, NULL),
('30001', 25.00, 'VENDIDO', 2, 1),
('40001', 25.00, 'DISPONIBLE', 2, NULL),
('50001', 25.00, 'DISPONIBLE', 2, NULL);

-- Insertar billetes para Sorteo del Niño (ID 3)
INSERT INTO billete (numero, precio, estado, sorteo_id, cliente_id) VALUES 
('99001', 15.00, 'DISPONIBLE', 3, NULL),
('99002', 15.00, 'DISPONIBLE', 3, NULL),
('99003', 15.00, 'DISPONIBLE', 3, NULL),
('99004', 15.00, 'VENDIDO', 3, 4),
('99005', 15.00, 'DISPONIBLE', 3, NULL),
('99999', 15.00, 'VENDIDO', 3, 5),
('88888', 15.00, 'DISPONIBLE', 3, NULL),
('77777', 15.00, 'DISPONIBLE', 3, NULL),
('66666', 15.00, 'DISPONIBLE', 3, NULL),
('55555', 15.00, 'DISPONIBLE', 3, NULL);


