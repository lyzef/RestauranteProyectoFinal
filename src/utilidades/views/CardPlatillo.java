package utilidades.views;

import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.Categoria;
import models.ComponenteIngredienteReceta;
import models.Platillo;

import java.awt.*;

/**
 * Para poner imagen:
 *   card.setImagen(new ImageIcon("src/image/taco.jpg"));
 */
public class CardPlatillo extends JPanel {

    private static final Color COLOR_CARD        = new Color(30, 41, 59);
    private static final Color COLOR_TEXTO        = new Color(255, 255, 255);
    private static final Color COLOR_TEXTO_GRIS   = new Color(148, 163, 184);
    private static final Color COLOR_ACENTO       = new Color(255, 165, 0);

    private JLabel imagenPlatillo;
    private Platillo platillo;
    private JButton botonAgregar;
    
    private CardPlatillo(String nombre, String precio, String tiempo,
                        String descripcion, String badge, Color colorBadge, String urlImagen) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(crearImgPanel(badge, colorBadge, tiempo,urlImagen), BorderLayout.NORTH);
        add(crearInfoPanel(nombre, precio, descripcion),  BorderLayout.CENTER);
    }
    
    public CardPlatillo(Platillo platillo) {
    	this(platillo.getComponenteNombre(),Double.toString(platillo.getPrecioVenta()),Double.toString(platillo.getCalorias()) + " Calorias",
    			platillo.getDescripcion(),platillo.getEmblema().getValorBaseDatos(),Paleta_Colores.ATENCION.getColor(),platillo.getImagenUrl());
    	this.platillo = platillo;
    }
    
    //  PANEL IMAGEN
    private JPanel crearImgPanel(String badge, Color colorBadge, String tiempo, String urlImagen) {
        JPanel imgPanel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(30, 22, 10));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        imgPanel.setPreferredSize(new Dimension(0, 140));
        imgPanel.setOpaque(false);
        
        
        
        ImageIcon img = new GeneradorIconos().obtenerIconoPlatillo(urlImagen,80,80);
        if (img != null) {
        	imagenPlatillo = new JLabel(img);
        } else {
        	imagenPlatillo = new JLabel();
        }
        
        
        imagenPlatillo.setHorizontalAlignment(SwingConstants.CENTER);
        imagenPlatillo.setVerticalAlignment(SwingConstants.CENTER);
        imgPanel.add(imagenPlatillo, BorderLayout.CENTER);
        if (badge != null && !badge.isBlank()) {
            JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            badgePanel.setOpaque(false);
            badgePanel.add(crearBadge(badge, colorBadge != null ? colorBadge : COLOR_ACENTO));
            imgPanel.add(badgePanel, BorderLayout.NORTH);
        }
        JPanel tiempoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        tiempoPanel.setOpaque(false);
        JLabel tiempoLbl = new JLabel(tiempo);
        tiempoLbl.setFont(AppFont.bold().deriveFont(10f));
        tiempoLbl.setForeground(COLOR_TEXTO);
        tiempoLbl.setBackground(new Color(0, 0, 0, 150));
        tiempoLbl.setOpaque(true);
        tiempoLbl.setBorder(new EmptyBorder(3, 7, 3, 7));
        tiempoPanel.add(tiempoLbl);
        imgPanel.add(tiempoPanel, BorderLayout.SOUTH);

        return imgPanel;
    }
    //  PANEL INFO

    private JPanel crearInfoPanel(String nombre, String precio, String descripcion) {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(10, 12, 10, 12));

        //NOMBRE + PRECIO
        JPanel nombrePrecio = new JPanel(new BorderLayout());
        nombrePrecio.setOpaque(false);
        nombrePrecio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel nombreLbl = new JLabel("<html><b>" + nombre + "</b></html>");
        nombreLbl.setFont(AppFont.bold().deriveFont(16f));
        nombreLbl.setForeground(COLOR_TEXTO);

        JLabel precioLbl = new JLabel("$"+precio);
        precioLbl.setFont(new Font("Arial", Font.BOLD, 14));
        precioLbl.setForeground(COLOR_ACENTO);

        nombrePrecio.add(nombreLbl, BorderLayout.CENTER);
        nombrePrecio.add(precioLbl, BorderLayout.EAST);
        info.add(nombrePrecio);
        info.add(Box.createVerticalStrut(8));

        //DESCRIPCION
        JLabel descLbl = new JLabel("<html><p style='width:700px'>" + descripcion + "</p></html>");
        //JLabel descLbl = new JLabel("<html><p style='width:230px'>" + descripcion + "</p></html>");
        descLbl.setFont(AppFont.small().deriveFont(12f));
        descLbl.setForeground(COLOR_TEXTO_GRIS);
        descLbl.setAlignmentX(LEFT_ALIGNMENT);
        info.add(descLbl);
        info.add(Box.createVerticalStrut(50));
        //BOTON
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        botones.setOpaque(false);
        botones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        botones.setAlignmentX(LEFT_ALIGNMENT);

        //JButton btnRechazar = crearBotonCard("-", new Color(239, 68, 68));
        
        botonAgregar  = crearBotonCard("Agregar", Paleta_Colores.EXITO.getColor());

        //botones.add(btnRechazar);
        botones.add(botonAgregar);
        info.add(botones);

        return info;
    }

    private JButton crearBotonCard(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(AppFont.bold());
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        //btn.setPreferredSize(new Dimension(55, 38));
        btn.setPreferredSize(new Dimension(120, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ─────────────────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────────────────
    private JLabel crearBadge(String texto, Color color) {
        JLabel badge = new JLabel(texto);
        badge.setFont(AppFont.bold().deriveFont(12f));
        badge.setForeground(Color.WHITE);
        badge.setBackground(color);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(3, 7, 3, 7));
        return badge;
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(COLOR_CARD);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
        g2.dispose();
    }
    public void setImagen(ImageIcon icon) {
        imagenPlatillo.setIcon(icon);
    }

	public JButton getBotonAgregar() {
		return botonAgregar;
	}

	public Platillo getPlatillo() {
		return platillo;
	}

	public void setPlatillo(Platillo platillo) {
		this.platillo = platillo;
	}
    
	
    
}