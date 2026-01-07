package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

/**
 * Diálogo de configuración para opciones de rendimiento y UX.
 * Accesible con Ctrl+L desde la ventana principal.
 */
public class DialogoConfiguracion extends JDialog {
    
    private static final Color BG_PRINCIPAL = new Color(25, 35, 55);
    private static final Color BG_PANEL = new Color(30, 42, 65);
    private static final Color BG_INPUT = new Color(45, 58, 82);
    private static final Color TEXTO_PRINCIPAL = new Color(220, 220, 220);
    private static final Color TEXTO_SECUNDARIO = new Color(160, 160, 160);
    private static final Color ACENTO = new Color(0, 150, 255);
    private static final Color BORDE = new Color(70, 85, 110);
    
    private Preferences prefs;
    
    // Opciones de rendimiento
    private JCheckBox chkAntialiasing;
    private JCheckBox chkAnimaciones;
    private JCheckBox chkRelojSegundos;
    private JCheckBox chkToastNotifications;
    private JCheckBox chkDebugMode;
    private JCheckBox chkDoubleBuffering;
    private JCheckBox chkPausarMinimizado;
    
    // Opciones de interfaz
    private JComboBox<String> cmbTamañoFuente;
    private JComboBox<String> cmbIntervaloReloj;
    private JComboBox<String> cmbVelocidadAnimacion;
    private JComboBox<String> cmbDuracionToast;
    private JCheckBox chkSonidos;
    private JCheckBox chkConfirmaciones;
    
    private boolean cambiosRealizados = false;
    private JFrame ventanaPrincipal;
    
    public DialogoConfiguracion(JFrame parent) {
        super(parent, "⚙ Configuración", true);
        this.ventanaPrincipal = parent;
        prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        
        configurarVentana();
        inicializarComponentes();
        cargarPreferencias();
    }
    
    private void configurarVentana() {
        setSize(650, 600);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BG_PRINCIPAL);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(BG_PRINCIPAL);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Configuración de Rendimiento y UX");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO_PRINCIPAL);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(10));
        
        JLabel lblSubtitulo = new JLabel("Ajusta estas opciones y presiona 'Guardar' para aplicar los cambios");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(TEXTO_SECUNDARIO);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.add(lblSubtitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        // Sección: Rendimiento
        panelPrincipal.add(crearSeccionRendimiento());
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        // Sección: Interfaz
        panelPrincipal.add(crearSeccionInterfaz());
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        // Sección: Avanzado
        panelPrincipal.add(crearSeccionAvanzado());
        panelPrincipal.add(Box.createVerticalGlue());
        
        // Botones
        panelPrincipal.add(crearPanelBotones());
        
        JScrollPane scroll = new JScrollPane(panelPrincipal);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        add(scroll);
    }
    
    private JPanel crearSeccionRendimiento() {
        JPanel panel = crearPanelSeccion("🚀 Rendimiento");
        
        chkAntialiasing = crearCheckBox(
            "Antialiasing de texto",
            "Texto más suave pero usa ~5% más CPU",
            true
        );
        
        chkAnimaciones = crearCheckBox(
            "Animaciones suaves",
            "Activar animaciones en la interfaz",
            true
        );
        
        // Velocidad de animación
        JPanel panelVelocidad = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelVelocidad.setBackground(BG_PANEL);
        panelVelocidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel lblVelocidad = new JLabel("  Velocidad:");
        lblVelocidad.setForeground(TEXTO_SECUNDARIO);
        lblVelocidad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        cmbVelocidadAnimacion = new JComboBox<>(new String[]{
            "Lenta (30 FPS)",
            "Normal (60 FPS)",
            "Rápida (120 FPS)"
        });
        cmbVelocidadAnimacion.setSelectedIndex(1);
        cmbVelocidadAnimacion.setBackground(BG_INPUT);
        cmbVelocidadAnimacion.setForeground(TEXTO_PRINCIPAL);
        
        panelVelocidad.add(lblVelocidad);
        panelVelocidad.add(cmbVelocidadAnimacion);
        
        chkDoubleBuffering = crearCheckBox(
            "Double buffering",
            "Reduce parpadeo pero usa más memoria",
            false
        );
        
        chkPausarMinimizado = crearCheckBox(
            "Pausar cuando minimizado",
            "Ahorra CPU cuando la ventana está minimizada",
            true
        );
        
        chkToastNotifications = crearCheckBox(
            "Notificaciones toast",
            "Mostrar notificaciones temporales",
            true
        );
        
        panel.add(chkAntialiasing);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkAnimaciones);
        panel.add(panelVelocidad);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkDoubleBuffering);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkPausarMinimizado);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkToastNotifications);
        
        return panel;
    }
    
    private JPanel crearSeccionInterfaz() {
        JPanel panel = crearPanelSeccion("🎨 Interfaz");
        
        // Tamaño de fuente
        JPanel panelFuente = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFuente.setBackground(BG_PANEL);
        panelFuente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel lblFuente = new JLabel("Tamaño de fuente:");
        lblFuente.setForeground(TEXTO_PRINCIPAL);
        lblFuente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        cmbTamañoFuente = new JComboBox<>(new String[]{"Pequeño", "Normal", "Grande"});
        cmbTamañoFuente.setSelectedIndex(1);
        cmbTamañoFuente.setBackground(BG_INPUT);
        cmbTamañoFuente.setForeground(TEXTO_PRINCIPAL);
        
        panelFuente.add(lblFuente);
        panelFuente.add(cmbTamañoFuente);
        
        // Intervalo de reloj
        JPanel panelReloj = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelReloj.setBackground(BG_PANEL);
        panelReloj.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel lblReloj = new JLabel("Actualización del reloj:");
        lblReloj.setForeground(TEXTO_PRINCIPAL);
        lblReloj.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        cmbIntervaloReloj = new JComboBox<>(new String[]{
            "Cada 10 segundos (muy rápido)",
            "Cada 30 segundos (recomendado)",
            "Cada 60 segundos (ahorro máximo)"
        });
        cmbIntervaloReloj.setSelectedIndex(1);
        cmbIntervaloReloj.setBackground(BG_INPUT);
        cmbIntervaloReloj.setForeground(TEXTO_PRINCIPAL);
        
        panelReloj.add(lblReloj);
        panelReloj.add(cmbIntervaloReloj);
        
        chkRelojSegundos = crearCheckBox(
            "Mostrar segundos en el reloj",
            "Formato HH:mm:ss (usa más CPU)",
            false
        );
        
        // Duración de toast
        JPanel panelToast = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelToast.setBackground(BG_PANEL);
        panelToast.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel lblToast = new JLabel("Duración notificaciones:");
        lblToast.setForeground(TEXTO_PRINCIPAL);
        lblToast.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        cmbDuracionToast = new JComboBox<>(new String[]{
            "2 segundos (rápido)",
            "3 segundos (normal)",
            "5 segundos (lento)"
        });
        cmbDuracionToast.setSelectedIndex(1);
        cmbDuracionToast.setBackground(BG_INPUT);
        cmbDuracionToast.setForeground(TEXTO_PRINCIPAL);
        
        panelToast.add(lblToast);
        panelToast.add(cmbDuracionToast);
        
        chkSonidos = crearCheckBox(
            "Sonidos de notificación",
            "Reproducir sonido al mostrar notificaciones",
            false
        );
        
        chkConfirmaciones = crearCheckBox(
            "Confirmar antes de eliminar",
            "Pedir confirmación antes de eliminar registros",
            true
        );
        
        panel.add(panelFuente);
        panel.add(Box.createVerticalStrut(10));
        panel.add(panelReloj);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkRelojSegundos);
        panel.add(Box.createVerticalStrut(10));
        panel.add(panelToast);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkSonidos);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkConfirmaciones);
        
        return panel;
    }
    
    private JPanel crearSeccionAvanzado() {
        JPanel panel = crearPanelSeccion("🔧 Avanzado");
        
        chkDebugMode = crearCheckBox(
            "Modo debug",
            "Mostrar información de depuración en consola",
            false
        );
        
        JButton btnRestaurar = new JButton("Restaurar valores por defecto");
        btnRestaurar.setBackground(BG_INPUT);
        btnRestaurar.setForeground(TEXTO_PRINCIPAL);
        btnRestaurar.setFocusPainted(false);
        btnRestaurar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        btnRestaurar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRestaurar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRestaurar.addActionListener(e -> restaurarDefecto());
        
        panel.add(chkDebugMode);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnRestaurar);
        
        return panel;
    }
    
    private JPanel crearPanelSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(ACENTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        
        return panel;
    }
    
    private JCheckBox crearCheckBox(String texto, String tooltip, boolean seleccionado) {
        JCheckBox checkbox = new JCheckBox(texto);
        checkbox.setSelected(seleccionado);
        checkbox.setBackground(BG_PANEL);
        checkbox.setForeground(TEXTO_PRINCIPAL);
        checkbox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        checkbox.setFocusPainted(false);
        checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkbox.setToolTipText(tooltip);
        return checkbox;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(BG_PRINCIPAL);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton btnCancelar = crearBoton("Cancelar", BG_INPUT);
        JButton btnGuardar = crearBoton("💾 Guardar y Aplicar", ACENTO);
        
        // Hacer el botón guardar más grande y destacado
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        
        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarYCerrar());
        
        panel.add(btnCancelar);
        panel.add(btnGuardar);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
    
    private void cargarPreferencias() {
        chkAntialiasing.setSelected(prefs.getBoolean("antialiasing", false)); // OFF para rendimiento
        chkAnimaciones.setSelected(prefs.getBoolean("animaciones", false)); // OFF para rendimiento
        chkRelojSegundos.setSelected(prefs.getBoolean("relojSegundos", false));
        chkToastNotifications.setSelected(prefs.getBoolean("toastNotifications", true));
        chkDebugMode.setSelected(prefs.getBoolean("debugMode", false));
        chkDoubleBuffering.setSelected(prefs.getBoolean("doubleBuffering", false));
        chkPausarMinimizado.setSelected(prefs.getBoolean("pausarMinimizado", true));
        chkSonidos.setSelected(prefs.getBoolean("sonidos", false));
        chkConfirmaciones.setSelected(prefs.getBoolean("confirmaciones", true));
        
        cmbTamañoFuente.setSelectedIndex(prefs.getInt("tamañoFuente", 1));
        cmbIntervaloReloj.setSelectedIndex(prefs.getInt("intervaloReloj", 2)); // 60s para rendimiento
        cmbVelocidadAnimacion.setSelectedIndex(prefs.getInt("velocidadAnimacion", 0)); // 30 FPS para rendimiento
        cmbDuracionToast.setSelectedIndex(prefs.getInt("duracionToast", 0)); // 2s para rendimiento
    }
    
    private void guardarYCerrar() {
        // Guardar todas las preferencias
        prefs.putBoolean("antialiasing", chkAntialiasing.isSelected());
        prefs.putBoolean("animaciones", chkAnimaciones.isSelected());
        prefs.putBoolean("relojSegundos", chkRelojSegundos.isSelected());
        prefs.putBoolean("toastNotifications", chkToastNotifications.isSelected());
        prefs.putBoolean("debugMode", chkDebugMode.isSelected());
        prefs.putBoolean("doubleBuffering", chkDoubleBuffering.isSelected());
        prefs.putBoolean("pausarMinimizado", chkPausarMinimizado.isSelected());
        prefs.putBoolean("sonidos", chkSonidos.isSelected());
        prefs.putBoolean("confirmaciones", chkConfirmaciones.isSelected());
        
        prefs.putInt("tamañoFuente", cmbTamañoFuente.getSelectedIndex());
        prefs.putInt("intervaloReloj", cmbIntervaloReloj.getSelectedIndex());
        prefs.putInt("velocidadAnimacion", cmbVelocidadAnimacion.getSelectedIndex());
        prefs.putInt("duracionToast", cmbDuracionToast.getSelectedIndex());
        
        cambiosRealizados = true;
        
        // Aplicar cambios que pueden actualizarse sin reiniciar
        if (ventanaPrincipal instanceof VentanaUnificada) {
            VentanaUnificada ventana = (VentanaUnificada) ventanaPrincipal;
            
            // Actualizar reloj si cambió el intervalo o formato
            ventana.reiniciarReloj();
            ventana.actualizarFormatoReloj();
            
            if (DialogoConfiguracion.isDebugMode()) {
                System.out.println("✓ Configuración aplicada y guardada");
            }
        }
        
        // Mostrar confirmación
        ToastNotification.mostrar(ventanaPrincipal,
            "Configuración guardada correctamente",
            ToastNotification.Tipo.EXITO);
        
        dispose();
    }
    
    private void restaurarDefecto() {
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Restaurar todos los valores por defecto?\n(Optimizados para rendimiento)",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            chkAntialiasing.setSelected(false); // OFF para rendimiento
            chkAnimaciones.setSelected(false); // OFF para rendimiento
            chkRelojSegundos.setSelected(false);
            chkToastNotifications.setSelected(true);
            chkDebugMode.setSelected(false);
            chkDoubleBuffering.setSelected(false);
            chkPausarMinimizado.setSelected(true);
            chkSonidos.setSelected(false);
            chkConfirmaciones.setSelected(true);
            
            cmbTamañoFuente.setSelectedIndex(1);
            cmbIntervaloReloj.setSelectedIndex(2); // 60s para rendimiento
            cmbVelocidadAnimacion.setSelectedIndex(0); // 30 FPS para rendimiento
            cmbDuracionToast.setSelectedIndex(0); // 2s para rendimiento
        }
    }
    
    public boolean huboCambiosRealizados() {
        return cambiosRealizados;
    }
    
    // Métodos estáticos para acceder a preferencias desde otras clases
    public static boolean isAntialiasingEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("antialiasing", false); // OFF por defecto
    }
    
    public static boolean isAnimacionesEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("animaciones", false); // OFF por defecto
    }
    
    public static boolean isToastEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("toastNotifications", true);
    }
    
    public static int getIntervaloReloj() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        int index = prefs.getInt("intervaloReloj", 2); // 60s por defecto
        return index == 0 ? 10000 : (index == 1 ? 30000 : 60000); // ms
    }
    
    public static boolean isDebugMode() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("debugMode", false);
    }
    
    public static boolean isDoubleBufferingEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("doubleBuffering", false);
    }
    
    public static boolean isPausarMinimizadoEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("pausarMinimizado", true);
    }
    
    public static boolean isSonidosEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("sonidos", false);
    }
    
    public static boolean isConfirmacionesEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("confirmaciones", true);
    }
    
    public static boolean isRelojSegundosEnabled() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getBoolean("relojSegundos", false);
    }
    
    /**
     * Obtiene el intervalo de animación en milisegundos según la velocidad configurada.
     * @return Intervalo en ms: 33ms (30 FPS), 16ms (60 FPS), 8ms (120 FPS)
     */
    public static int getIntervaloAnimacion() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        int index = prefs.getInt("velocidadAnimacion", 0); // 0 = 30 FPS por defecto (rendimiento)
        return index == 0 ? 33 : (index == 1 ? 16 : 8); // ms
    }
    
    /**
     * Obtiene la duración de las notificaciones toast en milisegundos.
     * @return Duración en ms: 2000, 3000, o 5000
     */
    public static int getDuracionToast() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        int index = prefs.getInt("duracionToast", 0); // 0 = 2 segundos por defecto (rendimiento)
        return index == 0 ? 2000 : (index == 1 ? 3000 : 5000); // ms
    }
    
    /**
     * Obtiene el tamaño de fuente configurado.
     * @return 0 = Pequeño, 1 = Normal, 2 = Grande
     */
    public static int getTamañoFuente() {
        Preferences prefs = Preferences.userNodeForPackage(DialogoConfiguracion.class);
        return prefs.getInt("tamañoFuente", 1); // 1 = Normal por defecto
    }
}
