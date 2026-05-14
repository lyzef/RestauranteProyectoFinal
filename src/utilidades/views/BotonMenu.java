package utilidades.views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonMenu extends JPanel {
    private JLabel lblIcono;
    private JLabel lblTexto;
    
    
    private final Color COLOR_AZUL_FONDO = new Color(240, 245, 255);
    private final Color COLOR_AZUL_BORDE = new Color(20, 80, 200);
    private final Color COLOR_TEXTO_ACTIVO = new Color(40, 90, 210);
    private final Color COLOR_TEXTO_INACTIVO = new Color(120, 120, 120); // Gris cuando no hay hover

    private boolean mouseEncima = false;

    public BotonMenu(String texto, Icon icono) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el puntero a la mano

        lblIcono = new JLabel(icono);
        lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Estado inicial: Gris
        lblTexto.setForeground(COLOR_TEXTO_INACTIVO);

        add(lblIcono);
        add(lblTexto);

        efectorHover();
    }

    private void efectorHover() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEncima = true;
                lblTexto.setForeground(COLOR_TEXTO_ACTIVO); // Cambia el texto a azul
                repaint(); // Redibuja para mostrar el fondo y la barra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseEncima = false;
                lblTexto.setForeground(COLOR_TEXTO_INACTIVO); // Vuelve el texto a gris
                repaint(); // Redibuja para ocultar el fondo y la barra
            }
        });
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        // IMPORTANTE: No llamamos a super.paintComponent para manejar nosotros el fondo
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // SOLO dibujamos si el mouse está encima
        if (mouseEncima) {
            // 1. Dibujar el fondo azul claro
            g2.setColor(COLOR_AZUL_FONDO);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

            // 2. Dibujar la barra lateral azul fuerte (estilo image_e80aa0.png)
            g2.setColor(COLOR_AZUL_BORDE);
            g2.fillRoundRect(-5, 5, 10, getHeight() - 10, 10, 10);
        }

        g2.dispose();
        super.paintComponent(g); // Dibuja los hijos (texto e icono) después
    }
}