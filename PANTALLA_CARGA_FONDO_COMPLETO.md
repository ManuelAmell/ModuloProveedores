# Pantalla de Carga - Imagen de Fondo Completo ✅

## ✅ Cambio Implementado

**Diseño anterior:**
- Imagen pequeña (200x200) en la parte superior
- Frase en el centro
- Barra de progreso abajo

**Diseño nuevo:**
- ✅ **Imagen de fondo a pantalla completa** (600x400)
- ✅ **Overlay oscuro semi-transparente** (78% opacidad)
- ✅ **Texto superpuesto** con fondo semi-transparente
- ✅ **Barra de progreso** con borde azul brillante

## 🎨 Estructura Visual

```
┌────────────────────────────────────────┐
│                                        │
│     [IMAGEN DE FONDO COMPLETA]        │
│          (Escalada 600x400)           │
│                                        │
│         (Overlay oscuro 78%)          │
│                                        │
│  ┌──────────────────────────────┐     │
│  │  "Frase motivacional..."     │     │
│  │  (Fondo negro 59% opacidad)  │     │
│  └──────────────────────────────┘     │
│                                        │
│  ┌──────────────────────────────┐     │
│  │ [████████████░░░░] 75%       │     │
│  │ (Borde azul brillante)       │     │
│  └──────────────────────────────┘     │
│            v2.3.0                      │
└────────────────────────────────────────┘
```

## 🔧 Características Técnicas

### Imagen de Fondo
- **Ubicación**: `lib/logo_carga.png` (880K)
- **Escalado**: Automático para cubrir toda la ventana (600x400)
- **Interpolación**: Bilinear para mejor calidad
- **Antialiasing**: Activado

### Overlay Oscuro
- **Color**: RGB(25, 35, 55) - Azul oscuro
- **Opacidad**: 200/255 = ~78%
- **Propósito**: Mejorar legibilidad del texto

### Texto Superpuesto
- **Frase**:
  - Fuente: Segoe UI Bold, 18px
  - Color: Blanco
  - Fondo: Negro semi-transparente (150/255 = 59%)
  - Borde: Negro con 100/255 opacidad
  - Padding: 10px vertical, 20px horizontal

- **Barra de progreso**:
  - Altura: 30px
  - Color: Azul brillante (#0096FF)
  - Fondo: RGB(45, 58, 82) con 200/255 opacidad
  - Borde: Azul brillante, 2px
  - Fuente: Segoe UI Bold, 14px

- **Versión**:
  - Fuente: Segoe UI Bold, 14px
  - Color: Blanco

## 🎨 Personalización

### Cambiar Opacidad del Overlay

Edita `src/vista/PantallaCarga.java`, línea ~50:
```java
g2d.setColor(new Color(25, 35, 55, 200)); // 200 = ~78% opacidad
```

**Valores sugeridos:**
- `255` = 100% opaco (imagen no visible)
- `200` = 78% opaco (actual, recomendado)
- `150` = 59% opaco (imagen más visible)
- `100` = 39% opaco (imagen muy visible)
- `50` = 20% opaco (imagen casi sin filtro)

### Cambiar Opacidad del Fondo del Texto

Edita `src/vista/PantallaCarga.java`, línea ~75:
```java
lblFrase.setBackground(new Color(0, 0, 0, 150)); // 150 = 59% opacidad
```

### Cambiar Imagen de Fondo

Reemplaza `lib/logo_carga.png` con tu imagen:
```bash
cp tu_imagen.png lib/logo_carga.png
./compilar.sh
./ejecutar_optimizado.sh
```

**Recomendaciones:**
- Cualquier tamaño (se escala automáticamente)
- Resolución alta: 1920x1080 o mayor
- Formato: PNG o JPG
- Peso: Menos de 2 MB
- Contenido claro (se aplicará overlay oscuro)

## 🚀 Compilar y Ejecutar

```bash
# Compilar
./compilar.sh

# Ejecutar
./ejecutar_optimizado.sh

# O usar el test
./test_pantalla_carga.sh
```

## 📊 Comparación

### Antes
```
┌──────────────────┐
│   [Logo 200x200] │
├──────────────────┤
│     Frase        │
├──────────────────┤
│  [Progress Bar]  │
└──────────────────┘
```

### Ahora
```
┌──────────────────┐
│  [FONDO COMPLETO]│
│    (Imagen)      │
│   + Overlay      │
│                  │
│  [Texto encima]  │
│  [Progress Bar]  │
└──────────────────┘
```

## ✅ Ventajas del Nuevo Diseño

1. **Más profesional**: Imagen de fondo completa
2. **Mejor uso del espacio**: Aprovecha toda la pantalla
3. **Mayor impacto visual**: La imagen es protagonista
4. **Legibilidad garantizada**: Overlay y fondos semi-transparentes
5. **Flexible**: Funciona con cualquier imagen

## 🎯 Resultado

**Pantalla de carga moderna** con:
- ✅ Imagen de fondo a pantalla completa (880K)
- ✅ Overlay oscuro para legibilidad
- ✅ Texto superpuesto con estilo
- ✅ 50 frases económicas y motivacionales
- ✅ Barra de progreso con diseño moderno
- ✅ Transición suave a ventana principal

**¡Diseño completamente renovado!** 🎨
