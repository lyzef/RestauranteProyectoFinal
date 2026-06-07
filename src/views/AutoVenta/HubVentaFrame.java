package views.AutoVenta;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BotonHub;

public class HubVentaFrame extends JFrame {
    
    // CONSTANTES DEL CARDLAYOUT
    public static final String MENU = "MENU";
    public static final String CARRITO = "CARRITO";
    public static final String PAGO = "PAGO";
    
    // COLORES DEL TEMA (Extraídos del primer MenuView)
    private static final Color COLOR_FONDO  = new Color(15, 23, 42);
    private static final Color COLOR_ACENTO = new Color(255, 165, 0);
    private static final Color COLOR_TEXTO  = new Color(255, 255, 255);
    private static final Color COLOR_CARD   = new Color(30, 41, 59);

    // PANELES Y LAYOUTS PRINCIPALES
    private CardLayout cardLayout;
    private JPanel panelPrincipal;
    private JPanel panelLateral;
    
    // GLOBALES
    private JLabel barraNavegacion; // Las 3 barras
    private BotonHub botonLogOut;
    private BotonHub botonConfiguracion;
    private BotonHub botonMenu;

    // VISTAS
    public MenuVentaView menuPanel;
    public CarritoView carritoPanel;
    public PagoView pagoPanel;

    public HubVentaFrame() {
        // MEDIDAS PARA POS VERTICAL 
        int ancho = 500;
        int alto = 900;

        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Madero's Restaurant - POS");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(ancho, alto));
        setMaximumSize(new Dimension(ancho, alto));
        getContentPane().setBackground(COLOR_FONDO);
        ImageIcon i = GeneradorIconos.cargarIcono("/assets/image/IconoApliacionPrincipal.jpg");
		if(i != null) {
			setIconImage(i.getImage());
		}
		
        inicializarComponentes();
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.setBackground(COLOR_FONDO);
        
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(COLOR_FONDO);
        
        //Card layout
        menuPanel = new MenuVentaView();
        carritoPanel = new CarritoView();
        pagoPanel = new PagoView();
        panelPrincipal.add(menuPanel, MENU);
        panelPrincipal.add(carritoPanel,CARRITO);
        panelPrincipal.add(pagoPanel,PAGO);
        
        contenedorPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.weighty = 1.0;
        
        gbc.gridx = 0;      
        gbc.gridy = 0;      
        gbc.weightx = 0.1; 
        gbc.gridheight = 2;
        contenedorPrincipal.add(crearBarraLateral(), gbc);
        
        gbc.gridx = 1;      
        gbc.gridy = 0;      
        gbc.weightx = 0.85; 
        gbc.weighty = 0;
        gbc.gridheight = 1;
        contenedorPrincipal.add(crearBarraSuperior(), gbc);
        
        gbc.weightx = 0.9; 
        gbc.weighty = 1;
        gbc.gridx = 1;      
        gbc.gridy = 1;      
        gbc.gridheight = 1;
        contenedorPrincipal.add(panelPrincipal, gbc);
       
        this.add(contenedorPrincipal);
    }
    
    // BARRA SUPERIOR (ESTILO ORIGINAL DE MENUVIEW)
    private JPanel crearBarraSuperior() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(COLOR_FONDO);
        bar.setBorder(new EmptyBorder(10, 20, 10, 20));
        bar.setPreferredSize(new Dimension(0, 56));

        // LOGO IZQUIERDO
        JPanel logo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        logo.setOpaque(false);
        
        // ICONO DE 3 BARRAS MANTENIDO PARA FUNCIONALIDAD
        barraNavegacion = new JLabel();
        GeneradorIconos.aplicarIcono("/assets/image/lista.png", barraNavegacion, Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        barraNavegacion.setFont(new Font("Arial", Font.BOLD, 22));
        barraNavegacion.setForeground(COLOR_TEXTO);
        barraNavegacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel madero = new JLabel("MADERO'S");
        madero.setFont(AppFont.bold().deriveFont(18f));
        madero.setForeground(COLOR_FONDO);
        madero.setBorder(new EmptyBorder(2, 6, 2, 6));
        madero.setOpaque(true);
        madero.setBackground(COLOR_ACENTO);

        JLabel restaurant = new JLabel("RESTAURANT");
        restaurant.setFont(AppFont.bold().deriveFont(16f));
        restaurant.setForeground(COLOR_TEXTO);

        logo.add(barraNavegacion);
        logo.add(new JLabel(" "));
        logo.add(madero);
        logo.add(restaurant);

        // PANEL DERECHO: Botón Order + Ícono Usuario
        JPanel derecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        derecho.setOpaque(false);
        
        JButton btnUser = crearIconBtn("👤");
        
        derecho.add(btnUser);

        bar.add(logo, BorderLayout.WEST);
        bar.add(derecho, BorderLayout.EAST);

        // LÍNEA SEPARADORA
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_FONDO);
        wrapper.add(bar, BorderLayout.CENTER);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(40, 55, 80));
        wrapper.add(sep, BorderLayout.SOUTH);
        return wrapper;
    }
    
    private JPanel crearBarraLateral() {
        panelLateral = new JPanel();
        Color colorIconos = Paleta_Colores.TEXTO_SECUNDARIO.getColor();
        panelLateral.setBackground(Paleta_Colores.CONTENEDORES.getColor());
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.PAGE_AXIS));
        
        Border margenEntrePanel = BorderFactory.createEmptyBorder(15, 20, 15, 20);
        Border lineaDerecha = new MatteBorder(0, 0, 0, 1, Paleta_Colores.CONTENEDORES.getColor());
        panelLateral.setBorder(BorderFactory.createCompoundBorder(lineaDerecha, margenEntrePanel));
        
        botonMenu = new BotonHub("Menu", "/assets/image/libro-alt.png", colorIconos);
        botonMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonMenu);
        
        // Empujar componentes hacia abajo
        panelLateral.add(Box.createVerticalGlue());
        
        botonConfiguracion = new BotonHub("Configuración", "/assets/image/ajustes-deslizadores.png", colorIconos);
        botonConfiguracion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonConfiguracion);
        
        botonLogOut = new BotonHub("Salir", "/assets/image/salida.png", colorIconos);
        botonLogOut.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonLogOut);
        
        return panelLateral;
    }
    
    // COMPONENTES REUTILIZABLES PARA LA BARRA SUPERIOR
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
    
    private JButton crearIconBtn(String icono) {
        JButton btn = new JButton(icono);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        btn.setForeground(COLOR_TEXTO);
        btn.setBackground(COLOR_CARD);
        btn.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 110), 1));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(25, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // MÉTODO PARA ABRIR/CERRAR BARRA LATERAL
    public void abrirBarra() {
        panelLateral.setVisible(!panelLateral.isVisible());
        this.revalidate(); 
        this.repaint();    
    }
    
    public void showView(String view) {
        cardLayout.show(panelPrincipal, view);
    }
    
    // GETTERS Y SETTERS
    public JLabel getBarraNavegacion() { return barraNavegacion; }

    public BotonHub getBotonLogOut() { return botonLogOut; }
    
    public BotonHub getBotonMenu() { return botonMenu; }

    public BotonHub getBotonConfiguracion() { return botonConfiguracion; }

    public MenuVentaView getMenuPanel() { return menuPanel; }
    
    public CarritoView getCarritoPanel() { return carritoPanel; }
    
    public PagoView getPagoPanel() { return pagoPanel; }
}