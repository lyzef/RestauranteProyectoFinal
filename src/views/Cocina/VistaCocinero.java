package views.Cocina;

import models.Venta;
import services.VentaService.ResumenCocinaDTO;
import utilidades.views.CardOrden;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

// CAMBIO 1: Heredar de JFrame en lugar de JPanel
public class VistaCocinero extends JFrame { 
    private static final Color COL_FONDO        = new Color(15,  23,  42);
    private static final Color COL_BARRA        = new Color(30,  41,  59);
    private static final Color ENFOQUE_NARANJA  = new Color(255, 165,   0);
    private static final Color TXT_GRISEO       = new Color(148, 163, 184);
    
    private JPanel panelGrid;
    private JLabel lblPendientes;
    private JLabel lblEnProceso;
    private JButton btnRefrescar;
    
    private CardOrden.AccionesComanda accionListener;

    public VistaCocinero() {
        // CAMBIO 2: Configuración básica de la ventana
        setTitle("Madero's Restaurant - Cocina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null); // Centrar en pantalla
        
        // El contenido va sobre el 'contentPane'
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(COL_FONDO);
        setContentPane(contentPane);

        contentPane.add(construirTopBar(), BorderLayout.NORTH);
        contentPane.add(construirAreaOrdenes(), BorderLayout.CENTER);
    }

    public void setAccionesListener(CardOrden.AccionesComanda listener) {
        this.accionListener = listener;
    }

    public void setBotonRefrescarListener(java.awt.event.ActionListener listener) {
        btnRefrescar.addActionListener(listener);
    }

    private JPanel construirTopBar() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(COL_BARRA);
        barra.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(51, 65, 85)),
                new EmptyBorder(10, 20, 10, 20)
        ));
        
        JLabel logo = new JLabel("MADERO´S RESTAURANT - COCINA");
        logo.setFont(new Font("Monospaced", Font.BOLD, 17));
        logo.setForeground(ENFOQUE_NARANJA);
        barra.add(logo, BorderLayout.WEST);
        
        lblPendientes = crearNumeroContador(CardOrden.COL_PENDIENTE);
        lblEnProceso  = crearNumeroContador(CardOrden.COL_PROCESO);
        
        JPanel contadores = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        contadores.setOpaque(false);
        contadores.add(construirBloqueContador("PLATOS PENDIENTES", lblPendientes));
        contadores.add(construirBloqueContador("PLATOS EN PROCESO",  lblEnProceso));
        barra.add(contadores, BorderLayout.CENTER);
        
        btnRefrescar = new JButton("REFRESCAR");
        btnRefrescar.setBackground(ENFOQUE_NARANJA);
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setFocusPainted(false);
        barra.add(btnRefrescar, BorderLayout.EAST);
        
        return barra;
    }
    
    .

    private JLabel crearNumeroContador(Color color) {
        JLabel lbl = new JLabel("00");
        lbl.setFont(new Font("Monospaced", Font.BOLD, 24));
        lbl.setForeground(color);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        return lbl;
    }

    private JPanel construirBloqueContador(String etiqueta, JLabel numero) {
        JPanel bloque = new JPanel();
        bloque.setLayout(new BoxLayout(bloque, BoxLayout.Y_AXIS));
        bloque.setOpaque(false);
        JLabel tag = new JLabel(etiqueta);
        tag.setFont(new Font("Monospaced", Font.PLAIN, 10));
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

    // --- Layout helper para el responsive grid ---
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