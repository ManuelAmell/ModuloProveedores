package servicio;

import dao.CompraDAO;
import dao.CompraDAOMySQL;
import modelo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio que maneja la lógica de negocio para Compras.
 */
public class CompraService {
    
    private final CompraDAO compraDAO;
    
    public CompraService() {
        this.compraDAO = new CompraDAOMySQL();
    }
    
    public String registrarCompra(Compra compra) {
        // Validaciones
        if (compra.getIdProveedor() <= 0) {
            return "Error: Debe seleccionar un proveedor";
        }
        
        if (compra.getNumeroFactura() == null || compra.getNumeroFactura().trim().isEmpty()) {
            return "Error: El número de factura es obligatorio";
        }
        
        if (compra.getCategoria() == null) {
            return "Error: Debe seleccionar una categoría";
        }
        
        if (compra.getDescripcion() == null || compra.getDescripcion().trim().isEmpty()) {
            return "Error: La descripción es obligatoria";
        }
        
        if (compra.getTotal() == null || compra.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            return "Error: El total debe ser mayor a cero";
        }
        
        if (compra.getFechaCompra() == null) {
            return "Error: La fecha de compra es obligatoria";
        }
        
        if (compra.getFormaPago() == null) {
            return "Error: Debe seleccionar una forma de pago";
        }
        
        // Validaciones específicas para crédito
        if (compra.getFormaPago() == FormaPago.CREDITO) {
            if (compra.getEstadoCredito() == null) {
                return "Error: Debe especificar el estado del crédito";
            }
            
            if (compra.getEstadoCredito() == EstadoCredito.PAGADO && compra.getFechaPago() == null) {
                return "Error: Si el crédito está pagado, debe especificar la fecha de pago";
            }
            
            if (compra.getEstadoCredito() == EstadoCredito.PENDIENTE && compra.getFechaPago() != null) {
                return "Error: Un crédito pendiente no puede tener fecha de pago";
            }
        } else {
            // Para efectivo y transferencia, no debe tener estado de crédito
            if (compra.getEstadoCredito() != null) {
                return "Error: Solo las compras a crédito pueden tener estado de crédito";
            }
        }
        
        boolean exito = compraDAO.insertar(compra);
        
        if (exito) {
            return "Compra registrada exitosamente con ID: " + compra.getId();
        } else {
            return "Error: No se pudo registrar la compra";
        }
    }
    
    public String actualizarCompra(Compra compra) {
        if (compraDAO.obtenerPorId(compra.getId()) == null) {
            return "Error: No existe una compra con ID " + compra.getId();
        }
        
        // Aplicar las mismas validaciones que en registrar
        String validacion = validarDatosCompra(compra);
        if (validacion != null) {
            return validacion;
        }
        
        boolean exito = compraDAO.actualizar(compra);
        
        if (exito) {
            return "Compra actualizada exitosamente";
        } else {
            return "Error: No se pudo actualizar la compra";
        }
    }
    
    public String eliminarCompra(int id) {
        Compra compra = compraDAO.obtenerPorId(id);
        if (compra == null) {
            return "Error: No existe una compra con ID " + id;
        }
        
        boolean exito = compraDAO.eliminar(id);
        
        if (exito) {
            return "Compra eliminada exitosamente";
        } else {
            return "Error: No se pudo eliminar la compra";
        }
    }
    
    public String marcarCreditoComoPagado(int idCompra, LocalDate fechaPago) {
        Compra compra = compraDAO.obtenerPorId(idCompra);
        
        if (compra == null) {
            return "Error: No existe una compra con ID " + idCompra;
        }
        
        if (compra.getFormaPago() != FormaPago.CREDITO) {
            return "Error: Esta compra no es a crédito";
        }
        
        if (compra.getEstadoCredito() == EstadoCredito.PAGADO) {
            return "Error: Este crédito ya está marcado como pagado";
        }
        
        if (fechaPago == null) {
            return "Error: Debe especificar la fecha de pago";
        }
        
        compra.setEstadoCredito(EstadoCredito.PAGADO);
        compra.setFechaPago(fechaPago);
        
        boolean exito = compraDAO.actualizar(compra);
        
        if (exito) {
            return "Crédito marcado como pagado exitosamente";
        } else {
            return "Error: No se pudo actualizar el estado del crédito";
        }
    }
    
    private String validarDatosCompra(Compra compra) {
        if (compra.getIdProveedor() <= 0) {
            return "Error: Debe seleccionar un proveedor";
        }
        
        if (compra.getNumeroFactura() == null || compra.getNumeroFactura().trim().isEmpty()) {
            return "Error: El número de factura es obligatorio";
        }
        
        if (compra.getCategoria() == null) {
            return "Error: Debe seleccionar una categoría";
        }
        
        if (compra.getDescripcion() == null || compra.getDescripcion().trim().isEmpty()) {
            return "Error: La descripción es obligatoria";
        }
        
        if (compra.getTotal() == null || compra.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            return "Error: El total debe ser mayor a cero";
        }
        
        if (compra.getFechaCompra() == null) {
            return "Error: La fecha de compra es obligatoria";
        }
        
        if (compra.getFormaPago() == null) {
            return "Error: Debe seleccionar una forma de pago";
        }
        
        if (compra.getFormaPago() == FormaPago.CREDITO) {
            if (compra.getEstadoCredito() == null) {
                return "Error: Debe especificar el estado del crédito";
            }
            
            if (compra.getEstadoCredito() == EstadoCredito.PAGADO && compra.getFechaPago() == null) {
                return "Error: Si el crédito está pagado, debe especificar la fecha de pago";
            }
            
            if (compra.getEstadoCredito() == EstadoCredito.PENDIENTE && compra.getFechaPago() != null) {
                return "Error: Un crédito pendiente no puede tener fecha de pago";
            }
        } else {
            if (compra.getEstadoCredito() != null) {
                return "Error: Solo las compras a crédito pueden tener estado de crédito";
            }
        }
        
        return null;
    }
    
    // Métodos de consulta
    public Compra obtenerCompra(int id) {
        return compraDAO.obtenerPorId(id);
    }
    
    public List<Compra> obtenerTodasCompras() {
        return compraDAO.obtenerTodas();
    }
    
    public List<Compra> obtenerComprasPorProveedor(int idProveedor) {
        return compraDAO.obtenerPorProveedor(idProveedor);
    }
    
    public List<Compra> obtenerComprasPorCategoria(String categoria) {
        return compraDAO.obtenerPorCategoria(categoria);
    }
    
    public List<Compra> obtenerComprasPorFormaPago(FormaPago formaPago) {
        return compraDAO.obtenerPorFormaPago(formaPago);
    }
    
    public List<Compra> obtenerComprasPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return compraDAO.obtenerPorRangoFechas(fechaInicio, fechaFin);
    }
    
    public List<Compra> obtenerCreditosPendientes() {
        return compraDAO.obtenerCreditosPendientes();
    }
    
    public List<Compra> obtenerCreditosPagados() {
        return compraDAO.obtenerCreditosPagados();
    }
    
    // Métodos de cálculo
    public BigDecimal calcularTotalPorProveedor(int idProveedor) {
        return compraDAO.calcularTotalPorProveedor(idProveedor);
    }
    
    public BigDecimal calcularTotalPorCategoria(String categoria) {
        return compraDAO.calcularTotalPorCategoria(categoria);
    }
    
    public BigDecimal calcularTotalPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return compraDAO.calcularTotalPorPeriodo(fechaInicio, fechaFin);
    }
    
    public BigDecimal calcularTotalCreditosPendientes() {
        return compraDAO.calcularTotalCreditosPendientes();
    }
    
    public BigDecimal calcularPendientesPorProveedor(int idProveedor) {
        return compraDAO.calcularPendientesPorProveedor(idProveedor);
    }
}
