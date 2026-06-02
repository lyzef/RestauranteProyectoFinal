package views.Admin;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BotonHub;

public class HubFrame extends JFrame{
	public static final String USERS = "USERS";
	public static final String DASHBOARD = "DASHBOARD";
	public static final String INVENTORY = "INVENTORY";
	public static final String SELLS = "SELLS";
	public static final String MENU = "MENU";
	public static final String RECIPE = "RECIPE";
	
	
	private CardLayout cardLayout;
	private JPanel panelPrincipal;
	private JPanel panelLateral;
	private JLabel barraNavegacion;
	private BotonHub botonLogOut;
	
	public UsersView userPanel;
	public DashboardView dashboardPanel;
	public InventoryView inventarioPanel;
	public RecipeView recetasPanel; 
	public MenuAdminView menuAdminPanel;

	public BotonHub botonUsuarios;
	public BotonHub botonInventario;
	public BotonHub botonReportes;
	public BotonHub botonVentas;
	public BotonHub botonDashboard;
	public BotonHub botonMenu;
	public BotonHub botonRecipe;

	public HubFrame() {
		setSize(1400,900);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		ImageIcon i = GeneradorIconos.cargarIcono("/assets/image/IconoApliacionPrincipal.jpg");
		if(i != null) {
			setIconImage(i.getImage());
		}
		
		inicializarComponentes();
		
		setVisible(true);
	}
	
	private void inicializarComponentes() {
		JPanel contenedorPrincipal = new JPanel(new BorderLayout());
		
		//Panel principal y identificador de panel para cardlayout
		cardLayout = new CardLayout();
		panelPrincipal = new JPanel(cardLayout); //Contenido
		panelPrincipal.add(crearAdministradorUsuarios(),USERS);
		panelPrincipal.add(crearDashboard(), DASHBOARD);
		panelPrincipal.add(crearInventario(),INVENTORY);
		panelPrincipal.add(crearRecetas(),RECIPE);
		panelPrincipal.add(crearMenu(),MENU);
		
		//Layout en contenedor principal
		contenedorPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Estirar en ambas direcciones
        gbc.weighty = 1.0;
        gbc.gridx = 0;      // Columna 0
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.15; 
        gbc.gridheight = 2;
        contenedorPrincipal.add(crearBarraLateral(),gbc);
        
        gbc.gridx = 1;      // Columna 1
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.90; 
        gbc.weighty = 0;
        gbc.gridheight = 1;
        contenedorPrincipal.add(crearBarraSuperior(),gbc);
        
        gbc.weightx = 0.85; 
        gbc.weighty = 1;
        gbc.gridx = 1;      // Columna 1
        gbc.gridy = 1;      // Fila 1
        gbc.gridheight = 1;
        contenedorPrincipal.add(panelPrincipal,gbc);
       
        this.add(contenedorPrincipal);
	}
	
	private JPanel crearBarraSuperior() {
		JPanel barra = new JPanel();
		barra.setLayout(new BoxLayout(barra, BoxLayout.X_AXIS));
		
		Border margenEntrePanel = BorderFactory.createEmptyBorder(15, 20, 15, 20);
		Border lineaInferior = new MatteBorder(0, 0, 1, 0, Paleta_Colores.CONTENEDORES.getColor());

		// Combinamos: la línea va afuera y el margen adentro
		barra.setBorder(BorderFactory.createCompoundBorder(lineaInferior, margenEntrePanel));
		
		barra.setBackground(Paleta_Colores.FONDO.getColor());
		barraNavegacion = new JLabel();
		GeneradorIconos.aplicarIcono("/assets/image/lista.png", barraNavegacion, Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		
		JLabel nombre = new JLabel("  Madero system");
		nombre.setFont(AppFont.title());
		nombre.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		
		barra.add(barraNavegacion);
		barra.add(nombre);
		
		return barra;
	}
	
	private JPanel crearBarraLateral() {
		panelLateral = new JPanel();
		Color colorIconos = Paleta_Colores.TEXTO_SECUNDARIO.getColor();
		panelLateral.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.PAGE_AXIS));
		
		Border margenEntrePanel = BorderFactory.createEmptyBorder(15, 20, 15, 20);
		Border lineaInferior = new MatteBorder(0, 1, 0, 0, Paleta_Colores.CONTENEDORES.getColor());

		// Combinamos: la línea va afuera y el margen adentro
		panelLateral.setBorder(BorderFactory.createCompoundBorder(lineaInferior, margenEntrePanel));
		
		botonDashboard = new BotonHub("Dashboard", "/assets/image/aplicaciones.png",colorIconos);
		botonDashboard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));		//Establece que el componente se puede estirar a infinito en x pero solo hasta 50 en Y
		panelLateral.add(botonDashboard);
		
		botonUsuarios = new BotonHub("Usuario", "/assets/image/users.png",colorIconos);
		botonUsuarios.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonUsuarios);
		
		botonInventario = new BotonHub("Inventario", "/assets/image/caja.png",colorIconos);
		botonInventario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonInventario);
		
		botonVentas = new BotonHub("Ventas", "/assets/image/receipt.png",colorIconos);
		botonVentas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonVentas);
		
		botonReportes = new BotonHub("Reportes", "/assets/image/stats.png",colorIconos);
		botonReportes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonReportes);
		
		botonRecipe = new BotonHub("Recetas", "/assets/image/libro-alt.png",colorIconos);
		botonRecipe.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonRecipe);
		
		botonMenu = new BotonHub("Menu", "/assets/image/restaurante.png",colorIconos);
		botonMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonMenu);
		
		//Separacion
		panelLateral.add(Box.createVerticalGlue());
		
		botonLogOut = new BotonHub("Salir", "/assets/image/salida.png",colorIconos);
		botonLogOut.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonLogOut);
		
		return panelLateral;
	}
	
	public void abrirBarra() {
		if(panelLateral.isVisible()) {
			panelLateral.setVisible(false);
			this.revalidate(); 
			this.repaint();    
		} else {
			panelLateral.setVisible(true);
			this.revalidate();
			this.repaint();
		}
	}
	
	private JPanel crearAdministradorUsuarios() {
		userPanel = new UsersView();
		return userPanel;
	}
	
	private JPanel crearDashboard() {
		dashboardPanel = new DashboardView();
		return dashboardPanel;
	}
	
	private JPanel crearInventario() {
		inventarioPanel = new InventoryView();
		return inventarioPanel;
	}
	
	public JPanel crearRecetas() {
		recetasPanel = new RecipeView();
		return recetasPanel;
	}
	
	public JPanel crearMenu() {
		menuAdminPanel = new MenuAdminView();
		return menuAdminPanel;
	}
	
	public void showView(String view) {
		cardLayout.show(panelPrincipal, view);
	}
	
	
	//Getter y setters
	
	public JPanel getBotonUsuarios() {
		return botonUsuarios;
	}

	public JPanel getBotonInventario() {
		return botonInventario;
	}

	public JPanel getBotonReportes() {
		return botonReportes;
	}

	public JPanel getBotonVentas() {
		return botonVentas;
	}

	public JPanel getBotonDashboard() {
		return botonDashboard;
	}

	public UsersView getUserPanel() {
		return userPanel;
	}

	public void setUserPanel(UsersView userPanel) {
		this.userPanel = userPanel;
	}

	public DashboardView getDashboardPanel() {
		return dashboardPanel;
	}
	
	public RecipeView getRecipePanel() {
		return recetasPanel;
	}

	public InventoryView getInventarioPanel() {
		return inventarioPanel;
	}
	
	public MenuAdminView getMenuAdminPanel() {
		return menuAdminPanel;
	}

	public void setDashboardPanel(DashboardView dashboardPanel) {
		this.dashboardPanel = dashboardPanel;
	}

	public void setInventarioPanel(InventoryView inventarioPanel) {
		this.inventarioPanel = inventarioPanel;
	}

	public JPanel getBotonMenu() {
		return botonMenu;
	}

	public JPanel getBotonRecipe() {
		return botonRecipe;
	}
	
	public JLabel getBarraNavegacion() {
		return barraNavegacion;
	}
	
	public JPanel getBotonLogOut() {
		return botonLogOut;
	}
	
	
	
	
	
}