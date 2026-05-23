package views.Admin;

import java.awt.*;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import config.Config;
import models.User;
import tablemodels.UserTableFormat;
import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.PanelRedondeadoConMargen;
import utilidades.views.PanelRedondeadoConMargenYTabla;

//Muestra la tabla, botones de edicion, eliminacion, añadido y exportacion de tablas a PDF
public class UsersView extends JPanel {
	
    private JTable table;

    private JButton btnSee;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnExportPDF; 
    private JButton btnRefresh;
    private JLabel advertencias;
    
    //Modulos superiores
    JLabel totalUsuarios;
    JLabel totalUsuariosActivos;
    JLabel totalCajeros;
    JLabel totalCocineros;
    
    public UsersView() {
        setBounds(100, 100, 900, 600);
        
        this.setBackground(Paleta_Colores.FONDO.getColor()); 
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 0.15;
		
		// Tres modulos superiores
		gbc.gridx = 0;
		this.add(totalEmpleados(),gbc);
		gbc.gridx = 1;
		this.add(totalUsuariosActivos(),gbc);
		gbc.gridx = 2;
		this.add(totalCocineros(),gbc);
		gbc.gridx = 3;
		this.add(totalCajeros(),gbc);
		
		// 1 Modulo de opciones en tabla
		gbc.weighty = 0;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		this.add(crearPanelAcciones(),gbc);
		
		// 1 Modulo de tabla
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		this.add(crearTabla(), gbc);
        
        
    }
    
    private JPanel totalEmpleados() {
    	JPanel panelTotalEmpleados = new PanelRedondeadoConMargen();
    	panelTotalEmpleados.setLayout(new BoxLayout(panelTotalEmpleados, BoxLayout.Y_AXIS));
	
    	JLabel icono = new JLabel("Icono");
    	
		JLabel titulo = new JLabel("Total empleados", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelTotalEmpleados.add(titulo);

		panelTotalEmpleados.add(Box.createRigidArea(new Dimension(0, 25)));
		
		totalUsuarios = new JLabel("Sin datos",JLabel.LEFT);
		totalUsuarios.setFont(AppFont.title());
		totalUsuarios.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelTotalEmpleados.add(totalUsuarios);
	
		return panelTotalEmpleados;
    }
    
    private JPanel totalUsuariosActivos() {
    	JPanel panelTotalUsuarios = new PanelRedondeadoConMargen();
    	panelTotalUsuarios.setLayout(new BoxLayout(panelTotalUsuarios, BoxLayout.Y_AXIS));
	
    	JLabel icono = new JLabel("Icono");
    	
		JLabel titulo = new JLabel("Total usuarios activos", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelTotalUsuarios.add(titulo);

		panelTotalUsuarios.add(Box.createRigidArea(new Dimension(0, 25)));
		
		totalUsuariosActivos = new JLabel("Sin datos",JLabel.LEFT);
		totalUsuariosActivos.setFont(AppFont.title());
		totalUsuariosActivos.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelTotalUsuarios.add(totalUsuariosActivos);
	
		return panelTotalUsuarios;
    }
    
    private JPanel totalCocineros() {
    	JPanel panelTotalCocineros = new PanelRedondeadoConMargen();
    	panelTotalCocineros.setLayout(new BoxLayout(panelTotalCocineros, BoxLayout.Y_AXIS));
	
    	JLabel icono = new JLabel("Icono");
    	
		JLabel titulo = new JLabel("Total cocineros", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelTotalCocineros.add(titulo);

		panelTotalCocineros.add(Box.createRigidArea(new Dimension(0, 25)));
		
		totalCocineros = new JLabel("Sin datos",JLabel.LEFT);
		totalCocineros.setFont(AppFont.title());
		totalCocineros.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelTotalCocineros.add(totalCocineros);
	
		return panelTotalCocineros;
    }
    
    private JPanel totalCajeros() {
    	JPanel panelTotalCajeros = new PanelRedondeadoConMargen();
    	panelTotalCajeros.setLayout(new BoxLayout(panelTotalCajeros, BoxLayout.Y_AXIS));
	
    	JLabel icono = new JLabel("Icono");
    	
		JLabel titulo = new JLabel("Total cajeros", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelTotalCajeros.add(titulo);

		panelTotalCajeros.add(Box.createRigidArea(new Dimension(0, 25)));
		
		totalCajeros = new JLabel("Sin datos");
		totalCajeros.setFont(AppFont.title());
		totalCajeros.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelTotalCajeros.add(totalCajeros);
	
		return panelTotalCajeros;
    }
    
    private JPanel crearPanelAcciones() {
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAcciones.setOpaque(false);
        btnSee = new JButton("Ver");
        btnAdd = new JButton("Añadir");
        btnEdit = new JButton("Editar");
        btnDelete = new JButton("Eliminar");
        
        configurarBoton(btnSee, Paleta_Colores.ACENTO_PRIMARIO.getColor(),Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        configurarBoton(btnAdd, Paleta_Colores.EXITO.getColor(),Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        configurarBoton(btnEdit, Paleta_Colores.ATENCION.getColor(),Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        configurarBoton(btnDelete, Paleta_Colores.URGENTE.getColor(),Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        
        panelAcciones.add(btnAdd);
        panelAcciones.add(btnEdit);
        panelAcciones.add(btnDelete);
        panelAcciones.add(btnSee);
        
        JPanel panelAccionesRIGHT = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAccionesRIGHT.setOpaque(false);
        btnExportPDF = new JButton("Exportar PDF"); 
        btnRefresh = new JButton("Refrescar tabla");
        
        configurarBoton(btnExportPDF, Paleta_Colores.ACENTO_PRIMARIO.getColor(),Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        configurarBoton(btnRefresh, new Color(0, 0, 0),Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        
        panelAccionesRIGHT.add(btnExportPDF);
        panelAccionesRIGHT.add(btnRefresh);
        
        advertencias = new JLabel("Listo");
        advertencias.setOpaque(false);
        advertencias.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        
        
        JPanel panelPrincipal = new PanelRedondeadoConMargen();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH; 

        gbc.gridx = 0;      
        gbc.gridy = 0;      
        gbc.weightx = 0.5; 
        gbc.weighty = 0;
        gbc.gridheight = 1;
        panelPrincipal.add(panelAcciones, gbc);
        
        gbc.gridx = 1;      
        gbc.gridy = 0;      
        gbc.weightx = 0.5; 
        gbc.weighty = 0;
        gbc.gridheight = 1;
        panelPrincipal.add(panelAccionesRIGHT, gbc);
        
        gbc.gridx = 0;      
        gbc.gridy = 1;      
        gbc.weightx = 1.0; 
        gbc.weighty = 0;
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.WEST; 
        panelPrincipal.add(advertencias, gbc);
        
        return panelPrincipal;
    }
    
    private JPanel crearTabla() {
    	PanelRedondeadoConMargenYTabla panelTabla = new PanelRedondeadoConMargenYTabla();
        table = panelTabla.getTabla();
        return panelTabla;
    }
    
    private void configurarBoton(JButton boton, Color colorFondo,Color colorTexto) {
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false); 
        boton.setFont(AppFont.normal());
    }
    
    public int getSelectedRow() {
        return table.getSelectedRow();
    }
    
    public void setTableModel(AdvancedTableModel<User> tableModel){
    	table.setModel(tableModel);
    }

    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnExportPDF() { return btnExportPDF; }
    public JLabel getAdvertencias() { return advertencias; }



	public JLabel getTotalUsuarios() {
		return totalUsuarios;
	}



	public JLabel getTotalUsuariosActivos() {
		return totalUsuariosActivos;
	}



	public JLabel getTotalCajeros() {
		return totalCajeros;
	}



	public JLabel getTotalCocineros() {
		return totalCocineros;
	}



	public void setTotalUsuarios(String totalUsuarios) {
		this.totalUsuarios.setText(totalUsuarios);
	}



	public void setTotalUsuariosActivos(String totalUsuariosActivos) {
		this.totalUsuariosActivos.setText(totalUsuariosActivos);
	}



	public void setTotalCajeros(String totalCajeros) {
		this.totalCajeros.setText(totalCajeros);
	}



	public void setTotalCocineros(String totalCocineros) {
		this.totalCocineros.setText(totalCocineros);
	}

	public JTable getTable() {
		return table;
	}

	public JButton getBtnSee() {
		return btnSee;
	}

	public JButton getBtnRefresh() {
		return btnRefresh;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void setBtnSee(JButton btnSee) {
		this.btnSee = btnSee;
	}

	public void setBtnAdd(JButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public void setBtnEdit(JButton btnEdit) {
		this.btnEdit = btnEdit;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public void setBtnExportPDF(JButton btnExportPDF) {
		this.btnExportPDF = btnExportPDF;
	}

	public void setBtnRefresh(JButton btnRefresh) {
		this.btnRefresh = btnRefresh;
	}

	public void setAdvertencias(JLabel advertencias) {
		this.advertencias = advertencias;
	}
	
	
    
    
    
}