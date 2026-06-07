package views.Dialog;

import utilidades.AppFont;
import utilidades.Paleta_Colores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketVentaDialog extends JDialog {

    private static final Color COLOR_FONDO         = new Color(15, 23, 42);
    private static final Color COLOR_PANEL         = new Color(30, 41, 59);
    private static final Color COLOR_ACENTO        = new Color(59, 130, 246);
    private static final Color COLOR_TEXTO         = new Color(255, 255, 255);
    private static final Color COLOR_TEXTO_GRIS    = new Color(148, 163, 184);
    private static final Color COLOR_SEPARADOR     = new Color(51, 65, 85);
    private static final Color COLOR_TOTAL         = new Color(255, 165, 0);
    private static final Color COLOR_URGENCIA_ALTA = new Color(239, 68, 68);

    private JLabel cajero;
    private JLabel lblTotalVal;
    private JPanel panelItems;
    private JLabel lblEstadoVenta;
    private JLabel lblInfoIds;
    private JLabel lblFechaCreacion;
    private JLabel lblFechaHoraInicio;
    private JLabel lblFechaHoraFin;
    private JLabel lblEstadoCocina;
    private JLabel lblUrgencia;
    private JLabel lblMetodoPago;

    // Constructor para ticket simple (solo ID y fecha de creación)
    public TicketVentaDialog(JFrame parent, int idTicket, String fechaCreacion) {
        super(parent, "Detalle de Venta", true);
        initComponents();
        setDatosTicketSimple(idTicket, fechaCreacion);
        pack();
        setLocationRelativeTo(parent);
    }

    // Constructor completo
    public TicketVentaDialog(JFrame parent) {
        super(parent, "Detalle de Venta", true);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(350, 680));

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        add(crearBotonSalir(), BorderLayout.SOUTH);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PANEL);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Detalle de Venta");
        titulo.setFont(AppFont.bold());
        titulo.setForeground(COLOR_TEXTO);
        izq.add(titulo);

        cajero = new JLabel("Cajero: ---");
        cajero.setFont(AppFont.small());
        cajero.setForeground(COLOR_TEXTO_GRIS);

        header.add(izq, BorderLayout.WEST);
        header.add(cajero, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_PANEL);
        wrapper.add(header, BorderLayout.CENTER);

        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_SEPARADOR);
        wrapper.add(sep, BorderLayout.SOUTH);

        return wrapper;
    }

    private JPanel crearCuerpo() {
        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(COLOR_FONDO);
        cuerpo.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel ticket = new JPanel() {
        	//Esquinas
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
            }
        };
        ticket.setLayout(new BoxLayout(ticket, BoxLayout.Y_AXIS));
        ticket.setOpaque(false);
        ticket.setBorder(new EmptyBorder(16, 18, 16, 18));

        JLabel lblRestaurante = new JLabel("Madero's Restaurant", SwingConstants.CENTER);
        lblRestaurante.setFont(new Font("Arial", Font.BOLD, 13));
        lblRestaurante.setForeground(COLOR_TEXTO);
        lblRestaurante.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblRestaurante);
        ticket.add(Box.createVerticalStrut(4));

        JLabel lblSlogan = new JLabel("Algo bello pa'l camello", SwingConstants.CENTER);
        lblSlogan.setFont(AppFont.small());
        lblSlogan.setForeground(COLOR_TEXTO_GRIS);
        lblSlogan.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblSlogan);
        ticket.add(Box.createVerticalStrut(4));

        lblEstadoVenta = new JLabel("PAGADO", SwingConstants.CENTER);
        lblEstadoVenta.setFont(AppFont.small());
        lblEstadoVenta.setForeground(COLOR_TEXTO_GRIS);
        lblEstadoVenta.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblEstadoVenta);
        ticket.add(Box.createVerticalStrut(14));

        lblInfoIds = new JLabel("Ticket: ---", SwingConstants.CENTER);
        lblInfoIds.setFont(new Font("Arial", Font.BOLD, 11));
        lblInfoIds.setForeground(COLOR_TEXTO);
        lblInfoIds.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblInfoIds);

        lblFechaCreacion = new JLabel("Creación: ---", SwingConstants.CENTER);
        lblFechaCreacion.setFont(AppFont.small());
        lblFechaCreacion.setForeground(COLOR_TEXTO);
        lblFechaCreacion.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblFechaCreacion);

        lblFechaHoraInicio = new JLabel("Inicio: ---", SwingConstants.CENTER);
        lblFechaHoraInicio.setFont(AppFont.small());
        lblFechaHoraInicio.setForeground(COLOR_TEXTO_GRIS);
        lblFechaHoraInicio.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblFechaHoraInicio);

        lblFechaHoraFin = new JLabel("Fin: ---", SwingConstants.CENTER);
        lblFechaHoraFin.setFont(AppFont.small());
        lblFechaHoraFin.setForeground(COLOR_TEXTO_GRIS);
        lblFechaHoraFin.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblFechaHoraFin);

        lblEstadoCocina = new JLabel("Cocina: PENDIENTE", SwingConstants.CENTER);
        lblEstadoCocina.setFont(AppFont.small());
        lblEstadoCocina.setForeground(COLOR_TEXTO_GRIS);
        lblEstadoCocina.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblEstadoCocina);

        lblUrgencia = new JLabel("Urgencia: NORMAL", SwingConstants.CENTER);
        lblUrgencia.setFont(AppFont.small());
        lblUrgencia.setForeground(COLOR_TEXTO_GRIS);
        lblUrgencia.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblUrgencia);

        lblMetodoPago = new JLabel("Método de pago: ---", SwingConstants.CENTER);
        lblMetodoPago.setFont(new Font("Arial", Font.BOLD, 11));
        lblMetodoPago.setForeground(COLOR_ACENTO);
        lblMetodoPago.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblMetodoPago);

        ticket.add(Box.createVerticalStrut(10));
        ticket.add(crearSeparadorPunteado());
        ticket.add(Box.createVerticalStrut(10));

        panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setOpaque(false);
        ticket.add(panelItems);

        ticket.add(Box.createVerticalStrut(4));
        ticket.add(crearSeparadorPunteado());
        ticket.add(Box.createVerticalStrut(12));

        JPanel filaTotal = new JPanel(new BorderLayout());
        filaTotal.setOpaque(false);
        filaTotal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel lblTotalTxt = new JLabel("Total");
        lblTotalTxt.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalTxt.setForeground(COLOR_TEXTO);

        lblTotalVal = new JLabel("$0.00", SwingConstants.RIGHT);
        lblTotalVal.setFont(new Font("Arial", Font.BOLD, 26));
        lblTotalVal.setForeground(COLOR_TOTAL);

        filaTotal.add(lblTotalTxt, BorderLayout.WEST);
        filaTotal.add(lblTotalVal, BorderLayout.EAST);
        ticket.add(filaTotal);

        cuerpo.add(ticket);

        JScrollPane scroll = new JScrollPane(cuerpo);
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

    private JPanel crearBotonSalir() {
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(COLOR_FONDO);
        panelBoton.setBorder(new EmptyBorder(10, 14, 14, 14));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(AppFont.normal());
        btnSalir.setBackground(COLOR_ACENTO);
        btnSalir.setForeground(COLOR_TEXTO);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(new EmptyBorder(8, 20, 8, 20));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panelBoton.add(btnSalir);
        return panelBoton;
    }

    private JPanel crearFilaTicket(int idProducto, int cantidad, String nombre, String precioUnitario, String subtotal) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(6, 0));
        panelIzquierdo.setOpaque(false);

        JPanel panelCantidadId = new JPanel(new GridLayout(2, 1));
        panelCantidadId.setOpaque(false);

        JLabel lblCantidad = new JLabel(cantidad + "x");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 13));
        lblCantidad.setForeground(COLOR_ACENTO);

        JLabel lblIdProducto = new JLabel("ID: " + idProducto);
        lblIdProducto.setFont(new Font("Arial", Font.PLAIN, 9));
        lblIdProducto.setForeground(COLOR_TEXTO_GRIS);

        panelCantidadId.add(lblCantidad);
        panelCantidadId.add(lblIdProducto);

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNombre.setForeground(COLOR_TEXTO);

        JLabel lblPrecioU = new JLabel("c/u: " + precioUnitario);
        lblPrecioU.setFont(new Font("Arial", Font.PLAIN, 10));
        lblPrecioU.setForeground(COLOR_TEXTO_GRIS);

        panelTextos.add(lblNombre);
        panelTextos.add(lblPrecioU);

        panelIzquierdo.add(panelCantidadId, BorderLayout.WEST);
        panelIzquierdo.add(panelTextos, BorderLayout.CENTER);

        JLabel lblSubtotal = new JLabel(subtotal, SwingConstants.RIGHT);
        lblSubtotal.setFont(new Font("Arial", Font.BOLD, 12));
        lblSubtotal.setForeground(COLOR_TEXTO);
        lblSubtotal.setVerticalAlignment(SwingConstants.TOP);

        fila.add(panelIzquierdo, BorderLayout.CENTER);
        fila.add(lblSubtotal, BorderLayout.EAST);
        return fila;
    }

    private JSeparator crearSeparadorPunteado() {
        JSeparator sep = new JSeparator() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(COLOR_SEPARADOR);
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    0, new float[]{4, 4}, 0));
                g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
                g2.dispose();
            }
        };
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        return sep;
    }

    private void setDatosTicketSimple(int idTicket, String fechaCreacion) {
        lblInfoIds.setText(String.format("Ticket: #%d", idTicket));
        lblFechaCreacion.setText("Creación: " + fechaCreacion);
        lblEstadoVenta.setVisible(false);
        lblEstadoCocina.setVisible(false);
        lblUrgencia.setVisible(false);
        lblFechaHoraInicio.setVisible(false);
        lblFechaHoraFin.setVisible(false);
        lblMetodoPago.setText("Método de pago: ---");
    }

    public void setDatosTicketCompleto(int ventaId, String estadoCocina, String urgencia, String fechaCreacion, String fechaInicio, String fechaFin, boolean pagado) {
        lblInfoIds.setText(String.format("Ticket: #%d", ventaId));
        lblFechaCreacion.setText("Creación: " + fechaCreacion);
        lblEstadoCocina.setText("Cocina: " + (estadoCocina != null ? estadoCocina : "PENDIENTE"));
        lblFechaHoraInicio.setText("Inicio: " + (fechaInicio != null ? fechaInicio : "---"));
        lblFechaHoraFin.setText("Fin: " + (fechaFin != null && !fechaFin.trim().isEmpty() ? fechaFin : "EN PROCESO"));

        String urg = (urgencia != null ? urgencia.toUpperCase() : "NORMAL");
        lblUrgencia.setText("Urgencia: " + urg);

        if ("ALTA".equals(urg)) {
            lblUrgencia.setForeground(COLOR_URGENCIA_ALTA);
            lblUrgencia.setFont(new Font("Arial", Font.BOLD, 11));
        } else {
            lblUrgencia.setForeground(COLOR_TEXTO_GRIS);
            lblUrgencia.setFont(AppFont.small());
        }

        if(pagado) {
            lblEstadoVenta.setText("PAGADO");
        } else {
            lblEstadoVenta.setText("CANCELADO");
            lblEstadoVenta.setForeground(Paleta_Colores.URGENTE.getColor());
            lblEstadoVenta.setFont(getFont().deriveFont(Font.BOLD));
        }
    }

    public void setMetodoPago(String metodoPago) {
        lblMetodoPago.setText("Método de pago: " + metodoPago);
    }

    public void agregarItemTicket(int idProducto, int cantidad, String nombre, String precioUnitario, String subtotal) {
        panelItems.add(crearFilaTicket(idProducto, cantidad, nombre, precioUnitario, subtotal));
        panelItems.add(Box.createVerticalStrut(8));
        panelItems.revalidate();
        panelItems.repaint();
    }

    public void limpiarTicket() {
        panelItems.removeAll();
        lblInfoIds.setText("Venta: ---");
        lblFechaCreacion.setText("Creación: ---");
        lblFechaHoraInicio.setText("Inicio: ---");
        lblFechaHoraFin.setText("Fin: ---");
        lblEstadoCocina.setText("Cocina: PENDIENTE");
        lblUrgencia.setText("Urgencia: NORMAL");
        lblUrgencia.setForeground(COLOR_TEXTO_GRIS);
        lblTotalVal.setText("$0.00");
        lblMetodoPago.setText("Método de pago: ---");
        panelItems.revalidate();
        panelItems.repaint();
    }

    public void setTotal(String totalTexto) {
        lblTotalVal.setText(totalTexto);
    }

    public void setCajero(String cajeroNombre) {
        this.cajero.setText("Cajero: " + cajeroNombre);
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Frame Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Prueba constructor ticket simple 
            TicketVentaDialog vistaSimple = new TicketVentaDialog(frame, 104, "06/06/2026 15:30:00");
            vistaSimple.setCajero("Carlos Mendoza");
            vistaSimple.setMetodoPago("EFECTIVO");
            vistaSimple.agregarItemTicket(1001, 2, "Hamburguesa Doble BBQ", "$150.00", "$300.00");
            vistaSimple.agregarItemTicket(1002, 1, "Orden de Papas Gde", "$55.00", "$55.00");
            vistaSimple.agregarItemTicket(1003, 3, "Refresco de Cola 600ml", "$30.00", "$90.00");
            vistaSimple.setTotal("$445.00");
            vistaSimple.setVisible(true);
        });
    }
}