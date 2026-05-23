package utilidades.views;

import javax.swing.*;

import utilidades.Paleta_Colores;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PanelRedondeadoConMargen extends JPanel {
	public static final int PADDING_INTERNO = 15;
	public static final int PADDING_INTERNO_TABLAS = -2;
    public static final int RADIO_ESQUINA_ESTANDAR = 15;
    public static final int MARGEN_ESTANDAR = 5;
    public static final Color COLOR_PANEL_ESTANDAR = Paleta_Colores.CONTENEDORES.getColor();
    
    
    protected int radioEsquina;
    protected Color colorFondo;
    protected int margenExterior; // Espacio FUERA de la curva
    protected int paddingInterno; // Espacio ADENTRO de la curva (para los hijos)

    // Constructor 
    public PanelRedondeadoConMargen(int radio, Color colorFondo, int margenExterior, int paddingInterno) {
        this.radioEsquina = radio;
        this.colorFondo = colorFondo;
        this.margenExterior = margenExterior;
        this.paddingInterno = paddingInterno;
        
        setOpaque(false); 
        
        //Margen total
        int espacioTotal = margenExterior + paddingInterno;
        this.setBorder(BorderFactory.createEmptyBorder(espacioTotal, espacioTotal, espacioTotal, espacioTotal));
    }
    
    // Constructor con color personalizable
    public PanelRedondeadoConMargen(Color colorFondo) {
        this(RADIO_ESQUINA_ESTANDAR, colorFondo, MARGEN_ESTANDAR, PADDING_INTERNO);
    }
    
    // Constructor vacío por defecto
    public PanelRedondeadoConMargen() {
        this(RADIO_ESQUINA_ESTANDAR, COLOR_PANEL_ESTANDAR, MARGEN_ESTANDAR, PADDING_INTERNO);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = margenExterior;
        int y = margenExterior;
        int ancho = getWidth() - (2 * margenExterior) - 1;
        int alto = getHeight() - (2 * margenExterior) - 1;

        Shape contornoRedondeado = new RoundRectangle2D.Float(x, y, ancho, alto, radioEsquina, radioEsquina);

        // Pintar fondo
        g2.setColor(colorFondo);
        g2.fill(contornoRedondeado);
        
        // Recortar elementos que sobrasalen de dibujo
        g2.clip(contornoRedondeado);
        
        super.paint(g2);
        g2.dispose();
    }
}