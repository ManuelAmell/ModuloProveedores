# Política de Seguridad

## 🔒 Versiones Soportadas

Actualmente se proporciona soporte de seguridad para las siguientes versiones:

| Versión | Soportada          |
| ------- | ------------------ |
| 2.2.x   | :white_check_mark: |
| 2.1.x   | :white_check_mark: |
| 2.0.x   | :x:                |
| < 2.0   | :x:                |

## 🚨 Reportar una Vulnerabilidad

Si descubres una vulnerabilidad de seguridad, por favor **NO** la reportes públicamente a través de Issues.

### Proceso de Reporte

1. **Envía un reporte privado:**
   - Crea un Issue privado (si está disponible)
   - O contacta directamente al mantenedor

2. **Incluye la siguiente información:**
   - Tipo de vulnerabilidad
   - Ubicación del código afectado (archivo y línea)
   - Pasos para reproducir
   - Impacto potencial
   - Sugerencias de corrección (si las tienes)

3. **Tiempo de respuesta:**
   - Reconocimiento inicial: 48 horas
   - Evaluación completa: 7 días
   - Corrección: Según severidad

### Severidad

**Crítica:** Corrección en 24-48 horas
- Ejecución remota de código
- Inyección SQL
- Exposición de credenciales

**Alta:** Corrección en 7 días
- Escalación de privilegios
- Bypass de autenticación
- Pérdida de datos

**Media:** Corrección en 30 días
- XSS
- CSRF
- Divulgación de información

**Baja:** Corrección en próxima versión
- Problemas menores de configuración
- Mejoras de seguridad

## 🛡️ Mejores Prácticas de Seguridad

### Para Usuarios

1. **Credenciales de Base de Datos:**
   ```java
   // Cambiar contraseña por defecto
   private static final String PASSWORD = "tu_password_seguro";
   ```

2. **Permisos de Base de Datos:**
   - Crear usuario específico para la aplicación
   - No usar root en producción
   - Limitar permisos al mínimo necesario

3. **Conexión a Base de Datos:**
   - Usar conexiones SSL cuando sea posible
   - No exponer MySQL a internet
   - Usar firewall para limitar acceso

4. **Respaldos:**
   - Realizar respaldos regulares
   - Encriptar respaldos sensibles
   - Probar restauración periódicamente

### Para Desarrolladores

1. **Inyección SQL:**
   - ✅ Usar PreparedStatement (ya implementado)
   - ❌ Nunca concatenar SQL con entrada de usuario

2. **Validación de Entrada:**
   - Validar todos los datos de entrada
   - Sanitizar antes de usar
   - Usar tipos apropiados (BigDecimal para dinero)

3. **Manejo de Errores:**
   - No exponer stack traces al usuario
   - Loggear errores de forma segura
   - No incluir información sensible en logs

4. **Dependencias:**
   - Mantener librerías actualizadas
   - Revisar vulnerabilidades conocidas
   - Usar versiones estables

## 🔐 Configuración Segura

### MySQL

```sql
-- Crear usuario específico
CREATE USER 'gestion_app'@'localhost' IDENTIFIED BY 'password_seguro';

-- Otorgar permisos mínimos
GRANT SELECT, INSERT, UPDATE, DELETE ON gestion_proveedores.* TO 'gestion_app'@'localhost';

-- Aplicar cambios
FLUSH PRIVILEGES;
```

### Archivo de Configuración

```java
// src/util/ConexionBD.java
private static final String URL = "jdbc:mysql://localhost:3306/gestion_proveedores?useSSL=true";
private static final String USUARIO = "gestion_app"; // No usar root
private static final String PASSWORD = System.getenv("DB_PASSWORD"); // Usar variable de entorno
```

### Variables de Entorno (Recomendado)

**Windows:**
```cmd
setx DB_PASSWORD "tu_password_seguro"
```

**Linux:**
```bash
export DB_PASSWORD="tu_password_seguro"
# Agregar a ~/.bashrc para persistencia
```

## 🔍 Auditoría de Seguridad

### Áreas Revisadas

- ✅ Inyección SQL (PreparedStatement usado)
- ✅ Validación de entrada (implementada)
- ✅ Manejo de errores (apropiado)
- ✅ Conexiones de BD (pooling implementado)
- ⚠️ Encriptación de contraseñas (no aplicable - app local)
- ⚠️ Autenticación de usuarios (no implementada - app local)

### Recomendaciones Futuras

1. **Autenticación de Usuarios:**
   - Implementar login de usuarios
   - Roles y permisos
   - Auditoría de acciones

2. **Encriptación:**
   - Encriptar datos sensibles en BD
   - Usar HTTPS si se implementa versión web

3. **Logs de Auditoría:**
   - Registrar acciones importantes
   - Timestamp de operaciones
   - Usuario que realizó la acción

## 📋 Checklist de Seguridad

Para nuevas características:

- [ ] Validación de entrada implementada
- [ ] PreparedStatement usado para SQL
- [ ] Manejo apropiado de errores
- [ ] Sin información sensible en logs
- [ ] Documentación de seguridad actualizada
- [ ] Pruebas de seguridad realizadas

## 🆘 Vulnerabilidades Conocidas

Actualmente no hay vulnerabilidades conocidas en la versión 2.2.5.

Historial:
- **v2.2.5:** Sin vulnerabilidades conocidas
- **v2.2.4:** Sin vulnerabilidades conocidas
- **v2.2.3:** Sin vulnerabilidades conocidas

## 📞 Contacto

Para reportes de seguridad urgentes:
- Crear Issue privado en GitHub
- Etiquetar como "security"

---

**Última actualización:** Enero 2026  
**Versión del documento:** 1.0
