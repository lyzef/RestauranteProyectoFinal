package utilidades.views;

import javax.swing.*;
import java.awt.*;

public class PanelRedondeadoConMargen extends JPanel {
    private int radioEsquina;
    private Color colorFondo;
    private int margen; // El espacio que quedará libre alrededor del borde

    // Constructor actualizado
    public PanelRedondeadoConMargen(int radio, Color colorFondo, int margen) {
        this.radioEsquina = radio;
        this.colorFondo = colorFondo;
        this.margen = margen;
        
        setOpaque(false); // Sigue siendo vital para que el margen sea transparente
        int padding = margen + 15; 
        this.setBorder(BorderFactory.createEmptyBorder(padding - 10, padding, padding, padding - 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Antialiasing para bordes suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calcular la nueva posición y tamaño restando el margen
        int x = margen;
        int y = margen;
        int ancho = getWidth() - (2 * margen) - 1;
        int alto = getHeight() - (2 * margen) - 1;

        // Dibujar el fondo redondeado encogido
        g2.setColor(colorFondo);
        g2.fillRoundRect(x, y, ancho, alto, radioEsquina, radioEsquina);
        
        // Opcional: Descomenta si quieres ver una línea de contorno
        // g2.setColor(colorFondo.darker());
        // g2.drawRoundRect(x, y, ancho, alto, radioEsquina, radioEsquina);
    }
}