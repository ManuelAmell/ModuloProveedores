package dao;

import java.util.List;

public interface CategoriaDAO {
    List<String> obtenerTodas();
    boolean agregar(String nombre);
    boolean existe(String nombre);
}
