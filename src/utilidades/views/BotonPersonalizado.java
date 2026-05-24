package utilidades.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JLabel;

import utilidades.AppFont;
import utilidades.Paleta_Colores;

public class BotonPersonalizado extends PanelRedondeadoConMargen {
    
    private JLabel etiquetaTexto;

    public BotonPersonalizado(String texto, Color color) {
        super(RADIO_ESQUINA_ESTANDAR, color, 3, 10);
        this.setLayout(new BorderLayout());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        etiquetaTexto = new JLabel(texto);
        etiquetaTexto.setHorizontalAlignment(JLabel.CENTER);
        etiquetaTexto.setVerticalAlignment(JLabel.CENTER);
        etiquetaTexto.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        etiquetaTexto.setFont(AppFont.normal());

        this.add(etiquetaTexto, BorderLayout.CENTER);
    }

    public void setColorBoton(Color colorFondo) {
        this.colorFondo = colorFondo;
        this.repaint();
    }

    public void setTextColor(Color colorTexto) {
        etiquetaTexto.setForeground(colorTexto);
    }

    public void setTextoFont(Font nuevaFuente) {
        etiquetaTexto.setFont(nuevaFuente);
    }

    public void setTextoSize(float nuevoTamano) {
        Font fuenteActual = etiquetaTexto.getFont();
        etiquetaTexto.setFont(fuenteActual.deriveFont(nuevoTamano));
    }

    public void setTexto(String texto) {
        etiquetaTexto.setText(texto);
    }
}
