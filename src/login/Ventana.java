package login;

import java.awt.Color;
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
		setLocationRelativeTo(null); //A mitad de pantalla
		setVisible(false); //Establece visibilidad
		
		setLayout(null);
		JButton boton = new JButton("Ingresar");
		boton.setBounds(10,290,200,50);
		add(boton);
		
		JLabel label = new JLabel("Ingrese su correo electronico");
		label.setFont(new Font("arial",Font.PLAIN,20));
		label.setBounds(10,20,500,100);
		add(label);
		
		JTextField textField = new JTextField();
		textField.setBounds(10,100,200,50);
		textField.setFont(new Font("Arial",Font.PLAIN,20));
		add(textField);
		
		JLabel label1 = new JLabel("Ingrese su contraseña");
		label1.setFont(new Font("arial",Font.PLAIN,20));
		label1.setBounds(10,160,500,100);
		add(label1);
		
		JPasswordField Password = new JPasswordField();
		Password.setBounds(10,230,200,50);
		Password.setFont(new Font("Arial",Font.PLAIN,20));
		add(Password);
		
		
		//Mal contraseña
		JLabel mensajeError = new JLabel("Usuario inexistente o contraseña incorrecta");
		mensajeError.setFont(new Font("arial",Font.PLAIN,15));
		mensajeError.setBounds(10,300,500,100);
		mensajeError.setForeground(Color.RED);
		add(mensajeError);
		
		setVisible(true);
		
	}
}
