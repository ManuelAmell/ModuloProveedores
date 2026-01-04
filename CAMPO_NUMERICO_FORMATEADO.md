# Campo Numérico Formateado

Componente reutilizable para campos numéricos con formato automático.

## Características

- ✅ Formato: 1.000.000,50 (punto para miles, coma para decimales)
- ✅ Validación en tiempo real
- ✅ Placeholder inteligente
- ✅ Valor interno como BigDecimal

## Uso Básico

```java
import util.CampoNumericoFormateado;

// Crear campo
CampoNumericoFormateado campo = new CampoNumericoFormateado("1.000.000");

// Configurar estilo
campo.setBackground(new Color(60, 60, 60));
campo.setColorTexto(Color.WHITE);
campo.setColorPlaceholder(Color.GRAY);

// Obtener valor
BigDecimal valor = campo.getValorNumerico();

// Establecer valor
campo.setValorNumerico(new BigDecimal("1500000.50"));

// Verificar si está vacío
if (campo.estaVacio()) {
    // Manejar campo vacío
}
```

## Métodos Principales

- `BigDecimal getValorNumerico()` - Obtiene valor sin formato
- `void setValorNumerico(BigDecimal)` - Establece y formatea valor
- `boolean estaVacio()` - Verifica si está vacío
- `void limpiar()` - Limpia el campo

## Implementado en

- FormularioCompraDark: Precio Unitario y Total
