# Categorías Personalizadas

Sistema de categorías dinámicas para clasificar compras.

## Categorías Predefinidas

- alimentos
- bebidas
- limpieza
- oficina
- mantenimiento
- servicios
- otros

## Crear Nueva Categoría

1. En formulario de compra, seleccionar **"otros"**
2. Aparece campo de texto
3. Escribir nombre de categoría (ej: "herramientas", "pocillos")
4. La categoría se guarda automáticamente
5. Estará disponible en futuras compras

## Características

- ✅ Categorías se guardan en minúsculas
- ✅ Sin duplicados
- ✅ Disponibles para todos los proveedores
- ✅ Persistentes en base de datos

## Implementación

- Tabla: `categorias`
- Servicio: `CategoriaService.java`
- DAO: `CategoriaDAOMySQL.java`
