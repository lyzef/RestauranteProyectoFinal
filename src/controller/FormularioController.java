package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



import models.User;
import repository.UserRepository;
import utilidades.PanelTipoPreguntaUtil;
import utilidades.ValidadorCadena;
import views.Login;
import views.formulario.FormularioRegistroParte1;
import views.formulario.FormularioRegistroParte3;
import views.formulario.FormularioRegistroParte2;

public class FormularioController {
	private FormularioRegistroParte1 formularioEntrada;
	private FormularioRegistroParte3 formularioDatos;
	private FormularioRegistroParte2 formularioInformacion;
	private UserRepository repositorio;
	
	/**
	 * Controlador de las clases dentro del paquete formulario
	 * @param formulario Primera parte del formulario
	 */
	public FormularioController(FormularioRegistroParte1 formulario) {
		this.formularioEntrada = formulario;
		this.repositorio = new UserRepository();
		controladorFormularioParte1();
	}
	
	/**
	 * Controla la primera parte del formulario 
	 */
	public void controladorFormularioParte1() {
		//Comprobacion datos primera parte
		formularioEntrada.getLblBotonRegistro().addActionListener(e -> {
			if(validarFormularioParte1()) {
				controladorFormularioParte2();
				formularioEntrada.setVisible(false);
			} else {
				// TODO anadir cambio visual en formulario parte 1
			}
			
		});
		
		formularioEntrada.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioEntrada.confirmacionSalidaPanel();
		        
		        if (verif == JOptionPane.YES_OPTION) {
		        	new LoginController( new Login());
		            cerrarFormulario();
		        }
		        
		    }
		});
		
		conectarPreguntasAControlador(formularioEntrada.getListaPreguntas());
	}
	
	/**
	 * Controla la segunda parte del formulario agregando
	 */
	public void controladorFormularioParte2() {
		formularioInformacion = new FormularioRegistroParte2();
		formularioInformacion.getBotonSiguiente().addActionListener(e -> {
			if(validarFormularioParte2()) {
				controladorFormularioParte3();
				formularioInformacion.setVisible(false);
			} else {
				// TODO anadir cambio visual en formulario parte 2
			}
			
		});
		
		formularioInformacion.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioInformacion.confirmacionSalidaPanel();
		      
		        if (verif == JOptionPane.YES_OPTION) {
		        	new LoginController( new Login());
		            cerrarFormulario();
		        }
		        
		    }
		});
		
		conectarPreguntasAControlador(formularioInformacion.getListaPreguntas());
	}
	
	/**
	 * Controla la tercera parte del formulario agregado
	 * Finaliza el formulario y regresa a login
	 */
	public void controladorFormularioParte3() {
		formularioDatos = new FormularioRegistroParte3();
		formularioDatos.getBotonFinalizar().addActionListener(e -> {
			if(validarFormularioParte3()) {
				guardarUsuario();
				
				formularioDatos.dispose();
				formularioInformacion.dispose();
				formularioEntrada.dispose();
				new LoginController( new Login());
			} else {
				// TODO anadir cambio visual en formulario parte 3
			}
			
		});
		
		formularioDatos.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioDatos.confirmacionSalidaPanel();
		        
		        if (verif == JOptionPane.YES_OPTION) {
		            new LoginController( new Login());
		            cerrarFormulario();		           
		        }
		        
		    }
		});
		
		conectarPreguntasAControlador(formularioDatos.getListaPreguntas());
		
	}
	
	/**
	 * Detecta las interfazes de formularios abiertos y los cierra
	 */
	public void cerrarFormulario() {
		if(formularioDatos != null) {
			formularioDatos.dispose();
		}
		if(formularioEntrada != null) {
			formularioEntrada.dispose();
		}
		if(formularioInformacion != null) {
			formularioInformacion.dispose();
		}
	}
	
	public void conectarPreguntasAControlador(List <PanelTipoPreguntaUtil> preguntas ) {
		for(PanelTipoPreguntaUtil p : preguntas) {
			PreguntaController.registrarPanel(p);
		}
	}
	
	/**
	 * Guarda todos las entradas del formulario a clase usuario
	 */
	public void  guardarUsuario() {
		
		try {
			repositorio.save(new User(
					formularioEntrada.getNombre().obtenerTextoEntrada(),
					formularioEntrada.getFechaNacimiento().obtenerTextoEntrada(),
					formularioEntrada.getCurp().obtenerTextoEntrada(),
					formularioEntrada.getTelefono().obtenerTextoEntrada(),
					formularioEntrada.getCorreo().obtenerTextoEntrada(),
					(String) formularioEntrada.getEstadoCivil().getSelectedItem(),
					(String) formularioEntrada.getGeneros().getSelectedItem(),
					formularioInformacion.getPuestoActual().obtenerTextoEntrada(),
					formularioInformacion.getDescripcionFunciones().obtenerTextoEntrada(),
					formularioInformacion.getPerfilPuesto().obtenerTextoEntrada(),
					formularioInformacion.getCondicionesLaborales().obtenerTextoEntrada(),
					formularioInformacion.getUbicacionOrganizacional().obtenerTextoEntrada(),
					formularioInformacion.getTipoContrato().obtenerTextoEntrada(),
					formularioInformacion.getRadioTurno().getSelection().getActionCommand(),
					formularioDatos.getNSS().obtenerTextoEntrada(),
					formularioDatos.getAlergiasConocidas().obtenerTextoEntrada(),
					formularioDatos.getContactoEmergencia().obtenerTextoEntrada(),
			        (String) formularioDatos.getTipoSangre().getSelectedItem().toString(), // Extracción del JComboBox
			        formularioDatos.getBanco().obtenerTextoEntrada(),
			        formularioDatos.getNumeroCuenta().obtenerTextoEntrada(),
			        formularioDatos.getSueldo().obtenerTextoEntrada()
					));
			formularioDatos.mensajeConfirmacionFormularioCompleto();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(formularioDatos, e.getMessage());
		}
	}
	
	/**
	 * Valida la primera parte del formulario
	 */
	public boolean validarFormularioParte1() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		for(PanelTipoPreguntaUtil pregunta: formularioEntrada.getListaPreguntas()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(formularioEntrada.getEstadoCivil().getSelectedItem() == "Seleccionar" || formularioEntrada.getGeneros().getSelectedItem() == "Seleccionar" ) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formularioEntrada.getListaPreguntas()) {
			try {
				ValidadorCadena.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}
	
	public boolean validarFormularioParte2() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		//Preguntas vacias
		for(PanelTipoPreguntaUtil pregunta: formularioInformacion.getListaPreguntas()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba raddio button
		if(formularioInformacion.getRadioTurno().getSelection() == null) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formularioInformacion.getListaPreguntas()) {
			try {
				ValidadorCadena.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}
	
	public boolean validarFormularioParte3() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		//Preguntas vacias
		for(PanelTipoPreguntaUtil pregunta: formularioDatos.getListaPreguntas()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(formularioDatos.getTipoSangre().getSelectedIndex() == 0) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formularioDatos.getListaPreguntas()) {
			try {
				ValidadorCadena.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}
}
