package modelo;

/**
 * Enumeración de formas de pago.
 */
public enum FormaPago {
    EFECTIVO("efectivo", "Efectivo"),
    TRANSFERENCIA("transferencia", "Transferencia"),
    CREDITO("credito", "Crédito");
    
    private final String valor;
    private final String etiqueta;
    
    FormaPago(String valor, String etiqueta) {
        this.valor = valor;
        this.etiqueta = etiqueta;
    }
    
    public String getValor() {
        return valor;
    }
    
    public String getEtiqueta() {
        return etiqueta;
    }
    
    @Override
    public String toString() {
        return etiqueta;
    }
    
    public static FormaPago fromValor(String valor) {
        for (FormaPago fp : values()) {
            if (fp.valor.equals(valor)) {
                return fp;
            }
        }
        return EFECTIVO;
    }
}
