package views;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaPrincipal extends JFrame{
	public VentanaPrincipal() {
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
		informacionPrincipal.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Nigger "+ "tocaste " + e.getKeyChar() );
				
			}
		});
		add(informacionPrincipal);
		informacionPrincipal.requestFocus();
	}
}
