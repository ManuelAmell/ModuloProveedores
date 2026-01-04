# Solución de Categorías

Implementación del sistema de categorías personalizadas.

## Problema Original

Las categorías estaban hardcodeadas en el código, limitando la flexibilidad.

## Solución Implementada

### Base de Datos
```sql
CREATE TABLE categorias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL
);
```

### Componentes

1. **CategoriaService.java**
   - `agregarCategoria(String nombre)`
   - `obtenerCategorias()`
   - `existeCategoria(String nombre)`

2. **CategoriaDAOMySQL.java**
   - Implementación de acceso a datos
   - Validación de duplicados

3. **FormularioCompraDark.java**
   - Opción "otros" en combo
   - Campo de texto para nueva categoría
   - Guardado automático

## Características

- ✅ Categorías dinámicas
- ✅ Sin duplicados
- ✅ Persistentes en BD
- ✅ Fácil de usar
