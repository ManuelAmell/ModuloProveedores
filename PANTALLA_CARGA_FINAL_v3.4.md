# Pantalla de Carga - Versión Final 3.4

## ✅ Cambios Implementados

### 1. Tamaño de Ventana
- **Anterior**: 900x600 píxeles
- **Actual**: 1080x720 píxeles
- Ventana más grande para mejor visualización

### 2. Elementos Eliminados
- ❌ Logo "APROUD" (header)
- ❌ Subtítulo "Sistema de Gestión Empresarial"
- ❌ Barra de progreso visual
- ❌ Texto de estado ("Iniciando...", etc.)
- ❌ Versión y copyright (footer)

### 3. Elementos Visibles
- ✅ **Solo frases económicas** sobre la imagen de fondo
- ✅ Frase principal (tamaño 32px)
- ✅ Autor de la frase (tamaño 24px)
- ✅ Fondo negro semi-transparente (opacidad 180) para legibilidad
- ✅ Texto blanco para máximo contraste

### 4. Diseño Minimalista
```
┌─────────────────────────────────────┐
│                                     │
│         [Imagen de Fondo]           │
│                                     │
│     ┌─────────────────────┐         │
│     │  "Frase económica"  │         │
│     │   — Autor           │         │
│     └─────────────────────┘         │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

### 5. Tipografía Aumentada
- **Frase**: 32px (antes 26px) - Segoe UI Plain
- **Autor**: 24px (antes 20px) - Segoe UI Italic
- **Padding**: Aumentado proporcionalmente
- **Espaciado**: 20px entre frase y autor

### 6. Duración
- **Total**: 3 segundos
- **Pasos**: 6 × 500ms
- **Frases**: Cambian en cada paso (aleatorias)

### 7. Comportamiento
- Imagen de fondo sin overlay oscuro
- Frases centradas vertical y horizontalmente
- Transición suave al cerrar
- Ventana principal pasa a primer plano automáticamente

## 📁 Archivos Modificados

### `src/vista/PantallaCarga.java`
- Tamaño: 1080x720
- Eliminados: header, footer, barra de progreso
- Fuentes aumentadas: 32px y 24px
- Solo frases visibles sobre imagen

### `src/Main.java`
- Ya configurado con `toFront()` y `requestFocus()`
- Ventana principal pasa a primer plano después de carga

## 🎨 Características Visuales

### Fondo
- Imagen completa sin filtros ni degradados
- Escala automática a 1080x720

### Frases
- Fondo negro semi-transparente (RGB: 0,0,0,180)
- Texto blanco (RGB: 255,255,255)
- Bordes redondeados implícitos
- Padding generoso para legibilidad

### Animación
- Frases cambian cada 500ms
- 50 frases económicas disponibles
- Formato: "Frase" — Autor
- Separación automática de frase y autor

## 🚀 Compilar y Ejecutar

```bash
# Compilar
./compilar.sh

# Ejecutar
./ejecutar.sh

# Probar solo pantalla de carga
./test_pantalla_carga.sh
```

## 📝 Formato de Frases

Archivo: `frases_carga.txt`

```
"La riqueza de una nación se mide por la productividad de su gente." — Adam Smith
"El precio es lo que pagas; el valor es lo que recibes." — Warren Buffett
```

- Formato: `"Frase" — Autor`
- Separador: ` — ` (espacio-guión-espacio)
- 50 frases económicas incluidas

## ✨ Resultado Final

Pantalla de carga minimalista y profesional:
- Solo frases económicas visibles
- Imagen de fondo completa sin obstáculos
- Texto legible con fondo semi-transparente
- Tamaño grande (1080x720) para mejor impacto
- Duración de 3 segundos
- Transición suave a ventana principal

---

**Versión**: 3.4  
**Fecha**: 6 de enero de 2026  
**Estado**: ✅ Completado
