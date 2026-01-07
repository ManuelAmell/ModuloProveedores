package vista;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

/**
 * Pantalla de carga con imagen de fondo y texto superpuesto.
 * Se muestra al iniciar la aplicación mientras se cargan los recursos.
 * 
 * La imagen se muestra en primer plano (fondo completo) y el texto
 * (frase y barra de progreso) se superpone encima.
 * 
 * Las frases se pueden personalizar editando el archivo frases_carga.txt
 */
public class PantallaCarga extends JWindow {
    
    private static final Color BG_PRINCIPAL = new Color(25, 35, 55);
    private static final Color TEXTO_PRINCIPAL = new Color(220, 220, 220);
    private static final Color ACENTO = new Color(0, 150, 255);
    
    private JLabel lblFrase;
    private JLabel lblAutor;
    private JProgressBar progressBar;
    
    // Frases motivacionales aleatorias (por defecto)
    private static final String[] FRASES_DEFAULT = {
        "Cargando sistema de gestión...",
        "Preparando interfaz...",
        "Conectando con base de datos...",
        "Optimizando rendimiento...",
        "Inicializando componentes...",
        "Configurando preferencias...",
        "Cargando proveedores...",
        "Preparando módulos...",
        "Casi listo...",
        "Un momento por favor..."
    };
    
    private String[] frases;
    
    public PantallaCarga() {
        cargarFrasesDesdeArchivo();
        configurarVentana();
        inicializarComponentes();
    }
    
    /**
     * Carga las frases desde el archivo frases_carga.txt
     * Si no existe o hay error, usa las frases por defecto
     */
    private void cargarFrasesDesdeArchivo() {
        try {
            File archivo = new File("frases_carga.txt");
            if (archivo.exists()) {
                List<String> lineas = Files.readAllLines(archivo.toPath());
                List<String> frasesValidas = new ArrayList<>();
                
                for (String linea : lineas) {
                    linea = linea.trim();
                    // Ignorar líneas vacías y comentarios
                    if (!linea.isEmpty() && !linea.startsWith("#")) {
                        frasesValidas.add(linea);
                    }
                }
                
                if (!frasesValidas.isEmpty()) {
                    frases = frasesValidas.toArray(new String[0]);
                    if (DialogoConfiguracion.isDebugMode()) {
                        System.out.println("✓ Frases cargadas desde frases_carga.txt (" + frases.length + " frases)");
                    }
                    return;
                }
            }
        } catch (Exception e) {
            if (DialogoConfiguracion.isDebugMode()) {
                System.err.println("⚠ No se pudo cargar frases_carga.txt: " + e.getMessage());
            }
        }
        
        // Si no se pudo cargar, usar frases por defecto
        frases = FRASES_DEFAULT;
        if (DialogoConfiguracion.isDebugMode()) {
            System.out.println("ℹ Usando frases por defecto (" + frases.length + " frases)");
        }
    }
    
    private void configurarVentana() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }
    
    private void inicializarComponentes() {
        // Panel principal con imagen de fondo
        JPanel panel = new JPanel() {
            private Image imagenFondo = null;
            
            {
                // Cargar imagen de fondo
                String[] rutasImagen = {
                    "lib/logo_carga.png",
                    "lib/ModuloProveedores.png",
                    "logo_carga.png"
                };
                
                for (String ruta : rutasImagen) {
                    try {
                        File archivoImagen = new File(ruta);
                        if (archivoImagen.exists()) {
                            ImageIcon icono = new ImageIcon(ruta);
                            if (icono.getIconWidth() > 0) {
                                imagenFondo = icono.getImage();
                                if (DialogoConfiguracion.isDebugMode()) {
                                    System.out.println("✓ Imagen de fondo cargada desde: " + ruta);
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        // Continuar con la siguiente ruta
                    }
                }
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Activar antialiasing para mejor calidad
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                if (imagenFondo != null) {
                    // Dibujar imagen escalada para cubrir toda la ventana
                    g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si no hay imagen, fondo sólido
                    g2d.setColor(BG_PRINCIPAL);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        
        // Panel central con frases (centrado verticalmente)
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 60, 10, 60);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Obtener frase y autor
        String[] fraseCompleta = obtenerFraseYAutor();
        
        // Frase principal (más grande y destacada)
        lblFrase = new JLabel("<html><div style='text-align: center;'>" + fraseCompleta[0] + "</div></html>");
        lblFrase.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFrase.setForeground(Color.WHITE);
        lblFrase.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Agregar fondo semi-transparente para legibilidad
        lblFrase.setOpaque(true);
        lblFrase.setBackground(new Color(0, 0, 0, 180)); // Negro 70% opacidad
        lblFrase.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        
        panelCentro.add(lblFrase, gbc);
        
        // Autor (más pequeño y discreto)
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 60, 0, 60);
        
        lblAutor = new JLabel(fraseCompleta[1]);
        lblAutor.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblAutor.setForeground(Color.WHITE);
        lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Agregar fondo semi-transparente para legibilidad
        lblAutor.setOpaque(true);
        lblAutor.setBackground(new Color(0, 0, 0, 180)); // Negro 70% opacidad
        lblAutor.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        panelCentro.add(lblAutor, gbc);
        
        // Panel inferior con barra de progreso y versión
        JPanel panelInferior = new JPanel(new BorderLayout(0, 15));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 60, 35, 60));
        
        // Barra de progreso minimalista
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false); // Sin texto en la barra
        progressBar.setForeground(new Color(0, 120, 215)); // Azul corporativo
        progressBar.setBackground(new Color(40, 50, 70, 150));
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(480, 4)); // Barra delgada y elegante
        
        // Panel para centrar la barra
        JPanel panelBarra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBarra.setOpaque(false);
        panelBarra.add(progressBar);
        
        // Versión y nombre de la aplicación
        JPanel panelVersion = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelVersion.setOpaque(false);
        
        JLabel lblNombre = new JLabel("APROUD");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(new Color(0, 120, 215));
        
        JLabel lblSeparador = new JLabel("•");
        lblSeparador.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSeparador.setForeground(new Color(120, 130, 140));
        
        JLabel lblVersionNum = new JLabel("v2.3.0");
        lblVersionNum.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblVersionNum.setForeground(new Color(140, 150, 160));
        
        panelVersion.add(lblNombre);
        panelVersion.add(lblSeparador);
        panelVersion.add(lblVersionNum);
        
        panelInferior.add(panelBarra, BorderLayout.CENTER);
        panelInferior.add(panelVersion, BorderLayout.SOUTH);
        
        // Agregar componentes al panel principal
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelInferior, BorderLayout.SOUTH);
        
        setContentPane(panel);
    }
    
    /**
     * Obtiene una frase aleatoria del array de frases
     */
    private String obtenerFraseAleatoria() {
        Random random = new Random();
        return frases[random.nextInt(frases.length)];
    }
    
    /**
     * Obtiene una frase y su autor separados
     * @return Array con [0] = frase, [1] = autor
     */
    private String[] obtenerFraseYAutor() {
        String fraseCompleta = obtenerFraseAleatoria();
        
        // Buscar el separador " — " (guion largo)
        int separador = fraseCompleta.lastIndexOf(" — ");
        
        if (separador > 0) {
            String frase = fraseCompleta.substring(0, separador).trim();
            String autor = fraseCompleta.substring(separador + 3).trim();
            
            // Limpiar comillas de la frase si existen
            frase = frase.replaceAll("^\"|\"$", "");
            
            return new String[]{frase, "— " + autor};
        } else {
            // Si no hay autor, devolver la frase completa
            return new String[]{fraseCompleta, ""};
        }
    }
    
    /**
     * Actualiza la frase mostrada
     */
    public void actualizarFrase(String frase) {
        SwingUtilities.invokeLater(() -> {
            String[] fraseYAutor = obtenerFraseYAutor();
            lblFrase.setText("<html><div style='text-align: center;'>" + fraseYAutor[0] + "</div></html>");
            lblAutor.setText(fraseYAutor[1]);
        });
    }
    
    /**
     * Actualiza el progreso de la barra
     */
    public void actualizarProgreso(int progreso) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(progreso));
    }
    
    /**
     * Cierra la pantalla de carga con efecto de fade out
     */
    public void cerrar() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            dispose();
        });
    }
    
    /**
     * Simula una carga con progreso automático
     */
    public void simularCarga(Runnable onComplete) {
        new Thread(() -> {
            try {
                // Usar 6 pasos para la carga
                int numPasos = 6;
                
                for (int i = 0; i < numPasos; i++) {
                    final int progreso = (i + 1) * 100 / numPasos;
                    
                    SwingUtilities.invokeLater(() -> {
                        // Actualizar frase aleatoria
                        String[] fraseYAutor = obtenerFraseYAutor();
                        lblFrase.setText("<html><div style='text-align: center;'>" + fraseYAutor[0] + "</div></html>");
                        lblAutor.setText(fraseYAutor[1]);
                        
                        // Actualizar progreso
                        actualizarProgreso(progreso);
                    });
                    
                    // Tiempo de carga por paso (500ms = 3 segundos total)
                    Thread.sleep(500);
                }
                
                // Esperar un poco antes de cerrar
                Thread.sleep(200);
                
                // Cerrar y ejecutar callback
                SwingUtilities.invokeLater(() -> {
                    cerrar();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                });
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
