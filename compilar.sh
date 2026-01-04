#!/bin/bash

# ============================================================
# SCRIPT DE COMPILACIÓN
# Sistema de Gestión de Proveedores y Compras
# ============================================================

echo "=========================================="
echo "Compilando Sistema de Gestión"
echo "=========================================="
echo ""

# Colores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

# Limpiar directorio bin
echo -e "${BLUE}[1/4] Limpiando directorio bin...${NC}"
rm -rf bin/*
mkdir -p bin
echo -e "${GREEN}✓ Directorio limpio${NC}"
echo ""

# Compilar clases base (modelo, dao, servicio, util)
echo -e "${BLUE}[2/4] Compilando clases base...${NC}"
javac -d bin -cp "lib/*:src" src/modelo/*.java src/dao/*.java src/servicio/*.java src/util/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}✗ Error al compilar clases base${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Clases base compiladas${NC}"
echo ""

# Compilar interfaces (vistas)
echo -e "${BLUE}[3/4] Compilando interfaces...${NC}"
javac -d bin -cp "lib/*:bin:src" \
    src/vista/VentanaUnificada.java \
    src/vista/FormularioProveedorDark.java \
    src/vista/FormularioCompraDark.java

if [ $? -ne 0 ]; then
    echo -e "${RED}✗ Error al compilar interfaces${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Interfaces compiladas${NC}"
echo ""

# Compilar Main
echo -e "${BLUE}[4/4] Compilando Main...${NC}"
javac -d bin -cp "lib/*:bin" src/Main.java

if [ $? -ne 0 ]; then
    echo -e "${RED}✗ Error al compilar Main${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Main compilado${NC}"
echo ""

echo -e "${GREEN}=========================================="
echo "  COMPILACIÓN EXITOSA"
echo "==========================================${NC}"
echo ""
echo "Para ejecutar la aplicación, usa:"
echo "  ./ejecutar.sh"
echo ""
