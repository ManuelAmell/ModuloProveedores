# Panel de Configuración v3.2 - Aplicación con Botón Guardar 💾

## ✅ Implementación Completa

### Acceso
- **Botón engranaje (⚙)** en esquina superior derecha
- **Atajo de teclado**: `Ctrl+L`
- **Ubicación**: Junto al reloj en la barra superior

### 🎯 Funcionamiento
1. **Ajustar opciones** - Cambia las configuraciones que desees
2. **Presionar "💾 Guardar y Aplicar"** - Los cambios se guardan y aplican
3. **Toast de confirmación** - "Configuración guardada correctamente" ✓
4. **Cambios aplicados** - El reloj y otras opciones se actualizan inmediatamente

#### Cambios que se aplican al guardar:
- ✅ **Intervalo del reloj** - Se reinicia automáticamente con el nuevo intervalo
- ✅ **Mostrar segundos** - El formato cambia inmediatamente
- ✅ **Duración de toast** - Las próximas notificaciones usan la nueva duración
- ✅ **Activar/desactivar toast** - Se aplica inmediatamente
- ✅ **Velocidad de animación** - Los nuevos toggles usan la velocidad configurada
- ✅ **Todas las demás opciones** - Se guardan y aplican

### Opciones Implementadas

#### 🚀 Rendimiento
1. **Antialiasing de texto**
   - Texto más suave pero usa ~5% más CPU
   - Por defecto: ✅ Activado

2. **Animaciones suaves**
   - Activar/desactivar animaciones en la interfaz
   - Por defecto: ✅ Activado

3. **Velocidad de animación**
   - Lenta (30 FPS) - 33ms
   - **Normal (60 FPS) - 16ms** ⭐ Por defecto
   - Rápida (120 FPS) - 8ms

4. **Double buffering**
   - Reduce parpadeo pero usa más memoria
   - Por defecto: ❌ Desactivado

5. **Pausar cuando minimizado**
   - Ahorra CPU cuando la ventana está minimizada
   - Por defecto: ✅ Activado

6. **Notificaciones toast**
   - Mostrar notificaciones temporales
   - Por defecto: ✅ Activado

#### 🎨 Interfaz
1. **Tamaño de fuente**
   - Pequeño / **Normal** / Grande
   - Por defecto: Normal

2. **Actualización del reloj**
   - Cada 10 segundos (muy rápido)
   - **Cada 30 segundos (recomendado)** ⭐ Por defecto
   - Cada 60 segundos (ahorro máximo)

3. **Mostrar segundos en el reloj**
   - Formato HH:mm:ss (usa más CPU)
   - Por defecto: ❌ Desactivado

4. **Duración notificaciones**
   - 2 segundos (rápido)
   - **3 segundos (normal)** ⭐ Por defecto
   - 5 segundos (lento)

5. **Sonidos de notificación**
   - Reproducir sonido al mostrar notificaciones
   - Por defecto: ❌ Desactivado

6. **Confirmar antes de eliminar**
   - Pedir confirmación antes de eliminar registros
   - Por defecto: ✅ Activado

#### 🔧 Avanzado
1. **Modo debug**
   - Mostrar información de depuración en consola
   - Por defecto: ❌ Desactivado

2. **Botón "Restaurar valores por defecto"**
   - Restaura todas las opciones a sus valores iniciales

### Persistencia
- Todas las preferencias se guardan usando `java.util.prefs.Preferences`
- Las configuraciones persisten entre sesiones
- Algunos cambios requieren reiniciar la aplicación

### Métodos Estáticos Disponibles
```java
// Acceso desde otras clases
DialogoConfiguracion.isAntialiasingEnabled()
DialogoConfiguracion.isAnimacionesEnabled()
DialogoConfiguracion.isToastEnabled()
DialogoConfiguracion.getIntervaloReloj()
DialogoConfiguracion.isDebugMode()
DialogoConfiguracion.isDoubleBufferingEnabled()
DialogoConfiguracion.isPausarMinimizadoEnabled()
DialogoConfiguracion.isSonidosEnabled()
DialogoConfiguracion.isConfirmacionesEnabled()
DialogoConfiguracion.isRelojSegundosEnabled()
DialogoConfiguracion.getIntervaloAnimacion()
DialogoConfiguracion.getDuracionToast()
DialogoConfiguracion.getTamañoFuente()
```

## 🎯 Cambios Realizados

### 1. DialogoConfiguracion.java
- ✅ Agregadas todas las opciones de configuración
- ✅ **Botón "💾 Guardar y Aplicar"** destacado y más grande
- ✅ Método `guardarYCerrar()` guarda y aplica todos los cambios
- ✅ Métodos estáticos para acceder a preferencias
- ✅ Interfaz visual completa con 3 secciones
- ✅ **Mensaje informativo**: "Ajusta estas opciones y presiona 'Guardar' para aplicar los cambios"

### 2. VentanaUnificada.java
- ✅ Método `reiniciarReloj()` público para actualizar intervalo
- ✅ Método `actualizarFormatoReloj()` público para cambiar formato
- ✅ `iniciarReloj()` usa configuración dinámica
- ✅ `actualizarHora()` usa formato con/sin segundos según config
- ✅ Pausar minimizado respeta configuración

### 3. ToastNotification.java
- ✅ Duración dinámica desde `DialogoConfiguracion.getDuracionToast()`
- ✅ Verifica si toast está habilitado antes de mostrar
- ✅ Se aplica al guardar configuración

### 4. ToggleSwitch.java
- ✅ Velocidad de animación desde `DialogoConfiguracion.getIntervaloAnimacion()`
- ✅ Soporta 30/60/120 FPS según configuración
- ✅ Nuevos toggles usan la velocidad configurada

## 💾 Flujo de Aplicación

### Al presionar "Guardar y Aplicar":
1. **Guarda todas las preferencias** en `java.util.prefs.Preferences`
2. **Llama a métodos de actualización** en VentanaUnificada:
   - `reiniciarReloj()` - Actualiza intervalo del reloj
   - `actualizarFormatoReloj()` - Actualiza formato con/sin segundos
3. **Muestra toast de confirmación** - "Configuración guardada correctamente" ✓
4. **Cierra el diálogo**

### Cambios que se aplican inmediatamente:
- ✅ Intervalo del reloj
- ✅ Formato del reloj (con/sin segundos)
- ✅ Duración de notificaciones toast
- ✅ Activar/desactivar toast
- ✅ Velocidad de animaciones
- ✅ Todas las demás opciones

## 🎨 Experiencia de Usuario
- **Botón destacado** con icono 💾 y texto claro
- **Mensaje informativo** en la parte superior
- **Feedback inmediato** al guardar con toast
- **Sin reinicios necesarios** para la mayoría de cambios
- **Configuración persistente** entre sesiones

## 🎨 Diseño Visual
- **Tema oscuro azul** consistente con el resto de la aplicación
- **Secciones organizadas**: Rendimiento, Interfaz, Avanzado
- **Tooltips informativos** en cada opción
- **Botones de acción**: Cancelar y Guardar
- **Scroll moderno** para navegación fluida

## 📊 Valores por Defecto Optimizados
- Antialiasing: ✅ ON (mejor legibilidad)
- Animaciones: ✅ ON a 60 FPS (fluidez óptima)
- Toast notifications: ✅ ON (feedback visual)
- Reloj: 30 segundos (balance CPU/UX)
- Double buffering: ❌ OFF (ahorro de memoria)
- Pausar minimizado: ✅ ON (ahorro de CPU)
- Confirmaciones: ✅ ON (seguridad)

## ✅ Estado
**COMPLETADO CON BOTÓN GUARDAR** 💾

Todas las opciones se aplican al presionar el botón "💾 Guardar y Aplicar".

## 🚀 Cómo Usar
1. Ejecutar la aplicación: `./ejecutar_optimizado.sh`
2. Presionar `Ctrl+L` o clic en botón ⚙
3. **Ajustar las opciones** que desees cambiar
4. **Presionar "💾 Guardar y Aplicar"** → Los cambios se aplican inmediatamente
5. Toast de confirmación: "Configuración guardada correctamente" ✓
6. ¡Listo! Los cambios ya están activos

## 🎯 Ejemplo de Uso
```
1. Abrir configuración (Ctrl+L)
2. Cambiar "Intervalo del reloj" a "Cada 10 segundos"
3. Activar "Mostrar segundos en el reloj"
4. Cambiar "Duración notificaciones" a "5 segundos"
5. Presionar "💾 Guardar y Aplicar"
   → El reloj se actualiza inmediatamente cada 10s con segundos
   → Las notificaciones duran 5s
   → Toast: "Configuración guardada correctamente" ✓
6. Cerrar diálogo
```
