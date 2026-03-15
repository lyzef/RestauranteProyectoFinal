package views.formulario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
public class FormularioRegistro extends JFrame{
	panelPregunta nombre;
	panelPregunta fechaNacimiento;
	panelPregunta curp;
	panelPregunta telefono;
	panelPregunta correo;
	List <panelPregunta> listaPreguntas;
	JComboBox<String> estadoCivil;
	JComboBox<String> generos;
	
	
	public FormularioRegistro() {
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
		//Paneles
		JPanel panelContenedorSuperior = new JPanel();
		JPanel panelContenedorInferior = new JPanel();
		
		//Panel superior
		JLabel lblTitulo = new JLabel("Registro - Datos personales");
		lblTitulo.setFont(new Font("Times", Font.PLAIN,17));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelContenedorSuperior.add(lblTitulo);
		
		//Panel inferior
		JButton lblBotonRegistro = new JButton("Siguiente");
		lblBotonRegistro.setBackground(new Color(144, 224, 239));
		panelContenedorInferior.add(lblBotonRegistro);
		
		lblBotonRegistro.addActionListener( e -> {
	        	validarFormulario();
	        	});
	        
		//Panel central
		JScrollPane scroll = new JScrollPane(crearPreguntas()); //Scroll almacena al panel de preguntas
		scroll.setHorizontalScrollBar(null);
		
		//Anadiendo paneles
		
		add(scroll,BorderLayout.CENTER); //Es el contenedor padre del panel cuestionario
		add(panelContenedorSuperior,BorderLayout.NORTH);
		add(panelContenedorInferior,BorderLayout.SOUTH);
		
		
		
	}
	
	public JPanel crearPreguntas() {
		//Panel central cuestionario
		JPanel panelCuestionario = new JPanel();
		panelCuestionario.setLayout(new  BoxLayout(panelCuestionario, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		panelCuestionario.setBorder(emptyBorder);
		
		nombre = new panelPregunta("Nombre", "ALFABETICO");
		fechaNacimiento = new panelPregunta("Fecha de nacimiento", "FECHA");
		curp = new panelPregunta("Curp", "ALFANUMERICO");
		telefono = new panelPregunta("Telefono", "NUMERICO");
		correo = new panelPregunta("Correo", "CORREO");
		//Inicializacion de array
		listaPreguntas = new ArrayList<>();
		
		listaPreguntas.add(nombre);
		listaPreguntas.add(fechaNacimiento);
		listaPreguntas.add(curp);
		listaPreguntas.add(telefono);
		listaPreguntas.add(correo);
		
		for(panelPregunta pregunta : listaPreguntas) {
			panelCuestionario.add(pregunta);
		}
		
		
		
		//ComboBox
		JLabel lblGenero = new JLabel("Genero ");
		panelCuestionario.add(lblGenero);
		String[] opcionesGenero = {"Seleccionar","Hombre", "Mujer","Therian","Otro"};
		generos = new JComboBox<String>(opcionesGenero);
		generos.setSelectedIndex(0); //Item preseleccionado
		panelCuestionario.add(generos);
		
		JLabel lblEstadoCivil = new JLabel("EstadoCivil ");
		panelCuestionario.add(lblEstadoCivil);
		String[] opcionesEstadoCivil = {"Seleccionar","Soltero","Casado","Union libre", "Viudo"};
		estadoCivil = new JComboBox<String>(opcionesEstadoCivil);
		estadoCivil.setSelectedIndex(0);
		panelCuestionario.add(estadoCivil);
		
		return panelCuestionario;
	}
	
	public void validarFormulario() {
		//Comprueba preguntas sin responder
		boolean faltaRellenar = false;
		for(panelPregunta pregunta: listaPreguntas) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				faltaRellenar = true;
			}
		}
		if(faltaRellenar) {return;}
		
		if(estadoCivil.getSelectedItem() == "Seleccionar" || generos.getSelectedItem() == "Seleccionar" ) {
			return;
		}
		
		new FormularioRegistroInformacionPuesto();
    	this.dispose();
		
	}
	
	
}
