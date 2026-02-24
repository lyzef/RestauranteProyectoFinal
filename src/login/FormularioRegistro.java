package login;

import image.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
		JLabel lblTitulo = new JLabel("Registro");
		lblTitulo.setFont(new Font("Times", Font.PLAIN,17));
		panelTitulo.add(lblTitulo);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		add(panelTitulo,BorderLayout.NORTH);
		JPanel panelComponentes = new JPanel();
		
		panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
		panelComponentes.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		//Preguntas
		JLabel lblNombre = new JLabel("Nombre completo ");
		panelComponentes.add(lblNombre);
		JTextField entradaNombre = new JTextField(20);
		panelComponentes.add(entradaNombre);
		
		
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
		
		JLabel lblCP = new JLabel("CP ");
		panelComponentes.add(lblCP);
		JTextField entradaCP = new JTextField(20);
		panelComponentes.add(entradaCP);
		
		
	
		
		JScrollPane scroll = new JScrollPane(panelComponentes);
		scroll.setHorizontalScrollBar(null);
		
		add(scroll);
	}

}
