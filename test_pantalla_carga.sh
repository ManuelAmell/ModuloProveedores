#!/bin/bash

# Script de prueba para la pantalla de carga

echo "=========================================="
echo "  PRUEBA DE PANTALLA DE CARGA"
echo "=========================================="
echo ""

# Verificar que exista el archivo de frases
if [ -f "frases_carga.txt" ]; then
    echo "✓ Archivo frases_carga.txt encontrado"
    NUM_FRASES=$(grep -v "^#" frases_carga.txt | grep -v "^$" | wc -l)
    echo "  → $NUM_FRASES frases disponibles"
else
    echo "⚠ Archivo frases_carga.txt NO encontrado"
    echo "  → Se usarán frases por defecto"
fi

echo ""

# Verificar que exista la imagen
if [ -f "lib/logo_carga.png" ]; then
    echo "✓ Imagen lib/logo_carga.png encontrada"
    SIZE=$(du -h lib/logo_carga.png | cut -f1)
    echo "  → Tamaño: $SIZE"
elif [ -f "lib/ModuloProveedores.png" ]; then
    echo "✓ Imagen lib/ModuloProveedores.png encontrada (alternativa)"
    SIZE=$(du -h lib/ModuloProveedores.png | cut -f1)
    echo "  → Tamaño: $SIZE"
else
    echo "⚠ No se encontró imagen"
    echo "  → Se mostrará logo como texto"
fi

echo ""

# Verificar compilación
if [ -f "bin/vista/PantallaCarga.class" ]; then
    echo "✓ PantallaCarga.class compilado"
else
    echo "✗ PantallaCarga.class NO compilado"
    echo "  → Ejecuta: ./compilar.sh"
    exit 1
fi

echo ""
echo "=========================================="
echo "  TODO LISTO - Ejecutando aplicación..."
echo "=========================================="
echo ""

# Ejecutar aplicación
./ejecutar_optimizado.sh
