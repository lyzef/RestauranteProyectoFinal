package views.AutoVenta;

import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.CardPlatillo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import models.ComponenteIngredienteReceta;
import models.Platillo;

import java.awt.*;

public class MenuVentaView extends JPanel {
    //  COLORES DEL TEMA
    private static final Color COLOR_FONDO         = Paleta_Colores.FONDO.getColor();
    private static final Color COLOR_ACENTO        = new Color(255, 165, 0);
    private static final Color COLOR_CARD          = Paleta_Colores.CONTENEDORES.getColor();
    private static final Color COLOR_TEXTO         = Paleta_Colores.TEXTO_PRINCIPAL.getColor();
    private static final Color COLOR_TEXTO_GRIS    = Paleta_Colores.TEXTO_SECUNDARIO.getColor();
    private static final Color COLOR_BADGE_VERDE   = Paleta_Colores.EXITO.getColor();
    private static final Color COLOR_BADGE_ROJO    = Paleta_Colores.URGENTE.getColor();
    private static final Color COLOR_BADGE_AZUL    = new Color(59, 130, 246);
    
    //  CONFIGURACIÓN DE CARDS
    private static final int CARD_ANCHO = 280;
    private static final int CARD_ALTO  = 290;
    
    // CONTENEDOR DE CATEGORIAS
    JPanel secciones;
    
    //  DATOS DEL MENÚ - PLATILLOS PRINCIPALES
    private static final String[] NOMBRES = {
        "El Taco Que Te Toca", "La Torta de Pierna Pa' Llevar", "Burrito Pa' Que Te Llenes",
        "Chile Relleno de Sabor", "El Pájaro que Quema", "Paquete de Carnitas Pa' Comer"
    };
    private static final String[] PRECIOS = {"$45", "$120", "$95", "$80", "$180", "$250"};
    private static final String[] TIEMPOS = {"15 MINS", "20 MINS", "15 MINS", "25 MINS", "30 MINS", "22 MINS"};
    private static final String[] DESCRIPCIONES = {
        "Loaded steak taco, doble tortilla con todo el sabor.",
        "Torta mexicana con pierna marinada para los valientes.",
        "Un burrito gigante relleno hasta el tope, garantizado.",
        "Chile poblano relleno de queso con mucho 'sabor'.",
        "Nuestro pollo rostizado estrella. Picante y ahumado.",
        "Plato completo de carnitas famosas. Para compartir."
    };
    private static final String[] BADGES = {"TOP RATED", "HOT ITEM", "VERIFIED", "", "BEST SELLER", "FAMILIAR"};
    private static final Color[] BADGE_COLORS = {
        COLOR_BADGE_VERDE, COLOR_BADGE_ROJO, COLOR_BADGE_AZUL, null, COLOR_ACENTO, new Color(100, 60, 180)
    };
    
    //  DATOS DEL MENÚ - BEBIDAS
    private static final String[] NOMBRES_BEBIDAS = {
        "Agua de Jamaica", "Limonada Mineral", "Horchata Fría", 
        "Refresco del Chef", "Café de Olla", "Tepache Natural"
    };
    private static final String[] PRECIOS_BEBIDAS = {"$25", "$30", "$28", "$35", "$20", "$30"};
    private static final String[] TIEMPOS_BEBIDAS = {"5 MINS", "5 MINS", "5 MINS", "5 MINS", "8 MINS", "5 MINS"};
    private static final String[] DESCRIPCIONES_BEBIDAS = {
        "Fresca agua de flor de jamaica natural.", "Limonada con gas y hierbabuena.",
        "Horchata casera bien fría.", "Refresco especial de la casa.",
        "Café de olla con canela tradicional.", "Tepache artesanal de piña fermentada."
    };
    private static final String[] BADGES_BEBIDAS = {"FAVORITA", "", "TOP", "", "ESPECIAL", "ARTESANAL"};
    private static final Color[] BADGE_COLORS_BEBIDAS = {
        new Color(239,68,68), null, new Color(16,185,129), null, COLOR_ACENTO, new Color(100,60,180)
    };
    
    //  CONSTRUCTOR
    public MenuVentaView() {
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);

        // La barra superior (TopBar) fue movida a MainFrame
        add(crearContenido(),    BorderLayout.CENTER); 
        add(crearBarraCarrito(), BorderLayout.SOUTH); 
    }
    
    //  CONTENIDO CENTRAL
    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        secciones = new JPanel();
        secciones.setLayout(new BoxLayout(secciones, BoxLayout.Y_AXIS));
        secciones.setBackground(COLOR_FONDO);
        secciones.setBorder(new EmptyBorder(15, 0, 20, 0));
        
        //secciones.add(crearSeccionCarrusel("Lo más", "Picante", crearGridPlatillos()));
        //secciones.add(Box.createVerticalStrut(30));
        //secciones.add(crearSeccionCarrusel("Nuestras", "Bebidas", crearGridBebidas()));
        //secciones.add(Box.createVerticalStrut(20));
        
        JScrollPane scrollVertical = new JScrollPane(secciones);
        scrollVertical.setOpaque(false);
        scrollVertical.getViewport().setBackground(COLOR_FONDO);
        scrollVertical.setBorder(null);
        scrollVertical.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollVertical.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollVertical.getVerticalScrollBar().setUnitIncrement(20);
        scrollVertical.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scrollVertical.getVerticalScrollBar().setBackground(COLOR_FONDO);
        scrollVertical.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = new Color(100, 130, 170);
                trackColor = COLOR_FONDO;
            }
            @Override protected JButton createDecreaseButton(int o) { return crearBotonInvisible(); }
            @Override protected JButton createIncreaseButton(int o) { return crearBotonInvisible(); }
        });

        contenido.add(scrollVertical, BorderLayout.CENTER);
        return contenido;
    }
    
    public JPanel crearGridCategoria(EventList<Platillo> platillos) {
    	JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 4)) {
            @Override public Dimension getPreferredSize() {
                int total = 0;
                for (Component c : getComponents()) total += c.getPreferredSize().width + 14;
                return new Dimension(total + 14, CARD_ALTO);
            }
        };
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        for (int i = 0; i < platillos.size(); i++) {
            CardPlatillo card = new CardPlatillo(
                platillos.get(i)
            );
            card.setPreferredSize(new Dimension(CARD_ANCHO, CARD_ALTO));
            grid.add(card);
        }
        return grid;
    }
    
    
    /* GRIDS DE PLATILLOS Y BEBIDAS
    private JPanel crearGridPlatillos() {
        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 4)) {
            @Override public Dimension getPreferredSize() {
                int total = 0;
                for (Component c : getComponents()) total += c.getPreferredSize().width + 14;
                return new Dimension(total + 14, CARD_ALTO);
            }
        };
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        for (int i = 0; i < NOMBRES.length; i++) {
            CardPlatillo card = new CardPlatillo(
                NOMBRES[i], PRECIOS[i], TIEMPOS[i], DESCRIPCIONES[i], BADGES[i], BADGE_COLORS[i]
            );
            card.setPreferredSize(new Dimension(CARD_ANCHO, CARD_ALTO));
            grid.add(card);
        }
        return grid;
    }
    
    private JPanel crearGridBebidas() {
        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 4)) {
            @Override public Dimension getPreferredSize() {
                int total = 0;
                for (Component c : getComponents()) total += c.getPreferredSize().width + 14;
                return new Dimension(total + 14, CARD_ALTO);
            }
        };
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        for (int i = 0; i < NOMBRES_BEBIDAS.length; i++) {
            CardPlatillo card = new CardPlatillo(
                NOMBRES_BEBIDAS[i], PRECIOS_BEBIDAS[i], TIEMPOS_BEBIDAS[i],
                DESCRIPCIONES_BEBIDAS[i], BADGES_BEBIDAS[i], BADGE_COLORS_BEBIDAS[i]
            );
            card.setPreferredSize(new Dimension(CARD_ANCHO, CARD_ALTO));
            grid.add(card);
        }
        return grid;
    }
    */
    
    //  SECCIÓN CARRUSEL GENÉRICA
    public JPanel crearSeccionCarrusel(String tituloNormal, String tituloColor, JPanel grid) {
        JPanel seccion = new JPanel(new BorderLayout());
        seccion.setOpaque(false);

        // TÍTULO
        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tituloPanel.setOpaque(false);
        tituloPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel lblNormal = new JLabel(tituloNormal + " ");
        lblNormal.setFont(new Font("Arial", Font.BOLD, 24));
        lblNormal.setForeground(COLOR_TEXTO);

        JLabel lblColor = new JLabel(tituloColor);
        lblColor.setFont(new Font("Arial", Font.BOLD, 24));
        lblColor.setForeground(COLOR_ACENTO);

        tituloPanel.add(lblNormal);
        tituloPanel.add(lblColor);
        
        JScrollPane scroll = new JScrollPane(grid);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.getHorizontalScrollBar().setUnitIncrement(30);
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 4));
        scroll.getHorizontalScrollBar().setBackground(new Color(30, 41, 59));
        
        scroll.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = new Color(100, 130, 170);
                trackColor = new Color(30, 41, 59);
            }
            @Override protected JButton createDecreaseButton(int o) { return crearBotonInvisible(); }
            @Override protected JButton createIncreaseButton(int o) { return crearBotonInvisible(); }
        });
        
        int altoScroll = grid.getPreferredSize().height + 14;
        scroll.setPreferredSize(new Dimension(0, altoScroll));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, altoScroll));
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(tituloPanel, BorderLayout.NORTH);
        wrapper.add(scroll,      BorderLayout.CENTER);

        seccion.add(wrapper, BorderLayout.CENTER);
        return seccion;
    }

    // BARRA DE CARRITO INFERIOR
    private JPanel crearBarraCarrito() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_FONDO);
        
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(40, 55, 80));
        wrapper.add(sep, BorderLayout.NORTH);

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(COLOR_CARD); 
        bar.setBorder(new EmptyBorder(15, 25, 15, 25));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel lblCantidad = new JLabel("0 Productos");
        lblCantidad.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCantidad.setForeground(COLOR_TEXTO_GRIS);
        
        JLabel lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(COLOR_TEXTO);

        infoPanel.add(lblCantidad);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblTotal);

        JButton btnPedir = crearBotonAcento("Ver Carrito");
        btnPedir.setPreferredSize(new Dimension(130, 40));
        btnPedir.setFont(new Font("Arial", Font.BOLD, 14));
        // Aquí podrías acceder a la instancia del Frame para cambiar la vista luego
        // btnPedir.addActionListener(e -> { ... });

        bar.add(infoPanel, BorderLayout.WEST);
        bar.add(btnPedir, BorderLayout.EAST);
        
        wrapper.add(bar, BorderLayout.CENTER);
        return wrapper;
    }

    // BOTONES UTILITARIOS
    private JButton crearBotonAcento(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_ACENTO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(110, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JButton crearBotonInvisible() {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(0, 0));
        btn.setMinimumSize(new Dimension(0, 0));
        btn.setMaximumSize(new Dimension(0, 0));
        return btn;
    }
    
    public void addSeccion(String tituloNormal, String tituloColor, JPanel grid) {
    	secciones.add(crearSeccionCarrusel(tituloNormal, tituloColor, grid));
    	secciones.add(Box.createVerticalStrut(20));
    }
}