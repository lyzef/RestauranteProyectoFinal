package views.AutoVenta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.CardCarritoPlatillo;
import utilidades.views.CardIngrediente;

public class CarritoView extends JPanel {
    
    private JPanel panelIngredientes;
    private JLabel titulo;
    private JLabel Slogan;
    
    private JLabel lblCantidad;
    private JLabel lblTotal;
    private JButton btnPedir;
    
    private final Color COLOR_FONDO = Paleta_Colores.FONDO.getColor();
    private final Color COLOR_CARD = Paleta_Colores.CONTENEDORES.getColor();
    private final Color COLOR_TEXTO_GRIS = new Color(150, 150, 150);
    private final Color COLOR_TEXTO = Paleta_Colores.TEXTO_PRINCIPAL.getColor();
    
    public CarritoView() {
        setLayout(new BorderLayout());
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel panelContenedorSuperior = new JPanel();
        panelContenedorSuperior.setLayout(new BoxLayout(panelContenedorSuperior, BoxLayout.Y_AXIS));
        panelContenedorSuperior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
        panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        titulo = new JLabel("Carrito");
        titulo.setFont(AppFont.title());
        titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Slogan = new JLabel("Algo bello pal camello");
        Slogan.setFont(AppFont.normal());
        Slogan.setForeground(Paleta_Colores.ATENCION.getColor());
        Slogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelContenedorSuperior.add(titulo);
        panelContenedorSuperior.add(Box.createVerticalStrut(5));
        panelContenedorSuperior.add(Slogan);
        
        panelIngredientes = new JPanel();
        panelIngredientes.setOpaque(false);
        panelIngredientes.setLayout(new BoxLayout(panelIngredientes, BoxLayout.Y_AXIS));
        
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(Paleta_Colores.FONDO.getColor());
        contenedorCentral.add(panelIngredientes, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(contenedorCentral);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(panelContenedorSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(crearBarraCarrito(), BorderLayout.SOUTH); 
    }
    
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

        lblCantidad = new JLabel("0 Productos");
        lblCantidad.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCantidad.setForeground(COLOR_TEXTO_GRIS);
        
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(COLOR_TEXTO);

        infoPanel.add(lblCantidad);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblTotal);

        btnPedir = crearBotonAcento("Ir a pagar");
        btnPedir.setPreferredSize(new Dimension(130, 40));
        btnPedir.setFont(AppFont.normal().deriveFont(Font.BOLD));
        
        bar.add(infoPanel, BorderLayout.WEST);
        bar.add(btnPedir, BorderLayout.EAST);
        
        wrapper.add(bar, BorderLayout.CENTER);
        return wrapper;
    }
    
    private JButton crearBotonAcento(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Paleta_Colores.EXITO.getColor());
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
    
    public void agregarCardPlatillo(CardCarritoPlatillo card) {
        panelIngredientes.add(card);
        panelIngredientes.revalidate();
        panelIngredientes.repaint();
    }
    
    public int solicitarCierre(String texto) {
        return JOptionPane.showConfirmDialog(null, texto, "Confirmación", JOptionPane.YES_NO_OPTION);
    }
    
    public void mostrarDialogMensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }
    
    public Component[] getListaIngredientes() {
        return panelIngredientes.getComponents();
    }
    
    public JPanel getPanelIngredientes() { 
        return panelIngredientes; 
    }
    
    public void setTitulo(String t) { 
        titulo.setText(t); 
    }

    // Métodos para modificar los labels desde el controlador
    public void setCantidadLabel(String texto) {
        lblCantidad.setText(texto);
    }
    
    public void setTotalLabel(String texto) {
        lblTotal.setText(texto);
    }

    public JLabel getLblCantidad() { return lblCantidad; }
    public JLabel getLblTotal() { return lblTotal; }
    public JButton getBtnPedir() { return btnPedir; }
}