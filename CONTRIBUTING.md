# Contribuir al Proyecto

¡Gracias por tu interés en contribuir! Este documento proporciona pautas para contribuir al proyecto.

## 🚀 Cómo Contribuir

### 1. Fork del Repositorio
```bash
# Hacer fork en GitHub y clonar
git clone https://github.com/tu-usuario/ModuloProveedores.git
cd ModuloProveedores
```

### 2. Crear una Rama
```bash
git checkout -b feature/nueva-caracteristica
# o
git checkout -b fix/correccion-error
```

### 3. Realizar Cambios

#### Configurar el Entorno
```bash
# Linux
bash setup_database.sh
bash compilar.sh

# Windows
mysql -u root -p < db\schema.sql
compilar.bat
```

#### Hacer Cambios
- Editar código en `src/`
- Seguir las convenciones de código existentes
- Agregar comentarios cuando sea necesario

### 4. Probar los Cambios

```bash
# Compilar
bash compilar.sh    # Linux
compilar.bat        # Windows

# Ejecutar
bash ejecutar.sh    # Linux
ejecutar.bat        # Windows
```

### 5. Commit y Push

```bash
git add .
git commit -m "feat: descripción clara del cambio"
git push origin feature/nueva-caracteristica
```

### 6. Crear Pull Request

1. Ir a GitHub
2. Crear Pull Request desde tu rama
3. Describir los cambios realizados
4. Esperar revisión

## 📝 Convenciones de Código

### Java
- **Indentación:** 4 espacios
- **Nombres de clases:** PascalCase (`CompraService`)
- **Nombres de métodos:** camelCase (`obtenerCompras`)
- **Nombres de constantes:** UPPER_SNAKE_CASE (`BG_PRINCIPAL`)
- **Comentarios:** Javadoc para clases y métodos públicos

### Estructura de Paquetes
```
src/
├── dao/        # Data Access Objects
├── modelo/     # Clases de dominio
├── servicio/   # Lógica de negocio
├── util/       # Utilidades
└── vista/      # Interfaz gráfica
```

### Commits
Usar [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` Nueva característica
- `fix:` Corrección de error
- `docs:` Cambios en documentación
- `style:` Formato, punto y coma faltantes, etc.
- `refactor:` Refactorización de código
- `test:` Agregar tests
- `chore:` Mantenimiento

**Ejemplos:**
```
feat: agregar exportación de reportes a PDF
fix: corregir cálculo de totales en créditos
docs: actualizar guía de instalación
```

## 🐛 Reportar Errores

### Crear un Issue
1. Ir a la pestaña "Issues"
2. Clic en "New Issue"
3. Incluir:
   - Descripción clara del error
   - Pasos para reproducir
   - Comportamiento esperado
   - Comportamiento actual
   - Capturas de pantalla (si aplica)
   - Versión del sistema
   - Sistema operativo

### Plantilla de Issue
```markdown
**Descripción del error:**
[Descripción clara y concisa]

**Pasos para reproducir:**
1. Ir a '...'
2. Hacer clic en '...'
3. Ver error

**Comportamiento esperado:**
[Qué debería pasar]

**Comportamiento actual:**
[Qué está pasando]

**Entorno:**
- Versión: 2.2.5
- SO: Windows 10 / Linux Ubuntu 22.04
- Java: 11 / 17
- MySQL: 8.0
```

## ✨ Sugerir Características

1. Crear un Issue con etiqueta "enhancement"
2. Describir la característica propuesta
3. Explicar el caso de uso
4. Discutir implementación

## 🔍 Revisión de Código

### Criterios de Aceptación
- ✅ Código compila sin errores
- ✅ Funcionalidad probada
- ✅ Documentación actualizada
- ✅ Sigue convenciones de código
- ✅ No rompe funcionalidad existente

### Proceso de Revisión
1. Revisor asignado revisa el PR
2. Solicita cambios si es necesario
3. Aprueba cuando todo está correcto
4. Merge a la rama principal

## 📚 Documentación

### Actualizar Documentación
Si tu cambio afecta:
- **Funcionalidad:** Actualizar `MANUAL.md`
- **Instalación:** Actualizar `INSTALACION_WINDOWS.md`
- **API/Código:** Actualizar comentarios Javadoc
- **Cambios:** Agregar entrada en `CHANGELOG.md`

### Formato de CHANGELOG
```markdown
## [X.X.X] - YYYY-MM-DD

### ✨ Nuevo
- Descripción de nueva característica

### 🔧 Mejorado
- Descripción de mejora

### 🐛 Corregido
- Descripción de corrección
```

## 🧪 Testing

### Pruebas Manuales
1. Compilar el proyecto
2. Ejecutar la aplicación
3. Probar la funcionalidad modificada
4. Verificar que no se rompió nada más

### Casos de Prueba
- Agregar proveedor
- Registrar compra
- Editar compra
- Marcar como pagado
- Buscar y filtrar
- Crear categoría personalizada

## 🎨 Estilo Visual

### Colores del Tema
```java
BG_PRINCIPAL = new Color(25, 35, 55);      // Azul oscuro
TEXTO_PRINCIPAL = new Color(220, 220, 220); // Blanco
CREDITO_PENDIENTE = new Color(255, 80, 80); // Rojo
CREDITO_PAGADO = new Color(80, 255, 120);   // Verde
MORADO_PROVEEDOR = new Color(200, 120, 255); // Morado
```

### Fuentes
- **Principal:** Segoe UI
- **Monoespaciada:** Consolas
- **Tamaños:** 12-17px

## 📦 Estructura del Proyecto

```
ModuloProveedores/
├── src/                    # Código fuente
│   ├── dao/               # Acceso a datos
│   ├── modelo/            # Modelos de dominio
│   ├── servicio/          # Lógica de negocio
│   ├── util/              # Utilidades
│   └── vista/             # Interfaz gráfica
├── db/                    # Scripts de base de datos
├── lib/                   # Librerías externas
├── instaladores/          # Versiones empaquetadas
├── docs/                  # Documentación adicional
├── compilar.bat/sh        # Scripts de compilación
├── ejecutar.bat/sh        # Scripts de ejecución
└── README.md              # Documentación principal
```

## 🤝 Código de Conducta

### Nuestro Compromiso
- Ser respetuoso y profesional
- Aceptar críticas constructivas
- Enfocarse en lo mejor para el proyecto
- Mostrar empatía hacia otros colaboradores

### Comportamiento Esperado
- ✅ Usar lenguaje acogedor e inclusivo
- ✅ Respetar diferentes puntos de vista
- ✅ Aceptar críticas constructivas
- ✅ Enfocarse en lo mejor para la comunidad

### Comportamiento Inaceptable
- ❌ Lenguaje o imágenes sexualizadas
- ❌ Comentarios insultantes o despectivos
- ❌ Acoso público o privado
- ❌ Publicar información privada de otros

## 📞 Contacto

Para preguntas o discusiones:
- Crear un Issue en GitHub
- Discusión en Pull Requests
- Sección de Discussions (si está habilitada)

## 📄 Licencia

Al contribuir, aceptas que tus contribuciones se licenciarán bajo la misma licencia del proyecto.

---

**¡Gracias por contribuir!** 🎉
