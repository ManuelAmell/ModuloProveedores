package servicio;

import dao.CategoriaDAO;
import dao.CategoriaDAOMySQL;
import java.util.List;

/**
 * Servicio para gestionar categorías dinámicas de compras.
 */
public class CategoriaService {
    
    private final CategoriaDAO categoriaDAO;
    
    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAOMySQL();
    }
    
    public List<String> obtenerTodasCategorias() {
        return categoriaDAO.obtenerTodas();
    }
    
    public String agregarCategoria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Error: El nombre de la categoría no puede estar vacío";
        }
        
        String nombreNormalizado = nombre.trim().toLowerCase();
        
        if (categoriaDAO.existe(nombreNormalizado)) {
            return "Error: La categoría ya existe";
        }
        
        boolean exito = categoriaDAO.agregar(nombreNormalizado);
        
        if (exito) {
            return "Categoría agregada exitosamente";
        } else {
            return "Error: No se pudo agregar la categoría";
        }
    }
    
    public boolean existeCategoria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return categoriaDAO.existe(nombre.trim().toLowerCase());
    }
}
