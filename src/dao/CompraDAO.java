package dao;

import modelo.Compra;
import modelo.FormaPago;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para Compra.
 */
public interface CompraDAO {
    
    // Operaciones CRUD básicas
    boolean insertar(Compra compra);
    Compra obtenerPorId(int id);
    List<Compra> obtenerTodas();
    boolean actualizar(Compra compra);
    boolean eliminar(int id);
    
    // Consultas específicas
    List<Compra> obtenerPorProveedor(int idProveedor);
    List<Compra> obtenerPorCategoria(String categoria);
    List<Compra> obtenerPorFormaPago(FormaPago formaPago);
    List<Compra> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    List<Compra> obtenerCreditosPendientes();
    List<Compra> obtenerCreditosPagados();
    
    // Cálculos y estadísticas
    BigDecimal calcularTotalPorProveedor(int idProveedor);
    BigDecimal calcularPendientesPorProveedor(int idProveedor);  // Nuevo método
    BigDecimal calcularTotalPorCategoria(String categoria);
    BigDecimal calcularTotalPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    BigDecimal calcularTotalCreditosPendientes();
}
