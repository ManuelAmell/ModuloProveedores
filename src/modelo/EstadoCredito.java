package modelo;

/**
 * Enumeración de estados de crédito.
 */
public enum EstadoCredito {
    PENDIENTE("pendiente", "Pendiente"),
    PAGADO("pagado", "Pagado");
    
    private final String valor;
    private final String etiqueta;
    
    EstadoCredito(String valor, String etiqueta) {
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
    
    public static EstadoCredito fromValor(String valor) {
        for (EstadoCredito ec : values()) {
            if (ec.valor.equals(valor)) {
                return ec;
            }
        }
        return PENDIENTE;
    }
}
