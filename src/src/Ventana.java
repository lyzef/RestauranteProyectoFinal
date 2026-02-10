package src;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Ventana extends JFrame{
	public Ventana() {
		setSize(500,500); //Establece el tamaño
		//Termina la ejecución del programa al cerrar la ventana.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Establece el lugar donde aparecerá la ventana
		setLocation(100,100); 
		//Establece la ubicación y el tamaño de la ventana 
		//setBounds(100,100,500,500);
		//Establece si la ventana puede redimensionarse
		setResizable(false);
		//Cambia el título de la ventana
		setTitle("Mi Aplicación");
		//Coloca la ventana al centro de la pantalla
		setLocationRelativeTo(null);
		setVisible(true); //Establece visibilidad
		
		setLayout(null);
		JButton boton = new JButton("Ingresar");
		boton.setBounds(10,250,200,50);
		add(boton);
		
		JLabel label = new JLabel("Ingrese su correo electronico");
		label.setFont(new Font("arial",Font.PLAIN,20));
		label.setBounds(10,20,500,100);
		add(label);
		
		JTextField textField = new JTextField();
		textField.setFont(new Font("Arial",Font.PLAIN,20));
		textField.setBounds(10,100,200,50);
		add(textField);
		
		JLabel label1 = new JLabel("Ingrese su contraseña");
		label1.setFont(new Font("arial",Font.PLAIN,20));
		label1.setBounds(10,120,500,100);
		add(label1);
		
		JPasswordField Password = new JPasswordField();
		Password.setFont(new Font("Arial",Font.PLAIN,20));
		Password.setBounds(10,200,200,50);
		add(Password);
	}
}
