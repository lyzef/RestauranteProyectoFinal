package views.Admin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Locale.Category;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import org.jfree.data.category.DefaultCategoryDataset;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import models.Categoria;
import models.Platillo;
import models.User;
import tableFormat.UserTableFormat;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BarraBusquedaFiltro;
import utilidades.views.BotonPersonalizado;
import utilidades.views.ModuloParaEstadistica;
import utilidades.views.PanelPersonalizadoTabla;
import utilidades.views.PanelRedondeadoConMargen;

public class MenuAdminView extends JPanel{
    public ModuloParaEstadistica moduloVentas;
    public ModuloParaEstadistica moduloOrdenes;
    
    DefaultCategoryDataset dataset;
    
    BotonPersonalizado btnAgregarPlatillo;
    BotonPersonalizado btnEditarPlatillo;
    BotonPersonalizado btnEliminarPlatillo;
    BotonPersonalizado btnVerPlatillo;
    JLabel lblRefrescarTabla;
    
    BotonPersonalizado btnAgregarCategoria;
    BotonPersonalizado btnEditarCategoria;
    BotonPersonalizado btnEliminarCategoria;
    BotonPersonalizado btnVerCategoria; 
    
    JTable tablaPlatillos;
    JTable tablaCategorias;
    
    BarraBusquedaFiltro barraBusquedaConFiltroPlatillos;
    
    public MenuAdminView() {
        this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        
        moduloVentas = new ModuloParaEstadistica(
            "Ventas hoy", "Sin datos", "Aumento en 15% desde ayer", 
            Paleta_Colores.ACENTO_PRIMARIO.getColor(), "/assets/image/dineroIcon.png"
        );
        
        moduloOrdenes = new ModuloParaEstadistica(
            "Ordenes en el dia", "Sin datos", "25 % menos que el promedio", 
            Paleta_Colores.ATENCION.getColor(), "/assets/image/receipt.png"
        );
        
        gbc.gridy = 0; 
        gbc.weighty = 0.0; 
        
        gbc.gridx = 0;
        gbc.weightx = 0.375; 
        this.add(moduloVentas, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.375; 
        this.add(moduloOrdenes, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.25; 
        this.add(crearPanelAccionesCategoria(), gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2; 
        gbc.weightx = 0.75; 
        gbc.weighty = 0.0; 
        this.add(crearPanelAcciones(), gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 2;      
        gbc.gridwidth = 1;  
        gbc.gridheight = 2; 
        gbc.weightx = 0.25; 
        gbc.weighty = 1.0; 
        this.add(crearTablaCategoria(), gbc);
        
        gbc.gridy = 2;      
        gbc.gridx = 0;      
        gbc.gridwidth = 2; 
        gbc.gridheight = 1; 
        gbc.weightx = 0.75; 
        gbc.weighty = 1.0; 
        this.add(crearTablaPlatillo(), gbc);
    }
	
    private JPanel crearPanelAcciones() {
		JPanel panelTitulo = new JPanel();
		panelTitulo.setOpaque(false);
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
		JLabel titulo = new JLabel("Platillos  ");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		lblRefrescarTabla = new JLabel();
		new GeneradorIconos().aplicarIcono("/assets/image/actualizar.png", lblRefrescarTabla);
		
		panelTitulo.add(titulo);
		panelTitulo.add(lblRefrescarTabla);
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.X_AXIS));
		panelBusqueda.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		String[] ejemplo = {"Elegir","Nombre","Stock","Tipo"};
		barraBusquedaConFiltroPlatillos = new BarraBusquedaFiltro("",ejemplo);
		panelBusqueda.add(barraBusquedaConFiltroPlatillos);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		btnVerPlatillo = new BotonPersonalizado("Ver",Paleta_Colores.ACENTO_PRIMARIO.getColor());
        btnAgregarPlatillo = new BotonPersonalizado("Añadir", Paleta_Colores.EXITO.getColor());
        btnEditarPlatillo = new BotonPersonalizado("Editar", Paleta_Colores.ATENCION.getColor());
        btnEliminarPlatillo = new BotonPersonalizado("Borrar", Paleta_Colores.URGENTE.getColor());
        
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnAgregarPlatillo);
        panelBotones.add(btnEliminarPlatillo);
        panelBotones.add(btnEditarPlatillo);
        panelBotones.add(btnVerPlatillo);
        
		JPanel panelPrincipal = new PanelRedondeadoConMargen(PanelRedondeadoConMargen.RADIO_ESQUINA_ESTANDAR, Paleta_Colores.CONTENEDORES.getColor(), PanelRedondeadoConMargen.MARGEN_ESTANDAR, PanelRedondeadoConMargen.PADDING_INTERNO);
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;      
        gbc.gridy = 0;      
        gbc.gridwidth = 2;
        gbc.weightx = 1.0; 
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(panelTitulo, gbc);
        
        gbc.gridy = 1;      
        gbc.gridwidth = 1; 
        gbc.weightx = 0.5; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        panelPrincipal.add(panelBusqueda, gbc);
        
        gbc.fill = GridBagConstraints.NONE; 
        gbc.gridx = 1;      
        gbc.anchor = GridBagConstraints.EAST; 
        panelPrincipal.add(panelBotones, gbc);
        
        return panelPrincipal;
	}
    
    private JPanel crearPanelAccionesCategoria() {
		JPanel panelTitulo = new JPanel();
		panelTitulo.setOpaque(false);
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
		JLabel titulo = new JLabel("Categorias");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelTitulo.add(titulo);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		btnVerCategoria = new BotonPersonalizado("Ver",Paleta_Colores.ACENTO_PRIMARIO.getColor());
        btnAgregarCategoria = new BotonPersonalizado("Añadir", Paleta_Colores.EXITO.getColor());
        btnEditarCategoria = new BotonPersonalizado("Editar", Paleta_Colores.ATENCION.getColor());
        btnEliminarCategoria = new BotonPersonalizado("Borrar", Paleta_Colores.URGENTE.getColor());
        
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnAgregarCategoria);
        panelBotones.add(btnEliminarCategoria);
        panelBotones.add(btnEditarCategoria);
        panelBotones.add(btnVerCategoria);
        
        JPanel panelDescripcion = new JPanel();
        panelDescripcion.setOpaque(false);
        panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.X_AXIS)); 
        JLabel descripcion = new JLabel("Borrar platillos antes que categoria");
        descripcion.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        descripcion.setFont(AppFont.normal());
        panelDescripcion.add(descripcion);
        
		JPanel panelPrincipal = new PanelRedondeadoConMargen();
        panelPrincipal.setLayout(new GridBagLayout());
        int espacioTotal = PanelRedondeadoConMargen.PADDING_INTERNO + PanelRedondeadoConMargen.MARGEN_ESTANDAR;
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(2, espacioTotal, 2, espacioTotal));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;      
        gbc.gridy = 0;      
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(panelTitulo, gbc);
        
        gbc.gridx = 1;      
        gbc.anchor = GridBagConstraints.EAST; 
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(panelBotones, gbc);
        
        gbc.gridx = 0;      
        gbc.gridy = 1;      
        gbc.gridwidth = 2; 
        gbc.weightx = 1.0; 
        gbc.anchor = GridBagConstraints.WEST; 
        panelPrincipal.add(panelDescripcion, gbc);
        
        return panelPrincipal;
	}
	
	private JPanel crearTablaPlatillo() {
		PanelPersonalizadoTabla panel = new PanelPersonalizadoTabla();
        tablaPlatillos = panel.getTabla();
        return panel;
	}
	
	private JPanel crearTablaCategoria() {
		PanelPersonalizadoTabla panel = new PanelPersonalizadoTabla();
        tablaCategorias = panel.getTabla();
        return panel;
	}
	
	public int getSelectedRowCategoria() {
		return tablaCategorias.getSelectedRow();
	}
	
	public int getSelectedRowPlatillo() {
		return tablaPlatillos.getSelectedRow();
	}
	
	public void setTableModelPlatillos(AdvancedTableModel<Platillo> tableModel){
		tablaPlatillos.setModel(tableModel);
    }
	
	public void setTableModelCategorias(AdvancedTableModel<Categoria> tableModel){
		tablaCategorias.setModel(tableModel);
    }

	public JTable getTablaPlatillos() {
		return tablaPlatillos;
	}

	public JTable getTablaCategorias() {
		return tablaCategorias;
	}
	
	public ModuloParaEstadistica getModuloVentas() {
        return moduloVentas;
    }

    public ModuloParaEstadistica getModuloOrdenes() {
        return moduloOrdenes;
    }

    public DefaultCategoryDataset getDataset() {
        return dataset;
    }
    
    public BotonPersonalizado getBtnAgregarPlatillo() {
        return btnAgregarPlatillo;
    }

    public BotonPersonalizado getBtnEditarPlatillo() {
        return btnEditarPlatillo;
    }

    public BotonPersonalizado getBtnEliminarPlatillo() {
        return btnEliminarPlatillo;
    }

    public BotonPersonalizado getBtnVerPlatillo() {
        return btnVerPlatillo;
    }
    
    public BotonPersonalizado getBtnAgregarCategoria() {
        return btnAgregarCategoria;
    }

    public BotonPersonalizado getBtnEditarCategoria() {
        return btnEditarCategoria;
    }

    public BotonPersonalizado getBtnEliminarCategoria() {
        return btnEliminarCategoria;
    }

    public BotonPersonalizado getBtnVerCategoria() {
        return btnVerCategoria;
    }
    
    public BarraBusquedaFiltro getBarraBusquedaConFiltroPlatillos() {
        return barraBusquedaConFiltroPlatillos;
    }

	public JLabel getLblRefrescarTabla() {
		return lblRefrescarTabla;
	}
    
    
	
}