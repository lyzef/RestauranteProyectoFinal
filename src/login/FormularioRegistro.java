package login;

import image.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioRegistro extends JFrame{
	
	public FormularioRegistro() {
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		//FJOTO -- kfc
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("/restauranteProyectoFinal/src/image") ;
		setIconImage(icono);
		
		InicializarComponentes();
		
		setVisible(true);
		
		
	}
	
	public void InicializarComponentes() {
		//Label titulo (Panel Norte)
		JPanel panelTitulo = new JPanel();
		JPanel panelAbajo = new JPanel();
		JPanel panelComponentes = new JPanel();
		
		JLabel lblTitulo = new JLabel("Registro - Datos personales");
		lblTitulo.setFont(new Font("Times", Font.PLAIN,17));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.add(lblTitulo);
		
		JButton lblBotonRegistro = new JButton("Siguiente");
		panelAbajo.add(lblBotonRegistro);
		
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
		panelComponentes.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		add(panelTitulo,BorderLayout.NORTH);
		add(panelAbajo,BorderLayout.SOUTH);
		
		//Preguntas
		JLabel lblApellidpoP = new JLabel("Apellido Paterno");
		panelComponentes.add(lblApellidpoP);
		JTextField entradaApellidoP = new JTextField(20);
		panelComponentes.add(entradaApellidoP);
		
		JLabel lblApellidoMaterno = new JLabel("Apellido Materno");
		panelComponentes.add(lblApellidoMaterno);
		JTextField entradaApellidoM = new JTextField(20);
		panelComponentes.add(entradaApellidoM);
		
		JLabel lblNombres = new JLabel("Nombres");
		panelComponentes.add(lblNombres);
		JTextField entradaNombres = new JTextField(20);
		panelComponentes.add(entradaNombres);
		
		JLabel lblEdad = new JLabel("Edad ");
		panelComponentes.add(lblEdad);
		JTextField entradaEdad = new JTextField(20);
		panelComponentes.add(entradaEdad);
		
		JLabel lblCorreo = new JLabel("Correo Electronico");
		panelComponentes.add(lblCorreo);
		JTextField entradaCorreo = new JTextField(20);
		panelComponentes.add(entradaCorreo);
		
		JLabel lblTelefono = new JLabel("Telefono ");
		panelComponentes.add(lblTelefono);
		JTextField entradaTelefono = new JTextField(20);
		panelComponentes.add(entradaTelefono);
		
		JLabel lblGenero = new JLabel("Genero ");
		panelComponentes.add(lblGenero);
		String[] opcionesGenero = {"Hombre", "Mujer","Therian","Trans","Mamadero","Otro"};
		JComboBox<String> generos = new JComboBox<String>(opcionesGenero);
		generos.setSelectedIndex(2);
		panelComponentes.add(generos);
		
		JLabel lblEstadoCivil = new JLabel("EstadoCivil ");
		panelComponentes.add(lblEstadoCivil);
		String[] opcionesEstadoCivil = {"Soltero","Casado","Union libre", "Viudo"};
		JComboBox<String> estadoCivil = new JComboBox<String>(opcionesEstadoCivil);
		estadoCivil.setSelectedIndex(2);
		panelComponentes.add(estadoCivil);
		
		JLabel lblCurp = new JLabel("Curp ");
		panelComponentes.add(lblCurp);
		JTextField entradaCurp = new JTextField(20);
		panelComponentes.add(entradaCurp);

		//Registro parte 1 de formulario
		
		
		
		JScrollPane scroll = new JScrollPane(panelComponentes);
		scroll.setHorizontalScrollBar(null);
		
		add(scroll);
	}

}
