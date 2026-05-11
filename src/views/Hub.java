package views;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Hub extends JFrame{
	public static final String USERS = "USERS";
	public static final String MENU = "MENU";
	
	private CardLayout cardLayout;
	private JPanel contenedorPrincipal;
	
	public UsersView userPanel;
	public JPanel menu;
	
	
	public JButton btnUsers;
	public JButton btnHub;

	public Hub() {
		setSize(1200,700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		//FOTO
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/image/icono.jpg");
		setIconImage(icono);
		
		inicializarComponentes();
		
		setVisible(true);
	}
	
	public void inicializarComponentes() {
		this.add(crearBarraSuperior(),BorderLayout.NORTH);
		
		cardLayout = new CardLayout();
		contenedorPrincipal = new JPanel(cardLayout);
		
		//Panel a mostrar y identificador de panel para cardlayout
		contenedorPrincipal.add(crearAdministradorUsuarios(),USERS);
		contenedorPrincipal.add(crearMenu(), MENU); 
		
		this.add(contenedorPrincipal);
	}
	
	public JPanel crearBarraSuperior() {
		JPanel barra = new JPanel();
		btnHub = new JButton("Hub");
		barra.add(btnHub);
		btnUsers = new JButton("Usuarios");
		barra.add(btnUsers);
		
		return barra;
	}
	
	public JPanel crearAdministradorUsuarios() {
		userPanel = new UsersView();
		return userPanel;
	}
	
	public JPanel crearMenu() {
		menu = new JPanel();
		
		JLabel bienvenida = new JLabel("Weelcum");
		menu.add(bienvenida);
		
		return menu;
	}
	
	public void showView(String view) {
		cardLayout.show(contenedorPrincipal, view);
	}
}
