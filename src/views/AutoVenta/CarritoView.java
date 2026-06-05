package views.AutoVenta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utilidades.views.ItemCarritoCard;

import java.awt.*;

public class CarritoView extends JPanel {

    // Colores del tema
    private static final Color COLOR_FONDO       = new Color(15, 23, 42);
    private static final Color COLOR_PANEL        = new Color(30, 41, 59);
    private static final Color COLOR_SEPARADOR    = new Color(51, 65, 85);
    private static final Color COLOR_TEXTO        = new Color(255, 255, 255);
    private static final Color COLOR_TEXTO_GRIS   = new Color(148, 163, 184);
    private static final Color COLOR_BTN_CONFIRMAR = new Color(59, 130, 246);
    private static final Color COLOR_ROJO         = new Color(239, 68, 68);

    // Componentes
    private JLabel lblSubtotalValor;
    private JPanel cuerpoPanel;
    private JLabel iconoLimpiar;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextArea txtInstrucciones;

    public CarritoView() {
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(300, 0));

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }
    
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PANEL);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Carrito de Compras");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(COLOR_TEXTO);
        izq.add(titulo);

        iconoLimpiar = new JLabel("Limpiar");
        iconoLimpiar.setFont(new Font("Arial", Font.PLAIN, 11));
        iconoLimpiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iconoLimpiar.setForeground(COLOR_TEXTO_GRIS);

        header.add(izq, BorderLayout.WEST);
        header.add(iconoLimpiar, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_PANEL);
        wrapper.add(header, BorderLayout.CENTER);

        JLabel subtitulo = new JLabel("Revisa tus productos seleccionados antes de confirmar.");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitulo.setForeground(COLOR_TEXTO_GRIS);
        subtitulo.setBorder(new EmptyBorder(0, 18, 10, 18));
        subtitulo.setBackground(COLOR_PANEL);
        subtitulo.setOpaque(true);

        JPanel full = new JPanel(new BorderLayout());
        full.setBackground(COLOR_PANEL);
        full.add(wrapper, BorderLayout.NORTH);
        full.add(subtitulo, BorderLayout.SOUTH);

        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_SEPARADOR);
        full.add(sep, BorderLayout.SOUTH);

        return full;
    }
    
    private JPanel crearCuerpo() {
        cuerpoPanel = new JPanel();
        cuerpoPanel.setLayout(new BoxLayout(cuerpoPanel, BoxLayout.Y_AXIS));
        cuerpoPanel.setBackground(COLOR_FONDO);
        cuerpoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(cuerpoPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_FONDO);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }
    
    private JPanel crearFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(COLOR_PANEL);
        footerPanel.setBorder(new EmptyBorder(14, 18, 18, 18));

        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_SEPARADOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        footerPanel.add(sep);
        footerPanel.add(Box.createVerticalStrut(12));

        JPanel resumenPanel = new JPanel(new BorderLayout());
        resumenPanel.setOpaque(false);
        resumenPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel lblResumen = new JLabel("Resumen");
        lblResumen.setFont(new Font("Arial", Font.BOLD, 13));
        lblResumen.setForeground(COLOR_TEXTO);
        lblResumen.setBorder(new EmptyBorder(0, 0, 0, 0));
        resumenPanel.add(lblResumen, BorderLayout.WEST);
        
        footerPanel.add(resumenPanel);
        footerPanel.add(Box.createVerticalStrut(10));

        footerPanel.add(crearFilaSubtotal());
        footerPanel.add(Box.createVerticalStrut(14));

        JPanel instruccionesPanel = new JPanel(new BorderLayout());
        instruccionesPanel.setOpaque(false);
        instruccionesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        
        JLabel lblInstrucciones = new JLabel("Instrucciones Especiales");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 11));
        lblInstrucciones.setForeground(COLOR_TEXTO_GRIS);
        lblInstrucciones.setBorder(new EmptyBorder(0, 0, 0, 0));
        instruccionesPanel.add(lblInstrucciones, BorderLayout.WEST);
        
        footerPanel.add(instruccionesPanel);
        footerPanel.add(Box.createVerticalStrut(6));

        txtInstrucciones = new JTextArea("");
        txtInstrucciones.setFont(new Font("Arial", Font.PLAIN, 11));
        txtInstrucciones.setForeground(COLOR_TEXTO);
        txtInstrucciones.setBackground(new Color(40, 55, 80));
        txtInstrucciones.setRows(3);
        txtInstrucciones.setLineWrap(true);
        txtInstrucciones.setWrapStyleWord(true);
        txtInstrucciones.setBorder(new EmptyBorder(8, 10, 8, 10));
        txtInstrucciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JScrollPane scrollTxt = new JScrollPane(txtInstrucciones);
        scrollTxt.setBorder(BorderFactory.createLineBorder(COLOR_SEPARADOR, 1));
        scrollTxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        footerPanel.add(scrollTxt);
        footerPanel.add(Box.createVerticalStrut(14));

        btnConfirmar = crearBotonConfirmar();
        footerPanel.add(btnConfirmar);
        footerPanel.add(Box.createVerticalStrut(8));

        btnCancelar = crearBotonCancelar();
        footerPanel.add(btnCancelar);
        footerPanel.add(Box.createVerticalStrut(10));

        JLabel lblNota = new JLabel("Al confirmar, aceptas nuestros términos y condiciones.", SwingConstants.CENTER);
        lblNota.setFont(new Font("Arial", Font.PLAIN, 10));
        lblNota.setForeground(COLOR_TEXTO_GRIS);
        lblNota.setAlignmentX(CENTER_ALIGNMENT);
        footerPanel.add(lblNota);

        return footerPanel;
    }

    private JPanel crearFilaSubtotal() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lblConcepto = new JLabel("Subtotal");
        lblConcepto.setFont(new Font("Arial", Font.BOLD, 14));
        lblConcepto.setForeground(COLOR_TEXTO);

        lblSubtotalValor = new JLabel("$0.00", SwingConstants.RIGHT);
        lblSubtotalValor.setFont(new Font("Arial", Font.BOLD, 16));
        lblSubtotalValor.setForeground(new Color(255, 165, 0));

        fila.add(lblConcepto, BorderLayout.WEST);
        fila.add(lblSubtotalValor, BorderLayout.EAST);
        return fila;
    }
    
    private JButton crearBotonConfirmar() {
        JButton btn = new JButton("CONFIRMAR PEDIDO") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_BTN_CONFIRMAR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(COLOR_TEXTO);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JButton crearBotonCancelar() {
        JButton btn = new JButton("CANCELAR") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_ROJO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(COLOR_TEXTO);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    public void setSubtotal(double subtotal) {
        if (lblSubtotalValor != null) {
            lblSubtotalValor.setText(String.format("$%.2f", subtotal));
        }
    }
    
    public void limpiarCarrito() {
        cuerpoPanel.removeAll();
        cuerpoPanel.revalidate();
        cuerpoPanel.repaint();
        setSubtotal(0.0);
    }
    
    public void agregarItem(ItemCarritoCard item) {
        cuerpoPanel.add(item);
        cuerpoPanel.add(Box.createVerticalStrut(8));
        cuerpoPanel.revalidate();
        cuerpoPanel.repaint();
    }
    
    public void eliminarItem(ItemCarritoCard item) {
        cuerpoPanel.remove(item);
        cuerpoPanel.revalidate();
        cuerpoPanel.repaint();
    }
    
    public JPanel getCuerpoPanel() {
        return cuerpoPanel;
    }
    
    public String getInstrucciones() {
        return txtInstrucciones != null ? txtInstrucciones.getText() : "";
    }
    
    public void setInstrucciones(String instrucciones) {
        if (txtInstrucciones != null) {
            txtInstrucciones.setText(instrucciones);
        }
    }
    
    public JLabel getIconoLimpiar() {
        return iconoLimpiar;
    }
    
    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }
    
    public JButton getBtnCancelar() {
        return btnCancelar;
    }
    
    public JTextArea getTxtInstrucciones() {
        return txtInstrucciones;
    }
    
    public Component[] getItemsCarrito() {
        return cuerpoPanel.getComponents();
    }
}