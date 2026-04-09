package views.formulario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import utilidades.PanelTipoPreguntaUtil;

public class FormularioRegistroInformacionPuesto extends JFrame {
	PanelTipoPreguntaUtil puestoActual;
	PanelTipoPreguntaUtil descripcionFunciones;
	PanelTipoPreguntaUtil perfilPuesto;
	PanelTipoPreguntaUtil condicionesLaborales;
	PanelTipoPreguntaUtil ubicacionOrganizacional;
	PanelTipoPreguntaUtil tipoContrato;
	List <PanelTipoPreguntaUtil> listaPreguntas;
	ButtonGroup radioTurno;
	
    public FormularioRegistroInformacionPuesto() {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Formulario");
        setLocationRelativeTo(null);

        // Ícono de la ventana (asegúrate de que la ruta apunte a un archivo de imagen real)
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/image/icono.jpg");
        setIconImage(icono);

        InicializarComponentes();

        setVisible(true);
    }

    public void InicializarComponentes() {
        // Paneles
        JPanel panelContenedorSuperior = new JPanel();
        JPanel panelContenedorInferior = new JPanel();
        

        // Panel superior
        JLabel lblTitulo = new JLabel("Registro - Informacion del puesto");
        lblTitulo.setFont(new Font("Times", Font.PLAIN, 17));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenedorSuperior.add(lblTitulo);

        // Panel inferior
        JButton lblBotonRegistro = new JButton("Siguiente");
        lblBotonRegistro.setBackground(new Color(144, 224, 239));
        lblBotonRegistro.addActionListener( e -> {
        	validarFormulario();
        	});
        
        
        panelContenedorInferior.add(lblBotonRegistro);

        // Scroll
        JScrollPane scroll = new JScrollPane(panelPreguntas());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Añadiendo paneles
        add(scroll, BorderLayout.CENTER);
        add(panelContenedorSuperior, BorderLayout.NORTH);
        add(panelContenedorInferior, BorderLayout.SOUTH);
    }
    
    private JPanel panelPreguntas() {
    	JPanel panelContenedorCentral = new JPanel();
    	
    	panelContenedorCentral.setLayout(new BoxLayout(panelContenedorCentral, BoxLayout.Y_AXIS));
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        panelContenedorCentral.setBorder(emptyBorder);

        puestoActual = new PanelTipoPreguntaUtil("Puesto actual:", "ALFANUMERICO");
    	descripcionFunciones = new PanelTipoPreguntaUtil("Funciones en la empresa", "ALFANUMERICO");
    	perfilPuesto = new PanelTipoPreguntaUtil("Perfil de puesto: ", "ALFANUMERICO");
    	condicionesLaborales = new PanelTipoPreguntaUtil("Condiciones laborales: ", "ALFANUMERICO");
    	ubicacionOrganizacional = new PanelTipoPreguntaUtil("Ubicacion organizacional", "ALFANUMERICO");
    	tipoContrato = new PanelTipoPreguntaUtil("Tipo de contrato: ", "ALFANUMERICO");
        
    	listaPreguntas = new ArrayList<>();
    	listaPreguntas.add(puestoActual);
    	listaPreguntas.add(descripcionFunciones);
    	listaPreguntas.add(perfilPuesto);
    	listaPreguntas.add(condicionesLaborales);
    	listaPreguntas.add(ubicacionOrganizacional);
    	listaPreguntas.add(tipoContrato);
    	
    	for(PanelTipoPreguntaUtil pregunta : listaPreguntas) {
    		panelContenedorCentral.add(pregunta);
		}
    	
        JLabel lblTurno = new JLabel("Turno");
        panelContenedorCentral.add(lblTurno);
        radioTurno = new ButtonGroup();
        JRadioButton rbMatutino = new JRadioButton("Matutino"); panelContenedorCentral.add(rbMatutino);
        JRadioButton rbVespertino = new JRadioButton("Vespertino"); panelContenedorCentral.add(rbVespertino);
        JRadioButton rbMixto = new JRadioButton("Mixto"); panelContenedorCentral.add(rbMixto);
        radioTurno.add(rbMatutino);radioTurno.add(rbVespertino);radioTurno.add(rbMixto);
     
        	
    	return panelContenedorCentral;
    }
    
    public void validarFormulario() {
		//Comprueba preguntas sin responder
		boolean faltaRellenar = false;
		for(PanelTipoPreguntaUtil pregunta: listaPreguntas) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				faltaRellenar = true;
			}
		}
		if(faltaRellenar) {return;}
		//Comprueba checkbox
		if(radioTurno.getSelection() == null) {
			return;
		}
		
		//Comprueba contenidos invalidos
		for(PanelTipoPreguntaUtil pregunta: listaPreguntas) {
			if(!pregunta.validarContenido()) {
				return;
			}
		}
		
		new FormularioRegistroDatosExtras();
    	this.dispose();
		
	}
}