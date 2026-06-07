package views.Admin;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import ca.odell.glazedlists.swing.AdvancedTableModel;
import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.BarraBusquedaFiltro;
import utilidades.views.BotonPersonalizado;
import utilidades.views.ModuloParaEstadistica;
import utilidades.views.PanelPersonalizadoTabla;
import utilidades.views.PanelRedondeadoConMargen;

public class VentaView extends JPanel{
	
	//Modulos superiores
	public ModuloParaEstadistica moduloVentas;
    public ModuloParaEstadistica moduloItemsBajoStock;
    public ModuloParaEstadistica moduloDineroGastadoMensual;
    public ModuloParaEstadistica moduloOrdenesHoy;
    
    JLabel titulo;
    
    //Barra de busqueda
    BarraBusquedaFiltro barraBusquedaConFiltro;
    
    //Periodo de busqueda 
    private JPanel btnRefrescar;
    private JDateChooser fechaInicio;
    private JDateChooser fechaFin;
    
    //Modificadores de tabla
    private JPanel btnSee;
    private JPanel btnCancel;
    private JPanel btnExportar;
    
    //Tabla
    JTable tabla;
	
	public VentaView() {
		//Ajustes
		this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Modulos superiores
		moduloVentas = new ModuloParaEstadistica(
            "Ventas hoy", "Sin datos", "Aumento en 0% desde ayer", 
            Paleta_Colores.ATENCION.getColor(), "/assets/image/dineroIcon.png"
        );
        
		moduloItemsBajoStock = new ModuloParaEstadistica(
            "Items con bajo stock", "Sin datos", "Atencion", 
            Paleta_Colores.URGENTE.getColor(), "/assets/image/triangle-warning.png"
        );
        
		moduloDineroGastadoMensual = new ModuloParaEstadistica(
            "Gasto mensual", "Sin datos", "%00 mas que el anterior", 
            Paleta_Colores.ACENTO_PRIMARIO.getColor(), "/assets/image/receipt.png"
        );
		
		moduloOrdenesHoy = new ModuloParaEstadistica(
            "Ordenes hoy", "Sin datos", "0 pendientes", 
            Paleta_Colores.ATENCION.getColor(), "/assets/image/shopping-cart.png"
	    );
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 0.15;
		
		// Tres modulos superiores
		gbc.gridx = 0;
		this.add(moduloVentas,gbc);
		gbc.gridx = 1;
		this.add(moduloItemsBajoStock,gbc);
		gbc.gridx = 2;
		this.add(moduloDineroGastadoMensual,gbc);
		gbc.gridx = 3;
		this.add(moduloOrdenesHoy,gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		this.add(crearPanelAcciones(), gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		this.add(crearTabla(), gbc);
		
	}
	
	private JPanel crearPanelAcciones() {
	    //Titulo
	    JPanel panelTitulo = new JPanel();
	    panelTitulo.setOpaque(false);
	    panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
	    
	    titulo = new JLabel("Ventas del dia");
	    titulo.setFont(AppFont.title());
	    titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
	    
	    panelTitulo.add(titulo);
	    
	    //Acciones - Botones Ver, Exportar y Borrar
	    JPanel panelAcciones = new JPanel();
	    panelAcciones.setOpaque(false);
	    panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.X_AXIS));
	    
	    btnSee = new BotonPersonalizado("Ver", Paleta_Colores.ACENTO_PRIMARIO.getColor());
	    btnExportar = new BotonPersonalizado("Exportar", Paleta_Colores.ATENCION.getColor());
	    btnCancel = new BotonPersonalizado("Cancelar", Paleta_Colores.URGENTE.getColor());
	    
	    panelAcciones.add(btnSee);
	    panelAcciones.add(Box.createHorizontalStrut(5));
	    panelAcciones.add(btnExportar);
	    panelAcciones.add(Box.createHorizontalStrut(5));
	    panelAcciones.add(btnCancel);
	    
	    // Contenedor para búsqueda y datechoosers
	    JPanel panelBusquedaFechas = new JPanel();
	    panelBusquedaFechas.setLayout(new BoxLayout(panelBusquedaFechas, BoxLayout.X_AXIS));
	    panelBusquedaFechas.setBackground(Paleta_Colores.CONTENEDORES.getColor());
	    
	    // Crear los JDateChooser
	    Date fechaActual = new Date();
	    
	    
	    fechaInicio = new JDateChooser();
	    fechaInicio.setMaxSelectableDate(fechaActual);
	    fechaInicio.setDateFormatString("dd/MM/yyyy");
	    fechaInicio.setPreferredSize(new Dimension(130, 25));
	    
	    fechaFin = new JDateChooser();
	    fechaFin.setDateFormatString("dd/MM/yyyy");
	    fechaFin.setMaxSelectableDate(fechaActual);
	    fechaFin.setPreferredSize(new Dimension(130, 25));
	    
	    JLabel lblDesde = new JLabel("Desde:");
	    lblDesde.setFont(AppFont.normal());
	    lblDesde.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
	    
	    JLabel lblHasta = new JLabel("Hasta:");
	    lblHasta.setFont(AppFont.normal());
	    lblHasta.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
	    
	    btnRefrescar = new BotonPersonalizado("Actualizar", Paleta_Colores.HEADER_TABLA.getColor());
	    
	    //Barra de búsqueda con filtro
	    String[] ejemplo = {"Elegir","Nombre","Stock","Tipo"};
	    barraBusquedaConFiltro = new BarraBusquedaFiltro("",ejemplo);
	    
	    // Agregar todo en el mismo contenedor
	    panelBusquedaFechas.add(barraBusquedaConFiltro);
	    panelBusquedaFechas.add(Box.createHorizontalStrut(5));
	    panelBusquedaFechas.add(lblDesde);
	    panelBusquedaFechas.add(Box.createHorizontalStrut(5));
	    panelBusquedaFechas.add(fechaInicio);
	    panelBusquedaFechas.add(Box.createHorizontalStrut(10));
	    panelBusquedaFechas.add(lblHasta);
	    panelBusquedaFechas.add(Box.createHorizontalStrut(5));
	    panelBusquedaFechas.add(fechaFin);
	    panelBusquedaFechas.add(Box.createHorizontalStrut(5));
	    panelBusquedaFechas.add(btnRefrescar);
	    
	    // Panel principal
	    JPanel panelPrincipal = new PanelRedondeadoConMargen();
	    panelPrincipal.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    // Primera fila: Título y Acciones (Ver, Exportar, Borrar)
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridx = 0;      
	    gbc.gridy = 0;      
	    gbc.weightx = 0.5; 
	    gbc.weighty = 0;
	    gbc.gridheight = 1;
	    panelPrincipal.add(panelTitulo, gbc);
	    
	    gbc.fill = GridBagConstraints.NONE;
	    gbc.anchor = GridBagConstraints.EAST; 
	    gbc.gridx = 1;      
	    gbc.gridy = 0;      
	    gbc.weightx = 0.5; 
	    gbc.weighty = 0;
	    gbc.gridheight = 1;
	    panelPrincipal.add(panelAcciones, gbc);
	    
	    // Segunda fila: DateChoosers y barra de búsqueda juntos
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridx = 0;      
	    gbc.gridy = 1;      
	    gbc.weightx = 1; 
	    gbc.weighty = 0;
	    gbc.gridwidth = 2; 
	    panelPrincipal.add(panelBusquedaFechas, gbc);
	    
	    return panelPrincipal;
	}

	
	private JPanel crearTabla() {
		PanelPersonalizadoTabla panelTabla = new PanelPersonalizadoTabla();
        tabla = panelTabla.getTabla();
        return panelTabla;
	}
	
	public void setTableModel(AdvancedTableModel<?> e){
	    tabla.setModel(e);
	}
	
	public JTextField getTextoBuscador() {
		return barraBusquedaConFiltro.getTextFieldTabla();
	}
	
	//Usada para anadir listener y saber el filtro actual
	public JList<String> getListaFiltros(){
		return barraBusquedaConFiltro.getListaFiltros();
	}
	
	public String getFiltroSeleccionado() {
		return barraBusquedaConFiltro.getFiltroSeleccionado();
	}
	
	public void setFiltrosBusqueda(String[] listData) {
		barraBusquedaConFiltro.setListaFiltros(listData);
	}
	
	public int getSelectedRow() {
        return tabla.getSelectedRow();
    }
	
	public void actualizarTabla() {
	    if(tabla != null) {
	    	tabla.repaint();
	        tabla.revalidate();
	    }
	}
	
	// Métodos para obtener las fechas
	public Date getFechaInicio() {
		return fechaInicio.getDate();
	}
	
	public Date getFechaFin() {
		return fechaFin.getDate();
	}
	
	public JDateChooser getFechaInicioChooser() {
		return fechaInicio;
	}
	
	public JDateChooser getFechaFinChooser() {
		return fechaFin;
	}
	
	public JPanel getBtnBuscar() {
		return btnRefrescar;
	}
	
	// Métodos getter para los botones
	public JPanel getBtnSee() {
		return btnSee;
	}

	public JPanel getBtnCancel() {
		return btnCancel;
	}

	public JPanel getBtnExportar() {
		return btnExportar;
	}
	
	public JTable getTabla() {
		return tabla;
	}
	
	public void setTextoTituloTabla(String t){
		titulo.setText(t);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba de VentaView");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Crear instancia de tu panel
            VentaView ventaView = new VentaView();

            // Agregarlo al frame
            frame.add(ventaView);

            // Ajustar tamaño y mostrar
            frame.setSize(1200, 700); // puedes ajustar según tu preferencia
            frame.setLocationRelativeTo(null); // centrar en pantalla
            frame.setVisible(true);
        });
    }
}