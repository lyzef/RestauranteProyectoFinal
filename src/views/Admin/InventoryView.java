package views.Admin;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BarraBusquedaFiltro;
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
    
    //Tabla
    JTable tabla;
    JTextField textFieldTabla;
	
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
		panelAcciones.setOpaque(false);
		
		//Busqueda
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		String[] ejemplo = {"Nombre","Stock","Stock maximo"};
		barraBusquedaConFiltro = new BarraBusquedaFiltro(ejemplo);
		panelBusqueda.add(barraBusquedaConFiltro);
		
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
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.WEST; 
        panelPrincipal.add(panelBusqueda, gbc);
        
        return panelPrincipal;
	}
	
	private JPanel crearTabla() {
		PanelPersonalizadoTabla panelTabla = new PanelPersonalizadoTabla();
        tabla = panelTabla.getTabla();
        return panelTabla;
	}
	
	public JTextField getTextField() {
		return barraBusquedaConFiltro.getTextFieldTabla();
	}
	
	public void setListaFiltrosBusqueda(String[] listData) {
		barraBusquedaConFiltro.setListaFiltros(listData);
	}
    
	
	
}
