package util;

import javax.swing.*;
import java.awt.*;

/**
 * Utilidad para mostrar mensajes amigables al usuario.
 * Traduce errores técnicos a lenguaje humano.
 */
public class MensajesUsuario {
    
    private static final Color BG_OSCURO = new Color(30, 42, 65);
    private static final Color COLOR_EXITO = new Color(80, 255, 120);
    private static final Color COLOR_ERROR = new Color(255, 80, 80);
    private static final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);
    private static final Color COLOR_INFO = new Color(100, 180, 255);
    
    /**
     * Muestra mensaje de éxito
     */
    public static void exito(Component parent, String mensaje) {
        configurarEstilo();
        JOptionPane.showMessageDialog(parent, mensaje, "✓ Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra mensaje de error amigable
     */
    public static void error(Component parent, String mensaje) {
        configurarEstilo();
        JOptionPane.showMessageDialog(parent, mensaje, "✗ Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra advertencia
     */
    public static void advertencia(Component parent, String mensaje) {
        configurarEstilo();
        JOptionPane.showMessageDialog(parent, mensaje, "⚠ Atención", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Muestra información
     */
    public static void info(Component parent, String mensaje) {
        configurarEstilo();
        JOptionPane.showMessageDialog(parent, mensaje, "ℹ Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Traduce errores técnicos a lenguaje humano
     */
    public static String traducirError(Exception e) {
        String mensaje = e.getMessage();
        
        if (mensaje == null) {
            return "Ocurrió un error inesperado.\nPor favor, intenta de nuevo.";
        }
        
        // Errores de conexión
        if (mensaje.contains("Connection") || mensaje.contains("SQLException") || 
            mensaje.contains("Driver no encontrado")) {
            return "No se pudo conectar a la base de datos.\n\n" +
                   "Verifica que MySQL esté corriendo y las credenciales sean correctas.";
        }
        
        // Errores de duplicados
        if (mensaje.contains("Duplicate entry")) {
            return "Este registro ya existe.\n\n" +
                   "Verifica los datos e intenta con valores diferentes.";
        }
        
        // Errores de clave foránea
        if (mensaje.contains("foreign key") || mensaje.contains("Cannot delete")) {
            return "No se puede eliminar porque tiene registros relacionados.\n\n" +
                   "Elimina primero las compras asociadas.";
        }
        
        // Errores de validación
        if (mensaje.startsWith("Error:")) {
            return mensaje.replace("Error:", "").trim();
        }
        
        // Error genérico pero legible
        return "Ocurrió un problema:\n\n" + mensaje;
    }
    
    /**
     * Confirmación con botones personalizados
     */
    public static boolean confirmar(Component parent, String mensaje) {
        configurarEstilo();
        int respuesta = JOptionPane.showConfirmDialog(
            parent,
            mensaje,
            "Confirmar acción",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return respuesta == JOptionPane.YES_OPTION;
    }
    
    /**
     * Confirmación de eliminación
     */
    public static boolean confirmarEliminacion(Component parent, String elemento) {
        configurarEstilo();
        int respuesta = JOptionPane.showConfirmDialog(
            parent,
            "¿Estás seguro de eliminar " + elemento + "?\n\nEsta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return respuesta == JOptionPane.YES_OPTION;
    }
    
    private static void configurarEstilo() {
        UIManager.put("OptionPane.background", BG_OSCURO);
        UIManager.put("Panel.background", BG_OSCURO);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(0, 150, 255));
        UIManager.put("Button.foreground", Color.WHITE);
    }
}
