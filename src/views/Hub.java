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

public class Hub extends JFrame{
	public static final String USERS = "USERS";
	
	private CardLayout cardLayout;
	private JPanel contenedorPrincipal;
	
	public UsersView userPanel;
	public JButton btnUsers;

	public Hub() {
		setSize(1200,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		//FOTO
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/image/icono.jpg");
		setIconImage(icono);
		
		createButtons();
		createViews();
		
		setVisible(true);
	}
	
	public void createButtons() {
		JPanel barra = new JPanel();
		
		btnUsers = new JButton("Usuarios");
		barra.add(btnUsers);
		
		add(barra, BorderLayout.NORTH);
	}
	
	public void createViews() {
		cardLayout = new CardLayout();
		contenedorPrincipal = new JPanel(cardLayout);
		
		userPanel = new UsersView();
		contenedorPrincipal.add(userPanel, USERS);
		
		add(contenedorPrincipal, BorderLayout.SOUTH);
	}
	
	public void showView(String view) {
		cardLayout.show(contenedorPrincipal, view);
	}
}
