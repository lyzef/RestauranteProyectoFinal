package views;

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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BotonMenu;
import views.Admin.DashboardView;
import views.Admin.InventoryView;
import views.Admin.UsersView;

public class Hub extends JFrame{
	public static final String USERS = "USERS";
	public static final String DASHBOARD = "DASHBOARD";
	public static final String INVENTORY = "INVENTORY";
	public static final String SELLS = "SELLS";
	
	private CardLayout cardLayout;
	private JPanel panelPrincipal;
	
	public UsersView userPanel;
	public DashboardView dashboardPanel;
	public InventoryView inventarioPanel;

	public JPanel botonUsuarios;
	public JPanel botonInventario;
	public JPanel botonReportes;
	public JPanel botonVentas;
	public JPanel botonDashboard;

	public Hub() {
		setSize(1200,700);
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
		JLabel nombre = new JLabel("Madero system");
		nombre.setFont(new Font("Arial",Font.BOLD,25));
		nombre.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		
		barra.add(nombre);
		
		return barra;
	}
	
	private JPanel crearBarraLateral() {
		JPanel panelLateral = new JPanel();
		panelLateral.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.PAGE_AXIS));
		
		Border margenEntrePanel = BorderFactory.createEmptyBorder(15, 20, 15, 20);
		Border lineaInferior = new MatteBorder(0, 1, 0, 0, Paleta_Colores.CONTENEDORES.getColor());

		// Combinamos: la línea va afuera y el margen adentro
		panelLateral.setBorder(BorderFactory.createCompoundBorder(lineaInferior, margenEntrePanel));
		
		botonDashboard = new BotonMenu("Dashboard", null);					
		botonDashboard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));		//Establece que el componente se puede estirar a infinito en x pero solo hasta 50 en Y
		panelLateral.add(botonDashboard);
		
		botonUsuarios = new BotonMenu("Usuario", null);
		botonUsuarios.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonUsuarios);
		
		botonInventario = new BotonMenu("Inventario", null);
		botonInventario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonInventario);
		
		botonVentas = new BotonMenu("Ventas", null);
		botonVentas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonVentas);
		
		botonReportes = new BotonMenu("Reportes", null);
		botonReportes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panelLateral.add(botonReportes);
		
		return panelLateral;
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
	
	public void showView(String view) {
		cardLayout.show(panelPrincipal, view);
	}
	
	
	//Getter y setters
	
	public JPanel getBotonUsuarios() {
		return botonUsuarios;
	}

	public void setBotonUsuarios(JPanel botonUsuarios) {
		this.botonUsuarios = botonUsuarios;
	}

	public JPanel getBotonInventario() {
		return botonInventario;
	}

	public void setBotonInventario(JPanel botonInventario) {
		this.botonInventario = botonInventario;
	}

	public JPanel getBotonReportes() {
		return botonReportes;
	}

	public void setBotonReportes(JPanel botonReportes) {
		this.botonReportes = botonReportes;
	}

	public JPanel getBotonVentas() {
		return botonVentas;
	}

	public void setBotonVentas(JPanel botonVentas) {
		this.botonVentas = botonVentas;
	}

	public JPanel getBotonDashboard() {
		return botonDashboard;
	}

	public void setBotonDashboard(JPanel botonDashboard) {
		this.botonDashboard = botonDashboard;
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

	public InventoryView getInventarioPanel() {
		return inventarioPanel;
	}

	public void setDashboardPanel(DashboardView dashboardPanel) {
		this.dashboardPanel = dashboardPanel;
	}

	public void setInventarioPanel(InventoryView inventarioPanel) {
		this.inventarioPanel = inventarioPanel;
	}

	
	
	
	
	
	
}
