package login;

import javax.swing.*;
import java.awt.*;


public class Login extends JFrame{
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

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentroEntrada.add(textField);

        JLabel labelPass = new JLabel("Ingrese la contraseña");
        labelPass.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentroEntrada.add(labelPass);

        JPasswordField password = new JPasswordField(20);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentroEntrada.add(password);
        panelCentroEntrada.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        //Panel sur
        JButton boton = new JButton("Entrar");
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        panelSurEntrada.add(boton);
        
       
        
        //Layouts
        add(panelNorteContenedor, BorderLayout.NORTH);
        add(panelCentroEntrada, BorderLayout.CENTER);
        add(panelSurEntrada, BorderLayout.SOUTH);
        
        setVisible(true);
        
		
	}
}
