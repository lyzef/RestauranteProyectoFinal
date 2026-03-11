package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.formulario.FormularioRegistro;

public class Login extends JFrame{
	
	Font font = new Font("Arial", Font.PLAIN, 16);
	Font miniFont = new Font("Arial", Font.PLAIN, 13);
	JTextField entradaCorreo;
	JPasswordField entradaContrasena;
	JLabel labelAdvertenciaContrasena;
	JLabel labelAdvertenciaCorreo;
	Color colorFondo = new Color(242,244,243);
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
		setTitle("Madero's Chef System");
		//Coloca la ventana al centro de la pantalla
		setLocationRelativeTo(null); //A mitad de pantalla
		setVisible(false); //Establece visibilidad
		//this.setLayout(new BorderLayout()); Layout por defecto de JFrame
		
		loadIcon();
		initializeComponents();
        setVisible(true);
        
		
	}
	
	public void initializeComponents() {
		createLogo();
		createForm();
		createButtons();
	}
	
	public void loadIcon(){
		Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/image/IconoApliacionPrincipal.jpg");
        setIconImage(icono);
	}
	
	public void createLogo() {
		//Panel norte
		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(colorFondo);
		
		panelLogo.setLayout(new BoxLayout(panelLogo, BoxLayout.Y_AXIS));

		JLabel labelNombreRestaurante = new JLabel("Madero's");
		labelNombreRestaurante.setFont(new Font("Times", Font.PLAIN, 24));
		labelNombreRestaurante.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelLogo.add(labelNombreRestaurante);
		
		JLabel labelTitulo = new JLabel("Inicio de sesion");
	    labelTitulo.setFont(font);
	    labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panelLogo.add(labelTitulo);
		
	    panelLogo.setBorder(BorderFactory.createEmptyBorder(10,5,5,5));

		add(panelLogo, BorderLayout.NORTH);
	}
	
	public void createForm() {
		//Panel centro
		JPanel panelForm = new JPanel(new GridLayout(6, 1, 10, 10));
		panelForm.setBackground(colorFondo);
        JLabel labelCorreo = new JLabel("Ingrese el correo electrónico");
        labelCorreo.setFont(font);
        panelForm.add(labelCorreo);

        entradaCorreo = new JTextField(20);
        entradaCorreo.setFont(font);
        panelForm.add(entradaCorreo);
        
        labelAdvertenciaCorreo = new JLabel("Correo es requerido");
        labelAdvertenciaCorreo.setFont(miniFont);
        labelAdvertenciaCorreo.setVisible(false);
        panelForm.add(labelAdvertenciaCorreo);
        
        JLabel labelPass = new JLabel("Ingrese la contraseña");
        labelPass.setFont(font);
        panelForm.add(labelPass);

        entradaContrasena = new JPasswordField(20);
        entradaContrasena.setFont(font);
        panelForm.add(entradaContrasena);
        
        
        labelAdvertenciaContrasena = new JLabel("Contrasena es requerido");
        labelAdvertenciaContrasena.setFont(miniFont);
        labelAdvertenciaContrasena.setVisible(false);
        panelForm.add(labelAdvertenciaContrasena);
        
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelForm, BorderLayout.CENTER);
	}
	
	public void createButtons() {
		 //Panel sur
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(colorFondo);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
		
        JButton boton = new JButton("Entrar");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(font);
        panelButtons.add(boton);
        
        boton.addActionListener( e -> {
        	validarLogin();
		});
        
        JLabel botonRegistrar = new JLabel("Registrar nuevo empleado");
        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrar.setFont(miniFont);
        panelButtons.add(botonRegistrar);
        
        botonRegistrar.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				botonRegistrar.setForeground(Color.black);
				
			}
			
			public void mouseEntered(MouseEvent e) {
				botonRegistrar.setForeground(new Color(170,204,0));
				
			}
			
			public void mouseClicked(MouseEvent e) {
				registro();
				
			}
		});
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //Layouts
       
        
        add(panelButtons, BorderLayout.SOUTH);
	}
	// Eventos
	public void registro() {
		new FormularioRegistro();
		this.dispose();
	}
	// Metodos de presentacion
	public void reinicarMensajesError() {
		labelAdvertenciaCorreo.setVisible(false);
		labelAdvertenciaContrasena.setVisible(false);
	}
	// Validaciones
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
					"Bienvenido....",
					JOptionPane.INFORMATION_MESSAGE
					);
			new VentanaPrincipal();
			this.dispose();
		}
		System.out.println("CORREO: " + entradaCorreo.getText());
		System.out.println("CONTRASENA: " + String.valueOf(entradaContrasena.getPassword()));
	}
	
}
