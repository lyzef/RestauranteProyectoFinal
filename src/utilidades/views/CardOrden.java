package utilidades.views;

import models.Venta;
import models.DetalleVenta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CardOrden extends JPanel {

    private static final int CARD_W = 270;
    private static final int CARD_H = 350;

    private static final Color BG_CARD       = new Color(30,  41,  59);
    private static final Color BG_ITEM       = new Color(51,  65,  85);
    private static final Color TXT_PRINCIPAL = new Color(255, 255, 255);
    private static final Color TXT_MUTED     = new Color(148, 163, 184);
    
    public static final Color COL_PENDIENTE = new Color(245, 158,  11);
    public static final Color COL_PROCESO   = new Color( 59, 130, 246);
    public static final Color COL_URGENTE   = new Color(239,  68,  68);
    public static final Color COL_LISTO     = new Color( 16, 185, 129);
    public static final Color COL_INACTIVO  = new Color( 51,  65,  85);

    public interface AccionesComanda {
        void onIniciar(Venta venta);
        void onCompletar(Venta venta);
    }

    private final Venta venta;
    private final AccionesComanda listener;

    public CardOrden(Venta venta, AccionesComanda listener) {
        this.venta = venta;
        this.listener = listener;

        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(CARD_W, CARD_H));

        add(construirHeader(), BorderLayout.NORTH);
        add(construirCuerpo(), BorderLayout.CENTER);
        add(construirBotones(), BorderLayout.SOUTH);
    }

    // --- Lógica visual basada en los DetalleVenta reales ---
    private boolean isEnProceso() {
        return venta.getDetalles().stream().anyMatch(DetalleVenta::isEnProceso);
    }

    private boolean isUrgente() {
        return venta.getDetalles().stream().anyMatch(d -> d.getUrgencia() == DetalleVenta.Urgencia.ALTA);
    }

    private Color colorDeAlerta() {
        if (isEnProceso()) return COL_PROCESO;
        if (isUrgente()) return COL_URGENTE;
        return COL_PENDIENTE;
    }

    private JPanel construirHeader() {
        JPanel sup = new JPanel(new BorderLayout());
        sup.setOpaque(false);
        sup.setBorder(new EmptyBorder(12, 14, 8, 14));

        JLabel txtTicket = new JLabel("Ticket #" + venta.getId());
        txtTicket.setFont(new Font("Monospaced", Font.BOLD, 15));
        txtTicket.setForeground(TXT_PRINCIPAL);

        JPanel infoBloque = new JPanel(new GridLayout(2, 1));
        infoBloque.setOpaque(false);

        JLabel txtReloj = new JLabel(venta.getTiempoTranscurrido(), SwingConstants.RIGHT);
        txtReloj.setFont(new Font("Monospaced", Font.BOLD, 12));
        txtReloj.setForeground(colorDeAlerta());

        String modoStr = venta.getTipoPedidoVenta() != null ? venta.getTipoPedidoVenta().getValorDB() : "CAJA";
        JLabel txtModo = new JLabel(modoStr, SwingConstants.RIGHT);
        txtModo.setFont(new Font("Arial", Font.PLAIN, 10));
        txtModo.setForeground(TXT_MUTED);

        infoBloque.add(txtReloj);
        infoBloque.add(txtModo);

        sup.add(txtTicket, BorderLayout.WEST);
        sup.add(infoBloque, BorderLayout.EAST);
        return sup;
    }

    private JScrollPane construirCuerpo() {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(BG_CARD);
        lista.setBorder(new EmptyBorder(6, 10, 6, 10));

        for (DetalleVenta item : venta.getDetalles()) {
            lista.add(construirFilaItem(item));
            lista.add(Box.createVerticalStrut(6));
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setBackground(BG_CARD);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));
        return scroll;
    }

    private JPanel construirFilaItem(DetalleVenta item) {
        JPanel fila = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_ITEM);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(6, 8, 6, 8));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        JLabel txtCant = new JLabel(String.valueOf(item.getCantidad()), SwingConstants.CENTER);
        txtCant.setFont(new Font("Arial", Font.BOLD, 11));
        txtCant.setForeground(Color.WHITE);
        txtCant.setBackground(item.isEnProceso() ? COL_PROCESO : colorDeAlerta());
        txtCant.setOpaque(true);
        txtCant.setPreferredSize(new Dimension(22, 22));

        JPanel centrarBadge = new JPanel(new GridBagLayout());
        centrarBadge.setOpaque(false);
        centrarBadge.add(txtCant);
        
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        String nombreItem = item.getComponenteNombre() != null ? item.getComponenteNombre() : "Platillo #" + item.getComponenteId();
        JLabel txtNombre = new JLabel(nombreItem);
        txtNombre.setFont(new Font("Arial", Font.BOLD, 12));
        txtNombre.setForeground(TXT_PRINCIPAL);

        JLabel txtDet = new JLabel(item.getUrgencia().name());
        txtDet.setFont(new Font("Arial", Font.PLAIN, 10));
        txtDet.setForeground(TXT_MUTED);

        textos.add(txtNombre);
        textos.add(txtDet);

        fila.add(centrarBadge, BorderLayout.WEST);
        fila.add(textos, BorderLayout.CENTER);
        return fila;
    }

    private JPanel construirBotones() {
        JPanel botonera = new JPanel(new GridLayout(1, 2, 6, 0));
        botonera.setOpaque(false);
        botonera.setBorder(new EmptyBorder(4, 10, 12, 10));
        
        boolean proceso = isEnProceso();
        
        JButton btnAccion = fabricarBoton(proceso ? "PROCESANDO" : "INICIAR", colorDeAlerta());
        JButton btnCompletar = fabricarBoton("COMPLETAR", proceso ? COL_LISTO : COL_INACTIVO);
        
        btnAccion.addActionListener(e -> {
            if (!proceso && listener != null) {
                listener.onIniciar(venta);
            }
        });
        
        btnCompletar.addActionListener(e -> {
            if (proceso && listener != null) {
                listener.onCompletar(venta);
            }
        });
        
        botonera.add(btnAccion);
        botonera.add(btnCompletar);
        return botonera;
    }

    private JButton fabricarBoton(String texto, Color fondo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 10));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(0, 32));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(BG_CARD);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
        g2.dispose();
    }
 
}