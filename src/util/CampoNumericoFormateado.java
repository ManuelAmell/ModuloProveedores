package util;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 * Campo de texto numérico con formato automático.
 * Formato: 1.000.000,00 (punto para miles, coma para decimales)
 */
public class CampoNumericoFormateado extends JTextField {
    
    private final DecimalFormat formatoConSeparador;
    private final DecimalFormatSymbols simbolos;
    private BigDecimal valorReal;
    private String placeholder;
    private Color colorTexto;
    private Color colorPlaceholder;
    private boolean mostrandoPlaceholder;
    
    /**
     * Constructor con placeholder por defecto
     */
    public CampoNumericoFormateado() {
        this("1.000.000");
    }
    
    /**
     * Constructor con placeholder personalizado
     */
    public CampoNumericoFormateado(String placeholder) {
        super();
        this.placeholder = placeholder;
        this.valorReal = BigDecimal.ZERO;
        this.mostrandoPlaceholder = true;
        
        // Configurar formato regional (punto para miles, coma para decimales)
        simbolos = new DecimalFormatSymbols(new Locale("es", "CO"));
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        
        formatoConSeparador = new DecimalFormat("#,##0.00", simbolos);
        formatoConSeparador.setGroupingUsed(true);
        formatoConSeparador.setMaximumFractionDigits(2);
        formatoConSeparador.setMinimumFractionDigits(0);
        
        configurarComponente();
        configurarFiltro();
        configurarEventos();
    }
    
    private void configurarComponente() {
        // Guardar colores originales
        colorTexto = getForeground();
        colorPlaceholder = Color.GRAY;
        
        // Mostrar placeholder inicial
        setText(placeholder);
        setForeground(colorPlaceholder);
    }
    
    private void configurarFiltro() {
        ((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (validarEntrada(string)) {
                    super.insertString(fb, offset, string, attr);
                    formatearTexto(fb);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (validarEntrada(text)) {
                    super.replace(fb, offset, length, text, attrs);
                    formatearTexto(fb);
                }
            }
            
            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
                formatearTexto(fb);
            }
            
            private boolean validarEntrada(String texto) {
                if (texto == null || texto.isEmpty()) {
                    return true;
                }
                // Permitir solo dígitos, punto, coma
                return texto.matches("[0-9.,]*");
            }
            
            private void formatearTexto(FilterBypass fb) throws BadLocationException {
                if (mostrandoPlaceholder) {
                    return;
                }
                
                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                
                if (textoActual.isEmpty()) {
                    valorReal = BigDecimal.ZERO;
                    return;
                }
                
                // Guardar posición del cursor
                int posicionCursor = getCaretPosition();
                int digitosAntesCursor = contarDigitosAntes(textoActual, posicionCursor);
                
                // Limpiar formato: eliminar puntos de miles, reemplazar coma por punto
                String textoLimpio = textoActual.replace(".", "").replace(",", ".");
                
                try {
                    // Parsear valor
                    valorReal = new BigDecimal(textoLimpio);
                    
                    // Formatear con separadores
                    String textoFormateado = formatearNumero(valorReal);
                    
                    // Actualizar texto sin disparar eventos
                    fb.remove(0, fb.getDocument().getLength());
                    fb.insertString(0, textoFormateado, null);
                    
                    // Restaurar posición del cursor
                    int nuevaPosicion = calcularNuevaPosicionCursor(textoFormateado, digitosAntesCursor);
                    SwingUtilities.invokeLater(() -> setCaretPosition(nuevaPosicion));
                    
                } catch (NumberFormatException e) {
                    // Si no se puede parsear, mantener el texto anterior
                    valorReal = BigDecimal.ZERO;
                }
            }
            
            private int contarDigitosAntes(String texto, int posicion) {
                int count = 0;
                for (int i = 0; i < posicion && i < texto.length(); i++) {
                    if (Character.isDigit(texto.charAt(i))) {
                        count++;
                    }
                }
                return count;
            }
            
            private int calcularNuevaPosicionCursor(String textoFormateado, int digitosAntes) {
                int count = 0;
                for (int i = 0; i < textoFormateado.length(); i++) {
                    if (Character.isDigit(textoFormateado.charAt(i))) {
                        count++;
                        if (count == digitosAntes) {
                            return i + 1;
                        }
                    }
                }
                return textoFormateado.length();
            }
        });
    }
    
    private void configurarEventos() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mostrandoPlaceholder) {
                    setText("");
                    setForeground(colorTexto);
                    mostrandoPlaceholder = false;
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                String texto = getText().trim();
                if (texto.isEmpty() || valorReal.compareTo(BigDecimal.ZERO) == 0) {
                    setText(placeholder);
                    setForeground(colorPlaceholder);
                    mostrandoPlaceholder = true;
                    valorReal = BigDecimal.ZERO;
                }
            }
        });
    }
    
    /**
     * Obtiene el valor numérico real sin formato
     */
    public BigDecimal getValorNumerico() {
        if (mostrandoPlaceholder) {
            return BigDecimal.ZERO;
        }
        return valorReal;
    }
    
    /**
     * Establece el valor numérico y lo formatea
     */
    public void setValorNumerico(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) == 0) {
            setText(placeholder);
            setForeground(colorPlaceholder);
            mostrandoPlaceholder = true;
            valorReal = BigDecimal.ZERO;
        } else {
            valorReal = valor;
            setText(formatearNumero(valor));
            setForeground(colorTexto);
            mostrandoPlaceholder = false;
        }
    }
    
    /**
     * Verifica si el campo está vacío o muestra el placeholder
     */
    public boolean estaVacio() {
        return mostrandoPlaceholder || valorReal.compareTo(BigDecimal.ZERO) == 0;
    }
    
    /**
     * Limpia el campo
     */
    public void limpiar() {
        setText(placeholder);
        setForeground(colorPlaceholder);
        mostrandoPlaceholder = true;
        valorReal = BigDecimal.ZERO;
    }
    
    /**
     * Formatea un número con separadores de miles y decimales
     */
    private String formatearNumero(BigDecimal numero) {
        if (numero == null) {
            return "";
        }
        
        // Si tiene decimales, mostrarlos; si no, no mostrar
        if (numero.scale() > 0 && numero.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {
            formatoConSeparador.setMinimumFractionDigits(2);
            formatoConSeparador.setMaximumFractionDigits(2);
        } else {
            formatoConSeparador.setMinimumFractionDigits(0);
            formatoConSeparador.setMaximumFractionDigits(2);
        }
        
        return formatoConSeparador.format(numero);
    }
    
    /**
     * Establece el placeholder
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (mostrandoPlaceholder) {
            setText(placeholder);
        }
    }
    
    /**
     * Establece el color del placeholder
     */
    public void setColorPlaceholder(Color color) {
        this.colorPlaceholder = color;
        if (mostrandoPlaceholder) {
            setForeground(color);
        }
    }
    
    /**
     * Establece el color del texto normal
     */
    public void setColorTexto(Color color) {
        this.colorTexto = color;
        if (!mostrandoPlaceholder) {
            setForeground(color);
        }
    }
    
    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (!mostrandoPlaceholder) {
            this.colorTexto = fg;
        }
    }
}
