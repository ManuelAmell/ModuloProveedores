# Guía de Pantalla de Carga ✅

## ✅ Estado Actual

**Todo está funcionando correctamente:**
- ✅ Pantalla de carga implementada
- ✅ Imagen cargada desde `lib/logo_carga.png` (880K)
- ✅ Frases personalizadas cargadas desde `frases_carga.txt` (50 frases)
- ✅ Barra de progreso animada
- ✅ Transición suave a ventana principal

## 🎨 Componentes Actuales

### 1. Imagen de Fondo
**Ubicación**: `lib/logo_carga.png`
- ✅ Archivo encontrado
- Tamaño: 880K
- **Se muestra a pantalla completa** como fondo
- Overlay oscuro semi-transparente (78% opacidad) para mejor legibilidad

**Alternativas** (si no existe logo_carga.png):
1. `lib/ModuloProveedores.png` (1.4 MB)
2. Fondo sólido azul oscuro (#192337)

### 2. Texto Superpuesto
- **Frase motivacional** centrada con fondo semi-transparente
- **Barra de progreso** en la parte inferior con borde azul
- **Versión** debajo de la barra

### 3. Frases Motivacionales
**Ubicación**: `frases_carga.txt`
- ✅ Archivo encontrado
- 50 frases económicas y motivacionales
- Se muestran aleatoriamente durante la carga

**Ejemplos de tus frases actuales:**
```
"La riqueza de una nación se mide por la productividad de su gente." — Adam Smith
"El precio es lo que pagas; el valor es lo que recibes." — Warren Buffett
"En economía, nada es gratis." — Milton Friedman
...
```

## 🚀 Cómo Usar

### Ejecutar Aplicación
```bash
./ejecutar_optimizado.sh
```

### Probar Pantalla de Carga
```bash
./test_pantalla_carga.sh
```

Este script verifica:
- ✓ Archivo de frases existe
- ✓ Imagen existe
- ✓ Compilación correcta
- ✓ Ejecuta la aplicación

## 📝 Personalizar

### Cambiar Frases

Edita `frases_carga.txt`:
```bash
nano frases_carga.txt
```

**Formato:**
```
# Comentarios empiezan con #
# Una frase por línea

Tu frase aquí
Otra frase motivacional
"Frase con comillas" — Autor
```

**Reglas:**
- Una frase por línea
- Líneas con `#` son comentarios (se ignoran)
- Líneas vacías se ignoran
- Puedes usar comillas y guiones
- Puedes agregar o eliminar frases

### Cambiar Imagen

Reemplaza `lib/logo_carga.png` con tu imagen:
```bash
cp tu_imagen.png lib/logo_carga.png
```

**Especificaciones recomendadas:**
- Formato: PNG o JPG
- Tamaño: Cualquier tamaño (se escala automáticamente)
- Resolución recomendada: 1920x1080 o mayor
- Peso: Menos de 2 MB
- Contenido: Imagen clara y legible (se aplicará overlay oscuro)

## 🔧 Configuración Avanzada

### Cambiar Duración de Carga

Edita `src/vista/PantallaCarga.java`, línea ~220:
```java
Thread.sleep(300);  // 300ms por paso
```

Valores sugeridos:
- 200ms = Carga rápida
- 300ms = Carga normal (actual)
- 500ms = Carga lenta

### Cambiar Número de Pasos

El número de pasos se ajusta automáticamente:
- Mínimo: 6 pasos
- Máximo: Número de frases disponibles

### Cambiar Colores

Edita `src/vista/PantallaCarga.java`, líneas 18-20:
```java
private static final Color BG_PRINCIPAL = new Color(25, 35, 55);    // Fondo
private static final Color TEXTO_PRINCIPAL = new Color(220, 220, 220); // Texto
private static final Color ACENTO = new Color(0, 150, 255);         // Borde y barra
```

## 📊 Estructura Visual

```
┌────────────────────────────────────────┐
│                                        │
│     [IMAGEN DE FONDO COMPLETA]        │
│                                        │
│         (Overlay oscuro 78%)          │
│                                        │
│  ┌──────────────────────────────┐     │
│  │  "Frase motivacional..."     │     │
│  │  (Texto superpuesto)         │     │
│  └──────────────────────────────┘     │
│                                        │
│  [████████████████░░░░░░] 75%         │
│            v2.3.0                      │
└────────────────────────────────────────┘
```

**Características del diseño:**
- ✅ Imagen de fondo a pantalla completa
- ✅ Overlay oscuro semi-transparente (78% opacidad)
- ✅ Texto superpuesto con fondo semi-transparente
- ✅ Barra de progreso con borde azul brillante
- ✅ Versión en la parte inferior

## 🐛 Solución de Problemas

### La pantalla de carga no aparece
```bash
# Verificar compilación
./compilar.sh

# Verificar que PantallaCarga.class existe
ls -la bin/vista/PantallaCarga.class

# Ejecutar con test
./test_pantalla_carga.sh
```

### Las frases no cambian
```bash
# Verificar que el archivo existe
cat frases_carga.txt | grep -v "^#" | grep -v "^$" | wc -l

# Debe mostrar el número de frases (actualmente 50)
```

### La imagen no aparece
```bash
# Verificar que la imagen existe
ls -lh lib/logo_carga.png

# Si no existe, se mostrará texto "APROUD"
```

### Modo debug
Para ver mensajes de depuración:
1. Abrir configuración (Ctrl+L)
2. Activar "Modo debug"
3. Guardar y aplicar
4. Reiniciar aplicación
5. Ver consola para mensajes como:
   - "✓ Frases cargadas desde frases_carga.txt (50 frases)"
   - "✓ Imagen cargada desde: lib/logo_carga.png"

## ✅ Verificación Rápida

```bash
# Ejecutar test completo
./test_pantalla_carga.sh
```

**Salida esperada:**
```
==========================================
  PRUEBA DE PANTALLA DE CARGA
==========================================

✓ Archivo frases_carga.txt encontrado
  → 50 frases disponibles

✓ Imagen lib/logo_carga.png encontrada
  → Tamaño: 880K

✓ PantallaCarga.class compilado

==========================================
  TODO LISTO - Ejecutando aplicación...
==========================================
```

## 📚 Archivos Relacionados

- `src/vista/PantallaCarga.java` - Código de la pantalla
- `src/Main.java` - Inicialización
- `frases_carga.txt` - Frases personalizadas (50 frases)
- `lib/logo_carga.png` - Imagen del logo (880K)
- `test_pantalla_carga.sh` - Script de prueba
- `compilar.sh` - Script de compilación

## 🎉 Resultado

**Pantalla de carga profesional** con:
- ✅ Logo personalizado (880K)
- ✅ 50 frases económicas y motivacionales
- ✅ Barra de progreso animada
- ✅ Diseño consistente con tema oscuro azul
- ✅ Transición suave a ventana principal
- ✅ Carga en ~1.8 segundos (6 pasos × 300ms)

**Todo funcionando correctamente** 🚀
