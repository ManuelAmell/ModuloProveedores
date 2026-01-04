# 📦 Instrucciones para Subir a GitHub

Guía paso a paso para subir el proyecto a GitHub.

---

## 🚀 Primera Vez (Nuevo Repositorio)

### 1. Crear Repositorio en GitHub

1. Ir a https://github.com
2. Clic en "New repository"
3. Nombre: `ModuloProveedores` (o el que prefieras)
4. Descripción: "Sistema de gestión de proveedores y compras con Java y MySQL"
5. **NO** inicializar con README, .gitignore o licencia (ya los tenemos)
6. Clic en "Create repository"

### 2. Configurar Git Local (Si es primera vez)

```bash
# Configurar nombre y email
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"
```

### 3. Inicializar y Subir

```bash
# Inicializar repositorio (si no está inicializado)
git init

# Agregar todos los archivos
git add .

# Primer commit
git commit -m "feat: versión inicial 2.2.5 - sistema completo de gestión"

# Agregar repositorio remoto (reemplazar con tu URL)
git remote add origin https://github.com/tu-usuario/ModuloProveedores.git

# Subir a GitHub
git branch -M main
git push -u origin main
```

---

## 🔄 Actualizaciones Posteriores

### Flujo Normal de Trabajo

```bash
# 1. Ver estado de cambios
git status

# 2. Agregar archivos modificados
git add .
# O agregar archivos específicos
git add src/vista/VentanaUnificada.java

# 3. Commit con mensaje descriptivo
git commit -m "feat: agregar nueva característica"
# O
git commit -m "fix: corregir error en cálculo de totales"

# 4. Subir cambios
git push origin main
```

### Tipos de Commits (Conventional Commits)

```bash
# Nueva característica
git commit -m "feat: agregar exportación a PDF"

# Corrección de error
git commit -m "fix: corregir cálculo de pendientes"

# Documentación
git commit -m "docs: actualizar manual de usuario"

# Estilo/formato
git commit -m "style: formatear código según convenciones"

# Refactorización
git commit -m "refactor: reorganizar estructura de paquetes"

# Rendimiento
git commit -m "perf: optimizar consultas SQL"

# Mantenimiento
git commit -m "chore: actualizar dependencias"
```

---

## 🌿 Trabajar con Ramas

### Crear Nueva Rama para Feature

```bash
# Crear y cambiar a nueva rama
git checkout -b feature/exportar-pdf

# Hacer cambios y commits
git add .
git commit -m "feat: implementar exportación a PDF"

# Subir rama a GitHub
git push origin feature/exportar-pdf

# Crear Pull Request en GitHub
# Ir a GitHub → Pull Requests → New Pull Request
```

### Volver a Main

```bash
# Cambiar a main
git checkout main

# Actualizar main
git pull origin main
```

### Merge de Rama

```bash
# Desde main
git checkout main
git merge feature/exportar-pdf

# Subir cambios
git push origin main

# Eliminar rama local (opcional)
git branch -d feature/exportar-pdf

# Eliminar rama remota (opcional)
git push origin --delete feature/exportar-pdf
```

---

## 📋 Comandos Útiles

### Ver Historial

```bash
# Ver commits
git log

# Ver commits en una línea
git log --oneline

# Ver últimos 5 commits
git log -5

# Ver cambios de un archivo
git log -p src/vista/VentanaUnificada.java
```

### Ver Cambios

```bash
# Ver cambios no staged
git diff

# Ver cambios staged
git diff --staged

# Ver cambios de un archivo
git diff src/vista/VentanaUnificada.java
```

### Deshacer Cambios

```bash
# Deshacer cambios en archivo (antes de add)
git checkout -- archivo.java

# Deshacer add (unstage)
git reset HEAD archivo.java

# Deshacer último commit (mantener cambios)
git reset --soft HEAD~1

# Deshacer último commit (eliminar cambios)
git reset --hard HEAD~1
```

### Actualizar desde GitHub

```bash
# Traer cambios
git pull origin main

# O en dos pasos
git fetch origin
git merge origin/main
```

---

## 🏷️ Tags y Releases

### Crear Tag para Versión

```bash
# Crear tag anotado
git tag -a v2.2.5 -m "Versión 2.2.5 - Campos numéricos formateados"

# Subir tag
git push origin v2.2.5

# Subir todos los tags
git push origin --tags
```

### Crear Release en GitHub

1. Ir a GitHub → Releases
2. Clic en "Create a new release"
3. Seleccionar tag: v2.2.5
4. Título: "Versión 2.2.5"
5. Descripción: Copiar de CHANGELOG.md
6. Adjuntar instaladores (opcional)
7. Clic en "Publish release"

---

## 🔍 Verificar Antes de Subir

### Checklist

- [ ] Código compila sin errores
- [ ] Funcionalidad probada
- [ ] Documentación actualizada
- [ ] CHANGELOG.md actualizado
- [ ] .gitignore configurado
- [ ] Sin archivos sensibles (contraseñas, etc.)
- [ ] Sin archivos binarios grandes innecesarios

### Verificar .gitignore

```bash
# Ver qué archivos se subirán
git status

# Ver archivos ignorados
git status --ignored
```

### Archivos que NO deben subirse

- ❌ `bin/` (compilados)
- ❌ `.vscode/`, `.idea/` (configuración IDE)
- ❌ Archivos con contraseñas
- ❌ Bases de datos locales
- ❌ Archivos temporales

---

## 🚨 Solución de Problemas

### Error: "remote origin already exists"

```bash
# Ver remotes
git remote -v

# Eliminar remote
git remote remove origin

# Agregar nuevamente
git remote add origin https://github.com/tu-usuario/ModuloProveedores.git
```

### Error: "failed to push"

```bash
# Traer cambios primero
git pull origin main --rebase

# Luego subir
git push origin main
```

### Error: "Permission denied"

```bash
# Configurar SSH o usar HTTPS con token
# O usar GitHub Desktop
```

### Archivo muy grande

```bash
# Eliminar del historial (cuidado!)
git filter-branch --tree-filter 'rm -f archivo-grande.zip' HEAD

# O usar BFG Repo-Cleaner
```

---

## 📚 Recursos Adicionales

### Documentación

- [Git Documentation](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com/)
- [Conventional Commits](https://www.conventionalcommits.org/)

### Herramientas

- [GitHub Desktop](https://desktop.github.com/) - GUI para Git
- [GitKraken](https://www.gitkraken.com/) - Cliente Git visual
- [SourceTree](https://www.sourcetreeapp.com/) - Cliente Git gratuito

---

## ✅ Resumen Rápido

```bash
# Primera vez
git init
git add .
git commit -m "feat: versión inicial 2.2.5"
git remote add origin https://github.com/tu-usuario/ModuloProveedores.git
git push -u origin main

# Actualizaciones
git add .
git commit -m "feat: descripción del cambio"
git push origin main

# Con ramas
git checkout -b feature/nueva-caracteristica
# ... hacer cambios ...
git add .
git commit -m "feat: nueva característica"
git push origin feature/nueva-caracteristica
# Crear PR en GitHub
```

---

**¡Listo para subir a GitHub!** 🚀
