package views.Cocina;

import models.Venta;
import services.VentaService.ResumenCocinaDTO;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BotonHub;
import utilidades.views.CardOrden;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class VistaCocinero extends JFrame { 
    
    // COLORES DEL TEMA (Consistentes entre HubVenta y Cocina)
    private static final Color COL_FONDO        = new Color(15,  23,  42);
    private static final Color COL_BARRA        = new Color(30,  41,  59);
    private static final Color ENFOQUE_NARANJA  = new Color(255, 165,   0);
    private static final Color TXT_GRISEO       = new Color(148, 163, 184);
    private static final Color COLOR_TEXTO      = new Color(255, 255, 255);

    // COMPONENTES PRINCIPALES
    private JPanel panelPrincipal;
    private JPanel panelLateral;
    private JPanel panelGrid;
    
    // COMPONENTES DE LA BARRA SUPERIOR
    private JLabel barraNavegacion;
    private JLabel lblPendientes;
    private JLabel lblEnProceso;
    private JButton btnRefrescar;
    
    // BOTONES LATERALES
    private BotonHub botonCocina;
    private BotonHub botonInventario; 
    private BotonHub botonConfiguracion;
    private BotonHub botonLogOut;
    
    // LISTENERS
    private CardOrden.AccionesComanda accionListener;

    public VistaCocinero() {
        setTitle("Madero's Restaurant - Cocina");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null); 
        setMinimumSize(new Dimension(800, 600));
        
        ImageIcon i = GeneradorIconos.cargarIcono("/assets/image/IconoApliacionPrincipal.jpg");
        if(i != null) {
            setIconImage(i.getImage());
        }

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel contenedorPrincipal = new JPanel(new GridBagLayout());
        contenedorPrincipal.setBackground(COL_FONDO);
        setContentPane(contenedorPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.weighty = 1.0;
        gbc.gridx = 0;      
        gbc.gridy = 0;      
        gbc.weightx = 0.0; // Se ajusta al contenido
        gbc.gridheight = 2;
        contenedorPrincipal.add(crearBarraLateral(), gbc);
        
        gbc.gridx = 1;      
        gbc.gridy = 0;      
        gbc.weightx = 1.0; 
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        contenedorPrincipal.add(crearBarraSuperior(), gbc);
        
        gbc.gridx = 1;      
        gbc.gridy = 1;      
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0;
        gbc.gridheight = 1;
        contenedorPrincipal.add(construirAreaOrdenes(), gbc);
    }

    private JPanel crearBarraSuperior() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(COL_BARRA);
        bar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(51, 65, 85)),
                new EmptyBorder(10, 20, 10, 20)
        ));
        bar.setPreferredSize(new Dimension(0, 70));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        logoPanel.setOpaque(false);
        
        barraNavegacion = new JLabel();
        GeneradorIconos.aplicarIcono("/assets/image/lista.png", barraNavegacion, Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        barraNavegacion.setFont(AppFont.title());
        barraNavegacion.setForeground(COLOR_TEXTO);
        barraNavegacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        barraNavegacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirBarra();
            }
        });
        
        JLabel madero = new JLabel("MADERO'S");
        madero.setFont(AppFont.bold().deriveFont(18f));
        madero.setForeground(COL_FONDO);
        madero.setBorder(new EmptyBorder(2, 6, 2, 6));
        madero.setOpaque(true);
        madero.setBackground(ENFOQUE_NARANJA);

        JLabel restaurant = new JLabel("COCINA");
        restaurant.setFont(AppFont.bold().deriveFont(16f));
        restaurant.setForeground(COLOR_TEXTO);

        logoPanel.add(barraNavegacion);
        logoPanel.add(madero);
        logoPanel.add(restaurant);
        bar.add(logoPanel, BorderLayout.WEST);

        lblPendientes = crearNumeroContador(CardOrden.COL_PENDIENTE);
        lblEnProceso  = crearNumeroContador(CardOrden.COL_PROCESO);
        
        JPanel contadores = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        contadores.setOpaque(false);
        contadores.add(construirBloqueContador("PLATOS PENDIENTES", lblPendientes));
        contadores.add(construirBloqueContador("PLATOS EN PROCESO", lblEnProceso));
        bar.add(contadores, BorderLayout.CENTER);

        // BOTÓN REFRESCAR 
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
        panelDerecho.setOpaque(false);
        
        btnRefrescar = new JButton("REFRESCAR");
        btnRefrescar.setFont(AppFont.bold().deriveFont(13f));
        btnRefrescar.setBackground(ENFOQUE_NARANJA);
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRefrescar.setPreferredSize(new Dimension(120, 35));
        
        panelDerecho.add(btnRefrescar);
        bar.add(panelDerecho, BorderLayout.EAST);
        
        return bar;
    }

    private JPanel crearBarraLateral() {
        panelLateral = new JPanel();
        Color colorIconos = Paleta_Colores.TEXTO_SECUNDARIO.getColor();
        panelLateral.setBackground(Paleta_Colores.CONTENEDORES.getColor());
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.PAGE_AXIS));
        
        Border margenEntrePanel = BorderFactory.createEmptyBorder(15, 10, 15, 10);
        Border lineaDerecha = new MatteBorder(0, 0, 0, 1, new Color(51, 65, 85));
        panelLateral.setBorder(BorderFactory.createCompoundBorder(lineaDerecha, margenEntrePanel));
        
        botonCocina = new BotonHub("Comandas", "/assets/image/libro-alt.png", colorIconos);
        botonCocina.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonCocina);
        
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        
        botonInventario = new BotonHub("Inventario", "/assets/image/caja.png", colorIconos); 
        botonInventario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonInventario);
        
        // Empujar componentes hacia abajo
        panelLateral.add(Box.createVerticalGlue());
        
        botonConfiguracion = new BotonHub("Configuración", "/assets/image/ajustes-deslizadores.png", colorIconos);
        botonConfiguracion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonConfiguracion);
        
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        
        botonLogOut = new BotonHub("Salir", "/assets/image/salida.png", colorIconos);
        botonLogOut.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelLateral.add(botonLogOut);
        
        return panelLateral;
    }

    private JLabel crearNumeroContador(Color color) {
        JLabel lbl = new JLabel("00");
        lbl.setFont(AppFont.bold().deriveFont(26f));
        lbl.setForeground(color);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        return lbl;
    }

    private JPanel construirBloqueContador(String etiqueta, JLabel numero) {
        JPanel bloque = new JPanel();
        bloque.setLayout(new BoxLayout(bloque, BoxLayout.Y_AXIS));
        bloque.setOpaque(false);
        JLabel tag = new JLabel(etiqueta);
        tag.setFont(AppFont.small()); // Uso de AppFont
        tag.setForeground(TXT_GRISEO);
        tag.setAlignmentX(CENTER_ALIGNMENT);
        bloque.add(numero);
        bloque.add(tag);
        return bloque;
    }
    
    private JScrollPane construirAreaOrdenes() {
        panelGrid = new JPanel(new WrapLayout(FlowLayout.LEFT, 14, 14));
        panelGrid.setBackground(COL_FONDO);
        panelGrid.setBorder(new EmptyBorder(16, 16, 16, 16));
        JScrollPane scroll = new JScrollPane(panelGrid);
        scroll.setBorder(null);
        scroll.setBackground(COL_FONDO);
        scroll.getViewport().setBackground(COL_FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    public void mostrarDatos(ResumenCocinaDTO resumen) {
        panelGrid.removeAll();
        for (Venta venta : resumen.comandas) {
            panelGrid.add(new CardOrden(venta, accionListener));
        }
        lblPendientes.setText(String.format("%02d", resumen.totalPendientes));
        lblEnProceso.setText(String.format("%02d", resumen.totalEnProceso));
        panelGrid.revalidate();
        panelGrid.repaint();
    }
    
    // MÉTODO PARA ABRIR/CERRAR BARRA LATERAL
    public void abrirBarra() {
        panelLateral.setVisible(!panelLateral.isVisible());
        this.revalidate(); 
        this.repaint();    
    }

    // GETTERS Y SETTERS
    public void setAccionesListener(CardOrden.AccionesComanda listener) {
        this.accionListener = listener;
    }

    public void setBotonRefrescarListener(java.awt.event.ActionListener listener) {
        btnRefrescar.addActionListener(listener);
    }
    
    public BotonHub getBotonLogOut() { return botonLogOut; }
    public BotonHub getBotonCocina() { return botonCocina; }
    public BotonHub getBotonInventario() { return botonInventario; }
    public BotonHub getBotonConfiguracion() { return botonConfiguracion; }

    // --- Layout helper para el responsive grid --- dios y carlos sabe como funciona esto
    static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }
        @Override public Dimension preferredLayoutSize(Container t) { return calcDim(t, true);  }
        @Override public Dimension minimumLayoutSize(Container t)   { return calcDim(t, false); }
        private Dimension calcDim(Container target, boolean pref) {
            synchronized (target.getTreeLock()) {
                int w = target.getWidth();
                if (w == 0) w = Integer.MAX_VALUE;
                Insets ins = target.getInsets();
                int limW = w - ins.left - ins.right - getHgap() * 2;
                Dimension dim = new Dimension(0, 0);
                int filaW = 0, filaH = 0;
                for (Component c : target.getComponents()) {
                    if (!c.isVisible()) continue;
                    Dimension d = pref ? c.getPreferredSize() : c.getMinimumSize();
                    if (filaW + d.width > limW && filaW > 0) {
                        dim.height += filaH + getVgap();
                        dim.width   = Math.max(dim.width, filaW);
                        filaW = 0; filaH = 0;
                    }
                    filaW += d.width + getHgap();
                    filaH  = Math.max(filaH, d.height);
                }
                dim.height += filaH + getVgap() * 2 + ins.top + ins.bottom;
                dim.width   = Math.max(dim.width, filaW) + ins.left + ins.right;
                return dim;
            }
        }
    }
}