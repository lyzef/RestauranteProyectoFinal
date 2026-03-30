package views.formulario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;


public class FormularioRegistroDatosExtras extends JFrame{
	panelPregunta NSS;
	panelPregunta alergiasConocidas;
	panelPregunta contactoEmergencia;
	panelPregunta banco;
	panelPregunta numeroCuenta;
	panelPregunta sueldo;
	
	
	List <panelPregunta> listaPreguntas;
	
	JComboBox<String> tipoSangre;
	
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
	        lblBotonRegistro.addActionListener( e -> {
	        	validarFormulario();
	        	});
	        // Panel central - Sub paneles
	        
	        // Scroll
	        JScrollPane scroll = new JScrollPane(panelpreguntas());
	        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	        // Añadiendo paneles
	        add(scroll, BorderLayout.CENTER);
	        add(panelContenedorSuperior, BorderLayout.NORTH);
	        add(panelContenedorInferior, BorderLayout.SOUTH);
	    }
	    
	    private JPanel panelpreguntas() {
	    	 JPanel panelContenedorCentral = new JPanel();
	    	 panelContenedorCentral.setLayout(new BoxLayout(panelContenedorCentral, BoxLayout.Y_AXIS));
		        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		        panelContenedorCentral.setBorder(emptyBorder);
		        
		        listaPreguntas = new ArrayList<panelPregunta>();
		        NSS = new panelPregunta("Numero seguro social: ", "ALFANUMERICO");
		        alergiasConocidas = new panelPregunta("Alergias: ", "ALFANUMERICO");		        
		        contactoEmergencia = new panelPregunta("Contacto emergencia", "ALFANUMERICO");
		        
		        panelContenedorCentral.add(NSS);
		        panelContenedorCentral.add(alergiasConocidas);
		        panelContenedorCentral.add(contactoEmergencia);
		        
		        listaPreguntas.add(NSS);
		        listaPreguntas.add(alergiasConocidas);
		        listaPreguntas.add(contactoEmergencia);
		        
		        JLabel lblTurno = new JLabel("Tipo de sangre");
				panelContenedorCentral.add(lblTurno);
				String[] opcionesSangre = {"Seleccionar","O-","O+","B-","B+","A-","A+","AB+","AB-"};
				tipoSangre = new JComboBox<String>(opcionesSangre);
				tipoSangre.setSelectedIndex(0);
				panelContenedorCentral.add(tipoSangre);
				tipoSangre.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				
		
		        
		        JLabel lblDatosBancarios = new JLabel("Datos bancarios");
		        lblDatosBancarios.setFont(new Font("Arial",Font.BOLD,15));
		        panelContenedorCentral.add(lblDatosBancarios);
		        
		        banco = new panelPregunta("Banco: ", "ALFANUMERICO");
		        numeroCuenta = new panelPregunta("Clabe o numero de cuenta: ", "NUMERICO");		        
		        sueldo = new panelPregunta("Sueldo", "NUMERICO");
		        
		        panelContenedorCentral.add(banco);
		        panelContenedorCentral.add(numeroCuenta);
		        panelContenedorCentral.add(sueldo);
		        
		        listaPreguntas.add(banco);
		        listaPreguntas.add(numeroCuenta);
		        listaPreguntas.add(sueldo);
		        
	    	return panelContenedorCentral;
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
			//Comprueba checkbox
			if(tipoSangre.getSelectedIndex() == 0) {
				return;
			}
			
			//Comprueba contenidos invalidos
			for(panelPregunta pregunta: listaPreguntas) {
				if(!pregunta.validarContenido()) {
					return;
				}
			}
			
			JOptionPane.showMessageDialog(null,
					"Error",
					"No sabemos que salio mal",
					JOptionPane.WARNING_MESSAGE);
        	this.dispose();
        	System.exit(0);
			
		}
}
