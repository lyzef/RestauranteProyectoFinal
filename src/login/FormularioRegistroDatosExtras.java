package login;

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

public class FormularioRegistroDatosExtras extends JFrame{
	 public FormularioRegistroDatosExtras() {
	        Toolkit tk = Toolkit.getDefaultToolkit();
	        Image icono = tk.getImage("src/image/icono.jpg");
	        setIconImage(icono);
	        
		 
	        setSize(400, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setResizable(true);
	        setTitle("Formulario");
	        setLocationRelativeTo(null);

	       
	        InicializarComponentes();

	        setVisible(true);
	    }

	    public void InicializarComponentes() {
	        // Paneles
	        JPanel panelContenedorSuperior = new JPanel();
	        JPanel panelContenedorInferior = new JPanel();
	        JPanel panelContenedorCentral = new JPanel();

	        // Panel superior
	        JLabel lblTitulo = new JLabel("Registro - Datos extras");
	        lblTitulo.setFont(new Font("Times", Font.PLAIN, 17));
	        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
	        panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        panelContenedorSuperior.add(lblTitulo);

	        // Panel inferior
	        JButton lblBotonRegistro = new JButton("Siguiente");
	        lblBotonRegistro.setBackground(new Color(144, 224, 239));
	        panelContenedorInferior.add(lblBotonRegistro);

	        // Panel central - Sub paneles
	        panelContenedorCentral.setLayout(new BoxLayout(panelContenedorCentral, BoxLayout.Y_AXIS));
	        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
	        panelContenedorCentral.setBorder(emptyBorder);

	        String[] informacionPersonal = {
	            "NSS","Alergias conocidas","Contacto de emergencia",
	        };

	        for (String info : informacionPersonal) {
	            JLabel lbl = new JLabel(info);
	            panelContenedorCentral.add(lbl);
	            JTextField txtField = new JTextField(10);
	            panelContenedorCentral.add(txtField);
	        }
	        
	        JLabel lblTurno = new JLabel("Tipo de sangre");
			panelContenedorCentral.add(lblTurno);
			String[] opcionesSangre = {"O-","O+","B-","B+","A-","A+","AB+","AB-"};
			JComboBox<String> tipoSangre = new JComboBox<String>(opcionesSangre);
			tipoSangre.setSelectedIndex(2);
			panelContenedorCentral.add(tipoSangre);
			tipoSangre.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
			
	
	        
	        JLabel lblDatosBancarios = new JLabel("Datos bancarios");
	        lblDatosBancarios.setFont(new Font("Arial",Font.BOLD,15));
	        panelContenedorCentral.add(lblDatosBancarios);
	        
	        String[] datosBancarios = {
	        		"Banco", "Numero de cuenta / CLABE interbancaria", "Sueldo"
		        };

		        for (String info : datosBancarios) {
		            JLabel lbl = new JLabel(info);
		            panelContenedorCentral.add(lbl);
		            JTextField txtField = new JTextField(10);
		            panelContenedorCentral.add(txtField);
		        }
	     
	        // Scroll
	        JScrollPane scroll = new JScrollPane(panelContenedorCentral);
	        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	        // Añadiendo paneles
	        add(scroll, BorderLayout.CENTER);
	        add(panelContenedorSuperior, BorderLayout.NORTH);
	        add(panelContenedorInferior, BorderLayout.SOUTH);
	    }
}
