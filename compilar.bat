@echo off
REM ==========================================
REM Script de Compilación para Windows
REM Sistema de Gestión de Proveedores
REM ==========================================

echo.
echo ==========================================
echo Compilando Sistema de Gestion
echo ==========================================
echo.

REM [1/4] Limpiar directorio bin
echo [1/4] Limpiando directorio bin...
if exist bin (
    rmdir /s /q bin
)
mkdir bin
echo OK Directorio limpio
echo.

REM [2/4] Compilar clases base (modelo, util)
echo [2/4] Compilando clases base...
javac -encoding UTF-8 -d bin -cp "lib\*" src\modelo\*.java src\util\*.java
if %errorlevel% neq 0 (
    echo ERROR: Fallo al compilar clases base
    pause
    exit /b 1
)
echo OK Clases base compiladas
echo.

REM [3/4] Compilar DAO y servicios
echo [3/4] Compilando DAO y servicios...
javac -encoding UTF-8 -d bin -cp "bin;lib\*" src\dao\*.java src\servicio\*.java
if %errorlevel% neq 0 (
    echo ERROR: Fallo al compilar DAO y servicios
    pause
    exit /b 1
)
echo OK DAO y servicios compilados
echo.

REM [4/4] Compilar vistas y Main
echo [4/4] Compilando vistas y Main...
javac -encoding UTF-8 -d bin -cp "bin;lib\*" src\vista\*.java src\Main.java
if %errorlevel% neq 0 (
    echo ERROR: Fallo al compilar vistas y Main
    pause
    exit /b 1
)
echo OK Vistas y Main compilados
echo.

echo ==========================================
echo   COMPILACION EXITOSA
echo ==========================================
echo.
echo Para ejecutar la aplicacion, usa:
echo   ejecutar.bat
echo.
pause
