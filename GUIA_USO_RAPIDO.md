# Guía de Uso Rápido

Inicio rápido para usar el sistema.

## Instalación Rápida (Windows)

1. Extraer `instaladores/ModuloProveedores-windows.zip`
2. Configurar MySQL:
   ```cmd
   mysql -u root -p < db/schema.sql
   ```
3. Compilar (opcional):
   ```cmd
   compilar.bat
   ```
4. Ejecutar: `ejecutar.bat`

## Tareas Comunes

### Agregar Proveedor
1. **"+ Nuevo Proveedor"**
2. Llenar nombre
3. **"Guardar"**

### Registrar Compra
1. Seleccionar proveedor
2. **"+ Nueva Compra"**
3. Llenar:
   - Factura
   - Categoría
   - Descripción
   - Total (ej: 1000000 → 1.000.000)
   - Fecha (dd/mm/aa)
   - Forma de pago
4. **"Guardar"**

### Marcar Pagado
1. Seleccionar compra roja
2. **"✓ Marcar Pagado"**
3. Ingresar fecha
4. Confirmar

### Buscar
- **Proveedores:** Escribir en barra superior izquierda
- **Compras:** Escribir en barra superior derecha
- **Filtros:** Usar combos de forma de pago, estado, fechas

### Limpiar Filtros
Clic en **"✕ Limpiar"**

## Formatos

- **Fechas:** dd/mm/aa (ej: 03/01/26)
- **Números:** Se formatean automáticamente (1.000.000,50)

## Colores

- 🔴 Rojo = Pendiente
- 🟢 Verde = Pagado
- 🟣 Morado = Proveedores

## Ayuda Completa

Ver **[MANUAL.md](MANUAL.md)** para documentación completa.
