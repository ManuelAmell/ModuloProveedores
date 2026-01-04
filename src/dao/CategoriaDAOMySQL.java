package dao;

import util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOMySQL implements CategoriaDAO {
    
    private final ConexionBD conexionBD;
    
    public CategoriaDAOMySQL() {
        this.conexionBD = ConexionBD.getInstance();
    }
    
    @Override
    public List<String> obtenerTodas() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre FROM categorias_compra ORDER BY es_predefinida DESC, nombre ASC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }
            
            System.out.println("Categorías obtenidas de BD: " + categorias.size());
            
        } catch (SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
            e.printStackTrace();
        }
        
        return categorias;
    }
    
    @Override
    public boolean agregar(String nombre) {
        String sql = "INSERT INTO categorias_compra (nombre, es_predefinida) VALUES (?, 0)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al agregar categoría: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean existe(String nombre) {
        String sql = "SELECT COUNT(*) FROM categorias_compra WHERE nombre = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar categoría: " + e.getMessage());
        }
        
        return false;
    }
}
