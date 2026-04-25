package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import models.User;
import utilidades.PanelTipoPreguntaUtil;

public class UserFormDialog extends JDialog{
	PanelTipoPreguntaUtil nombre;
	PanelTipoPreguntaUtil fechaNacimiento;
	PanelTipoPreguntaUtil curp;
	PanelTipoPreguntaUtil telefono;
	PanelTipoPreguntaUtil correo;
	List <PanelTipoPreguntaUtil> listaPreguntas;
	JComboBox<String> estadoCivil;
	JComboBox<String> generos;
	JButton lblBotonGuardar;
	JButton lblBotonCancelar;
	
	private User user;
	
	
	
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

	public UserFormDialog(JFrame parent, User usuario) {
		super(parent, true);
		this.user = usuario;
		
		setSize(400,400);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		
		InicializarComponentes();
		
		
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
		lblBotonGuardar = new JButton("Guardar");
		lblBotonCancelar = new JButton("Cancelar");
		
		lblBotonGuardar.setBackground(new Color(144, 224, 239));
		panelContenedorInferior.add(lblBotonGuardar);
		
		lblBotonCancelar.setBackground(new Color(255, 0, 0));
		panelContenedorInferior.add(lblBotonCancelar);
	        
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
