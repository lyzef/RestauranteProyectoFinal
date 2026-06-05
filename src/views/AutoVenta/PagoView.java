package views.AutoVenta;

import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.BotonPersonalizado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PagoView extends JPanel {

    // Colores del tema
    private static final Color COLOR_FONDO        = new Color(15, 23, 42);
    private static final Color COLOR_PANEL         = new Color(30, 41, 59);
    private static final Color COLOR_PANEL_ITEM    = new Color(40, 55, 80);
    private static final Color COLOR_ACENTO        = new Color(59, 130, 246);
    private static final Color COLOR_TEXTO         = new Color(255, 255, 255);
    private static final Color COLOR_TEXTO_GRIS    = new Color(148, 163, 184);
    private static final Color COLOR_SEPARADOR     = new Color(51, 65, 85);
    private static final Color COLOR_TOTAL         = new Color(255, 165, 0);

    // Componentes expuestos para el Controlador
    private JLabel cajero;
    private JLabel lblTotalVal;
    private JPanel panelItems; // Contenedor específico para las filas del ticket
    
    // Botones
    private BotonPersonalizado botonTarjeta;
    private BotonPersonalizado botonEfectivo;
    private BotonPersonalizado botonTransferencia;

    public PagoView() {
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(300, 0));

        add(crearHeader(),   BorderLayout.NORTH);
        add(crearCuerpo(),   BorderLayout.CENTER);
        add(crearMetodosPago(), BorderLayout.SOUTH);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PANEL);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        // Ícono + título
        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Ventana de pago");
        titulo.setFont(AppFont.bold());
        titulo.setForeground(COLOR_TEXTO);

        izq.add(titulo);

        // Ícono usuario
        cajero = new JLabel("Cajero: Pedrito lopez");
        cajero.setFont(AppFont.small());
        cajero.setForeground(COLOR_TEXTO_GRIS);

        header.add(izq,        BorderLayout.WEST);
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

        // Panel ticket estilo papel
        JPanel ticket = new JPanel() {
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

        // Nombre restaurante centrado
        JLabel lblRestaurante = new JLabel("Madero's Restaurant", SwingConstants.CENTER);
        lblRestaurante.setFont(new Font("Arial", Font.BOLD, 13));
        lblRestaurante.setForeground(COLOR_TEXTO);
        lblRestaurante.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblRestaurante);
        ticket.add(Box.createVerticalStrut(4));

        //Slogan,fecha, numero de ticket etc opcional
        JLabel lblTicket = new JLabel("Algo bello pa'l camello", SwingConstants.CENTER);
        lblTicket.setFont(AppFont.small());
        lblTicket.setForeground(COLOR_TEXTO_GRIS);
        lblTicket.setAlignmentX(CENTER_ALIGNMENT);
        ticket.add(lblTicket);
        ticket.add(Box.createVerticalStrut(14));

        // Separador punteado
        ticket.add(crearSeparadorPunteado());
        ticket.add(Box.createVerticalStrut(10));

       
        panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setOpaque(false);
        ticket.add(panelItems);
        
        // Separador punteado
        ticket.add(Box.createVerticalStrut(4));
        ticket.add(crearSeparadorPunteado());
        ticket.add(Box.createVerticalStrut(12));

        // Total
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

    private JPanel crearFilaTicket(int cantidad, String nombre, String precio) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

        // Panel izquierdo para alinear cantidad y nombre
        JPanel panelIzquierdo = new JPanel(new BorderLayout(6, 0));
        panelIzquierdo.setOpaque(false);

        JLabel lblCantidad = new JLabel(cantidad + "x");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 12));
        lblCantidad.setForeground(COLOR_ACENTO);

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNombre.setForeground(COLOR_TEXTO_GRIS);

        panelIzquierdo.add(lblCantidad, BorderLayout.WEST);
        panelIzquierdo.add(lblNombre, BorderLayout.CENTER);

        JLabel lblPrecio = new JLabel(precio, SwingConstants.RIGHT);
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrecio.setForeground(COLOR_TEXTO);

        fila.add(panelIzquierdo, BorderLayout.WEST);
        fila.add(lblPrecio, BorderLayout.EAST);
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

    private JPanel crearMetodosPago() {
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBackground(COLOR_PANEL);
        footer.setBorder(new EmptyBorder(14, 14, 18, 14));

        // Título
        JLabel lblTitulo = new JLabel("SELECCIONE MÉTODO DE PAGO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 11));
        lblTitulo.setForeground(COLOR_TEXTO_GRIS);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        footer.add(lblTitulo);
        footer.add(Box.createVerticalStrut(12));

        // Instanciación de los botones de clase
        botonTarjeta = new BotonPersonalizado("Tarjeta", COLOR_PANEL_ITEM);
        botonEfectivo = new BotonPersonalizado("Efectivo", COLOR_PANEL_ITEM);
        botonTransferencia = new BotonPersonalizado("Transferencia", COLOR_PANEL_ITEM);
        
        botonTarjeta.setTextoFont(AppFont.bold());
        botonEfectivo.setTextoFont(AppFont.bold());
        botonTransferencia.setTextoFont(AppFont.bold());
        
        footer.add(botonTarjeta);
        footer.add(Box.createVerticalStrut(8));
        footer.add(botonTransferencia);
        footer.add(Box.createVerticalStrut(8));
        footer.add(botonEfectivo);
        footer.add(Box.createVerticalStrut(14));

        return footer;
    } 
    
    /**
     * Agrega un nuevo producto a la lista visual del ticket.
     */
    public void agregarItemTicket(int cantidad, String nombre, String precio) {
        panelItems.add(crearFilaTicket(cantidad, nombre, precio));
        panelItems.add(Box.createVerticalStrut(8)); // Espacio entre elementos
        
        // Refrescar el panel para mostrar los cambios
        panelItems.revalidate();
        panelItems.repaint();
    }

    /**
     * Elimina todos los productos visuales del ticket.
     */
    public void limpiarTicket() {
        panelItems.removeAll();
        
        // Refrescar el panel para mostrar los cambios
        panelItems.revalidate();
        panelItems.repaint();
    }
    
    /**
     * Actualiza el valor total a pagar mostrado en el ticket.
     */
    public void setTotal(String totalTexto) {
        lblTotalVal.setText(totalTexto);
    }

    public BotonPersonalizado getBotonTarjeta() {
        return botonTarjeta;
    }

    public void setBotonTarjeta(BotonPersonalizado botonTarjeta) {
        this.botonTarjeta = botonTarjeta;
    }

    public BotonPersonalizado getBotonEfectivo() {
        return botonEfectivo;
    }

    public void setBotonEfectivo(BotonPersonalizado botonEfectivo) {
        this.botonEfectivo = botonEfectivo;
    }

    public BotonPersonalizado getBotonTransferencia() {
        return botonTransferencia;
    }

    public void setBotonTransferencia(BotonPersonalizado botonTransferencia) {
        this.botonTransferencia = botonTransferencia;
    }

	public void setCajero(String cajero) {
		this.cajero.setText("Cajero: "+ cajero);
		this.revalidate();
		this.repaint();
	}
    
    
}