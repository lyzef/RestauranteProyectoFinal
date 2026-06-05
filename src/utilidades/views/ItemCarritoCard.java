package utilidades.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class ItemCarritoCard extends JPanel {
    
    // Colores
    private static final Color COLOR_PANEL_ITEM   = new Color(40, 55, 80);
    private static final Color COLOR_ACENTO       = new Color(59, 130, 246);
    private static final Color COLOR_TEXTO        = new Color(255, 255, 255);
    private static final Color COLOR_TEXTO_GRIS   = new Color(148, 163, 184);
    private static final Color COLOR_VERDE        = new Color(34, 197, 94);
    private static final Color COLOR_ROJO         = new Color(239, 68, 68);
    
    // Componentes internos
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JButton btnMenos;
    private JButton btnMas;
    
    // Datos
    private int productoId;
    private String nombre;
    private String detalle;
    private double precioUnitario;
    private int cantidad;
    
    public ItemCarritoCard(int productoId, String nombre, String detalle, 
                           int cantidad, double precioUnitario) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.detalle = detalle;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(12, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(10, 12, 10, 12));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        setPreferredSize(new Dimension(280, 80));
        
        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelDerecho(), BorderLayout.EAST);
    }
    
    private JPanel crearPanelIzquierdo() {
        JPanel izqPanel = new JPanel(new GridBagLayout());
        izqPanel.setOpaque(false);
        izqPanel.setPreferredSize(new Dimension(40, 60));
        
        JLabel burbujaQ = new JLabel(String.valueOf(cantidad), SwingConstants.CENTER);
        burbujaQ.setOpaque(true);
        burbujaQ.setBackground(COLOR_ACENTO);
        burbujaQ.setForeground(COLOR_TEXTO);
        burbujaQ.setFont(new Font("Arial", Font.BOLD, 14));
        burbujaQ.setPreferredSize(new Dimension(32, 32));
        burbujaQ.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        izqPanel.add(burbujaQ);
        
        return izqPanel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblNombre.setForeground(COLOR_TEXTO);
        lblNombre.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel lblDetalle = new JLabel(detalle.isEmpty() ? "Sin modificaciones" : detalle);
        lblDetalle.setFont(new Font("Arial", Font.PLAIN, 10));
        lblDetalle.setForeground(COLOR_TEXTO_GRIS);
        lblDetalle.setAlignmentX(LEFT_ALIGNMENT);
        
        infoPanel.add(lblNombre);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblDetalle);
        
        return infoPanel;
    }
    
    private JPanel crearPanelDerecho() {
        JPanel derPanel = new JPanel();
        derPanel.setLayout(new BoxLayout(derPanel, BoxLayout.Y_AXIS));
        derPanel.setOpaque(false);
        derPanel.setBorder(new EmptyBorder(0, 0, 0, 5));
        
        // Botones de cantidad
        JPanel botonesQ = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        botonesQ.setOpaque(false);
        
        btnMenos = crearBotonCantidad("-", COLOR_ROJO);
        btnMenos.setPreferredSize(new Dimension(30, 30));
        
        lblCantidad = new JLabel(String.valueOf(cantidad), SwingConstants.CENTER);
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 14));
        lblCantidad.setForeground(COLOR_TEXTO);
        lblCantidad.setPreferredSize(new Dimension(25, 30));
        lblCantidad.setMinimumSize(new Dimension(25, 30));
        
        btnMas = crearBotonCantidad("+", COLOR_VERDE);
        btnMas.setPreferredSize(new Dimension(30, 30));
        
        botonesQ.add(btnMenos);
        botonesQ.add(lblCantidad);
        botonesQ.add(btnMas);
        
        // Subtotal
        double subtotal = cantidad * precioUnitario;
        lblSubtotal = new JLabel(String.format("$%.2f", subtotal), SwingConstants.RIGHT);
        lblSubtotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblSubtotal.setForeground(new Color(255, 165, 0));
        lblSubtotal.setAlignmentX(RIGHT_ALIGNMENT);
        lblSubtotal.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        derPanel.add(botonesQ);
        derPanel.add(Box.createVerticalStrut(5));
        derPanel.add(lblSubtotal);
        
        return derPanel;
    }
    
    private JButton crearBotonCantidad(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
            }
        };
        btn.setForeground(COLOR_TEXTO);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(COLOR_PANEL_ITEM);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        g2.dispose();
        super.paintComponent(g);
    }
    
    // Getters y Setters
    public int getProductoId() {
        return productoId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getCantidad() {
        try {
            return Integer.parseInt(lblCantidad.getText());
        } catch (NumberFormatException e) {
            return cantidad;
        }
    }
    
    public void setCantidad(int nuevaCantidad) {
        this.cantidad = nuevaCantidad;
        lblCantidad.setText(String.valueOf(nuevaCantidad));
        actualizarSubtotal();
    }
    
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    
    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
    
    public void actualizarSubtotal() {
        double nuevoSubtotal = cantidad * precioUnitario;
        lblSubtotal.setText(String.format("$%.2f", nuevoSubtotal));
    }
    
    public void setOnMasListener(ActionListener listener) {
        for (ActionListener al : btnMas.getActionListeners()) {
            btnMas.removeActionListener(al);
        }
        btnMas.addActionListener(listener);
    }
    
    public void setOnMenosListener(ActionListener listener) {
        for (ActionListener al : btnMenos.getActionListeners()) {
            btnMenos.removeActionListener(al);
        }
        btnMenos.addActionListener(listener);
    }
    
    public JButton getBtnMas() {
        return btnMas;
    }
    
    public JButton getBtnMenos() {
        return btnMenos;
    }
    
    public JLabel getLblCantidad() {
        return lblCantidad;
    }
    
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }
}