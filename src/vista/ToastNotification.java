package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Notificación temporal estilo "toast" para feedback visual inmediato.
 * Aparece en la esquina inferior derecha y desaparece automáticamente.
 */
public class ToastNotification extends JWindow {
    
    public enum Tipo {
        EXITO(new Color(80, 255, 120), "✓"),
        ERROR(new Color(255, 80, 80), "✗"),
        INFO(new Color(100, 180, 255), "ℹ"),
        ADVERTENCIA(new Color(255, 193, 7), "⚠");
        
        final Color color;
        final String icono;
        
        Tipo(Color color, String icono) {
            this.color = color;
            this.icono = icono;
        }
    }
    
    public ToastNotification(JFrame parent, String mensaje, Tipo tipo) {
        // Verificar si los toast están habilitados
        if (!DialogoConfiguracion.isToastEnabled()) {
            return;
        }
        
        setAlwaysOnTop(true);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 42, 65));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(tipo.color, 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Icono
        JLabel lblIcono = new JLabel(tipo.icono);
        lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblIcono.setForeground(tipo.color);
        
        // Mensaje
        JLabel lblMensaje = new JLabel("<html><div style='width: 250px;'>" + mensaje + "</div></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensaje.setForeground(Color.WHITE);
        
        panel.add(lblIcono, BorderLayout.WEST);
        panel.add(lblMensaje, BorderLayout.CENTER);
        
        setContentPane(panel);
        pack();
        
        // Posicionar en esquina inferior derecha
        if (parent != null && parent.isVisible()) {
            Point parentLocation = parent.getLocationOnScreen();
            Dimension parentSize = parent.getSize();
            int x = parentLocation.x + parentSize.width - getWidth() - 20;
            int y = parentLocation.y + parentSize.height - getHeight() - 60;
            setLocation(x, y);
        } else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = screenSize.width - getWidth() - 20;
            int y = screenSize.height - getHeight() - 60;
            setLocation(x, y);
        }
        
        // Obtener duración desde configuración
        int duracion = DialogoConfiguracion.getDuracionToast();
        
        // Auto-cerrar después de la duración configurada
        Timer timer = new Timer(duracion, e -> {
            setVisible(false);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    public static void mostrar(JFrame parent, String mensaje, Tipo tipo) {
        // Verificar si los toast están habilitados antes de crear
        if (!DialogoConfiguracion.isToastEnabled()) {
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            ToastNotification toast = new ToastNotification(parent, mensaje, tipo);
            toast.setVisible(true);
        });
    }
}
