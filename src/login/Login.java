package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import login.formulario.*;

public class Login extends JFrame{
	JTextField entradaCorreo;
	JPasswordField entradaContrasena;
	JLabel labelAdvertenciaContrasena;
	JLabel labelAdvertenciaCorreo;
	
	public Login() {
		setSize(500,400); //Establece el tamaño
		//Termina la ejecución del programa al cerrar la ventana.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Establece el lugar donde aparecerá la ventana
		setLocation(100,100); 
		//Establece la ubicación y el tamaño de la ventana 
		//setBounds(100,100,500,500);
		//Establece si la ventana puede redimensionarse
		setResizable(false);
		//Cambia el título de la ventana
		setTitle("Madero's System");
		//Coloca la ventana al centro de la pantalla
		setLocationRelativeTo(null); //A mitad de pantalla
		setVisible(false); //Establece visibilidad
		
		//this.setLayout(new BorderLayout()); Layout por defecto de JFrame
		
		Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/image/IconoApliacionPrincipal.jpg");
        setIconImage(icono);
		
		//Organizacion de paneles
		JPanel panelNorteContenedor = new JPanel();
		panelNorteContenedor.setLayout(new BoxLayout(panelNorteContenedor, BoxLayout.Y_AXIS));
		JPanel panelCentroEntrada = new JPanel(new GridLayout(6, 1, 10, 10));
		JPanel panelSurEntrada = new JPanel();
		
		//Panel norte
		JLabel labelNombreRestaurante = new JLabel("Madero's");
		labelNombreRestaurante.setFont(new Font("Times", Font.PLAIN, 24));
		labelNombreRestaurante.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panelNorteContenedor.add(labelNombreRestaurante);
		
		JLabel labelTitulo = new JLabel("Inicio de sesion");
	    labelTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
	    labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panelNorteContenedor.add(labelTitulo);
		
	    panelNorteContenedor.setBorder(BorderFactory.createEmptyBorder(10,5,5,5));
	    
	    //Panel centro
        JLabel labelCorreo = new JLabel("Ingrese el correo electrónico");
        labelCorreo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentroEntrada.add(labelCorreo);

        entradaCorreo = new JTextField(20);
        entradaCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentroEntrada.add(entradaCorreo);
        
        labelAdvertenciaCorreo = new JLabel("Correo es requerido");
        labelAdvertenciaCorreo.setFont(new Font("Arial", Font.BOLD, 13));
        labelAdvertenciaCorreo.setVisible(false);
        panelCentroEntrada.add(labelAdvertenciaCorreo);
        
        
        
        JLabel labelPass = new JLabel("Ingrese la contraseña");
        labelPass.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentroEntrada.add(labelPass);

        entradaContrasena = new JPasswordField(20);
        entradaContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentroEntrada.add(entradaContrasena);
        
        
        labelAdvertenciaContrasena = new JLabel("Contrasena es requerido");
        labelAdvertenciaContrasena.setFont(new Font("Arial", Font.BOLD, 13));
        labelAdvertenciaContrasena.setVisible(false);
        panelCentroEntrada.add(labelAdvertenciaContrasena);
        
        panelCentroEntrada.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //Panel sur
        JButton boton = new JButton("Entrar");
        panelSurEntrada.add(boton);
        
        boton.addActionListener( e -> {
        	validarLogin();
		});
        
        JButton botonRegistrar = new JButton("Registrar");
        panelSurEntrada.add(botonRegistrar);
        
        botonRegistrar.addActionListener( e -> {
        		registro();
        	});
        panelSurEntrada.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //Layouts
        add(panelNorteContenedor, BorderLayout.NORTH);
        add(panelCentroEntrada, BorderLayout.CENTER);
        add(panelSurEntrada, BorderLayout.SOUTH);
        
        setVisible(true);
        
		
	}
	
	public void reinicarMensajesError() {
		labelAdvertenciaCorreo.setVisible(false);
		labelAdvertenciaContrasena.setVisible(false);
	}
	
	public void validarLogin() {
		reinicarMensajesError();
		
		if(entradaCorreo.getText().isBlank() ) {
			labelAdvertenciaCorreo.setVisible(true);
		} else if (!(entradaContrasena.getPassword().length > 0)) {
			labelAdvertenciaContrasena.setVisible(true);
		} else {
			System.out.println("Es valido");
			JOptionPane.showMessageDialog(
					this,
					"Felicidades sabes escribir!",
					"No hay nada mas que ver aqui....",
					JOptionPane.INFORMATION_MESSAGE
					);
		}
		System.out.println("CORREO: " + entradaCorreo.getText());
		System.out.println("CONTRASENA: " + String.valueOf(entradaContrasena.getPassword()));
	}
	public void registro() {
		FormularioRegistro f = new FormularioRegistro();
    	setVisible(false);
	}
}
