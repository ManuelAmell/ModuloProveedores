package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase que representa una Compra en el sistema.
 */
public class Compra {
    
    private int id;
    private int idProveedor;
    private String nombreProveedor; // Para mostrar en tablas
    private String numeroFactura;
    private String categoria; // Ahora es String para categorías dinámicas
    private String descripcion;
    private Integer cantidad; // Opcional
    private BigDecimal precioUnitario; // Opcional
    private BigDecimal total;
    private LocalDate fechaCompra;
    private FormaPago formaPago;
    private EstadoCredito estadoCredito; // Null si no es crédito
    private LocalDate fechaPago; // Null si no está pagado
    
    // Constructores
    public Compra() {
    }
    
    public Compra(int id, int idProveedor, String numeroFactura, String categoria,
                  String descripcion, Integer cantidad, BigDecimal precioUnitario, BigDecimal total,
                  LocalDate fechaCompra, FormaPago formaPago, EstadoCredito estadoCredito, LocalDate fechaPago) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.numeroFactura = numeroFactura;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.fechaCompra = fechaCompra;
        this.formaPago = formaPago;
        this.estadoCredito = estadoCredito;
        this.fechaPago = fechaPago;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdProveedor() {
        return idProveedor;
    }
    
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public String getNombreProveedor() {
        return nombreProveedor;
    }
    
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }
    
    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    
    public FormaPago getFormaPago() {
        return formaPago;
    }
    
    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }
    
    public EstadoCredito getEstadoCredito() {
        return estadoCredito;
    }
    
    public void setEstadoCredito(EstadoCredito estadoCredito) {
        this.estadoCredito = estadoCredito;
    }
    
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", idProveedor=" + idProveedor +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", categoria=" + categoria +
                ", total=" + total +
                ", fechaCompra=" + fechaCompra +
                ", formaPago=" + formaPago +
                '}';
    }
}
