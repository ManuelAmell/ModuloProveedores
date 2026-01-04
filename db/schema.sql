-- ============================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS Y TABLA
-- ============================================================
-- 
-- DESCRIPCIÓN:
-- Este script crea la base de datos para el sistema de gestión
-- de proveedores y la tabla principal 'proveedores'.
-- 
-- INSTRUCCIONES DE USO:
-- 1. Abre MySQL Workbench o conéctate a MySQL desde terminal
-- 2. Ejecuta este script completo
-- 3. La base de datos y tabla se crearán automáticamente
-- 
-- DESDE TERMINAL:
-- mysql -u root -p < db/schema.sql
-- 
-- ============================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS gestion_proveedores
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE gestion_proveedores;

-- Eliminar la tabla si existe (para poder recrearla)
DROP TABLE IF EXISTS proveedores;

-- Crear la tabla de proveedores
CREATE TABLE proveedores (
    -- ID: Clave primaria, autoincremental
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Nombre de la empresa proveedora
    nombre VARCHAR(200) NOT NULL,
    
    -- Número de Identificación Tributaria (OPCIONAL)
    nit VARCHAR(50) UNIQUE,
    
    -- Tipo de proveedor (ropa, calzado, insumos, varios)
    tipo VARCHAR(100),
    
    -- Dirección física del proveedor
    direccion VARCHAR(300),
    
    -- Teléfono de contacto
    telefono VARCHAR(50),
    
    -- Correo electrónico
    email VARCHAR(100),
    
    -- Persona de contacto
    persona_contacto VARCHAR(150),
    
    -- Información de pago (banco, cuenta, etc.)
    informacion_pago TEXT,
    
    -- Estado activo/inactivo
    activo TINYINT(1) DEFAULT 1,
    
    -- Fecha de creación del registro
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Fecha de última actualización
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Crear índices para mejorar el rendimiento de las búsquedas
-- Índice en el campo 'nombre' para búsquedas rápidas
CREATE INDEX idx_nombre ON proveedores(nombre);

-- Índice en el campo 'activo' para filtrar por estado
CREATE INDEX idx_activo ON proveedores(activo);

-- Insertar datos de ejemplo
INSERT INTO proveedores (nombre, nit, tipo, direccion, telefono, email, persona_contacto, informacion_pago, activo) VALUES
('Distribuidora ABC', '900123456-1', 'ropa', 'Calle 50 #30-25, Bogotá', '601-555-0100', 'ventas@distribuidoraabc.com', 'María García', 'Bancolombia - Cta 123456789', 1),
('Suministros del Norte', '800987654-2', 'calzado', 'Av. Libertador 150, Barranquilla', '605-555-0200', 'contacto@suministrosnorte.com', 'Carlos Rodríguez', 'Davivienda - Cta 987654321', 1),
('Importadora XYZ', NULL, 'insumos', 'Zona Franca, Cartagena', '605-555-0300', 'info@importadoraxyz.com', 'Ana Martínez', NULL, 0);

-- ============================================================
-- TABLA DE CATEGORÍAS PERSONALIZADAS
-- ============================================================

DROP TABLE IF EXISTS categorias_compra;

CREATE TABLE categorias_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    es_predefinida TINYINT(1) DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar categorías predefinidas (en minúsculas para consistencia)
INSERT INTO categorias_compra (nombre, es_predefinida) VALUES
('zapatos', 1),
('pantalones', 1),
('ropa', 1),
('camisas', 1),
('accesorios', 1),
('otros', 1);

-- ============================================================
-- TABLA DE COMPRAS
-- ============================================================

DROP TABLE IF EXISTS compras;

CREATE TABLE compras (
    -- ID: Clave primaria, autoincremental
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- ID del proveedor (relación con tabla proveedores)
    id_proveedor INT NOT NULL,
    
    -- Número de factura o referencia
    numero_factura VARCHAR(100) NOT NULL,
    
    -- Categoría del producto (ahora es texto libre)
    categoria VARCHAR(100) NOT NULL,
    
    -- Descripción detallada de lo comprado
    descripcion TEXT NOT NULL,
    
    -- Cantidad (opcional)
    cantidad INT,
    
    -- Precio unitario (opcional)
    precio_unitario DECIMAL(10, 2),
    
    -- Total de la compra (obligatorio)
    total DECIMAL(10, 2) NOT NULL,
    
    -- Fecha de compra
    fecha_compra DATE NOT NULL,
    
    -- Forma de pago
    forma_pago ENUM('efectivo', 'transferencia', 'credito') NOT NULL,
    
    -- Estado del crédito (solo aplica si forma_pago = 'credito')
    estado_credito ENUM('pendiente', 'pagado') DEFAULT NULL,
    
    -- Fecha de pago (obligatoria solo cuando estado_credito = 'pagado')
    fecha_pago DATE DEFAULT NULL,
    
    -- Fechas de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Clave foránea hacia proveedores
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id) ON DELETE RESTRICT,
    
    -- Restricciones de validación para crédito
    CHECK (
        -- Si forma_pago != 'credito', entonces estado_credito debe ser NULL
        (forma_pago != 'credito' AND estado_credito IS NULL)
        OR
        -- Si forma_pago = 'credito', entonces estado_credito no puede ser NULL
        (forma_pago = 'credito' AND estado_credito IS NOT NULL)
    ),
    CHECK (
        -- Si estado_credito = 'pendiente', fecha_pago debe ser NULL
        (estado_credito = 'pendiente' AND fecha_pago IS NULL)
        OR
        -- Si estado_credito = 'pagado', fecha_pago debe tener valor
        (estado_credito = 'pagado' AND fecha_pago IS NOT NULL)
        OR
        -- Si no es crédito, puede o no tener fecha de pago
        (estado_credito IS NULL)
    )
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para mejorar el rendimiento
CREATE INDEX idx_proveedor ON compras(id_proveedor);
CREATE INDEX idx_fecha_compra ON compras(fecha_compra);
CREATE INDEX idx_categoria ON compras(categoria);
CREATE INDEX idx_forma_pago ON compras(forma_pago);
CREATE INDEX idx_estado_credito ON compras(estado_credito);

-- Insertar datos de ejemplo
INSERT INTO compras (id_proveedor, numero_factura, categoria, descripcion, cantidad, precio_unitario, total, fecha_compra, forma_pago, estado_credito, fecha_pago) VALUES
(1, 'FAC-001', 'zapatos', 'Zapatos deportivos Nike talla 42, color negro', 10, 150000, 1500000, '2025-01-02', 'efectivo', NULL, '2025-01-02'),
(1, 'FAC-002', 'camisas', 'Camisas polo manga corta, colores variados', 20, 45000, 900000, '2025-01-03', 'credito', 'pendiente', NULL),
(2, 'FAC-003', 'pantalones', 'Pantalones jean clásico, tallas 28-34', 15, 80000, 1200000, '2024-12-28', 'transferencia', NULL, '2024-12-28'),
(2, 'FAC-004', 'accesorios', 'Cinturones de cuero genuino', 30, 25000, 750000, '2024-12-20', 'credito', 'pagado', '2025-01-02');

-- Mostrar mensaje de confirmación
SELECT 'Base de datos y tablas creadas exitosamente' AS Mensaje;

-- Mostrar los datos insertados
SELECT * FROM proveedores;
SELECT * FROM compras;
