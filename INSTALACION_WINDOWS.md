# 🪟 Instalación en Windows

Guía paso a paso para instalar el sistema en Windows.

---

## 📋 Requisitos Previos

### 1. Java JDK 11 o superior

**Verificar si está instalado:**
```cmd
java -version
```

**Si no está instalado:**
1. Descargar desde: https://www.oracle.com/java/technologies/downloads/
2. Instalar el JDK
3. Agregar a PATH (el instalador lo hace automáticamente)

### 2. MySQL 8.0 o superior

**Verificar si está instalado:**
```cmd
mysql --version
```

**Si no está instalado:**
1. Descargar desde: https://dev.mysql.com/downloads/installer/
2. Instalar MySQL Server
3. Configurar contraseña de root durante instalación
4. Iniciar el servicio MySQL

---

## 🚀 Instalación

### Opción 1: Usar Instalador Precompilado (Recomendado)

1. **Extraer el instalador:**
   - Ubicación: `instaladores/ModuloProveedores-windows.zip`
   - Extraer en: `C:\ModuloProveedores\` (o carpeta de tu elección)

2. **Configurar la base de datos:**
   
   Abrir CMD como Administrador y ejecutar:
   ```cmd
   cd C:\ModuloProveedores
   mysql -u root -p < db\schema.sql
   ```
   
   Ingresar la contraseña de MySQL cuando se solicite.

3. **Ejecutar la aplicación:**
   - Doble clic en `ejecutar.bat`
   - O desde CMD:
     ```cmd
     ejecutar.bat
     ```

### Opción 2: Compilar desde Código Fuente

1. **Clonar o descargar el proyecto**

2. **Configurar la base de datos:**
   ```cmd
   mysql -u root -p < db\schema.sql
   ```

3. **Editar configuración de conexión:**
   
   Abrir `src\util\ConexionBD.java` y modificar:
   ```java
   private static final String PASSWORD = "tu_password_mysql";
   ```

4. **Compilar:**
   
   Opción A - Usar script (Recomendado):
   ```cmd
   compilar.bat
   ```
   
   Opción B - Compilar manualmente:
   ```cmd
   javac -encoding UTF-8 -d bin -cp "lib\*" src\Main.java src\modelo\*.java src\dao\*.java src\servicio\*.java src\util\*.java src\vista\*.java
   ```

5. **Ejecutar:**
   ```cmd
   ejecutar.bat
   ```

---

## ⚙️ Configuración

### Cambiar Contraseña de Base de Datos

1. Abrir `src\util\ConexionBD.java`
2. Modificar la línea:
   ```java
   private static final String PASSWORD = "tu_password";
   ```
3. Guardar el archivo
4. Recompilar (si usaste Opción 2)

### Cambiar Puerto de MySQL

Si MySQL usa un puerto diferente a 3306:

1. Abrir `src\util\ConexionBD.java`
2. Modificar la línea:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/gestion_proveedores";
   ```
   Cambiar `3306` por tu puerto.
3. Guardar y recompilar:
   ```cmd
   compilar.bat
   ```

---

## 🔧 Solución de Problemas

### Error: "java no se reconoce como comando"

**Solución:**
1. Verificar instalación de Java
2. Agregar Java al PATH:
   - Panel de Control → Sistema → Configuración avanzada
   - Variables de entorno
   - Agregar a PATH: `C:\Program Files\Java\jdk-XX\bin`

### Error: "mysql no se reconoce como comando"

**Solución:**
1. Verificar instalación de MySQL
2. Agregar MySQL al PATH:
   - Agregar a PATH: `C:\Program Files\MySQL\MySQL Server 8.0\bin`

### Error: "Access denied for user 'root'"

**Solución:**
1. Verificar contraseña en `ConexionBD.java`
2. Verificar que MySQL esté ejecutándose:
   ```cmd
   net start MySQL80
   ```

### Error: "Unknown database 'gestion_proveedores'"

**Solución:**
Crear la base de datos:
```cmd
mysql -u root -p < db\schema.sql
```

### La aplicación no inicia

**Solución:**
1. Verificar que `lib\mysql-connector-j-9.1.0.jar` exista
2. Verificar que MySQL esté ejecutándose
3. Revisar configuración en `ConexionBD.java`
4. Intentar ejecutar desde CMD para ver errores:
   ```cmd
   ejecutar.bat
   ```

---

## 📁 Estructura de Archivos

```
ModuloProveedores/
├── bin/                    # Clases compiladas
├── db/
│   └── schema.sql         # Script de base de datos
├── lib/
│   └── mysql-connector-j-9.1.0.jar
├── src/                   # Código fuente
├── ejecutar.bat           # Script de ejecución
├── MANUAL.md              # Manual de usuario
└── README.md              # Documentación principal
```

---

## 🎯 Primer Uso

1. **Iniciar la aplicación:**
   ```cmd
   ejecutar.bat
   ```

2. **Agregar primer proveedor:**
   - Clic en "+ Nuevo Proveedor"
   - Llenar nombre
   - Guardar

3. **Registrar primera compra:**
   - Seleccionar el proveedor
   - Clic en "+ Nueva Compra"
   - Llenar formulario
   - Guardar

4. **Explorar funcionalidades:**
   - Búsqueda de proveedores
   - Filtros de compras
   - Marcar como pagado
   - Ver estadísticas

---

## 🔄 Actualización

Para actualizar a una nueva versión:

1. **Respaldar base de datos:**
   ```cmd
   mysqldump -u root -p gestion_proveedores > backup.sql
   ```

2. **Reemplazar archivos:**
   - Extraer nueva versión
   - Copiar sobre archivos antiguos

3. **Ejecutar scripts de actualización** (si existen):
   ```cmd
   mysql -u root -p gestion_proveedores < db\update_schema.sql
   ```

4. **Iniciar aplicación actualizada**

---

## 📞 Soporte

Para más información:
- **Manual completo:** [MANUAL.md](MANUAL.md)
- **Guía rápida:** [GUIA_USO_RAPIDO.md](GUIA_USO_RAPIDO.md)
- **Changelog:** [CHANGELOG.md](CHANGELOG.md)

---

## ✅ Checklist de Instalación

- [ ] Java JDK 11+ instalado
- [ ] MySQL 8.0+ instalado
- [ ] MySQL ejecutándose
- [ ] Base de datos creada (`schema.sql`)
- [ ] Contraseña configurada en `ConexionBD.java`
- [ ] Aplicación ejecuta correctamente
- [ ] Primer proveedor agregado
- [ ] Primera compra registrada

---

**¡Listo para usar! 🎉**
