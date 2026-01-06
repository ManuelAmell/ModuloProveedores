-- ============================================================================
-- SCRIPT DE OPTIMIZACIÓN: ÍNDICES DE BASE DE DATOS
-- ============================================================================
-- Este script crea índices para mejorar el rendimiento de consultas frecuentes
-- Ejecutar en MySQL después de crear las tablas principales
-- Mejora esperada: 4-6x más rápido en consultas con filtros
-- ============================================================================

USE gestion_proveedores;

-- Verificar índices existentes antes de crear
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    COLUMN_NAME,
    INDEX_TYPE
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = 'gestion_proveedores'
ORDER BY TABLE_NAME, INDEX_NAME;

-- ============================================================================
-- ÍNDICES PARA TABLA: compras
-- ============================================================================

-- Índice para búsquedas por proveedor (consulta más frecuente)
CREATE INDEX IF NOT EXISTS idx_compras_proveedor 
ON compras(id_proveedor);

-- Índice para filtros por fecha
CREATE INDEX IF NOT EXISTS idx_compras_fecha 
ON compras(fecha_compra);

-- Índice para filtros por forma de pago
CREATE INDEX IF NOT EXISTS idx_compras_forma_pago 
ON compras(forma_pago);

-- Índice para filtros por estado de crédito
CREATE INDEX IF NOT EXISTS idx_compras_estado 
ON compras(estado_credito);

-- Índice compuesto para filtros combinados (más eficiente)
-- Orden: id_proveedor primero (más selectivo), luego otros filtros
CREATE INDEX IF NOT EXISTS idx_compras_filtros 
ON compras(id_proveedor, forma_pago, estado_credito, fecha_compra);

-- Índice para búsquedas por número de factura (único)
CREATE INDEX IF NOT EXISTS idx_compras_numero_factura 
ON compras(numero_factura);

-- ============================================================================
-- ÍNDICES PARA TABLA: items_compra
-- ============================================================================

-- Índice para obtener items por compra (consulta más frecuente)
CREATE INDEX IF NOT EXISTS idx_items_compra 
ON items_compra(id_compra);

-- Índice compuesto para ordenar items por compra
CREATE INDEX IF NOT EXISTS idx_items_compra_orden 
ON items_compra(id_compra, orden);

-- ============================================================================
-- ÍNDICES PARA TABLA: proveedores
-- ============================================================================

-- Índice para filtrar proveedores activos/inactivos
CREATE INDEX IF NOT EXISTS idx_proveedores_activo 
ON proveedores(activo);

-- Índice para búsquedas por nombre
CREATE INDEX IF NOT EXISTS idx_proveedores_nombre 
ON proveedores(nombre);

-- Índice para filtros por tipo
CREATE INDEX IF NOT EXISTS idx_proveedores_tipo 
ON proveedores(tipo);

-- ============================================================================
-- VERIFICAR ÍNDICES CREADOS
-- ============================================================================

SHOW INDEX FROM compras;
SHOW INDEX FROM items_compra;
SHOW INDEX FROM proveedores;

-- ============================================================================
-- ANALIZAR RENDIMIENTO DE CONSULTAS (EXPLAIN)
-- ============================================================================

-- Consulta 1: Compras por proveedor (más frecuente)
EXPLAIN SELECT * FROM compras WHERE id_proveedor = 1;

-- Consulta 2: Compras con filtros combinados
EXPLAIN SELECT * FROM compras 
WHERE id_proveedor = 1 
  AND forma_pago = 'CREDITO' 
  AND estado_credito = 'PENDIENTE';

-- Consulta 3: Items por compra
EXPLAIN SELECT * FROM items_compra WHERE id_compra = 1 ORDER BY orden;

-- Consulta 4: Suma de cantidades (batch query)
EXPLAIN SELECT id_compra, SUM(cantidad) as total 
FROM items_compra 
WHERE id_compra IN (1, 2, 3, 4, 5) 
GROUP BY id_compra;

-- Consulta 5: Proveedores activos
EXPLAIN SELECT * FROM proveedores WHERE activo = 1;

-- ============================================================================
-- ESTADÍSTICAS DE TABLAS
-- ============================================================================

-- Analizar tablas para actualizar estadísticas
ANALYZE TABLE compras;
ANALYZE TABLE items_compra;
ANALYZE TABLE proveedores;

-- Optimizar tablas (desfragmentar)
OPTIMIZE TABLE compras;
OPTIMIZE TABLE items_compra;
OPTIMIZE TABLE proveedores;

-- ============================================================================
-- INFORMACIÓN DE RENDIMIENTO
-- ============================================================================

-- Tamaño de las tablas
SELECT 
    TABLE_NAME,
    ROUND(((DATA_LENGTH + INDEX_LENGTH) / 1024 / 1024), 2) AS 'Tamaño (MB)',
    TABLE_ROWS AS 'Filas'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'gestion_proveedores'
ORDER BY (DATA_LENGTH + INDEX_LENGTH) DESC;

-- ============================================================================
-- NOTAS DE OPTIMIZACIÓN
-- ============================================================================

/*
ÍNDICES CREADOS:
1. idx_compras_proveedor - Para filtrar por proveedor (más usado)
2. idx_compras_fecha - Para filtros por rango de fechas
3. idx_compras_forma_pago - Para filtrar por forma de pago
4. idx_compras_estado - Para filtrar créditos pendientes/pagados
5. idx_compras_filtros - Índice compuesto para filtros combinados
6. idx_compras_numero_factura - Para búsquedas por factura
7. idx_items_compra - Para obtener items de una compra
8. idx_items_compra_orden - Para ordenar items
9. idx_proveedores_activo - Para filtrar activos/inactivos
10. idx_proveedores_nombre - Para búsquedas por nombre
11. idx_proveedores_tipo - Para filtrar por tipo

MEJORAS ESPERADAS:
- Consultas por proveedor: 5-10x más rápido
- Filtros combinados: 4-6x más rápido
- Batch queries: 3-5x más rápido
- Búsquedas por nombre: 2-3x más rápido

MANTENIMIENTO:
- Ejecutar ANALYZE TABLE mensualmente
- Ejecutar OPTIMIZE TABLE trimestralmente
- Monitorear tamaño de índices con la consulta de información

CONSIDERACIONES:
- Los índices ocupan espacio adicional en disco
- Pueden ralentizar INSERT/UPDATE/DELETE (mínimo impacto)
- Beneficio mayor en tablas con >1000 registros
- MySQL usa automáticamente el índice más eficiente
*/

-- ============================================================================
-- FIN DEL SCRIPT
-- ============================================================================
