/*
 * ============================================================
 * CLASE: Main.java
 * ============================================================
 * 
 * DESCRIPCIÓN:
 * Esta es la clase PRINCIPAL de la aplicación.
 * Contiene el método main(), que es el punto de entrada
 * cuando ejecutamos el programa.
 * 
 * ¿QUÉ ES EL MÉTODO main()?
 * El método main() es especial en Java:
 * - Es el primer método que se ejecuta
 * - Siempre tiene la misma firma (signature):
 *   public static void main(String[] args)
 * 
 * Desglose de la firma:
 * - public: Accesible desde fuera de la clase
 * - static: Se puede llamar sin crear un objeto de la clase
 * - void: No retorna ningún valor
 * - main: El nombre que Java busca como punto de entrada
 * - String[] args: Argumentos de línea de comandos
 * 
 * ============================================================
 */

// Un paquete por defecto (predeterminado) está bien para la clase Main
// También podrías usar: package aplicacion;

// Importamos Swing para crear la interfaz
import javax.swing.*;

// Importamos nuestra ventana unificada
import vista.VentanaUnificada;

/**
 * Clase principal que inicia la aplicación.
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class Main {

    /**
     * Método principal - Punto de entrada de la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no los usamos aquí)
     */
    public static void main(String[] args) {
        /*
         * ========================================================
         * SWING Y EL EVENT DISPATCH THREAD (EDT)
         * ========================================================
         * 
         * Swing tiene un hilo especial llamado "Event Dispatch Thread"
         * (EDT) que maneja todos los eventos de la interfaz gráfica:
         * - Clicks de botones
         * - Escritura en campos de texto
         * - Redibujado de la ventana
         * - etc.
         * 
         * REGLA IMPORTANTE:
         * Todas las operaciones de Swing deben ejecutarse en el EDT.
         * 
         * ¿POR QUÉ?
         * Swing no es "thread-safe", lo que significa que si
         * múltiples hilos acceden a la GUI al mismo tiempo,
         * pueden ocurrir errores impredecibles.
         * 
         * ¿CÓMO LO HACEMOS?
         * Usamos SwingUtilities.invokeLater() para enviar código
         * al EDT para que se ejecute cuando esté listo.
         */

        // Primero, configuramos el Look and Feel (apariencia)
        configurarLookAndFeel();

        /*
         * invokeLater() encola una tarea para ejecutarse en el EDT.
         * 
         * El parámetro es un Runnable, que es una interfaz funcional
         * con un solo método: run().
         * 
         * Usamos una expresión lambda () -> { ... } como forma
         * corta de crear un Runnable.
         * 
         * Forma larga equivalente:
         * SwingUtilities.invokeLater(new Runnable() {
         * 
         * @Override
         * public void run() {
         * iniciarAplicacion();
         * }
         * });
         */
        SwingUtilities.invokeLater(() -> {
            // Este código se ejecuta en el Event Dispatch Thread
            iniciarAplicacion();
        });
    }

    /**
     * Configura el Look and Feel de la aplicación.
     * 
     * El "Look and Feel" (L&F) determina la apariencia visual
     * de los componentes de Swing (botones, campos, etc.)
     * 
     * Opciones comunes:
     * - Metal: El L&F por defecto de Java (se ve igual en todos los SO)
     * - Nimbus: L&F moderno incluido en Java
     * - System: Usa el L&F nativo del sistema operativo
     */
    private static void configurarLookAndFeel() {
        try {
            /*
             * Intentamos usar el Look and Feel del sistema operativo.
             * Esto hace que la aplicación se vea "nativa":
             * - En Windows: apariencia de Windows
             * - En macOS: apariencia de Mac
             * - En Linux: apariencia de GTK o similar
             */

            /*
             * UIManager es la clase que controla la apariencia.
             * getSystemLookAndFeelClassName() devuelve el nombre
             * de la clase del L&F nativo del sistema.
             */
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();

            /*
             * setLookAndFeel() aplica el L&F especificado.
             */
            UIManager.setLookAndFeel(lookAndFeel);

            System.out.println("Look and Feel aplicado: " + lookAndFeel);

        } catch (Exception e) {
            /*
             * Si falla (por cualquier razón), usamos el L&F por defecto.
             * 
             * Capturamos Exception genérica porque setLookAndFeel()
             * puede lanzar varios tipos de excepciones:
             * - ClassNotFoundException
             * - InstantiationException
             * - IllegalAccessException
             * - UnsupportedLookAndFeelException
             */
            System.err.println("No se pudo aplicar el Look and Feel del sistema.");
            System.err.println("Usando Look and Feel por defecto.");
            // No hacemos nada más, se usará el L&F por defecto (Metal)
        }
    }

    /**
     * Inicializa y muestra la aplicación.
     * 
     * Este método se ejecuta en el Event Dispatch Thread.
     */
    private static void iniciarAplicacion() {
        System.out.println("========================================");
        System.out.println("  SISTEMA DE GESTIÓN - Iniciando...  ");
        System.out.println("========================================");
        System.out.println();

        try {
            // Crear la ventana unificada con tema oscuro
            vista.VentanaUnificada ventana = new vista.VentanaUnificada();
            ventana.setVisible(true);

            System.out.println("Aplicación iniciada correctamente.");
            System.out.println("Ventana unificada con tema oscuro activa.");
            System.out.println();

        } catch (Exception e) {
            System.err.println("ERROR: No se pudo iniciar la aplicación.");
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Error al iniciar la aplicación:\n" + e.getMessage(),
                    "Error Fatal",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }
}
