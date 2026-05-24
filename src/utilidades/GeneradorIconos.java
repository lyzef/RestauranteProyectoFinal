package utilidades;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GeneradorIconos {

	// /menu/icono.png 
    public static ImageIcon cargarIcono(String rutaRelativa) {
        URL urlRecurso = GeneradorIconos.class.getResource(rutaRelativa);
        
        if (urlRecurso == null) {
            System.err.println("No se pudo encontrar el recurso en la ruta: " + rutaRelativa);
            return null; 
        }
        
        return new ImageIcon(urlRecurso);
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

}