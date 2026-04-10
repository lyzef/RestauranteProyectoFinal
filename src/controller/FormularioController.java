package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	
	/**
	 * Controlador de las clases dentro del paquete formulario
	 * @param formulario Primera parte del formulario
	 */
	public FormularioController(FormularioRegistroParte1 formulario) {
		this.formularioEntrada = formulario;
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
				formularioEntrada.dispose();
			} else {
				// TODO anadir cambio visual en formulario parte 1
			}
			
		});
		
		formularioEntrada.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioEntrada.confirmacionSalidaPanel();
		        
		        if (verif == JOptionPane.YES_OPTION) {
		            new Login();
		            formularioEntrada.dispose();
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
				formularioInformacion.dispose();
			} else {
				// TODO anadir cambio visual en formulario parte 2
			}
			
		});
		
		formularioInformacion.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioInformacion.confirmacionSalidaPanel();
		      
		        if (verif == JOptionPane.YES_OPTION) {
		            new Login();
		            formularioInformacion.dispose();
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
				formularioDatos.mensajeConfirmacionFormularioCompleto();
				formularioDatos.dispose();
				new Login();
			} else {
				// TODO anadir cambio visual en formulario parte 3
			}
			
		});
		
		formularioDatos.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int verif = formularioDatos.confirmacionSalidaPanel();
		        
		        if (verif == JOptionPane.YES_OPTION) {
		            new Login();
		            formularioDatos.dispose();
		        }
		        
		    }
		});
		
		conectarPreguntasAControlador(formularioDatos.getListaPreguntas());
		
	}
	
	public void conectarPreguntasAControlador(List <PanelTipoPreguntaUtil> preguntas ) {
		for(PanelTipoPreguntaUtil p : preguntas) {
			PreguntaController.registrarPanel(p);
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
