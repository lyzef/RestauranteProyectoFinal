package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import models.User;
import repository.UserRepository;
import utilidades.PanelTipoPreguntaUtil;
import utilidades.ValidadorCadena;
import views.FormularioView;
import views.Login;

public class FormularioController {
	private FormularioView formularioRegistro;
	private UserRepository repositorio;
	private String parteFormularioActual;	
	
	/**
	 * Controlador de las clases dentro del paquete formulario
	 * @param formulario Primera parte del formulario
	 */
	public FormularioController(FormularioView formularioRegistro) {
		this.formularioRegistro = formularioRegistro;
		this.repositorio = new UserRepository();
		
		//Conectando paneles pregunta a su controlador
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte1());
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte2());
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte3());
		
		//Iniciando vista formulario parte 1
		parteFormularioActual = FormularioView.FORMPARTE1;
		formularioRegistro.showView(parteFormularioActual);
		
		
		//Boton siguiente listener
		formularioRegistro.getBotonSiguiente().addActionListener(e -> {
			controlFlujoFormulario();
			
		});
		
		//Cerrar formulario
		formularioRegistro.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioRegistro.confirmacionSalidaPanel();
		        
		        if (verif == JOptionPane.YES_OPTION) {
		        	new LoginController( new Login());
		            formularioRegistro.dispose();
		        }
		        
		    }
		});		
		
	}
	
	/**
	 * Controla el flujo del formulario, cambia de ventana si la actual ya esta completa y validada
	 * termina el formulario si ya esta completo
	 */
	public void controlFlujoFormulario() {
		switch (parteFormularioActual) {
		case FormularioView.FORMPARTE1:{
			if(validarFormularioParte1()) {
				parteFormularioActual = formularioRegistro.FORMPARTE2;
				formularioRegistro.showView(parteFormularioActual);
			}
			break;
		}
		case FormularioView.FORMPARTE2:{
			if(validarFormularioParte2()) {
				parteFormularioActual = formularioRegistro.FORMPARTE3;
				formularioRegistro.showView(parteFormularioActual);
			}
			break;
		}
		case FormularioView.FORMPARTE3:{
			if(validarFormularioParte3()) {
				formularioRegistro.mensajeConfirmacionFormularioCompleto();
				guardarUsuario();
				new LoginController(new Login());
				formularioRegistro.dispose();
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parteFormularioActual);
		}
	}
	
	public void conectarPreguntasAsuControlador(List <PanelTipoPreguntaUtil> preguntas ) {
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
					formularioRegistro.getNombre().obtenerTextoEntrada(),
					formularioRegistro.getFechaNacimiento().obtenerTextoEntrada(),
					formularioRegistro.getCurp().obtenerTextoEntrada(),
					formularioRegistro.getTelefono().obtenerTextoEntrada(),
					formularioRegistro.getCorreo().obtenerTextoEntrada(),
					(String) formularioRegistro.getEstadoCivil().getSelectedItem(),
					(String) formularioRegistro.getGeneros().getSelectedItem(),
					formularioRegistro.getPuestoActual().obtenerTextoEntrada(),
					formularioRegistro.getDescripcionFunciones().obtenerTextoEntrada(),
					formularioRegistro.getPerfilPuesto().obtenerTextoEntrada(),
					formularioRegistro.getCondicionesLaborales().obtenerTextoEntrada(),
					formularioRegistro.getUbicacionOrganizacional().obtenerTextoEntrada(),
					formularioRegistro.getTipoContrato().obtenerTextoEntrada(),
					formularioRegistro.getRadioTurno().getSelection().getActionCommand(),
					formularioRegistro.getNSS().obtenerTextoEntrada(),
					formularioRegistro.getAlergiasConocidas().obtenerTextoEntrada(),
					formularioRegistro.getContactoEmergencia().obtenerTextoEntrada(),
			        (String) formularioRegistro.getTipoSangre().getSelectedItem().toString(), // Extracción del JComboBox
			        formularioRegistro.getBanco().obtenerTextoEntrada(),
			        formularioRegistro.getNumeroCuenta().obtenerTextoEntrada(),
			        formularioRegistro.getSueldo().obtenerTextoEntrada()
					));
			formularioRegistro.mensajeConfirmacionFormularioCompleto();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(formularioRegistro, e.getMessage());
		}
	}
	
	/**
	 * Valida la primera parte del formulario
	 */
	public boolean validarFormularioParte1() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		for(PanelTipoPreguntaUtil pregunta: formularioRegistro.getListaPreguntasParte1()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(formularioRegistro.getEstadoCivil().getSelectedItem() == "Seleccionar" || formularioRegistro.getGeneros().getSelectedItem() == "Seleccionar" ) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formularioRegistro.getListaPreguntasParte1()) {
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
		for(PanelTipoPreguntaUtil pregunta: formularioRegistro.getListaPreguntasParte2()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba raddio button
		if(formularioRegistro.getRadioTurno().getSelection() == null) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formularioRegistro.getListaPreguntasParte2()) {
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
		for(PanelTipoPreguntaUtil pregunta: formularioRegistro.getListaPreguntasParte3()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(formularioRegistro.getTipoSangre().getSelectedIndex() == 0) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formularioRegistro.getListaPreguntasParte3()) {
			try {
				ValidadorCadena.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}
}
