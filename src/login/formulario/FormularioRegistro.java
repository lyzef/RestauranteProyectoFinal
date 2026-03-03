package login.formulario;

import image.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.Border;
public class FormularioRegistro extends JFrame{
	
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
		JPanel panelContenedorCentral = new JPanel();

		
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
	        	FormularioRegistroInformacionPuesto f = new FormularioRegistroInformacionPuesto();
	        	setVisible(false);
	        	});
	        
			
		//Panel central - Sub paneles
		panelContenedorCentral.setLayout(new  BoxLayout(panelContenedorCentral, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		panelContenedorCentral.setBorder(emptyBorder);

		String[] informacionPersonal = {"Nombres: ", "Fecha de nacimiento: ", "CURP: ", "Telefono: ", "Correo electronico: "};
		int largoTotalDeInformacionPersonal = informacionPersonal.length;
		for(int i = 0; i < largoTotalDeInformacionPersonal ; i++) {
			JLabel lbl = new JLabel(informacionPersonal[i]);
			
			panelContenedorCentral.add(lbl);
			JTextField txtField = new JTextField(10);
			panelContenedorCentral.add(txtField);
		}
		
		
		
		
		//Checkbox
		JLabel lblGenero = new JLabel("Genero ");
		panelContenedorCentral.add(lblGenero);
		String[] opcionesGenero = {"Hombre", "Mujer","Therian","Otro"};
		JComboBox<String> generos = new JComboBox<String>(opcionesGenero);
		generos.setSelectedIndex(2);
		panelContenedorCentral.add(generos);
		
		JLabel lblEstadoCivil = new JLabel("EstadoCivil ");
		panelContenedorCentral.add(lblEstadoCivil);
		String[] opcionesEstadoCivil = {"Soltero","Casado","Union libre", "Viudo"};
		JComboBox<String> estadoCivil = new JComboBox<String>(opcionesEstadoCivil);
		estadoCivil.setSelectedIndex(2);
		panelContenedorCentral.add(estadoCivil);
		
		//Anadiendo paneles
		JScrollPane scroll = new JScrollPane(panelContenedorCentral);
		scroll.setHorizontalScrollBar(null);
		
		
		//add(panelContenedorCentral,BorderLayout.CENTER);
		add(scroll); //Es el contenedor padre del panel contenedor central
		add(panelContenedorSuperior,BorderLayout.NORTH);
		add(panelContenedorInferior,BorderLayout.SOUTH);
		
	}

}
