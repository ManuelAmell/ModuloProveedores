-- ============================================================
-- SCRIPT DE ACTUALIZACIÓN DE BASE DE DATOS
-- ============================================================
-- 
-- DESCRIPCIÓN:
-- Este script actualiza la base de datos existente agregando
-- la tabla de compras sin afectar los datos de proveedores.
-- 
-- INSTRUCCIONES DE USO:
-- 1. Abre MySQL Workbench o conéctate a MySQL desde terminal
-- 2. Ejecuta este script
-- 3. La tabla de compras se creará automáticamente
-- 
-- DESDE TERMINAL:
-- mysql -u root -p gestion_proveedores < db/update_schema.sql
-- 
-- ============================================================

USE gestion_proveedores;

-- Crear la tabla de compras si no existe
CREATE TABLE IF NOT EXISTS compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor INT NOT NULL,
    numero_factura VARCHAR(100) NOT NULL,
    categoria ENUM('zapatos', 'pantalones', 'ropa', 'camisas', 'accesorios', 'otros') NOT NULL,
    descripcion TEXT NOT NULL,
    cantidad INT,
    precio_unitario DECIMAL(10, 2),
    total DECIMAL(10, 2) NOT NULL,
    fecha_compra DATE NOT NULL,
    forma_pago ENUM('efectivo', 'transferencia', 'credito') NOT NULL,
    estado_credito ENUM('pendiente', 'pagado') DEFAULT NULL,
    fecha_pago DATE DEFAULT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id) ON DELETE RESTRICT,
    
    CHECK (
        (forma_pago != 'credito' AND estado_credito IS NULL AND fecha_pago IS NULL)
        OR
        (forma_pago = 'credito' AND estado_credito IS NOT NULL)
    ),
    CHECK (
        (estado_credito = 'pendiente' AND fecha_pago IS NULL)
        OR
        (estado_credito = 'pagado' AND fecha_pago IS NOT NULL)
        OR
        (estado_credito IS NULL AND fecha_pago IS NULL)
    )
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Crear índices si no existen
CREATE INDEX IF NOT EXISTS idx_proveedor ON compras(id_proveedor);
CREATE INDEX IF NOT EXISTS idx_fecha_compra ON compras(fecha_compra);
CREATE INDEX IF NOT EXISTS idx_categoria ON compras(categoria);
CREATE INDEX IF NOT EXISTS idx_forma_pago ON compras(forma_pago);
CREATE INDEX IF NOT EXISTS idx_estado_credito ON compras(estado_credito);

-- Insertar datos de ejemplo solo si la tabla está vacía
INSERT INTO compras (id_proveedor, numero_factura, categoria, descripcion, cantidad, precio_unitario, total, fecha_compra, forma_pago, estado_credito, fecha_pago)
SELECT 1, 'FAC-001', 'zapatos', 'Zapatos deportivos Nike talla 42, color negro', 10, 150000, 1500000, '2025-01-02', 'efectivo', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM compras LIMIT 1);

INSERT INTO compras (id_proveedor, numero_factura, categoria, descripcion, cantidad, precio_unitario, total, fecha_compra, forma_pago, estado_credito, fecha_pago)
SELECT 1, 'FAC-002', 'camisas', 'Camisas polo manga corta, colores variados', 20, 45000, 900000, '2025-01-03', 'credito', 'pendiente', NULL
WHERE (SELECT COUNT(*) FROM compras) = 1;

INSERT INTO compras (id_proveedor, numero_factura, categoria, descripcion, cantidad, precio_unitario, total, fecha_compra, forma_pago, estado_credito, fecha_pago)
SELECT 2, 'FAC-003', 'pantalones', 'Pantalones jean clásico, tallas 28-34', 15, 80000, 1200000, '2024-12-28', 'transferencia', NULL, NULL
WHERE (SELECT COUNT(*) FROM compras) = 2;

INSERT INTO compras (id_proveedor, numero_factura, categoria, descripcion, cantidad, precio_unitario, total, fecha_compra, forma_pago, estado_credito, fecha_pago)
SELECT 2, 'FAC-004', 'accesorios', 'Cinturones de cuero genuino', 30, 25000, 750000, '2024-12-20', 'credito', 'pagado', '2025-01-02'
WHERE (SELECT COUNT(*) FROM compras) = 3;

SELECT 'Actualización completada exitosamente' AS Mensaje;
SELECT COUNT(*) as 'Total Proveedores' FROM proveedores;
SELECT COUNT(*) as 'Total Compras' FROM compras;
