package views.Admin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import ca.odell.glazedlists.swing.AdvancedTableModel;
import controller.dialogs.InventarioFormController;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BarraBusquedaFiltro;
import utilidades.views.BotonPersonalizado;
import utilidades.views.ModuloParaEstadistica;
import utilidades.views.PanelPersonalizadoTabla;
import utilidades.views.PanelRedondeadoConMargen;
import views.Dialog.InventarioDialog;

public class RecipeView extends JPanel{
	
	JPanel panelAcciones;
	JPanel panelFormulario;
	PanelPersonalizadoTabla panelTabla;
	//Boton tabla
	BotonPersonalizado btnCrear;
	BotonPersonalizado btnEditarReceta;
	BotonPersonalizado btnVer;
	BotonPersonalizado btnDelete;
	
	BotonPersonalizado btnVerIngredientes;
	BotonPersonalizado btnEdit;
	
	
	//Tabla
	JLabel lblRefrescarTabla;
	BarraBusquedaFiltro	barraBusquedaConFiltro;
	JTable tabla;
	
	public RecipeView() {
        // Ajustes
        this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        
        // Acciones
        gbc.weighty = 0.05;
        gbc.gridy = 0;
        gbc.gridx = 0;
        this.add(crearModuloAcciones(), gbc);
        
        //Tabla
        gbc.weighty = 0.95;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(crearModuloTabla(), gbc);
        
    }
	
	public JPanel crearModuloAcciones() {
		//Titulo
		JPanel panelTitulo = new JPanel();
		panelTitulo.setOpaque(false);
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
		
		JLabel titulo = new JLabel("Tabla de recetas  ");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		lblRefrescarTabla = new JLabel();
		new GeneradorIconos().aplicarIcono("/assets/image/actualizar.png", lblRefrescarTabla);
		
		panelTitulo.add(titulo);
		panelTitulo.add(lblRefrescarTabla);
		
		//Acciones
		JPanel panelAcciones = new JPanel();
		panelAcciones.setOpaque(false);
		
		btnVer = new BotonPersonalizado("Ver",Paleta_Colores.ACENTO_PRIMARIO.getColor());
        btnCrear = new BotonPersonalizado("Añadir", Paleta_Colores.EXITO.getColor());
        btnEditarReceta = new BotonPersonalizado("Editar", Paleta_Colores.ATENCION.getColor());
        btnDelete = new BotonPersonalizado("Borrar", Paleta_Colores.URGENTE.getColor());
		
		panelAcciones.setOpaque(false);
		panelAcciones.add(Box.createHorizontalGlue());
		panelAcciones.add(btnCrear);
		panelAcciones.add(btnEditarReceta);
		panelAcciones.add(btnDelete);
		panelAcciones.add(btnVer);
	        
		
		//Busqueda
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.X_AXIS));
		panelBusqueda.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		String[] ejemplo = {"Elegir","Nombre","Categoria"};
		barraBusquedaConFiltro = new BarraBusquedaFiltro("",ejemplo);
		panelBusqueda.add(barraBusquedaConFiltro);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		btnVerIngredientes = new BotonPersonalizado("Solo Ver",Paleta_Colores.ACENTO_PRIMARIO.getColor());
        btnEdit = new BotonPersonalizado("Modificar ingredientes", Paleta_Colores.ATENCION.getColor());
        
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnEdit);
        panelBotones.add(btnVerIngredientes);
        
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
	
	public JPanel crearModuloTabla() {
		panelTabla = new PanelPersonalizadoTabla();
		tabla = panelTabla.getTabla();
		return panelTabla;
	}
	
	public JList<String> getListaFiltros() {
		return barraBusquedaConFiltro.getListaFiltros();
	}
	
	public void setListaFiltrosBusqueda(String[] listData) {
		barraBusquedaConFiltro.setListaFiltros(listData);
	}
	
	public String getFiltroSeleccionado() {
		return barraBusquedaConFiltro.getFiltroSeleccionado();
	}
	
	public int getSelectedRow() {
        return tabla.getSelectedRow();
    }
		
	public JTextField getTextoBuscador() {
		return barraBusquedaConFiltro.getTextFieldTabla();
	}	
	
	public void setTableModel(AdvancedTableModel<?> e){
	    tabla.setModel(e);
	}
	

	//Getters y setters
	
	public BotonPersonalizado getBtnVerIngredientes() {
		return btnVerIngredientes;
	}
	
	public BotonPersonalizado getBtnEdit() {
		return btnEdit;
	}

	public BotonPersonalizado getBtnCrear() {
		return btnCrear;
	}

	public BotonPersonalizado getBtnEditarReceta() {
		return btnEditarReceta;
	}

	public BotonPersonalizado getBtnVer() {
		return btnVer;
	}

	public BotonPersonalizado getBtnDelete() {
		return btnDelete;
	}

	public void setBtnVer(BotonPersonalizado btnVer) {
		this.btnVerIngredientes = btnVer;
	}

	public void setBtnEdit(BotonPersonalizado btnEdit) {
		this.btnEdit = btnEdit;
	}

	public JLabel getLblRefrescarTabla() {
		return lblRefrescarTabla;
	}
	
	
}
