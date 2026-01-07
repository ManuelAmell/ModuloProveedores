# Pantalla de Carga - Instrucciones de Personalización 🎨

## ✅ Implementación Completada

La pantalla de carga se muestra automáticamente al iniciar la aplicación.

## 📝 Personalizar Frases

### Archivo: `frases_carga.txt`

Edita este archivo para cambiar las frases que aparecen durante la carga.

**Formato:**
```
# Comentarios empiezan con #
# Una frase por línea

Tu frase personalizada aquí
Otra frase motivacional
Cargando datos importantes...
```

**Reglas:**
- Una frase por línea
- Las líneas que empiezan con `#` son comentarios (se ignoran)
- Las líneas vacías se ignoran
- Puedes agregar tantas frases como quieras
- Las frases se muestran aleatoriamente

**Ejemplo:**
```
# Frases personalizadas para mi empresa
Bienvenido a APROUD
Cargando tu sistema de gestión...
Preparando todo para ti...
Optimizando rendimiento...
Conectando con la base de datos...
Casi listo para empezar...
¡Listo para trabajar!
```

## 🖼️ Personalizar Imagen

### Archivo: `lib/logo_carga.png`

Coloca tu imagen en esta ubicación para que aparezca en la pantalla de carga.

**Especificaciones:**
- **Formato**: PNG (recomendado) o JPG
- **Tamaño recomendado**: 200x200 píxeles
- **Tamaño máximo**: 400x400 píxeles
- **Fondo**: Transparente (PNG) o del mismo color que la pantalla (#192337)

**Si no hay imagen:**
- Se muestra el texto "APROUD" en grande
- El texto es azul brillante (#0096FF)

## 🎨 Colores de la Pantalla

Los colores están definidos en `src/vista/PantallaCarga.java`:

```java
private static final Color BG_PRINCIPAL = new Color(25, 35, 55);    // Fondo azul oscuro
private static final Color TEXTO_PRINCIPAL = new Color(220, 220, 220); // Texto gris claro
private static final Color ACENTO = new Color(0, 150, 255);         // Azul brillante
```

Para cambiar los colores, edita estos valores en el archivo.

## ⚙️ Configuración Avanzada

### Duración de la Carga

En `src/vista/PantallaCarga.java`, método `simularCarga()`:

```java
// Cambiar el tiempo de cada paso (en milisegundos)
Thread.sleep(300);  // 300ms por paso (ajustar según necesidad)
```

### Número de Pasos

El número de pasos se ajusta automáticamente según las frases disponibles:
- Mínimo: 6 pasos
- Máximo: Número de frases en el archivo

## 📊 Estructura Visual

```
┌──────────────────────────────────────────┐
│                                          │
│            [IMAGEN/LOGO]                 │
│              200x200                     │
│                                          │
├──────────────────────────────────────────┤
│                                          │
│      Frase aleatoria aquí...            │
│                                          │
├──────────────────────────────────────────┤
│  [████████████████░░░░░░░░░░] 75%       │
│              v2.3.0                      │
└──────────────────────────────────────────┘
```

## 🚀 Cómo Funciona

1. **Al iniciar la aplicación** (`Main.java`):
   - Se crea la pantalla de carga
   - Se muestra inmediatamente
   - Se inicia la simulación de carga

2. **Durante la carga** (`PantallaCarga.java`):
   - Lee frases desde `frases_carga.txt`
   - Si no existe, usa frases por defecto
   - Muestra frases aleatoriamente
   - Actualiza la barra de progreso

3. **Al terminar**:
   - Cierra la pantalla de carga
   - Abre la ventana principal
   - La aplicación está lista para usar

## 📝 Ejemplo Completo

### 1. Crear tu archivo de frases

`frases_carga.txt`:
```
# Mi empresa - Frases personalizadas
Bienvenido a tu sistema de gestión
Cargando módulo de proveedores...
Preparando facturas y compras...
Optimizando base de datos...
Configurando tu espacio de trabajo...
¡Todo listo para empezar!
```

### 2. Agregar tu logo

Coloca tu imagen en: `lib/logo_carga.png`

### 3. Ejecutar

```bash
./compilar.sh
./ejecutar_optimizado.sh
```

## ✨ Resultado

- Pantalla de carga con tu logo
- Frases personalizadas
- Barra de progreso animada
- Transición suave a la ventana principal

## 🔧 Solución de Problemas

### Las frases no cambian
- Verifica que `frases_carga.txt` esté en la raíz del proyecto
- Asegúrate de que las líneas no empiecen con `#`
- Recompila y ejecuta de nuevo

### La imagen no aparece
- Verifica que el archivo esté en `lib/logo_carga.png`
- Verifica que sea PNG o JPG
- Verifica los permisos del archivo
- Si no aparece, se mostrará el texto "APROUD"

### La carga es muy rápida/lenta
- Edita `src/vista/PantallaCarga.java`
- Cambia el valor de `Thread.sleep(300)` en el método `simularCarga()`
- Recompila con `./compilar.sh`

## 📚 Archivos Relacionados

- `src/vista/PantallaCarga.java` - Código de la pantalla
- `src/Main.java` - Inicialización
- `frases_carga.txt` - Frases personalizadas
- `lib/logo_carga.png` - Imagen del logo
- `compilar.sh` - Script de compilación

## ✅ Valores por Defecto Optimizados para Rendimiento

Los valores por defecto de configuración ahora priorizan el rendimiento:

| Opción | Valor Anterior | Valor Nuevo | Razón |
|--------|----------------|-------------|-------|
| Antialiasing | ✅ ON | ❌ OFF | Ahorra ~5% CPU |
| Animaciones | ✅ ON | ❌ OFF | Ahorra CPU y memoria |
| Velocidad animación | 60 FPS | 30 FPS | Reduce uso de CPU |
| Intervalo reloj | 30s | 60s | Menos actualizaciones |
| Duración toast | 3s | 2s | Menos tiempo en pantalla |

**Para cambiar a modo UX (mejor experiencia visual):**
1. Abrir configuración (Ctrl+L)
2. Activar Antialiasing
3. Activar Animaciones
4. Cambiar velocidad a 60 FPS
5. Cambiar intervalo de reloj a 30s
6. Presionar "💾 Guardar y Aplicar"

¡Listo! 🎉
