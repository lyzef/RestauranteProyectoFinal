package views.formulario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

import controller.PreguntaController;
import utilidades.PanelTipoPreguntaUtil;
public class FormularioRegistro extends JFrame{
	PanelTipoPreguntaUtil nombre;
	PanelTipoPreguntaUtil fechaNacimiento;
	PanelTipoPreguntaUtil curp;
	PanelTipoPreguntaUtil telefono;
	PanelTipoPreguntaUtil correo;
	List <PanelTipoPreguntaUtil> listaPreguntas;
	JComboBox<String> estadoCivil;
	JComboBox<String> generos;
	JButton lblBotonRegistro;
	
	
	
	public PanelTipoPreguntaUtil getNombre() {
		return nombre;
	}

	public void setNombre(PanelTipoPreguntaUtil nombre) {
		this.nombre = nombre;
	}

	public PanelTipoPreguntaUtil getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(PanelTipoPreguntaUtil fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public PanelTipoPreguntaUtil getCurp() {
		return curp;
	}

	public void setCurp(PanelTipoPreguntaUtil curp) {
		this.curp = curp;
	}

	public PanelTipoPreguntaUtil getTelefono() {
		return telefono;
	}

	public void setTelefono(PanelTipoPreguntaUtil telefono) {
		this.telefono = telefono;
	}

	public PanelTipoPreguntaUtil getCorreo() {
		return correo;
	}

	public void setCorreo(PanelTipoPreguntaUtil correo) {
		this.correo = correo;
	}

	public List<PanelTipoPreguntaUtil> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<PanelTipoPreguntaUtil> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public JComboBox<String> getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(JComboBox<String> estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public JComboBox<String> getGeneros() {
		return generos;
	}

	public void setGeneros(JComboBox<String> generos) {
		this.generos = generos;
	}

	public JButton getLblBotonRegistro() {
		return lblBotonRegistro;
	}

	public void setLblBotonRegistro(JButton lblBotonRegistro) {
		this.lblBotonRegistro = lblBotonRegistro;
	}

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
		
		//Confirmar salida de formulario
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = JOptionPane.showConfirmDialog(null, 
		            "¿Seguro que quieres salir?", 
		            "Confirmar salida", JOptionPane.YES_NO_OPTION);
		        
		        if (verif == JOptionPane.YES_OPTION) {
		            System.exit(0); 
		        }
		    }
		});
		
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
		lblBotonRegistro = new JButton("Siguiente");
		lblBotonRegistro.setBackground(new Color(144, 224, 239));
		panelContenedorInferior.add(lblBotonRegistro);
	        
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
		
		//Inicializacion paneles
		nombre = new PanelTipoPreguntaUtil("Nombre", "ALFABETICO");
		fechaNacimiento = new PanelTipoPreguntaUtil("Fecha de nacimiento", "FECHA");
		curp = new PanelTipoPreguntaUtil("Curp", "ALFANUMERICO");
		telefono = new PanelTipoPreguntaUtil("Telefono", "NUMERICO");
		correo = new PanelTipoPreguntaUtil("Correo", "CORREO");
		//Inicializacion de array
		listaPreguntas = new ArrayList<>();
		
		//Conexion con controlador 
		PreguntaController.registrarPanel(nombre);
		PreguntaController.registrarPanel(fechaNacimiento);
		PreguntaController.registrarPanel(curp);
		PreguntaController.registrarPanel(telefono);
		PreguntaController.registrarPanel(correo);
		
		
		
		listaPreguntas.add(nombre);
		listaPreguntas.add(fechaNacimiento);
		listaPreguntas.add(curp);
		listaPreguntas.add(telefono);
		listaPreguntas.add(correo);
		
		for(PanelTipoPreguntaUtil pregunta : listaPreguntas) {
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
	
	
	
	
}
