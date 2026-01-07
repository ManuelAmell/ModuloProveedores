# Optimizaciones de Memoria Agresivas

## 🔴 Problema Detectado

**Consumo actual**:
- VSZ (Virtual Size): **5726 MB** ⚠️ MUY ALTO
- RSS (Resident Set): 143 MB (aceptable)
- CPU: 9-13% (normal para Swing)

**Causa**: Java reserva mucha memoria virtual por defecto (heap + metaspace + threads + buffers)

**Objetivo**: Reducir VSZ de 5726 MB a **~500-800 MB** sin perder funcionalidad

---

## 🎯 Optimizaciones a Implementar

### 1. Limitar Heap de Java (CRÍTICO) ⭐⭐⭐

**Problema**: Java reserva heap por defecto basado en RAM del sistema (puede ser 1-4 GB)

**Solución**: Limitar heap a lo necesario

```bash
# Antes (sin límites)
java -cp ... Main

# Después (con límites optimizados)
java -Xms32m -Xmx128m -XX:MaxMetaspaceSize=64m -cp ... Main
```

**Parámetros**:
- `-Xms32m` - Heap inicial: 32 MB (arranca rápido)
- `-Xmx128m` - Heap máximo: 128 MB (suficiente para la app)
- `-XX:MaxMetaspaceSize=64m` - Metaspace máximo: 64 MB (clases)

**Reducción esperada**: 5726 MB → **~500 MB** (90% menos)

---

### 2. Optimizar Garbage Collector ⭐⭐

**Problema**: GC por defecto (G1GC) usa mucha memoria para buffers

**Solución**: Usar Serial GC (más ligero para apps pequeñas)

```bash
java -Xms32m -Xmx128m -XX:+UseSerialGC -cp ... Main
```

**Beneficio**:
- Menos threads de GC
- Menos buffers internos
- Más predecible en apps pequeñas

**Reducción adicional**: ~50-100 MB

---

### 3. Reducir Stack Size de Threads ⭐

**Problema**: Cada thread reserva 1 MB de stack por defecto

**Solución**: Reducir a 256 KB (suficiente para Swing)

```bash
java -Xms32m -Xmx128m -Xss256k -cp ... Main
```

**Cálculo**:
- Threads típicos en Swing: ~20-30
- Ahorro: 30 threads × 768 KB = ~23 MB

---

### 4. Desactivar Compilación JIT Agresiva ⭐

**Problema**: JIT compiler reserva memoria para código compilado

**Solución**: Limitar niveles de compilación

```bash
java -Xms32m -Xmx128m -XX:TieredStopAtLevel=1 -cp ... Main
```

**Beneficio**:
- Menos código compilado en memoria
- Startup más rápido
- Ahorro: ~30-50 MB

---

### 5. Comprimir Punteros de Objetos ⭐⭐

**Problema**: Punteros de 64 bits usan más memoria

**Solución**: Activar compressed oops (automático con heap < 32 GB)

```bash
java -Xms32m -Xmx128m -XX:+UseCompressedOops -cp ... Main
```

**Beneficio**: ~20-30% menos memoria para objetos

---

### 6. Optimizar Conexiones de Base de Datos ⭐⭐

**Problema**: Cada conexión MySQL usa ~2-5 MB

**Solución**: Reutilizar conexión singleton (ya implementado, pero mejorar)

```java
// En ConexionBD.java - Agregar configuración de memoria
private Connection crearConexion() throws SQLException {
    Properties props = new Properties();
    props.setProperty("user", USUARIO);
    props.setProperty("password", CONTRASENA);
    
    // Optimizaciones de memoria
    props.setProperty("useServerPrepStmts", "false"); // No cachear en servidor
    props.setProperty("cachePrepStmts", "false"); // No cachear localmente
    props.setProperty("cacheResultSetMetadata", "false"); // No cachear metadata
    props.setProperty("cacheServerConfiguration", "true"); // Cachear config (pequeño)
    props.setProperty("elideSetAutoCommits", "true"); // Reducir comandos
    props.setProperty("useLocalSessionState", "true"); // Estado local
    
    return DriverManager.getConnection(URL, props);
}
```

**Ahorro**: ~10-20 MB por conexión

---

### 7. Optimizar Swing Components ⭐

**Problema**: Swing crea muchos buffers para rendering

**Solución**: Desactivar double buffering donde no es necesario

```java
// En VentanaUnificada.java - Constructor
public VentanaUnificada() {
    // ... código existente ...
    
    // Optimizar rendering
    RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
    
    // Reducir buffers de imágenes
    System.setProperty("sun.java2d.pmoffscreen", "false");
}
```

**Ahorro**: ~20-30 MB

---

### 8. Lazy Loading de Componentes ⭐⭐

**Problema**: Todos los componentes se crean al inicio

**Solución**: Crear formularios solo cuando se necesitan

```java
// En VentanaUnificada.java
private void nuevaCompra() {
    if (proveedorActual == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un proveedor primero");
        return;
    }
    
    // Crear formulario solo cuando se necesita (no al inicio)
    FormularioCompraDarkConItems formulario = 
        new FormularioCompraDarkConItems(this, null, proveedorActual);
    formulario.setVisible(true);
    
    // Liberar memoria después de cerrar
    formulario.dispose();
    formulario = null;
    System.gc(); // Sugerir GC (opcional)
    
    cargarComprasProveedor();
}
```

**Ahorro**: ~10-15 MB por formulario no usado

---

### 9. Limitar Tamaño de Caché ⭐

**Problema**: Caché puede crecer indefinidamente

**Solución**: Ya implementado con límite de 100, pero reducir a 50

```java
// En CompraService.java
private final Map<Integer, Integer> cacheCantidades = 
    Collections.synchronizedMap(new LinkedHashMap<Integer, Integer>(50, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > 50; // Reducir de 100 a 50
        }
    });
```

**Ahorro**: ~5-10 MB

---

### 10. Optimizar Strings ⭐

**Problema**: Strings duplicados ocupan memoria

**Solución**: Usar String.intern() para strings repetidos

```java
// En mapearResultSet de DAOs
private Compra mapearResultSet(ResultSet rs) throws SQLException {
    Compra compra = new Compra();
    // ... otros campos ...
    
    // Internar strings repetidos (categorías, formas de pago)
    String categoria = rs.getString("categoria");
    if (categoria != null) {
        compra.setCategoria(categoria.intern()); // Reutiliza strings iguales
    }
    
    return compra;
}
```

**Ahorro**: ~5-10 MB con muchos registros

---

## 📝 Script Optimizado de Ejecución

### Crear: `ejecutar_optimizado.sh`

```bash
#!/bin/bash

echo "=========================================="
echo "  SISTEMA DE GESTIÓN v2.5 (OPTIMIZADO)"
echo "=========================================="
echo ""
echo "Iniciando con configuración de memoria optimizada..."

# Parámetros de memoria optimizados
JAVA_OPTS="-Xms32m"                      # Heap inicial: 32 MB
JAVA_OPTS="$JAVA_OPTS -Xmx128m"          # Heap máximo: 128 MB
JAVA_OPTS="$JAVA_OPTS -XX:MaxMetaspaceSize=64m"  # Metaspace: 64 MB
JAVA_OPTS="$JAVA_OPTS -Xss256k"          # Stack por thread: 256 KB
JAVA_OPTS="$JAVA_OPTS -XX:+UseSerialGC"  # GC ligero
JAVA_OPTS="$JAVA_OPTS -XX:TieredStopAtLevel=1"  # Compilación básica
JAVA_OPTS="$JAVA_OPTS -XX:+UseCompressedOops"   # Comprimir punteros
JAVA_OPTS="$JAVA_OPTS -XX:+UseStringDeduplication"  # Deduplicar strings

# Optimizaciones adicionales
JAVA_OPTS="$JAVA_OPTS -Dsun.java2d.pmoffscreen=false"  # Reducir buffers gráficos
JAVA_OPTS="$JAVA_OPTS -Dswing.bufferPerWindow=false"   # Sin buffer por ventana

# Classpath
CLASSPATH="bin:lib/mysql-connector-j-8.0.33.jar"

# Ejecutar
java $JAVA_OPTS -cp "$CLASSPATH" Main

echo ""
echo "Aplicación finalizada."
```

**Hacer ejecutable**:
```bash
chmod +x ejecutar_optimizado.sh
```

---

## 📊 Comparación de Consumo

| Configuración | VSZ | RSS | Heap | Metaspace | Total Estimado |
|---------------|-----|-----|------|-----------|----------------|
| **Actual (sin límites)** | 5726 MB | 143 MB | ~2048 MB | ~256 MB | ~5726 MB |
| **Optimizado Moderado** | ~800 MB | ~100 MB | 128 MB | 64 MB | ~800 MB |
| **Optimizado Agresivo** | ~500 MB | ~80 MB | 96 MB | 48 MB | ~500 MB |

**Reducción**: 5726 MB → 500-800 MB = **85-90% menos memoria**

---

## 🚀 Plan de Implementación

### Fase 1: Optimizaciones Inmediatas (5 minutos)

1. ✅ Crear `ejecutar_optimizado.sh` con parámetros JVM
2. ✅ Probar ejecución y verificar funcionamiento
3. ✅ Medir consumo con `top` o `htop`

### Fase 2: Optimizaciones de Código (30 minutos)

1. ✅ Optimizar `ConexionBD.java` (propiedades MySQL)
2. ✅ Reducir caché de 100 a 50 en `CompraService.java`
3. ✅ Agregar `dispose()` y `null` en formularios
4. ✅ Desactivar double buffering en `VentanaUnificada.java`

### Fase 3: Optimizaciones Avanzadas (1 hora)

1. ✅ Implementar String.intern() en DAOs
2. ✅ Lazy loading de componentes pesados
3. ✅ Profiling con VisualVM para identificar memory leaks

---

## 🔧 Comandos de Monitoreo

### Verificar consumo en tiempo real:
```bash
# Opción 1: top
top -p $(pgrep -f "java.*Main")

# Opción 2: htop (más visual)
htop -p $(pgrep -f "java.*Main")

# Opción 3: Script de monitoreo
./monitorear.sh
```

### Ver detalles de memoria Java:
```bash
# Mientras la app está corriendo
jps  # Obtener PID
jstat -gc <PID> 1000  # Estadísticas de GC cada 1 segundo
jmap -heap <PID>  # Ver configuración de heap
```

---

## ⚠️ Consideraciones

### Límites Seguros

**Heap mínimo recomendado**: 96 MB
- Con 128 MB: Cómodo para operaciones normales
- Con 96 MB: Funciona pero puede tener GC frecuente
- Con 64 MB: Muy ajustado, puede dar OutOfMemoryError

**Si aparece OutOfMemoryError**:
```bash
# Aumentar heap gradualmente
-Xmx128m  # Probar primero
-Xmx192m  # Si sigue fallando
-Xmx256m  # Máximo recomendado
```

### Funcionalidades que NO se pierden

✅ Todas las funciones actuales se mantienen
✅ Rendimiento igual o mejor
✅ Estabilidad igual o mejor
✅ Solo cambia el consumo de memoria

### Funcionalidades que pueden verse afectadas

⚠️ Con heap muy bajo (< 96 MB):
- Carga de muchas facturas (>1000) puede ser lenta
- Importación masiva de datos puede fallar
- Generación de reportes grandes puede fallar

**Solución**: Usar `-Xmx192m` si trabajas con muchos datos

---

## 📈 Métricas Esperadas

### Antes (sin optimizaciones)
```
VSZ: 5726 MB
RSS: 143 MB
Heap usado: ~80 MB
Heap reservado: ~2048 MB
Metaspace: ~256 MB
Threads: ~30
```

### Después (optimizado moderado)
```
VSZ: 800 MB (-85%)
RSS: 100 MB (-30%)
Heap usado: ~70 MB
Heap reservado: 128 MB (-94%)
Metaspace: 64 MB (-75%)
Threads: ~25
```

### Después (optimizado agresivo)
```
VSZ: 500 MB (-91%)
RSS: 80 MB (-44%)
Heap usado: ~60 MB
Heap reservado: 96 MB (-95%)
Metaspace: 48 MB (-81%)
Threads: ~20
```

---

## 🎯 Recomendación Final

### Para tu caso (7.2 GB RAM total):

**Configuración recomendada**:
```bash
java -Xms32m -Xmx128m -XX:MaxMetaspaceSize=64m \
     -Xss256k -XX:+UseSerialGC \
     -XX:TieredStopAtLevel=1 \
     -cp "bin:lib/mysql-connector-j-8.0.33.jar" Main
```

**Resultado esperado**:
- VSZ: ~600-800 MB (vs 5726 MB actual)
- RSS: ~90-110 MB (vs 143 MB actual)
- **Reducción total: 85-90%**

---

## 📝 Checklist de Implementación

- [ ] Crear `ejecutar_optimizado.sh`
- [ ] Probar ejecución básica
- [ ] Verificar todas las funciones
- [ ] Medir consumo con `top`
- [ ] Optimizar `ConexionBD.java`
- [ ] Reducir caché a 50 entradas
- [ ] Agregar `dispose()` en formularios
- [ ] Desactivar double buffering
- [ ] Implementar String.intern() (opcional)
- [ ] Documentar en README.md
- [ ] Commit y push

---

## 🔗 Referencias

- [Java Memory Management](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/)
- [JVM Options](https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html)
- [Swing Performance](https://docs.oracle.com/javase/tutorial/uiswing/misc/perf.html)

---

**Versión**: 2.5.1  
**Fecha**: 05/01/2026  
**Objetivo**: Reducir consumo de memoria en 85-90% sin perder funcionalidad
