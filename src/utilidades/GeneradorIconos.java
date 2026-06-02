package utilidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    
    /*
     * Solo funciona para imagenes externas
     */
    public String seleccionarYGuardarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Extensiones permitidas
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showOpenDialog(null);

        // Sigue 
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoOrigen = fileChooser.getSelectedFile();
            
            try {
            	String rutaBase = System.getProperty("user.dir"); 

            	// Crea una carpeta llamada "datos_restaurante" junto al programa
            	String carpetaDestino = rutaBase + File.separator + "datos_restaurante" + File.separator + "imagenes";
            	Path rutaDirectorio = Paths.get(carpetaDestino);

            	// Si no existe, la crea
            	if (!Files.exists(rutaDirectorio)) {
            	    Files.createDirectories(rutaDirectorio);
            	}
                String nombreOriginal = archivoOrigen.getName();
                String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));

                String nombreNuevo = UUID.randomUUID().toString() + extension;

                Path rutaDestino = Paths.get(carpetaDestino, nombreNuevo);

                Files.copy(archivoOrigen.toPath(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
                
                //Nombre del archivo
                return nombreNuevo; 

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar la imagen: " + e.getMessage());
            }
        }
        
        return null; 
    }
    
    // Para imagenes externas
    public ImageIcon obtenerIconoPlatillo(String nombreImagen, int ancho, int alto) {
        if (nombreImagen == null || nombreImagen.trim().isEmpty()) {
            return cargarIcono("/assets/image/exclamacion.png"); // Imagen por defecto en caso de no existir
        }

        // Reconstruir ruta
        String rutaBase = System.getProperty("user.dir");
        Path rutaDestino = Paths.get(rutaBase, "datos_restaurante", "imagenes", nombreImagen);
        File archivoImagen = rutaDestino.toFile();

        
        if (!archivoImagen.exists()) {
            System.err.println("No se encontró la imagen en la ruta " + archivoImagen.getAbsolutePath());
            return cargarIcono("/assets/image/exclamacion.png");
        }

        ImageIcon iconoOriginal = new ImageIcon(archivoImagen.getAbsolutePath());

        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);

        iconoFinal.setDescription(nombreImagen);

        return new ImageIcon(imagenEscalada);
    }
    
    /**
     * Obtiene todas las imágenes guardadas en el directorio local a modo de array.
     * * @param ancho El ancho deseado para escalar las imágenes.
     * @param alto El alto deseado para escalar las imágenes.
     * @return Un array de ImageIcon con todas las imágenes encontradas.
     */
    public ImageIcon[] obtenerTodasLasImagenes(int ancho, int alto) {
    	String rutaBase = System.getProperty("user.dir");
        Path rutaDirectorio = Paths.get(rutaBase, "datos_restaurante", "imagenes");
        File carpeta = rutaDirectorio.toFile();

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.err.println("No existe la carpeta de imágenes: " + carpeta.getAbsolutePath());
            return new ImageIcon[0];
        }

        File[] archivos = carpeta.listFiles((dir, nombre) -> {
            String lower = nombre.toLowerCase();
            return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg");
        });

        if (archivos == null || archivos.length == 0) {
            return new ImageIcon[0];
        }

        ImageIcon[] iconos = new ImageIcon[archivos.length];

        for (int i = 0; i < archivos.length; i++) {
            File archivo = archivos[i];
            ImageIcon iconoOriginal = new ImageIcon(archivo.getAbsolutePath());
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
            iconoFinal.setDescription(archivo.getName()); // asigna el nombre del archivo

            iconos[i] = iconoFinal;
        }

        return iconos;
    }
    
}