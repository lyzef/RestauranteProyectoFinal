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
import utilidades.views.PanelPersonalizadoTabla;
import utilidades.views.ModuloParaEstadistica;

public class UsersView extends JPanel {
	
    private JTable table;

    private JButton btnSee;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnExportPDF; 
    private JButton btnRefresh;
    private JLabel advertencias;
    
    private ModuloParaEstadistica modTotalUsuarios;
    private ModuloParaEstadistica modTotalUsuariosActivos;
    private ModuloParaEstadistica modTotalCajeros;
    private ModuloParaEstadistica modTotalCocineros;
    
    public UsersView() {
        setBounds(100, 100, 900, 600);
        
        this.setBackground(Paleta_Colores.FONDO.getColor()); 
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 0.15;
		
        modTotalUsuarios = new ModuloParaEstadistica("Total empleados", "Sin datos", "", Paleta_Colores.HEADER_TABLA.getColor(), "/assets/image/users.png");
        modTotalUsuariosActivos = new ModuloParaEstadistica("Total usuarios activos", "Sin datos", "", Paleta_Colores.HEADER_TABLA.getColor(), "/assets/image/user-gear.png");
        modTotalCocineros = new ModuloParaEstadistica("Total cocineros", "Sin datos", "", Paleta_Colores.HEADER_TABLA.getColor(), "/assets/image/dineroIcon.png");
        modTotalCajeros = new ModuloParaEstadistica("Total cajeros", "Sin datos", "", Paleta_Colores.HEADER_TABLA.getColor(), "/assets/image/hat-chef.png");

		gbc.gridx = 0;
		this.add(modTotalUsuarios, gbc);
		gbc.gridx = 1;
		this.add(modTotalUsuariosActivos, gbc);
		gbc.gridx = 2;
		this.add(modTotalCocineros, gbc);
		gbc.gridx = 3;
		this.add(modTotalCajeros, gbc);
		
		gbc.weighty = 0;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		this.add(crearPanelAcciones(), gbc);
		
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		this.add(crearTabla(), gbc);
    }
    
    private JPanel crearPanelAcciones() {
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAcciones.setOpaque(false);
        btnSee = new JButton("Ver");
        btnAdd = new JButton("Añadir");
        btnEdit = new JButton("Editar");
        btnDelete = new JButton("Eliminar");
        
        configurarBoton(btnSee, Paleta_Colores.ACENTO_PRIMARIO.getColor(), Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        configurarBoton(btnAdd, Paleta_Colores.EXITO.getColor(), Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        configurarBoton(btnEdit, Paleta_Colores.ATENCION.getColor(), Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        configurarBoton(btnDelete, Paleta_Colores.URGENTE.getColor(), Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        
        panelAcciones.add(btnAdd);
        panelAcciones.add(btnEdit);
        panelAcciones.add(btnDelete);
        panelAcciones.add(btnSee);
        
        JPanel panelAccionesRIGHT = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAccionesRIGHT.setOpaque(false);
        btnExportPDF = new JButton("Exportar PDF"); 
        btnRefresh = new JButton("Refrescar tabla");
        
        configurarBoton(btnExportPDF, Paleta_Colores.ACENTO_PRIMARIO.getColor(), Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        configurarBoton(btnRefresh, new Color(0, 0, 0), Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        
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
    	PanelPersonalizadoTabla panelTabla = new PanelPersonalizadoTabla();
        table = panelTabla.getTabla();
        return panelTabla;
    }
    
    private void configurarBoton(JButton boton, Color colorFondo, Color colorTexto) {
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

	public ModuloParaEstadistica getModTotalUsuarios() {
		return modTotalUsuarios;
	}

	public ModuloParaEstadistica getModTotalUsuariosActivos() {
		return modTotalUsuariosActivos;
	}

	public ModuloParaEstadistica getModTotalCajeros() {
		return modTotalCajeros;
	}

	public ModuloParaEstadistica getModTotalCocineros() {
		return modTotalCocineros;
	}

	public void setTotalUsuarios(String totalUsuarios) {
		this.modTotalUsuarios.setValor(totalUsuarios);
	}

	public void setTotalUsuariosActivos(String totalUsuariosActivos) {
		this.modTotalUsuariosActivos.setValor(totalUsuariosActivos);
	}

	public void setTotalCajeros(String totalCajeros) {
		this.modTotalCajeros.setValor(totalCajeros);
	}

	public void setTotalCocineros(String totalCocineros) {
		this.modTotalCocineros.setValor(totalCocineros);
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