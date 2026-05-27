package views.Admin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import controller.InventarioDialogController;
import utilidades.AppFont;
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
	BotonPersonalizado btnVer;
	BotonPersonalizado btnEdit;
	BotonPersonalizado btnDelete;
	BotonPersonalizado btnAdd;
	
	
	//Tabla
	BarraBusquedaFiltro	barraBusquedaConFiltro;
	JTable tabla;
	
	public RecipeView() {
        // Ajustes
        this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        // Acciones
        gbc.weighty = 0.25;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        this.add(crearModuloAcciones(), gbc);
        
        // Form derecho
        gbc.weighty = 1;
        gbc.weightx = 0.35;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        this.add(crearModuloFormulario(), gbc);
        
        //Tabla
        gbc.weighty = 0.75;
        gbc.weightx = 0.65;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        this.add(crearModuloTabla(), gbc);
        
        btnAdd.addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				abrirFormulario();
		    }
		});
    }
	
	public JPanel crearModuloAcciones() {
		//Titulo
		JPanel panelTitulo = new JPanel();
		panelTitulo.setOpaque(false);
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
		
		JLabel titulo = new JLabel("Tabla de recetas");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		
		panelTitulo.add(titulo);
		
		//Acciones
		JPanel panelAcciones = new JPanel();
		panelAcciones.setOpaque(false);
        
        
	        
		
		//Busqueda
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.X_AXIS));
		panelBusqueda.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		String[] ejemplo = {"Elegir","Nombre","Categoria"};
		barraBusquedaConFiltro = new BarraBusquedaFiltro("",ejemplo);
		panelBusqueda.add(barraBusquedaConFiltro);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		btnVer = new BotonPersonalizado("Ver",Paleta_Colores.ACENTO_PRIMARIO.getColor());
        btnAdd = new BotonPersonalizado("Añadir", Paleta_Colores.EXITO.getColor());
        btnEdit = new BotonPersonalizado("Editar", Paleta_Colores.ATENCION.getColor());
        btnDelete = new BotonPersonalizado("Borrar", Paleta_Colores.URGENTE.getColor());
        
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnAdd);
        panelBotones.add(btnEdit);
        panelBotones.add(btnDelete);
        panelBotones.add(btnVer);
        
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
	
	public JPanel crearModuloFormulario() {
		panelFormulario = new PanelRedondeadoConMargen();
		return panelFormulario;
	}
	
	public void abrirFormulario() {
		if(panelFormulario.isVisible()) {
			panelFormulario.setVisible(false);
			this.revalidate(); 
			this.repaint();    
		} else {
			panelFormulario.setVisible(true);
			this.revalidate();
			this.repaint();
		}
	}
		
		
	
}
