-- Script para actualizar la base de datos con los nuevos campos
USE gestion_proveedores;

-- Hacer el NIT opcional y agregar nuevos campos
ALTER TABLE proveedores 
  MODIFY COLUMN nit VARCHAR(50) UNIQUE;

-- Agregar campo tipo si no existe
ALTER TABLE proveedores 
  ADD COLUMN IF NOT EXISTS tipo VARCHAR(100);

-- Agregar campo informacion_pago si no existe
ALTER TABLE proveedores 
  ADD COLUMN IF NOT EXISTS informacion_pago TEXT;

-- Actualizar datos de ejemplo con los nuevos campos
UPDATE proveedores SET tipo = 'ropa', informacion_pago = 'Bancolombia - Cta 123456789' WHERE id = 1;
UPDATE proveedores SET tipo = 'calzado', informacion_pago = 'Davivienda - Cta 987654321' WHERE id = 2;
UPDATE proveedores SET tipo = 'insumos', informacion_pago = NULL WHERE id = 3;

SELECT 'Base de datos actualizada exitosamente' AS Mensaje;
SELECT * FROM proveedores;
