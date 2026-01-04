package dao;

import modelo.*;
import util.ConexionBD;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de CompraDAO usando MySQL.
 */
public class CompraDAOMySQL implements CompraDAO {
    
    private final ConexionBD conexionBD;
    
    public CompraDAOMySQL() {
        this.conexionBD = ConexionBD.getInstance();
    }
    
    @Override
    public boolean insertar(Compra compra) {
        String sql = "INSERT INTO compras (id_proveedor, numero_factura, categoria, descripcion, " +
                     "cantidad, precio_unitario, total, fecha_compra, forma_pago, estado_credito, fecha_pago) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, compra.getIdProveedor());
            stmt.setString(2, compra.getNumeroFactura());
            stmt.setString(3, compra.getCategoria());
            stmt.setString(4, compra.getDescripcion());
            
            if (compra.getCantidad() != null) {
                stmt.setInt(5, compra.getCantidad());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            
            if (compra.getPrecioUnitario() != null) {
                stmt.setBigDecimal(6, compra.getPrecioUnitario());
            } else {
                stmt.setNull(6, Types.DECIMAL);
            }
            
            stmt.setBigDecimal(7, compra.getTotal());
            stmt.setDate(8, Date.valueOf(compra.getFechaCompra()));
            stmt.setString(9, compra.getFormaPago().getValor());
            
            if (compra.getEstadoCredito() != null) {
                stmt.setString(10, compra.getEstadoCredito().getValor());
            } else {
                stmt.setNull(10, Types.VARCHAR);
            }
            
            if (compra.getFechaPago() != null) {
                stmt.setDate(11, Date.valueOf(compra.getFechaPago()));
            } else {
                stmt.setNull(11, Types.DATE);
            }
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        compra.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar compra: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public Compra obtenerPorId(int id) {
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.id = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener compra por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Compra> obtenerTodas() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "ORDER BY c.fecha_compra DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                compras.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las compras: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public boolean actualizar(Compra compra) {
        String sql = "UPDATE compras SET id_proveedor = ?, numero_factura = ?, categoria = ?, " +
                     "descripcion = ?, cantidad = ?, precio_unitario = ?, total = ?, " +
                     "fecha_compra = ?, forma_pago = ?, estado_credito = ?, fecha_pago = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, compra.getIdProveedor());
            stmt.setString(2, compra.getNumeroFactura());
            stmt.setString(3, compra.getCategoria());
            stmt.setString(4, compra.getDescripcion());
            
            if (compra.getCantidad() != null) {
                stmt.setInt(5, compra.getCantidad());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            
            if (compra.getPrecioUnitario() != null) {
                stmt.setBigDecimal(6, compra.getPrecioUnitario());
            } else {
                stmt.setNull(6, Types.DECIMAL);
            }
            
            stmt.setBigDecimal(7, compra.getTotal());
            stmt.setDate(8, Date.valueOf(compra.getFechaCompra()));
            stmt.setString(9, compra.getFormaPago().getValor());
            
            if (compra.getEstadoCredito() != null) {
                stmt.setString(10, compra.getEstadoCredito().getValor());
            } else {
                stmt.setNull(10, Types.VARCHAR);
            }
            
            if (compra.getFechaPago() != null) {
                stmt.setDate(11, Date.valueOf(compra.getFechaPago()));
            } else {
                stmt.setNull(11, Types.DATE);
            }
            
            stmt.setInt(12, compra.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar compra: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM compras WHERE id = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar compra: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public List<Compra> obtenerPorProveedor(int idProveedor) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.id_proveedor = ? " +
                     "ORDER BY c.fecha_compra DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idProveedor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    compras.add(mapearResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener compras por proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public List<Compra> obtenerPorCategoria(String categoria) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.categoria = ? " +
                     "ORDER BY c.fecha_compra DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    compras.add(mapearResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener compras por categoría: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public List<Compra> obtenerPorFormaPago(FormaPago formaPago) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.forma_pago = ? " +
                     "ORDER BY c.fecha_compra DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, formaPago.getValor());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    compras.add(mapearResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener compras por forma de pago: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public List<Compra> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.fecha_compra BETWEEN ? AND ? " +
                     "ORDER BY c.fecha_compra DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    compras.add(mapearResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener compras por rango de fechas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public List<Compra> obtenerCreditosPendientes() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.forma_pago = 'credito' AND c.estado_credito = 'pendiente' " +
                     "ORDER BY c.fecha_compra ASC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                compras.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener créditos pendientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public List<Compra> obtenerCreditosPagados() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_proveedor " +
                     "FROM compras c " +
                     "INNER JOIN proveedores p ON c.id_proveedor = p.id " +
                     "WHERE c.forma_pago = 'credito' AND c.estado_credito = 'pagado' " +
                     "ORDER BY c.fecha_pago DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                compras.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener créditos pagados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return compras;
    }
    
    @Override
    public BigDecimal calcularTotalPorProveedor(int idProveedor) {
        String sql = "SELECT COALESCE(SUM(total), 0) as total FROM compras WHERE id_proveedor = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idProveedor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al calcular total por proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calcularPendientesPorProveedor(int idProveedor) {
        // Calcular pendientes del proveedor: créditos pendientes + efectivo/transferencia sin fecha
        String sql = "SELECT COALESCE(SUM(total), 0) as total FROM compras " +
                     "WHERE id_proveedor = ? AND " +
                     "((forma_pago = 'credito' AND estado_credito = 'pendiente') " +
                     "OR (forma_pago IN ('efectivo', 'transferencia') AND fecha_pago IS NULL))";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idProveedor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al calcular pendientes por proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calcularTotalPorCategoria(String categoria) {
        String sql = "SELECT COALESCE(SUM(total), 0) as total FROM compras WHERE categoria = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al calcular total por categoría: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calcularTotalPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        String sql = "SELECT COALESCE(SUM(total), 0) as total FROM compras " +
                     "WHERE fecha_compra BETWEEN ? AND ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al calcular total por período: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calcularTotalCreditosPendientes() {
        // Calcular TODOS los pendientes: créditos, efectivo y transferencias sin fecha de pago
        String sql = "SELECT COALESCE(SUM(total), 0) as total FROM compras " +
                     "WHERE (forma_pago = 'credito' AND estado_credito = 'pendiente') " +
                     "OR (forma_pago IN ('efectivo', 'transferencia') AND fecha_pago IS NULL)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al calcular total créditos pendientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    private Compra mapearResultSet(ResultSet rs) throws SQLException {
        Compra compra = new Compra();
        compra.setId(rs.getInt("id"));
        compra.setIdProveedor(rs.getInt("id_proveedor"));
        compra.setNombreProveedor(rs.getString("nombre_proveedor"));
        compra.setNumeroFactura(rs.getString("numero_factura"));
        compra.setCategoria(rs.getString("categoria"));
        compra.setDescripcion(rs.getString("descripcion"));
        
        int cantidad = rs.getInt("cantidad");
        if (!rs.wasNull()) {
            compra.setCantidad(cantidad);
        }
        
        BigDecimal precioUnitario = rs.getBigDecimal("precio_unitario");
        if (precioUnitario != null) {
            compra.setPrecioUnitario(precioUnitario);
        }
        
        compra.setTotal(rs.getBigDecimal("total"));
        compra.setFechaCompra(rs.getDate("fecha_compra").toLocalDate());
        compra.setFormaPago(FormaPago.fromValor(rs.getString("forma_pago")));
        
        String estadoCredito = rs.getString("estado_credito");
        if (estadoCredito != null) {
            compra.setEstadoCredito(EstadoCredito.fromValor(estadoCredito));
        }
        
        Date fechaPago = rs.getDate("fecha_pago");
        if (fechaPago != null) {
            compra.setFechaPago(fechaPago.toLocalDate());
        }
        
        return compra;
    }
}
