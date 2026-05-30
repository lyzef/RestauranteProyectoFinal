package views.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import ca.odell.glazedlists.swing.AdvancedTableModel;
import models.ComponenteIngredienteReceta;
import models.User;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BarraBusquedaFiltro;
import utilidades.views.BotonPersonalizado;
import utilidades.views.ModuloParaEstadistica;
import utilidades.views.PanelRedondeadoConMargen;
import utilidades.views.PanelPersonalizadoTabla;

public class InventoryView extends JPanel{
	
	//Modulos superiores
	public ModuloParaEstadistica moduloVentas;
    public ModuloParaEstadistica moduloItemsBajoStock;
    public ModuloParaEstadistica moduloDineroGastadoMensual;
    public ModuloParaEstadistica moduloOrdenesHoy;
    
    //Barra de busqueda
    BarraBusquedaFiltro barraBusquedaConFiltro;
    
    //Modificadores de tabla
    private JPanel btnMovimientoInventario;
    private JPanel btnCambiarTabla;
    
    private JPanel btnSee;
    private JPanel btnAdd;
    private JPanel btnEdit;
    private JPanel btnDelete;
    private JPanel btnRefresh;
    private JLabel ultimoMovimiento;
    
    //Tabla
    JTable tabla;
	
	public InventoryView() {
		//Ajustes
		this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Modulks superiores
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
		
		JLabel titulo = new JLabel("Tabla de insumos");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		
		panelTitulo.add(titulo);
		
		//Acciones
		JPanel panelAcciones = new JPanel();
		
		btnCambiarTabla = new BotonPersonalizado("Cambiar tabla", Paleta_Colores.HEADER_TABLA.getColor());
		btnCambiarTabla.setAlignmentX(RIGHT_ALIGNMENT);
		panelAcciones.add(btnCambiarTabla);
		
		btnMovimientoInventario = new BotonPersonalizado("Nuevo movimiento de inventario", Paleta_Colores.HEADER_TABLA.getColor());
		btnMovimientoInventario.setAlignmentX(RIGHT_ALIGNMENT);
		panelAcciones.add(btnMovimientoInventario);
		panelAcciones.setOpaque(false);
        
        
	        
		
		//Busqueda
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.X_AXIS));
		panelBusqueda.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		String[] ejemplo = {"Elegir","Nombre","Stock","Tipo"};
		barraBusquedaConFiltro = new BarraBusquedaFiltro("",ejemplo);
		panelBusqueda.add(barraBusquedaConFiltro);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		btnSee = new BotonPersonalizado("Ver",Paleta_Colores.ACENTO_PRIMARIO.getColor());
        btnAdd = new BotonPersonalizado("Añadir", Paleta_Colores.EXITO.getColor());
        btnEdit = new BotonPersonalizado("Editar", Paleta_Colores.ATENCION.getColor());
        btnDelete = new BotonPersonalizado("Borrar", Paleta_Colores.URGENTE.getColor());
        
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnAdd);
        panelBotones.add(btnEdit);
        panelBotones.add(btnDelete);
        panelBotones.add(btnSee);
        
		JPanel panelPrincipal = new PanelRedondeadoConMargen();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
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
        
        gbc.gridx = 0;      
        gbc.gridy = 1;      
        gbc.weightx = 1; 
        gbc.weighty = 0;
        gbc.gridwidth = 1; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.WEST; 
        panelPrincipal.add(panelBusqueda, gbc);
        
        gbc.gridx = 1;      
        gbc.gridy = 1;      
        gbc.weightx = 1; 
        gbc.weighty = 0;
        gbc.gridwidth = 1; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.EAST; 
        panelPrincipal.add(panelBotones, gbc);
        
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
	
	
	//Getters y setters
	
	
	public String getFiltroSeleccionado() {
		return barraBusquedaConFiltro.getFiltroSeleccionado();
	}
	public JPanel getBtnMovimientoInventario() {
		return btnMovimientoInventario;
	}

	public JPanel getBtnSee() {
		return btnSee;
	}

	public JPanel getBtnAdd() {
		return btnAdd;
	}

	public JPanel getBtnEdit() {
		return btnEdit;
	}

	public JPanel getBtnDelete() {
		return btnDelete;
	}

	public JPanel getBtnRefresh() {
		return btnRefresh;
	}

	public JLabel getUltimoMovimiento() {
		return ultimoMovimiento;
	}
	
	public JPanel getBtnCambiarTabla() {
		return btnCambiarTabla;
	}

	public void setBtnMovimientoInventario(JPanel btnMovimientoInventario) {
		this.btnMovimientoInventario = btnMovimientoInventario;
	}

	public void setBtnSee(JPanel btnSee) {
		this.btnSee = btnSee;
	}

	public void setBtnAdd(JPanel btnAdd) {
		this.btnAdd = btnAdd;
	}

	public void setBtnEdit(JPanel btnEdit) {
		this.btnEdit = btnEdit;
	}

	public void setBtnDelete(JPanel btnDelete) {
		this.btnDelete = btnDelete;
	}

	public void setBtnRefresh(JPanel btnRefresh) {
		this.btnRefresh = btnRefresh;
	}

	public void setUltimoMovimiento(String ultimoMovimiento) {
		this.ultimoMovimiento.setText(ultimoMovimiento);
	}

	public JTextField getTextFieldTabla() {
		return barraBusquedaConFiltro.getTextFieldTabla();
	}
	
	public void setBtnCambiarTablaText (String t) {
		((BotonPersonalizado) btnCambiarTabla).setTexto(t);;
	}
    
	
	
	
}
