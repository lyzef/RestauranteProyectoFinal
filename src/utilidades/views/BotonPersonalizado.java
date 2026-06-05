package utilidades.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import utilidades.AppFont;
import utilidades.Paleta_Colores;

public class BotonPersonalizado extends PanelRedondeadoConMargen {
    
    private JLabel etiquetaTexto;
    private Color colorOriginal;
    private Color colorPresionado;
    private Color colorHover;
    private boolean presionado = false;
    private boolean hover = false;

    public BotonPersonalizado(String texto, Color color) {
        super(RADIO_ESQUINA_ESTANDAR, color, 3, 10);
        this.setLayout(new BorderLayout());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.colorOriginal = color;
        this.colorPresionado = color.darker().darker(); // Más oscuro para efecto de presión
        this.colorHover = color.brighter(); // Más claro para efecto hover

        etiquetaTexto = new JLabel(texto);
        etiquetaTexto.setHorizontalAlignment(JLabel.CENTER);
        etiquetaTexto.setVerticalAlignment(JLabel.CENTER);
        etiquetaTexto.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        etiquetaTexto.setFont(AppFont.normal());

        this.add(etiquetaTexto, BorderLayout.CENTER);
        
        /*
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presionado = true;
                cambiarColorFondo(colorPresionado);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                presionado = false;
                if (hover) {
                    cambiarColorFondo(colorHover);
                } else {
                    cambiarColorFondo(colorOriginal);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                if (!presionado) {
                    cambiarColorFondo(colorHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                if (!presionado) {
                    cambiarColorFondo(colorOriginal);
                } else {
                    cambiarColorFondo(colorPresionado);
                }
            }
        });
        */
    }

    private void cambiarColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
        this.repaint();
    }

    public void setColorBoton(Color colorFondo) {
        this.colorOriginal = colorFondo;
        this.colorPresionado = colorFondo.darker().darker();
        this.colorHover = colorFondo.brighter();
        
        if (!presionado && !hover) {
            cambiarColorFondo(colorFondo);
        } else if (presionado) {
            cambiarColorFondo(colorPresionado);
        } else if (hover) {
            cambiarColorFondo(colorHover);
        }
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
    
    public void simularClick() {
        // Simular presión
        cambiarColorFondo(colorPresionado);
        
        // Programar liberación después de 100ms
        new Thread(() -> {
            try {
                Thread.sleep(100);
                SwingUtilities.invokeLater(() -> {
                    cambiarColorFondo(colorOriginal);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}