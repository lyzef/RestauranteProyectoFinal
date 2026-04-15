package views;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Hub extends JFrame{
	JButton tocame;
	
	public JButton getTocame() {
		return tocame;
	}
	public Hub() {
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		//FOTO
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/image/icono.jpg");
		setIconImage(icono);
		
		InicializarComponentes();
		
		setVisible(true);
	}
	
	public void InicializarComponentes() {
		Panel informacionPrincipal = new Panel();
		tocame = new JButton("Lista de usuario ;~/");
		informacionPrincipal.add(tocame);
		this.add(informacionPrincipal);
	}
}
