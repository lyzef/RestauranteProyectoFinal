package utilidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GeneradorIconos {

	//Ruta ejemplo "/assets/image/aplicaciones.png"
    public static ImageIcon cargarIcono(String rutaRelativa) {
        URL urlRecurso = GeneradorIconos.class.getResource(rutaRelativa);
        
        if (urlRecurso == null) {
            System.err.println("No se pudo encontrar el recurso en la ruta: " + rutaRelativa);
            return null; 
        }
        
        return new ImageIcon(urlRecurso);
    }
    
    public static ImageIcon cargarIcono(String rutaRelativa,Color color) {
        URL urlRecurso = GeneradorIconos.class.getResource(rutaRelativa);
        
        if (urlRecurso == null) {
            System.err.println("No se pudo encontrar el recurso en la ruta: " + rutaRelativa);
            return null; 
        }
        
        return cambiarColorImageIcon(new ImageIcon(urlRecurso), color);
        
    }
    
    public static void aplicarIcono(String rutaRelativa, JLabel label) {
    	URL urlRecurso = GeneradorIconos.class.getResource(rutaRelativa);
        
        if (urlRecurso == null) {
            System.err.println("No se pudo encontrar el recurso en la ruta: " + rutaRelativa);
            label.setText("Icono");
            return;
        }    	
        label.setIcon(new ImageIcon(urlRecurso));
    }
    
    public static void aplicarIcono(String rutaRelativa, JLabel label,Color color) {
    	URL urlRecurso = GeneradorIconos.class.getResource(rutaRelativa);
        
        if (urlRecurso == null) {
            System.err.println("No se pudo encontrar el recurso en la ruta: " + rutaRelativa);
            label.setText("Icono");
            return;
        }  
        
        label.setIcon(cambiarColorImageIcon(new ImageIcon(urlRecurso), color));
    }
    
    public static ImageIcon cambiarColorImageIcon(ImageIcon iconoOriginal, Color nuevoColor) {
        Image img = iconoOriginal.getImage();

        BufferedImage imagenOriginal = new BufferedImage(
            img.getWidth(null), 
            img.getHeight(null), 
            BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g = imagenOriginal.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        int ancho = imagenOriginal.getWidth();
        int alto = imagenOriginal.getHeight();
        BufferedImage imagenModificada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        int rgbNuevo = nuevoColor.getRGB() & 0x00FFFFFF;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixelActual = imagenOriginal.getRGB(x, y);
                int alfa = (pixelActual >> 24) & 0xff;

                if (alfa > 0) {
                    int nuevoPixel = (alfa << 24) | rgbNuevo;
                    imagenModificada.setRGB(x, y, nuevoPixel);
                } else {
                    imagenModificada.setRGB(x, y, pixelActual);
                }
            }
        }

        return new ImageIcon(imagenModificada);
    }
    
}