@echo off
REM ============================================================
REM SCRIPT INTELIGENTE DE EJECUCION - WINDOWS
REM Sistema de Gestion de Proveedores y Compras v2.0
REM ============================================================
REM Este script detecta automaticamente si es primera vez
REM y realiza todas las configuraciones necesarias
REM ============================================================

setlocal enabledelayedexpansion
chcp 65001 >nul 2>&1

cls
echo ==========================================
echo   SISTEMA DE GESTION v2.0
echo   Proveedores y Compras
echo ==========================================
echo.

REM ============================================================
REM Verificar si es primera vez
REM ============================================================

set PRIMERA_VEZ=0

REM Verificar si existe la base de datos
mysql -u root -e "SHOW DATABASES LIKE 'gestion_proveedores';" >nul 2>&1
if %errorlevel% neq 0 (
    set PRIMERA_VEZ=1
)

REM Verificar si existe el directorio bin con archivos
if not exist "bin\Main.class" (
    set PRIMERA_VEZ=1
)

REM ============================================================
REM Configurar base de datos (si es primera vez)
REM ============================================================

if %PRIMERA_VEZ%==1 (
    echo [33m⚙️  Primera ejecucion detectada[0m
    echo [33mConfigurando sistema...[0m
    echo.
    
    echo [34m[1/4] Configurando base de datos...[0m
    
    REM Verificar si MySQL esta instalado
    where mysql >nul 2>&1
    if %errorlevel% neq 0 (
        echo [31m✗ MySQL no esta instalado[0m
        echo.
        echo Instala MySQL desde: https://dev.mysql.com/downloads/installer/
        pause
        exit /b 1
    )
    
    REM Verificar si el servicio esta corriendo
    sc query MySQL >nul 2>&1
    if %errorlevel% neq 0 (
        echo [33mIniciando servicio MySQL...[0m
        net start MySQL >nul 2>&1
        timeout /t 2 >nul
    )
    
    REM Crear base de datos
    echo Ingrese la contraseña de MySQL root (presione Enter si no tiene):
    mysql -u root -p < db\schema.sql
    
    if %errorlevel% neq 0 (
        echo [31m✗ Error al configurar base de datos[0m
        echo.
        echo Intenta ejecutar manualmente:
        echo   mysql -u root -p ^< db\schema.sql
        pause
        exit /b 1
    )
    
    echo [32m✓ Base de datos configurada[0m
    echo.
)

REM ============================================================
REM Compilar proyecto
REM ============================================================

if %PRIMERA_VEZ%==1 (
    echo [34m[2/4] Compilando proyecto...[0m
) else (
    echo [32m✓ Sistema ya configurado[0m
    echo.
    
    REM Verificar si necesita recompilar
    if not exist "bin\Main.class" (
        echo [33m[2/4] Recompilando proyecto...[0m
    ) else (
        echo [32m[2/4] ✓ Proyecto ya compilado[0m
        goto :ejecutar
    )
)

REM Verificar Java
where javac >nul 2>&1
if %errorlevel% neq 0 (
    echo [31m✗ Java JDK no esta instalado[0m
    echo.
    echo Instala Java JDK desde: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

REM Limpiar y crear directorio bin
if exist bin rmdir /s /q bin >nul 2>&1
mkdir bin

REM Compilar clases base
javac -d bin -cp "lib\*;src" src\modelo\*.java src\dao\*.java src\servicio\*.java src\util\*.java >nul 2>&1

if %errorlevel% neq 0 (
    echo [31m✗ Error al compilar clases base[0m
    pause
    exit /b 1
)

REM Compilar interfaces
javac -d bin -cp "lib\*;bin;src" src\vista\VentanaUnificada.java src\vista\FormularioProveedorDark.java src\vista\FormularioCompraDark.java >nul 2>&1

if %errorlevel% neq 0 (
    echo [31m✗ Error al compilar interfaces[0m
    pause
    exit /b 1
)

REM Compilar Main
javac -d bin -cp "lib\*;bin" src\Main.java >nul 2>&1

if %errorlevel% neq 0 (
    echo [31m✗ Error al compilar Main[0m
    pause
    exit /b 1
)

echo [32m✓ Compilacion exitosa[0m
echo.

REM ============================================================
REM Ejecutar aplicacion
REM ============================================================

:ejecutar

echo [34m[3/4] Preparando ejecucion...[0m
timeout /t 1 >nul
echo [32m✓ Listo[0m
echo.
echo [34m[4/4] Iniciando aplicacion...[0m
echo.
echo [32m==========================================
echo   SISTEMA INICIADO
echo ==========================================[0m
echo.
echo [36mCaracteristicas:[0m
echo   ✓ Interfaz oscura profesional
echo   ✓ Busqueda en tiempo real
echo   ✓ Categorias dinamicas
echo   ✓ Control de creditos
echo.
echo [33mAbriendo interfaz grafica...[0m
echo.

REM Ejecutar
java -cp "bin;lib\*" Main

echo.
echo [32mAplicacion cerrada correctamente[0m
echo.
pause
